package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.procedures.effect.SuppressOnEffectEnded;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;

import com.zhouzhi.emeraldcraft.procedures.effect.EmeraldAttachOnEffectEnded;
import com.zhouzhi.emeraldcraft.potion.*;
import com.zhouzhi.emeraldcraft.EmeraldCraft;

@EventBusSubscriber
public class EmeraldcraftMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<MobEffect, MobEffect> EMERALD_ATTACH = REGISTRY.register("emerald_attach", () -> new EmeraldAttachMobEffect());
	public static final DeferredHolder<MobEffect, MobEffect> EMERALD_BONUS = REGISTRY.register("emerald_bonus", () -> new EmeraldBonusMobEffect());
    public static final DeferredHolder<MobEffect, MobEffect> SUPPRESS = REGISTRY.register("suppress", () -> new SuppressMobEffect());

	@SubscribeEvent
	public static void onEffectRemoved(MobEffectEvent.Remove event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	@SubscribeEvent
	public static void onEffectExpired(MobEffectEvent.Expired event) {
		MobEffectInstance effectInstance = event.getEffectInstance();
		if (effectInstance != null) {
			expireEffects(event.getEntity(), effectInstance);
		}
	}

	private static void expireEffects(Entity entity, MobEffectInstance effectInstance) {
		if (effectInstance.getEffect().is(EMERALD_ATTACH)) {
			EmeraldAttachOnEffectEnded.execute(entity);
		} else if (effectInstance.getEffect().is(SUPPRESS)) {
            SuppressOnEffectEnded.execute(entity);
        }
	}
}