package com.zhouzhi.emeraldcraft.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.registries.Registries;

import com.zhouzhi.emeraldcraft.EmeraldCraft;

public class EmeraldcraftPotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(Registries.POTION, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<Potion, Potion> EMERALD_ATTACH_POTION = REGISTRY.register("emerald_attach_potion", () -> new Potion(new MobEffectInstance(EmeraldcraftMobEffects.EMERALD_ATTACH, 3200, 5, false, true),
			new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1600, 3, false, false), new MobEffectInstance(MobEffects.ABSORPTION, 20, 1, false, true)));
}