package io.papermc.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.event.EventHandler;



public class Leaderboard implements Listener {
    public void showLeaderboard(Player player){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();

        // Create an objective
        Objective objective = board.registerNewObjective("kills", "dummy", ChatColor.RED + "Kill Leaderboard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Display
        player.setScoreboard(board);
    }

    @EventHandler
    private void updateScores(EntityDeathEvent event){
        if(event.getEntity().getKiller() instanceof Player killer && event.getEntity() instanceof Player victim ){
            Scoreboard currentBoard = killer.getScoreboard();
            Objective currentObjective = currentBoard.getObjective("kills");
            Score currentScore = currentObjective.getScore(ChatColor.RED + event.getEntity().getKiller().getName());
            currentScore.setScore((currentScore.getScore() + 1));
        }
    }
}


