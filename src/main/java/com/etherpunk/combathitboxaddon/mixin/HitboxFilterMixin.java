package com.etherpunk.combathitboxaddon.mixin;

import com.etherpunk.combathitboxaddon.filter.HitboxFilter;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityRenderDispatcher.class, priority = 500)
public abstract class HitboxFilterMixin {

    @Inject(
        method = "renderHitbox",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void onRenderHitbox(
        MatrixStack matrices,
        VertexConsumer vertices,
        Entity entity,
        float tickDelta,
        float red,
        float green,
        float blue,
        CallbackInfo ci
    ) {
        if (!HitboxFilter.shouldRender(entity)) {
            ci.cancel();
        }
    }
}
