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
                // 플레이어가 plugin.yml과 같은 아이템을 드롭한 경우
                event.setCancelled(true);
                player.sendMessage("§c[ItemSavePlugin] §4plugin.yml과 같은 아이템을 드롭할 수 없습니다!");
                return; // 드롭 취소 후 메서드 종료
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
        event.getDrops().clear();
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
        for (int i = 0; i < 54; i++) {
            ItemStack item = plugin.getConfig().getItemStack("protect." + i + ".item");
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }
}

