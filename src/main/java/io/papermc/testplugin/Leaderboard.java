package io.papermc.testplugin;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scoreboard.*;
import org.bukkit.event.EventHandler;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


public class Leaderboard implements Listener {
    public void showLeaderboard(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        // Create an objective
        Objective objective = board.registerNewObjective("Kills:", Criteria.DUMMY,  Component.text("Kill Leaderboard", NamedTextColor.RED));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Display
        player.setScoreboard(board);
    }

    @EventHandler
    private void updateScores(EntityDeathEvent event){
        if(event.getEntity().getKiller() instanceof Player killer && event.getEntity() instanceof Player){
            Scoreboard currentBoard = killer.getScoreboard();
            Objective currentObjective = currentBoard.getObjective("Kills:");
            if(currentObjective != null){
                Score currentScore = currentObjective.getScore(killer.getName());
                currentScore.setScore((currentScore.getScore() + 1));
            }
        }
    }
}


