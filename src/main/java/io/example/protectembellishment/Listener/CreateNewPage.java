package io.example.protectembellishment.Listener;

import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static io.example.protectembellishment.Main.plugin;

public class CreateNewPage implements Listener {

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        /* Next Page */
        if (clickedItem != null && clickedItem.getType() == Material.ARROW && event.getRawSlot() == 53) {
            event.setCancelled(true);
            Inventory inventory = event.getInventory();

            int page = Integer.parseInt(event.getView().getTitle().substring(10)) + 1;
            String nextPageName = "치장보호 Page " + page;

            Inventory newInventory = Bukkit.createInventory(null, 54, nextPageName);

            // Save items from the current inventory to the config for the previous page
            saveInventoryToConfig(page - 1, inventory);

            // Load items from the config for the new page
            loadInventoryFromConfig(page, newInventory);

            ItemStack arrowItem = new ItemStack(Material.ARROW);
            newInventory.setItem(53, arrowItem);

            player.openInventory(newInventory);
        }
    }

    private void saveInventoryToConfig(int page, Inventory inventory) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection inventorySection = config.getConfigurationSection("inventory");

        if (inventorySection == null) {
            inventorySection = config.createSection("inventory");
        }

        ConfigurationSection pageSection = inventorySection.getConfigurationSection(String.valueOf(page));
        if (pageSection == null) {
            pageSection = inventorySection.createSection(String.valueOf(page));
        }

        ItemStack[] items = inventory.getContents();
        for (int i = 0; i < items.length; i++) {
            if (i == 53) continue; // Skip slot 53
            ItemStack item = items[i];
            if (item != null && !item.getType().equals(Material.AIR)) {
                pageSection.set(i + ".item", item);
            } else {
                pageSection.set(i + ".item", null);
            }
        }

        plugin.saveConfig();
    }

    private void loadInventoryFromConfig(int page, Inventory inventory) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection inventorySection = config.getConfigurationSection("inventory");

        if (inventorySection != null) {
            ConfigurationSection pageSection = inventorySection.getConfigurationSection(String.valueOf(page));
            if (pageSection != null) {
                for (String slotString : pageSection.getKeys(false)) {
                    int slot = Integer.parseInt(slotString);
                    if (slot == 53) continue; // Skip slot 53
                    ItemStack item = pageSection.getItemStack(slotString + ".item");
                    if (item != null && !item.getType().equals(Material.AIR)) {
                        inventory.setItem(slot, item);
                    }
                }
            }
        }
    }
}
