package com.normallynormal.hardcorelimbo;

import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public interface GameRendererExt {
    void loadShader(Identifier identifier);
}
