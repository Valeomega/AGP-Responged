package agp.andwhat5.commands.gyms;

import agp.andwhat5.Utils;
import agp.andwhat5.commands.utils.PlayerOnlyCommand;
import agp.andwhat5.config.structs.GymStruc;
import com.pixelmonmod.pixelmon.client.gui.pokemoneditor.ImportExportConverter;
import com.pixelmonmod.pixelmon.comm.PixelmonData;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

public class GiveGymPoke extends PlayerOnlyCommand {
    @Override
    protected CommandResult execute(Player player, CommandContext args) {
        GymStruc gym = args.<GymStruc>getOne("GymName").get();
        if(!gym.pokemon.isEmpty())
        {
            PixelmonData data = new PixelmonData();
            ImportExportConverter.importText(gym.pokemon.get(0), data);
            EntityPixelmon pixelmon = Utils.pixelmonDataToEntityPixelmon(data, (World)player.getWorld());
            PlayerStorage storage = PixelmonStorage.pokeBallManager.getPlayerStorage((EntityPlayerMP)player).get();
            if(pixelmon != null) {
                storage.addToParty(pixelmon);
                player.sendMessage(Utils.toText("&7Successfully gave yourself that pokemon from the pool.", true));
                return CommandResult.success();
            }
            else
            {
                player.sendMessage(Utils.toText("&7That pokemon does not exist.", true));
                return CommandResult.success();
            }
        }
        player.sendMessage(Utils.toText("&7Theres no Pokemon in the pool", true));

        return CommandResult.success();
    }
}