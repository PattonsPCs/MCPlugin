package io.papermc.testplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


import java.util.Objects;
import java.util.Random;


public class EventListener implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player newPlayer = event.getPlayer();
        Bukkit.getServer().broadcastMessage("Hi there " + newPlayer.getName() + "! How are you?");
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent changedWorldEvent){
        Player player = changedWorldEvent.getPlayer();
        String fromWorld = changedWorldEvent.getFrom().getName();
        String toWorld = player.getWorld().getName();
        Bukkit.getServer().broadcastMessage(player.getName() + " has traveled from " + fromWorld + " to " + toWorld + "!");

    }

    @EventHandler
    public void annoyingListener(EntityPickupItemEvent annoyingEvent){
         Entity entity = annoyingEvent.getEntity();
         if(entity instanceof Player player){
             if(annoyingEvent.getItem().getItemStack().getType() == Material.DIAMOND){
                 annoyingEvent.setCancelled(true);
                 player.getWorld().dropItemNaturally(player.getLocation(), annoyingEvent.getItem().getItemStack());

                 ItemStack itemStack = new ItemStack(Material.DIRT, 64);
                 PlayerInventory inventory = player.getInventory();

                 for(int i = 0; i < inventory.getSize(); i++){
                     inventory.setItem(i, itemStack);
                 }

             }
         }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent dmgEvent){
        LivingEntity mob = dmgEvent.getEntity();
        if(dmgEvent.getEntity().getKiller() instanceof Player killer){
            if(dmgEvent.getEntity() instanceof Monster victim){
                Bukkit.getServer().broadcastMessage(killer.getName() + " has killed " + victim.getName() + "!");
            }
        }
        if(mob.hasMetadata("mobLevel")){
            int level = mob.getMetadata("mobLevel").get(0).asInt();
            int baseExp = 50;
            int expReward = baseExp + level * 10;

            dmgEvent.setDroppedExp(expReward);
        }
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event){
        if(event.getEntity() instanceof Monster monster){
            // Starting stuff
            LivingEntity mob = (LivingEntity) event.getEntity();
            Location location = mob.getLocation();
            String biome = location.getBlock().getBiome().toString();

            // Level logic for the mobs
            int baseLevel = getBaseLevelFromBiome(biome);
            int randomness = random.nextInt(5);
            int mobLevel = baseLevel + randomness;

            // Use PersistentDataContainer to store the level
            PersistentDataContainer data = mob.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(new ExamplePlugin(), "mobLevel");
            data.set(key, PersistentDataType.INTEGER, mobLevel);
            // Set up the mobs
            mob.setCustomName("Lvl " + mobLevel + "  " + mob.getType().name());
            mob.setCustomNameVisible(true);

            // Scaling stats
            statScaling(mob, mobLevel);
        }
    }

    private int getBaseLevelFromBiome(String inBiome){
        return switch (inBiome) {
            case "Nether_Wastes", "Soul_Sand_Valley" -> 20;
            case "Plains", "Desert" -> 5;
            default -> 10;
        };
    }

    private void statScaling(LivingEntity mob, int level){
        double health = mob.getHealth() + level * 2;
        mob.setHealth(Math.min(health, mob.getMaxHealth()));

        Objects.requireNonNull(mob.getAttribute(Attribute.ATTACK_DAMAGE))
                .setBaseValue(level * 1.5);
    }

    private int getMobLevel(LivingEntity mob){
        PersistentDataContainer data = mob.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(new ExamplePlugin(), "mobLevel");
        return data.has(key,PersistentDataType.INTEGER) ? data.get(key, PersistentDataType.INTEGER) : 1;
    }
}
