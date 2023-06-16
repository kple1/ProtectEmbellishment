package io.example.protectembellishment;

import io.example.protectembellishment.Command.MainCommand;
import io.example.protectembellishment.Inventory.OpenInventory;
import io.example.protectembellishment.Listener.DenyDrop;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main plugin;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new OpenInventory(), this);
        Bukkit.getPluginManager().registerEvents(new DenyDrop(), this);
        Bukkit.getPluginCommand("invSave").setExecutor(new MainCommand());
        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getPlugin() {
        return plugin;
    }
}
