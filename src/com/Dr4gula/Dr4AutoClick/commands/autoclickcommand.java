package com.Dr4gula.Dr4AutoClick.commands;

import com.Dr4gula.Dr4AutoClick.tasks.AutoClickTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import com.Dr4gula.Dr4AutoClick.Main;

import java.util.HashMap;
import java.util.UUID;

public class autoclickcommand implements CommandExecutor {

    private final Main plugin;
    private final HashMap<UUID, BukkitTask> activeClickers = new HashMap<>();

    public autoclickcommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Apenas jogadores podem usar este comando!");
            return true;
        }

        Player player = (Player) sender;
        UUID playerId = player.getUniqueId();

        if (activeClickers.containsKey(playerId)) {
            activeClickers.get(playerId).cancel();
            activeClickers.remove(playerId);
            player.sendMessage(ChatColor.YELLOW + "Autoclick desativado!");
        } else {
            BukkitTask task = new AutoClickTask(player).runTaskTimer(plugin, 0L, 2L);
            activeClickers.put(playerId, task);
            player.sendMessage(ChatColor.GREEN + "Autoclick ativado!");
        }

        return true;
    }
}
