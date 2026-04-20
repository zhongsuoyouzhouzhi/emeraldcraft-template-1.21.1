package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
	public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(Registries.POTION, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<Potion, Potion> EMERALD_ATTACH_POTION = REGISTRY.register("emerald_attach_potion", () -> new Potion(new MobEffectInstance(ModMobEffects.EMERALD_ATTACH, 3200, 5, false, true),
			new MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE, 1600, 3, false, false), new MobEffectInstance(net.minecraft.world.effect.MobEffects.ABSORPTION, 20, 1, false, true)));
}