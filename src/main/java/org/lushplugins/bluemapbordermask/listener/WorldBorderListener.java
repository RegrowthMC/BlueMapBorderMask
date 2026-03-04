package org.lushplugins.bluemapbordermask.listener;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.common.api.BlueMapAPIImpl;
import de.bluecolored.bluemap.common.rendermanager.MapUpdatePreparationTask;
import de.bluecolored.bluemap.common.rendermanager.RenderManager;
import de.bluecolored.bluemap.common.rendermanager.TileUpdateStrategy;
import de.bluecolored.bluemap.core.map.BmMap;
import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.joml.Vector3i;
import org.lushplugins.bluemapbordermask.BlueMapBorderMask;
import org.lushplugins.bluemapbordermask.mask.ModifiableBoxMask;

public class WorldBorderListener implements Listener {

    @EventHandler
    public void onWorldBorder(WorldBorderBoundsChangeEvent event) {
        World world = event.getWorld();
        String worldName = world.getName();
        ModifiableBoxMask mask = BlueMapBorderMask.getInstance().getMask(worldName);
        if (mask == null) {
            return;
        }

        WorldBorder border = event.getWorldBorder();
        Location center = border.getCenter();
        int newRadius = (int) (event.getNewSize() / 2);

        mask.min(new Vector3i(center.getBlockX() - newRadius, mask.min().y(), center.getBlockZ() - newRadius));
        mask.max(new Vector3i(center.getBlockX() + newRadius, mask.max().y(), center.getBlockZ() + newRadius));

        BlueMapAPI.getInstance().ifPresent(api -> {
            BlueMapAPIImpl apiImpl = (BlueMapAPIImpl) api;

            RenderManager renderManager = apiImpl.plugin().getRenderManager();
            BmMap map = apiImpl.blueMapService().getMaps().get(worldName);

            renderManager.scheduleRenderTask(MapUpdatePreparationTask.updateMap(
                map,
                TileUpdateStrategy.FORCE_EDGE,
                renderManager
            ));
        });
    }
}
