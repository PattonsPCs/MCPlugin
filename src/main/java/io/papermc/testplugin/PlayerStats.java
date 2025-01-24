package io.papermc.testplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.NotNull;


public class PlayerStats implements CommandExecutor, Listener {
    // This is going to be a command
    private int killCounter;
    private int deathCounter;
    public PlayerStats() {
        this.killCounter = 0;
        this.deathCounter = 0;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args){
        if(sender instanceof Player player){
            Component message = Component.text(getStats(player));
            Bukkit.getServer().sendMessage(message);
            return true;
        }
        sender.sendMessage("This command can only be run by a player.");
        return false;
    }

    private String getStats(Player player){
        long playerTime = player.getPlayerTime();
        return "Player Time: " + playerTime + "\nPlayer Kills: " + killCounter + "\n Player Deaths: " + deathCounter;
    }

    @EventHandler
    private void killCount(EntityDeathEvent killEvent){
        Player killer = killEvent.getEntity().getKiller();
        if(killer != null){
            this.killCounter += 1;
        }

    }
    @EventHandler
    private void deathCount(EntityDeathEvent deathEvent){
       if(deathEvent.getEntity() instanceof Player){
           this.deathCounter += 1;
       }

    }
}
