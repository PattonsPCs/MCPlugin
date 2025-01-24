package io.papermc.testplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class ExamplePlugin extends JavaPlugin{
    @Override
    public void onEnable() {
        this.getCommand("greet").setExecutor(new GreetCommand());
        this.getCommand("farewell").setExecutor(new FarewellCommand());
        this.getServer().getPluginManager().registerEvents(new EventListener() , this);
        this.getServer().getPluginManager().registerEvents(new Leaderboard(), this);
    }
}
