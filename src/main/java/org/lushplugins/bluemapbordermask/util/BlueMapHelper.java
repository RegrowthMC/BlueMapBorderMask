package org.lushplugins.bluemapbordermask.util;

import de.bluecolored.bluemap.api.BlueMapAPI;
import de.bluecolored.bluemap.common.api.BlueMapAPIImpl;
import de.bluecolored.bluemap.common.rendermanager.MapUpdatePreparationTask;
import de.bluecolored.bluemap.common.rendermanager.RenderManager;
import de.bluecolored.bluemap.common.rendermanager.TileUpdateStrategy;
import de.bluecolored.bluemap.core.map.BmMap;

public class BlueMapHelper {

    public static void scheduledRenderTask(String mapName) {
        BlueMapAPI.getInstance().ifPresent(api -> {
            BlueMapAPIImpl apiImpl = (BlueMapAPIImpl) api;

            RenderManager renderManager = apiImpl.plugin().getRenderManager();
            BmMap map = apiImpl.blueMapService().getMaps().get(mapName);

            renderManager.scheduleRenderTask(MapUpdatePreparationTask.updateMap(
                map,
                TileUpdateStrategy.FORCE_EDGE,
                renderManager
            ));
        });
    }
}
