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
        this.addAttributeModifier(Attributes.FOLLOW_RANGE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.void_0"), 1, AttributeModifier.Operation.ADD_VALUE);
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
