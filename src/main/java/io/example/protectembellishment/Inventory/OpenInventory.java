package io.example.protectembellishment.Inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static io.example.protectembellishment.Main.plugin;

public class OpenInventory implements Listener {

    private Inventory inv;


    @EventHandler
    public void closeEvent(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        this.inv = Bukkit.createInventory(null, 54, "아이템보호");
        if (event.getView().getTitle().equals("아이템보호")) {
            inv = event.getInventory();
            for (int i = 0; i < 54; i++) {
                ItemStack item = inv.getItem(i);
                if (item != null) {
                    plugin.getConfig().set("protect." + i + ".item", item);
                    plugin.getConfig().set("protect." + i + ".slot", i);
                } else {
                    plugin.getConfig().set("protect." + i + ".item", null);
                    plugin.getConfig().set("protect." + i + ".slot", null);
                }
            }
            player.sendMessage("정보가 저장되었습니다.");
            plugin.saveConfig();
        }
    }
}
