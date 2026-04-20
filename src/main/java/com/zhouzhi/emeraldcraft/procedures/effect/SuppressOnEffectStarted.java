package com.zhouzhi.emeraldcraft.procedures.effect;

import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class SuppressOnEffectStarted {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        if (!entity.level().isClientSide()){
            DamageSource magicDamage = entity.level().damageSources().magic();
            int num = TagChange.getOrCreateComponent(entity, "suppress", 0);
            if (num >= 4) {
                entity.hurt(magicDamage, 0.3f * ((SimpleUse.getEffectLevel(entity, ModMobEffects.SUPPRESS)+6)*10));
                TagChange.saveComponent(entity, "suppress", 0);
            } else {
                entity.hurt(magicDamage, 0.2f * ((SimpleUse.getEffectLevel(entity, ModMobEffects.SUPPRESS)+6)*4));
                TagChange.saveComponent(entity, "suppress", ++num);
            }
        }
    }
}
