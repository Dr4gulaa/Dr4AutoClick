package com.Dr4gula.Dr4AutoClick;

import com.Dr4gula.Dr4AutoClick.commands.autoclickcommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getCommand("autoclick").setExecutor(new autoclickcommand(this));
        getLogger().info("AutoClicker ativado!");
    }

    @Override
    public void onDisable() {
        getLogger().info("AutoClicker desativado!");
    }
}
