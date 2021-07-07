package toughasnails.temperature.modifier;

import com.stargatemc.constants.NpcRace;
import com.stargatemc.data.PlayerData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import toughasnails.api.temperature.IModifierMonitor;
import toughasnails.api.temperature.Temperature;
import toughasnails.init.ModConfig;
import zmaster587.advancedRocketry.dimension.DimensionManager;
import zmaster587.advancedRocketry.dimension.DimensionProperties;
import zmaster587.advancedRocketry.dimension.DimensionProperties.AtmosphereTypes;
import zmaster587.advancedRocketry.dimension.DimensionProperties.Temps;

import com.stargatemc.listener.listeners.ARListener;

public class PlayerRaceModifier extends TemperatureModifier
{
    public PlayerRaceModifier(String id)
    {
        super(id);
    }

    @Override
    public Temperature applyPlayerModifiers(EntityPlayer player, Temperature initialTemperature, IModifierMonitor monitor)
    {
        int temperatureLevel = initialTemperature.getRawValue();
        int newTemperatureLevel = temperatureLevel;
        BlockPos playerPos = player.getPosition();
        NpcRace race = PlayerData.getRace(player.getUniqueID());
		DimensionProperties props = DimensionManager.getEffectiveDimId(player.world.provider.getDimension(),playerPos);
		if (props == null) return new Temperature(temperatureLevel);
		
		Temps temp = Temps.getTempFromValue(props.getAverageTemp());		
		AtmosphereTypes atmType = AtmosphereTypes.getAtmosphereTypeFromValue(props.getAtmosphereDensity());
        
        switch (race) {
		case ANUBIS:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case APOPHIS:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case ASCENDED:
			break;
		case ASGARD:
		case ASGARD_RECLAIMATION_FORCE:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.NORMAL)) newTemperatureLevel += 15;
			break;
		case ASURAN:
			if (temp.colderThan(Temps.FRIGID)) newTemperatureLevel -= 5;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 5;
			break;
		case BAAL:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case CRONUS:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case FREE_JAFFA:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case LORD_YU:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case NAKAI:
			if (temp.colderThan(Temps.FRIGID)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.COLD)) newTemperatureLevel += 15;
			break;
		case RA:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case REPLICATOR:
		case REPLICATOR_ADVANCE_FORCE:
			if (temp.colderThan(Temps.FRIGID)) newTemperatureLevel -= 10;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 10;
			break;
		case TOKRA:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 15;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 15;
			break;
		case UNAS:
		case WRAITH:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 25;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 25;
			break;
		default:
			if (temp.colderThan(Temps.COLD)) newTemperatureLevel -= 50;
			if (temp.hotterThan(Temps.HOT)) newTemperatureLevel += 50;
			break;
        	
        }

        monitor.addEntry(new IModifierMonitor.Context(this.getId(), "Race", initialTemperature, new Temperature(newTemperatureLevel)));
        
        return new Temperature(newTemperatureLevel);
    }

    @Override
    public boolean isPlayerSpecific()
    {
        return true;
    }

}
