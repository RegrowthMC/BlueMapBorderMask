package org.lushplugins.bluemapbordermask.mask;

import de.bluecolored.bluemap.common.config.ConfigurationException;
import de.bluecolored.bluemap.common.config.mask.MaskConfig;
import de.bluecolored.bluemap.core.map.mask.Mask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.joml.Vector3i;
import org.lushplugins.bluemapbordermask.BlueMapBorderMask;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@SuppressWarnings("FieldMayBeFinal")
@ConfigSerializable
public class WorldBorderMaskConfig extends MaskConfig {
    private String world = "world";
    private int minY = Integer.MIN_VALUE;
    private int maxY = Integer.MAX_VALUE;

    @Override
    public Mask createMask() throws ConfigurationException {
        if (minY > maxY) {
            throw new ConfigurationException("""
                    The box-mask configuration results in a degenerate mask.
                    Make sure that all "min-" values are actually SMALLER than their "max-" counterparts.
                    """.trim());
        }

        World world = Bukkit.getWorld(this.world);
        WorldBorder border = world.getWorldBorder();
        Location center = border.getCenter();
        int radius = (int) (border.getSize() / 2);

        ModifiableBoxMask mask = new ModifiableBoxMask(
            new Vector3i(center.getBlockX() - radius, this.minY, center.getBlockZ() - radius),
            new Vector3i(center.getBlockX() + radius, this.maxY, center.getBlockZ() + radius)
        );

        BlueMapBorderMask.getInstance().setMask(this.world, mask);
        return mask;
    }

    public int getMinY() {
        return minY;
    }

    public int getMaxY() {
        return maxY;
    }
}
