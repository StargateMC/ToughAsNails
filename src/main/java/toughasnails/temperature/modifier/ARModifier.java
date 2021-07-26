package toughasnails.temperature.modifier;

import com.stargatemc.data.PerWorldData;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import toughasnails.api.temperature.IModifierMonitor;
import toughasnails.api.temperature.Temperature;
import toughasnails.init.ModConfig;
import zmaster587.advancedRocketry.dimension.DimensionManager;
import zmaster587.advancedRocketry.dimension.DimensionProperties;
import zmaster587.advancedRocketry.dimension.DimensionProperties.Temps;

public class ARModifier extends TemperatureModifier {
	public ARModifier(String id) {
		super(id);
	}

	@Override
	public Temperature applyEnvironmentModifiers(World world, BlockPos pos, Temperature initialTemperature,
			IModifierMonitor monitor) {
		int temperatureLevel = initialTemperature.getRawValue();
		int newTemperatureLevel = temperatureLevel;

		if (world.provider.isSurfaceWorld()) {
			DimensionProperties props = DimensionManager.getEffectiveDimId(world.provider.getDimension(), pos);
			if (props != null && !PerWorldData.isProtected(props.getId())) {
				int ideal = Temps.NORMAL.getTemp();
				if (props.getAverageTemp() > ideal) {
					newTemperatureLevel += (props.getAverageTemp() - ideal);
				}
				if (props.getAverageTemp() < ideal) {
					newTemperatureLevel -= (ideal - props.getAverageTemp());
				}
			}
		}

		monitor.addEntry(new IModifierMonitor.Context(this.getId(), "Planet Base Temperature", initialTemperature,
				new Temperature(newTemperatureLevel)));

		return new Temperature(newTemperatureLevel);
	}

	@Override
	public boolean isPlayerSpecific() {
		return false;
	}
}
