package com.zhouzhi.emeraldcraft.init;

import com.zhouzhi.emeraldcraft.EmeraldCraft;
import com.zhouzhi.emeraldcraft.potion.EmeraldAttachMobEffect;
import com.zhouzhi.emeraldcraft.potion.EmeraldBonusMobEffect;
import com.zhouzhi.emeraldcraft.potion.SuppressMobEffect;
import com.zhouzhi.emeraldcraft.potion.VoidMobEffect;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@EventBusSubscriber
public class ModMobEffects {
	public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, EmeraldCraft.MOD_ID);
	public static final DeferredHolder<MobEffect, MobEffect> EMERALD_ATTACH = REGISTRY.register("emerald_attach", EmeraldAttachMobEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> EMERALD_BONUS = REGISTRY.register("emerald_bonus", EmeraldBonusMobEffect::new);
    public static final DeferredHolder<MobEffect, MobEffect> SUPPRESS = REGISTRY.register("suppress", SuppressMobEffect::new);
	public static final DeferredHolder<MobEffect, MobEffect> VOID = REGISTRY.register("void", VoidMobEffect::new);

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
			if (entity instanceof Player player) {
				if (!player.getAbilities().instabuild) {
					if (!player.hasEffect(VOID)) {
						player.getAbilities().mayfly = false;
						player.getAbilities().flying = false;
						player.onUpdateAbilities();
					}
				}
			}
		} else if (effectInstance.getEffect().is(SUPPRESS)) {
			if (!entity.getCommandSenderWorld().isClientSide()){
				DamageSource magicDamage = entity.getCommandSenderWorld().damageSources().magic();
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
		} else if (effectInstance.getEffect().is(VOID)) {
			if (entity instanceof Player player) {
				if (!player.getAbilities().instabuild) {
					if (!player.hasEffect(EMERALD_ATTACH)) {
						player.getAbilities().mayfly = false;
						player.getAbilities().flying = false;
						player.onUpdateAbilities();
					}
				}
			}
		}
	}
}