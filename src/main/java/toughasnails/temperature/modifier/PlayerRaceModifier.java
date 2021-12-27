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

		if (player.world.provider.isSurfaceWorld()) {
			if (props != null) {
				int ideal = Temps.NORMAL.getTemp();
				int difference = Math.abs(ideal - props.getAverageTemp());
				if (difference != 0) {

					switch (race) {
					case Ancient:
						difference /= 5;
						break;
					case Asgard:
						difference /= 2;
						break;
					case Asuran:
						difference /= 14;
						break;
					case Goauld:
						difference /= 6;
						break;
					case Jaffa:
						difference /= 5;
						break;
					case Lucian:
						break;
					case Replicator:
						difference /= 12;
						break;
					case Tokra:
						difference /= 6;
						break;
					case Wraith:
						difference /= 7;
						break;
					default:
						difference /= 4;
						break;

					}
				}
				if (props.getAverageTemp() > ideal) {
					newTemperatureLevel += difference;
				}
				if (props.getAverageTemp() < ideal) {
					newTemperatureLevel -= difference;
				}
			}
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
