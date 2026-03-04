package org.lushplugins.bluemapbordermask;

import org.bukkit.plugin.java.JavaPlugin;

public final class BlueMapBorderMask extends JavaPlugin {
    private static BlueMapBorderMask plugin;

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        // Enable implementation
    }

    @Override
    public void onDisable() {
        // Disable implementation
    }

    public static BlueMapBorderMask getInstance() {
        return plugin;
    }
}
