package com.zhouzhi.emeraldcraft.procedures.others;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.world.item.ItemStack;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftAttributes;
import com.zhouzhi.emeraldcraft.init.EmeraldcraftMobEffects;

public class RefinedEmeraldPlusPlayerFinishedEating {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide() && _entity.getAttributes().hasAttribute(EmeraldcraftAttributes.EVOLVED))
{
			double effectlevel = _entity.getAttribute(EmeraldcraftAttributes.EVOLVED).getValue();
			if (effectlevel  < 5){
				_entity.getAttribute(EmeraldcraftAttributes.EVOLVED).setBaseValue(effectlevel + 1);
			}
			else if (entity instanceof ServerPlayer _player) {
				AdvancementHolder _adv = _player.server.getAdvancements().get(ResourceLocation.parse("emeraldcraft:strengthen_body"));
				if (_adv != null) {
					AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
					if (!_ap.isDone()) {
						for (String criteria : _ap.getRemainingCriteria())
							_player.getAdvancements().award(_adv, criteria);
					}
				}
			}
			effectlevel++;
			effectlevel = effectlevel / 2;
			MobEffectInstance[] b = {
				new MobEffectInstance(EmeraldcraftMobEffects.EMERALD_ATTACH, (int)(effectlevel * 14400), 50, false, false),
				new MobEffectInstance(EmeraldcraftMobEffects.EMERALD_BONUS, (int)(effectlevel * 14400), 50, false, false),
				new MobEffectInstance(MobEffects.ABSORPTION, (int)(effectlevel * 7200), 10, false, false),
				new MobEffectInstance(MobEffects.FIRE_RESISTANCE, (int)(effectlevel * 7200), 0, false, false),
				new MobEffectInstance(MobEffects.DAMAGE_BOOST, (int)(effectlevel * 7200), 5, false, false),
				new MobEffectInstance(MobEffects.HEAL, 1, 20, false, false),
				new MobEffectInstance(MobEffects.REGENERATION, (int)(effectlevel * 10800), 5, false, false),
				new MobEffectInstance(MobEffects.LUCK, (int)(effectlevel * 7200), 5, false, false),
				new MobEffectInstance(MobEffects.NIGHT_VISION, (int)(effectlevel * 14400), 0, false, false)};
			for(int o = 0; o < b.length; o++){
				_entity.addEffect(b[o]);
			}
		}
		if (entity instanceof Player player) {
            ItemStack[] inventory = player.getInventory().items.toArray(new ItemStack[0]);
            for (int i = 0; i < inventory.length; i++) {
                ItemStack item = inventory[i];
                if (item != null && item.isDamageableItem()) {
                    item.setDamageValue(0);
                }
            }
		}
	}
}