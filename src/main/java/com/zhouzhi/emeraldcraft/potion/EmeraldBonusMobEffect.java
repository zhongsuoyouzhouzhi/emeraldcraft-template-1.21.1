package com.zhouzhi.emeraldcraft.potion;

import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.resources.ResourceLocation;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftMobEffects;
import com.zhouzhi.emeraldcraft.EmeraldCraft;

@EventBusSubscriber
public class EmeraldBonusMobEffect extends MobEffect {
	public EmeraldBonusMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -10027264);
		this.addAttributeModifier(Attributes.MAX_HEALTH, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.emerald_bonus_0"), 2, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MAX_ABSORPTION, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.emerald_bonus_1"), 2, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_SPEED, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.emerald_bonus_2"), 1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.emerald_bonus_3"), 2, AttributeModifier.Operation.ADD_VALUE);
	}

	@SubscribeEvent
	public static void registerMobEffectExtensions(RegisterClientExtensionsEvent event) {
		event.registerMobEffect(new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInGui(MobEffectInstance effect) {
				return false;
			}
		}, EmeraldcraftMobEffects.EMERALD_BONUS.get());
	}
}