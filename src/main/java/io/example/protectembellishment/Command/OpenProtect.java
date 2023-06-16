package io.example.protectembellishment.Command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import static io.example.protectembellishment.Main.plugin;

public class OpenProtect implements CommandExecutor {

    private Inventory inv;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            Inventory inv = Bukkit.createInventory(null, 54, "아이템보호");
            for (int i = 0; i < 54; i++) {
                ItemStack item = plugin.getConfig().getItemStack("protect." + i + ".item");
                if (item != null) {
                    inv.setItem(i, item);
                }
            }
            player.openInventory(inv);
        }
        return true;
    }
}
