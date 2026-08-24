package com.zhouzhi.emeraldcraft.potion;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class VoidMobEffect extends MobEffect {
    public VoidMobEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x213F0E);
        this.addAttributeModifier(Attributes.FOLLOW_RANGE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_0"), -0.04, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_1"), 1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_2"), 1.2, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_3"), 0.075, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.FLYING_SPEED, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_4"), 0.5, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_5"), 0.075, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.STEP_HEIGHT, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_6"), 0.5, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.GRAVITY, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_7"), -0.075, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_8"), 0.25, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        this.addAttributeModifier(Attributes.BURNING_TIME, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_9"), -1, AttributeModifier.Operation.ADD_VALUE);
        this.addAttributeModifier(Attributes.SNEAKING_SPEED, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_10"), 0.1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
    }

    @Override
    public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
        if (!(entity instanceof Player _plr && _plr.getAbilities().instabuild)) {
            if (entity instanceof Player _player) {
                _player.getAbilities().mayfly = true;
                _player.onUpdateAbilities();
            }
        }
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity instanceof Player _player) {
            if (!_player.getAbilities().mayfly) {
                _player.getAbilities().mayfly = true;
                _player.onUpdateAbilities();
            }
        }
        return true;
    }

    @SubscribeEvent
    public static void registerMobEffectExtensions(RegisterClientExtensionsEvent event) {
        event.registerMobEffect(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInGui(@NotNull MobEffectInstance effect) {
                return false;
            }
        }, ModMobEffects.VOID.get());
    }
}
