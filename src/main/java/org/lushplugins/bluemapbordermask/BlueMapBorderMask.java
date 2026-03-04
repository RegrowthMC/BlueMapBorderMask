package org.lushplugins.bluemapbordermask;

import de.bluecolored.bluemap.common.config.mask.MaskType;
import de.bluecolored.bluemap.core.util.Key;
import org.lushplugins.bluemapbordermask.listener.WorldBorderListener;
import org.lushplugins.bluemapbordermask.mask.ModifiableBoxMask;
import org.lushplugins.lushlib.plugin.SpigotPlugin;
import org.lushplugins.bluemapbordermask.mask.WorldBorderMaskConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class BlueMapBorderMask extends SpigotPlugin {
    private static BlueMapBorderMask plugin;

    private final Map<String, ModifiableBoxMask> masks = new HashMap<>();

    @Override
    public void onLoad() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        MaskType.REGISTRY.register(new MaskType.Impl(
            Key.parse("regrowth:world_border"),
            WorldBorderMaskConfig.class));

        registerListener(new WorldBorderListener());
    }

    public ModifiableBoxMask getMask(String world) {
        return masks.get(world);
    }

    public void setMask(String world, ModifiableBoxMask mask) {
        masks.put(world, mask);
    }

    public static BlueMapBorderMask getInstance() {
        return plugin;
    }
}
