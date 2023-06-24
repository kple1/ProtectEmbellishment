package io.example.protectembellishment.Command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length >= 1) {
                if (args[0].equals("설정")) {
                    OpenProtect openProtect = new OpenProtect();
                    openProtect.onCommand(sender, command, label, args);
                }
            }
        }
        return false;
    }
}
