package io.example.protectembellishment.Command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
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
            int page = 0; // 초기 페이지를 0으로 설정
            this.inv = Bukkit.createInventory(null, 54, "치장보호 Page " + page);
            for (int i = 0; i < 53; i++) {
                // Get item from config.yml
                ItemStack storedItem = plugin.getConfig().getItemStack("inventory." + page + "." + i + ".item");
                if (storedItem != null) {
                    inv.setItem(i, storedItem);
                }

                ItemStack itemStack = new ItemStack(Material.ARROW);
                inv.setItem(53, itemStack);
            }
            player.openInventory(inv);
        }
        return false;
    }
}
