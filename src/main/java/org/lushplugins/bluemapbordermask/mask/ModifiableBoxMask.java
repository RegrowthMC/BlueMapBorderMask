package org.lushplugins.bluemapbordermask.mask;

import de.bluecolored.bluemap.core.map.mask.Mask;
import de.bluecolored.bluemap.core.util.Tristate;
import org.joml.Vector3i;

public class ModifiableBoxMask implements Mask {
    private Vector3i min;
    private Vector3i max;

    public ModifiableBoxMask(Vector3i min, Vector3i max) {
        this.min = min;
        this.max = max;
    }

    public Vector3i min() {
        return min;
    }

    public void min(Vector3i min) {
        this.min = min;
    }

    public Vector3i max() {
        return max;
    }

    public void max(Vector3i max) {
        this.max = max;
    }

    public boolean test(int x, int y, int z) {
        return this.testXZ(x, z) && y >= this.min.y() && y <= this.max.y();
    }

    public boolean testXZ(int x, int z) {
        return x >= this.min.x() && x <= this.max.x() && z >= this.min.z() && z <= this.max.z();
    }

    public Tristate test(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        if (minX >= this.min.x() && maxX <= this.max.x() && minZ >= this.min.z() && maxZ <= this.max.z() && minY >= this.min.y() && maxY <= this.max.y()) {
            return Tristate.TRUE;
        } else {
            return maxX >= this.min.x() && minX <= this.max.x() && maxZ >= this.min.z() && minZ <= this.max.z() && maxY >= this.min.y() && minY <= this.max.y() ? Tristate.UNDEFINED : Tristate.FALSE;
        }
    }

    public boolean isEdge(int minX, int minZ, int maxX, int maxZ) {
        return this.test(minX, this.min.y(), minZ, maxX, this.max.y(), maxZ) == Tristate.UNDEFINED;
    }
}
