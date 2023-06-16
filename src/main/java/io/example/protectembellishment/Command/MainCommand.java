package io.example.protectembellishment.Command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "설정" -> {
                        OpenProtect openProtect = new OpenProtect();
                        openProtect.onCommand(sender, command, label, args);
                    }
                }
            }
        }
        return false;
    }
}
