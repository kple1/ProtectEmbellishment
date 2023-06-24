package io.example.protectembellishment.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.example.protectembellishment.Main.plugin;

public class DenyDrop implements Listener {

    private Map<Player, List<ItemStack>> playerItems = new HashMap<>();

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        ItemStack droppedItem = event.getItemDrop().getItemStack();

        List<ItemStack> pluginItems = getPluginItems();
        for (ItemStack pluginItem : pluginItems) {
            if (droppedItem.isSimilar(pluginItem)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        List<ItemStack> pluginItems = getPluginItems();

        List<ItemStack> keptItems = new ArrayList<>();
        for (ItemStack droppedItem : event.getDrops()) {
            boolean shouldKeep = false;
            for (ItemStack pluginItem : pluginItems) {
                if (droppedItem.isSimilar(pluginItem)) {
                    shouldKeep = true;
                    break;
                }
            }
            if (shouldKeep) {
                keptItems.add(droppedItem);
            }
        }

        playerItems.put(player, keptItems);
        event.getDrops().removeAll(keptItems);
    }


    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        List<ItemStack> keptItems = playerItems.getOrDefault(player, new ArrayList<>());
        playerItems.remove(player);

        for (ItemStack item : keptItems) {
            player.getInventory().addItem(item);
        }
    }

    private List<ItemStack> getPluginItems() {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 54; j++) {
                ItemStack item = plugin.getConfig().getItemStack("inventory." + i + "." + j + ".item");
                if (item != null) {
                    items.add(item);
                }
            }
        }
        return items;
    }
}
