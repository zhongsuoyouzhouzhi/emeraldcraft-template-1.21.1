package com.zhouzhi.emeraldcraft.procedures.effect;

import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class SuppressOnEffectEnded {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        if (!entity.level().isClientSide()){
            DamageSource magicDamage = entity.level().damageSources().magic();
            entity.hurt(magicDamage, 0.2f * (SimpleUse.getEffectLevel(entity)+1));
        }
    }
}