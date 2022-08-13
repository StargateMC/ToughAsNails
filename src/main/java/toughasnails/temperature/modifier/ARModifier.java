package toughasnails.temperature.modifier;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import toughasnails.api.temperature.IModifierMonitor;
import toughasnails.api.temperature.Temperature;
import toughasnails.init.ModConfig;
import zmaster587.advancedRocketry.dimension.DimensionManager;
import zmaster587.advancedRocketry.dimension.DimensionProperties;
import zmaster587.advancedRocketry.dimension.DimensionProperties.Temps;

import java.util.Random;

public class ARModifier extends TemperatureModifier {
	public ARModifier(String id) {
		super(id);
	}

	@Override
	public Temperature applyEnvironmentModifiers(World world, BlockPos pos, Temperature initialTemperature,
			IModifierMonitor monitor) {
		int temperatureLevel = initialTemperature.getRawValue();
		int newTemperatureLevel = temperatureLevel;
		if (world.provider.isSurfaceWorld() && world.provider.getDimension() > -8000) {
			DimensionProperties props = DimensionManager.getEffectiveDimId(world.provider.getDimension(), pos);
			if (props != null) {
				int average = props.getAverageTemp();
				if (props.getId() == DimensionManager.defaultSpaceDimensionProperties.getId()) {
					if (new Random().nextFloat() < 0.5) {
						average = Temps.TOOHOT.getTemp();
					} else {
						average = Temps.SNOWBALL.getTemp();
					}
				}
				int ideal = Temps.NORMAL.getTemp();
				int difference = Math.abs(ideal - props.getAverageTemp());
				if (difference != 0)
					difference /= 4;
				if (props.getAverageTemp() > ideal) {
					newTemperatureLevel += difference;
				}
				if (props.getAverageTemp() < ideal) {
					newTemperatureLevel -= difference;
				}
			}
		}

		monitor.addEntry(new IModifierMonitor.Context(this.getId(), "Base Temperature", initialTemperature,
				new Temperature(newTemperatureLevel)));

		return new Temperature(newTemperatureLevel);
	}

	@Override
	public boolean isPlayerSpecific() {
		return false;
	}
}
