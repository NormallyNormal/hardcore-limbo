package com.normallynormal.hardcorelimbo;

import net.minecraft.util.math.Vec3d;

public interface PlayerEntityExt {
    Vec3d getLastDeathLocation();
    Vec3d getSoulRecoveryPoint();
    void setLastDeathLocation(Vec3d pos);
    void setSoulRecoveryPoint(Vec3d pos);
}
