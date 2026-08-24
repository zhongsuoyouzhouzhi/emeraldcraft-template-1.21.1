package com.zhouzhi.emeraldcraft.potion;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class SuppressMobEffect extends MobEffect {
    public SuppressMobEffect() {
        super(MobEffectCategory.HARMFUL, -14061710);
        this.addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.suppress_0"), -2, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.suppress_1"), -1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.suppress_2"), -0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
        if (!entity.getCommandSenderWorld().isClientSide()){
            DamageSource magicDamage = entity.getCommandSenderWorld().damageSources().magic();
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

    @SubscribeEvent
    public static void registerMobEffectExtensions(RegisterClientExtensionsEvent event) {
        event.registerMobEffect(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(@NotNull MobEffectInstance effect) {
                return false;
            }
        }, ModMobEffects.SUPPRESS.get());
    }
}