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

public class ARModifier extends TemperatureModifier
{
    public ARModifier(String id)
    {
        super(id);
    }

    @Override
    public Temperature applyEnvironmentModifiers(World world, BlockPos pos, Temperature initialTemperature, IModifierMonitor monitor)
    {
        int temperatureLevel = initialTemperature.getRawValue();
        int newTemperatureLevel = temperatureLevel;

        if (world.provider.isSurfaceWorld())
        {
        	DimensionProperties props = DimensionManager.getEffectiveDimId(world.provider.getDimension(), pos);
        	if (props != null) {
        		if (props.getAverageTemp() > Temps.NORMAL.getTemp()) {
        			newTemperatureLevel += props.getAverageTemp();
        		} else {
        			newTemperatureLevel -= props.getAverageTemp();
        		}
        	}
        }

        monitor.addEntry(new IModifierMonitor.Context(this.getId(), "AvgTemp", initialTemperature, new Temperature(newTemperatureLevel)));
        
        return new Temperature(newTemperatureLevel);
    }

    @Override
    public boolean isPlayerSpecific()
    {
        return false;
    }
}
