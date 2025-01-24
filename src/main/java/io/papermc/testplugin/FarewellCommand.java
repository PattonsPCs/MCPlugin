package io.papermc.testplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FarewellCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args){
        if(sender instanceof Player player){
            player.sendMessage("Goodbye, " + player.getName() + "!");
            return true;
        }
        sender.sendMessage("This command can only be run by a player.");
        return false;
    }

}
