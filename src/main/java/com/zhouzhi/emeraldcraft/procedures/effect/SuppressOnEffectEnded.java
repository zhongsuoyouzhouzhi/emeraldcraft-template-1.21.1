package com.zhouzhi.emeraldcraft.procedures.effect;

import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;

public class SuppressOnEffectEnded {
    public static void execute(Entity entity) {
        if (entity == null)
            return;
        if (!entity.level().isClientSide()){
            DamageSource magicDamage = entity.level().damageSources().magic();
            int num = TagChange.getOrCreateComponent(entity, "suppress", 0);
            if (num >= 3) {
                float damage = 0.72f * ((SimpleUse.getEffectLevel(entity, ModMobEffects.SUPPRESS)+7.5f)*2);//12.24,13.68,15.12
                num = TagChange.getOrCreateComponent(entity, "suppress_end", 0);
                TagChange.saveComponent(entity, "suppress_end", num);
                if (num >= 6) {
                    entity.hurt(magicDamage, damage * 0.35f);//25.704,28.728,31.752
                    TagChange.saveComponent(entity, "suppress_end", 0);
                } else {
                    entity.hurt(magicDamage, damage);
                    TagChange.saveComponent(entity, "suppress_end", ++num);
                }
            } else {
                entity.hurt(magicDamage, 0.05f * ((SimpleUse.getEffectLevel(entity, ModMobEffects.SUPPRESS)+8)*9));//4.05,4.5,4.95
            }
        }
    }
}