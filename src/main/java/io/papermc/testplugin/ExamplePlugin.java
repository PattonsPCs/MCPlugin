package io.papermc.testplugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ExamplePlugin extends JavaPlugin{
    private static ExamplePlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        Leaderboard leaderboard = new Leaderboard();
        Objects.requireNonNull(this.getCommand("greet")).setExecutor(new GreetCommand());
        Objects.requireNonNull(this.getCommand("farewell")).setExecutor(new FarewellCommand());
        Objects.requireNonNull(this.getCommand("showleaderboard")).setExecutor(new ShowLeaderboardCommand(leaderboard));
        Objects.requireNonNull(this.getCommand("showstats")).setExecutor(new PlayerStats());
        this.getServer().getPluginManager().registerEvents(new EventListener() , this);
        this.getServer().getPluginManager().registerEvents(new Leaderboard(), this);
        getLogger().info("ExamplePlugin has been enabled.");
    }

    @Override
    public void onDisable(){
        getLogger().info("ExamplePlugin has been disabled.");
    }

    public static ExamplePlugin getInstance(){
        return instance;
    }
}
