package com.zhouzhi.emeraldcraft.procedures.others;

import com.zhouzhi.emeraldcraft.init.ModAttributes;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class RefinedEmeraldPlusPlayerFinishedEating {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide() && _entity.getAttributes().hasAttribute(ModAttributes.EVOLVED))
{
			double effectlevel = Objects.requireNonNull(_entity.getAttribute(ModAttributes.EVOLVED)).getValue();
			if (effectlevel  < 5){
				Objects.requireNonNull(_entity.getAttribute(ModAttributes.EVOLVED)).setBaseValue(effectlevel + 1);
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
				new MobEffectInstance(ModMobEffects.EMERALD_ATTACH, (int)(effectlevel * 14400), 50, false, false),
				new MobEffectInstance(ModMobEffects.EMERALD_BONUS, (int)(effectlevel * 14400), 50, false, false),
				new MobEffectInstance(net.minecraft.world.effect.MobEffects.ABSORPTION, (int)(effectlevel * 7200), 10, false, false),
				new MobEffectInstance(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE, (int)(effectlevel * 7200), 0, false, false),
				new MobEffectInstance(net.minecraft.world.effect.MobEffects.DAMAGE_BOOST, (int)(effectlevel * 7200), 5, false, false),
				new MobEffectInstance(net.minecraft.world.effect.MobEffects.HEAL, 1, 20, false, false),
				new MobEffectInstance(net.minecraft.world.effect.MobEffects.REGENERATION, (int)(effectlevel * 10800), 5, false, false),
				new MobEffectInstance(net.minecraft.world.effect.MobEffects.LUCK, (int)(effectlevel * 7200), 5, false, false),
				new MobEffectInstance(net.minecraft.world.effect.MobEffects.NIGHT_VISION, (int)(effectlevel * 14400), 0, false, false)};
    for (MobEffectInstance mobEffectInstance : b) {
        _entity.addEffect(mobEffectInstance);
    }
		}
		if (entity instanceof Player player) {
            ItemStack[] inventory = player.getInventory().items.toArray(new ItemStack[0]);
            for (ItemStack item : inventory) {
                if (item != null && item.isDamageableItem()) {
                    item.setDamageValue(0);
                }
            }
		}
	}
}