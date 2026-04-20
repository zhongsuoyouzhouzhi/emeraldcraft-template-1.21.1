package com.zhouzhi.emeraldcraft.potion;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.effect.EmeraldAttachOnEffectStarted;
import net.minecraft.resources.ResourceLocation;
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
public class EmeraldAttachMobEffect extends MobEffect {
	public EmeraldAttachMobEffect() {
		super(MobEffectCategory.BENEFICIAL, -10027264);
		this.addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.emerald_attach_0"), 2, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.emerald_attach_1"), 1, AttributeModifier.Operation.ADD_VALUE);
		this.addAttributeModifier(Attributes.MAX_ABSORPTION, ResourceLocation.fromNamespaceAndPath(EmeraldCraft.MOD_ID, "effect.emerald_attach_2"), 1, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
	}

	@Override
	public void onEffectStarted(@NotNull LivingEntity entity, int amplifier) {
		EmeraldAttachOnEffectStarted.execute(entity);
	}

	@SubscribeEvent
	public static void registerMobEffectExtensions(RegisterClientExtensionsEvent event) {
		event.registerMobEffect(new IClientMobEffectExtensions() {
			@Override
			public boolean isVisibleInGui(@NotNull MobEffectInstance effect) {
				return false;
			}
		}, ModMobEffects.EMERALD_ATTACH.get());
	}
}