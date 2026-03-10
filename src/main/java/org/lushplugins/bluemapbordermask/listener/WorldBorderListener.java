package org.lushplugins.bluemapbordermask.listener;

import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent;
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeFinishEvent;
import io.papermc.paper.event.world.border.WorldBorderCenterChangeEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.joml.Vector3i;
import org.lushplugins.bluemapbordermask.BlueMapBorderMask;
import org.lushplugins.bluemapbordermask.mask.ModifiableBoxMask;
import org.lushplugins.bluemapbordermask.util.BlueMapHelper;

public class WorldBorderListener implements Listener {

    private void updateWorldBorderMask(String worldName, ModifiableBoxMask mask, Location center, int newRadius) {
        mask.min(new Vector3i(center.getBlockX() - newRadius, mask.min().y(), center.getBlockZ() - newRadius));
        mask.max(new Vector3i(center.getBlockX() + newRadius, mask.max().y(), center.getBlockZ() + newRadius));

        BlueMapHelper.scheduledRenderTask(worldName);
    }

    private void updateWorldBorderMask(World world) {
        String worldName = world.getName();
        ModifiableBoxMask mask = BlueMapBorderMask.getInstance().getMask(worldName);
        if (mask == null) {
            return;
        }

        WorldBorder border = world.getWorldBorder();
        Location center = border.getCenter();
        int newRadius = (int) (border.getSize() / 2);

        updateWorldBorderMask(worldName, mask, center, newRadius);
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        updateWorldBorderMask(event.getWorld());
    }

    @EventHandler
    public void onWorldBorderChange(WorldBorderBoundsChangeEvent event) {
        World world = event.getWorld();
        String worldName = world.getName();
        ModifiableBoxMask mask = BlueMapBorderMask.getInstance().getMask(worldName);
        if (mask == null) {
            return;
        }

        WorldBorder border = event.getWorldBorder();
        Location center = border.getCenter();
        int newRadius = (int) (event.getNewSize() / 2);

        updateWorldBorderMask(worldName, mask, center, newRadius);
    }

    @EventHandler
    public void onWorldBorderChangeFinish(WorldBorderBoundsChangeFinishEvent event) {
        updateWorldBorderMask(event.getWorld());
    }

    @EventHandler
    public void onWorldBorderCenterChange(WorldBorderCenterChangeEvent event) {
        updateWorldBorderMask(event.getWorld());
    }
}
