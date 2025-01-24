package io.papermc.testplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShowLeaderboardCommand implements CommandExecutor {
    private final Leaderboard leaderbord;

    public ShowLeaderboardCommand(Leaderboard leaderboard){
        this.leaderbord = leaderboard;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args){
        if(sender instanceof Player player){
            leaderbord.showLeaderboard(player);
            return true;
        }
        sender.sendMessage("This command can only be sent by a player!");
        return false;
    }
}
