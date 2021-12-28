package toughasnails.temperature.modifier;

import com.stargatemc.constants.NpcRace;
import com.stargatemc.constants.PlayerRace;
import com.stargatemc.data.PerWorldData;
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

public class PlayerRaceModifier extends TemperatureModifier {
	public PlayerRaceModifier(String id) {
		super(id);
	}

	@Override
	public Temperature applyPlayerModifiers(EntityPlayer player, Temperature initialTemperature,
			IModifierMonitor monitor) {
		int temperatureLevel = initialTemperature.getRawValue();
		int newTemperatureLevel = temperatureLevel;
		BlockPos playerPos = player.getPosition();
		PlayerRace race = PlayerData.getRace(player.getUniqueID());
		DimensionProperties props = DimensionManager.getEffectiveDimId(player.world.provider.getDimension(), playerPos);
		if (props == null || PerWorldData.isProtected(player.world.provider.getDimension()))
			return new Temperature(temperatureLevel);

		Temps temp = Temps.getTempFromValue(props.getAverageTemp());
		AtmosphereTypes atmType = AtmosphereTypes.getAtmosphereTypeFromValue(props.getAtmosphereDensity());

		switch (race) {
		case Ancient:
			if (temp.colderThan(Temps.COLD))
				newTemperatureLevel -= 25;
			if (temp.hotterThan(Temps.HOT))
				newTemperatureLevel += 25;
			break;
		case Asgard:
			if (temp.colderThan(Temps.COLD))
				newTemperatureLevel -= 25;
			if (temp.hotterThan(Temps.NORMAL))
				newTemperatureLevel += 25;
			break;
		case Asuran:
			if (temp.colderThan(Temps.COLD))
				newTemperatureLevel -= 5;
			if (temp.hotterThan(Temps.HOT))
				newTemperatureLevel += 5;
			break;
		case Goauld:
			if (temp.colderThan(Temps.COLD))
				newTemperatureLevel -= 20;
			if (temp.hotterThan(Temps.HOT))
				newTemperatureLevel += 20;
			break;
		case Jaffa:
			if (temp.colderThan(Temps.COLD))
				newTemperatureLevel -= 35;
			if (temp.hotterThan(Temps.HOT))
				newTemperatureLevel += 35;
			break;
		case Lucian:
			break;
		case Ori:
			break;
		case Replicator:
			if (temp.colderThan(Temps.FRIGID))
				newTemperatureLevel -= 10;
			if (temp.hotterThan(Temps.HOT))
				newTemperatureLevel += 10;
			break;
		case Tauri:
			break;
		case Tokra:
			if (temp.colderThan(Temps.COLD))
				newTemperatureLevel -= 25;
			if (temp.hotterThan(Temps.HOT))
				newTemperatureLevel += 25;
			break;
		case Wraith:
			if (temp.colderThan(Temps.FRIGID))
				newTemperatureLevel -= 25;
			if (temp.hotterThan(Temps.HOT))
				newTemperatureLevel += 25;
			break;
		default:
			if (temp.colderThan(Temps.NORMAL))
				newTemperatureLevel -= 50;
			if (temp.hotterThan(Temps.NORMAL))
				newTemperatureLevel += 50;
			break;

		}

		monitor.addEntry(new IModifierMonitor.Context(this.getId(), "Race", initialTemperature,
				new Temperature(newTemperatureLevel)));

		return new Temperature(newTemperatureLevel);
	}

	@Override
	public boolean isPlayerSpecific() {
		return true;
	}

}
