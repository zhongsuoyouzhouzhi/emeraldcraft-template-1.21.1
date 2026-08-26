package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.google.common.collect.Iterables;
import com.zhouzhi.emeraldcraft.init.ModBlocks;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModMobEffects;
import com.zhouzhi.emeraldcraft.procedures.compress.MobEffectALL;
import com.zhouzhi.emeraldcraft.procedures.compress.PushAway;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.List;

@EventBusSubscriber
public abstract class EmeraldArmorT3Item extends ArmorItem {
	public static Holder<ArmorMaterial> ARMOR_MATERIAL = null;

	@SubscribeEvent
	public static void registerArmorMaterial(RegisterEvent event) {
		event.register(Registries.ARMOR_MATERIAL, registerHelper -> {
			ArmorMaterial armorMaterial = new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.BOOTS, 20);
				map.put(ArmorItem.Type.LEGGINGS, 40);
				map.put(ArmorItem.Type.CHESTPLATE, 60);
				map.put(ArmorItem.Type.HELMET, 25);
				map.put(ArmorItem.Type.BODY, 60);
			}), 30, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY),
					() -> Ingredient.of(new ItemStack(ModBlocks.REFINED_EMERALD_BLOCK_3.get()), new ItemStack(ModItems.REFINED_EMERALD_T_3.get())),
					List.of(new ArmorMaterial.Layer(ResourceLocation.parse("emeraldcraft:refined_emerald_t3"))),
					6f,
					0.5f);
			registerHelper.register(ResourceLocation.parse("emeraldcraft:emerald_armor_t_3"), armorMaterial);
			ARMOR_MATERIAL = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
		});
	}

	public EmeraldArmorT3Item(ArmorItem.Type type, Item.Properties properties) {
		super(ARMOR_MATERIAL, type, properties);
	}

	public static class Helmet extends EmeraldArmorT3Item {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(520)).fireResistant().rarity(Rarity.EPIC));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}

	public static class Chestplate extends EmeraldArmorT3Item {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(520)).fireResistant().rarity(Rarity.EPIC));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}

	public static class Leggings extends EmeraldArmorT3Item {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(520)).fireResistant().rarity(Rarity.EPIC));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}

	public static class Boots extends EmeraldArmorT3Item {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(520)).fireResistant().rarity(Rarity.EPIC));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}

	private static void EmeraldArmorT3PerTick(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide()) {
			MobEffectInstance[] effectInstances = new MobEffectInstance[]{
					new MobEffectInstance(ModMobEffects.EMERALD_ATTACH, 40, 8, false, false),
					new MobEffectInstance(ModMobEffects.EMERALD_BONUS, 20, 14, false, false),
					new MobEffectInstance(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE, 40, 0, false, false)
			};
			MobEffectALL.execute(_entity.level(), effectInstances, _entity);
		}
		if (entity instanceof Player player && world instanceof ServerLevel _level) {
			if (player.getFoodData().getFoodLevel() < 20) {
				itemstack.hurtAndBreak(2, _level, player, i -> {
				});
				player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() + 1);
			}
			if (player.getFoodData().getSaturationLevel() < 20) {
				itemstack.hurtAndBreak(1, _level, player, i -> {
				});
				player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel() + 1);
				player.getCooldowns().addCooldown(itemstack.getItem(), 30);
			}
			player.getCooldowns().addCooldown(itemstack.getItem(), 30);
		}
		if (entity.isOnFire()) {
			entity.clearFire();
			if (world instanceof ServerLevel _level) {
				itemstack.hurtAndBreak(1, _level, null, i -> {
				});
			}
			if (entity instanceof Player _player)
				_player.getCooldowns().addCooldown(itemstack.getItem(), 40);
		}
		if (entity.isUnderWater()) {
			if (entity.getAirSupply() <= 10) {
				entity.setAirSupply(300);
				if (entity instanceof Player _player && world instanceof ServerLevel _level) {
					_player.getCooldowns().addCooldown(itemstack.getItem(), 200);
					itemstack.hurtAndBreak(3, _level, _player, i -> {
					});
				}
			}
		}
		if (entity.isShiftKeyDown()) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.MOVEMENT_SPEED, 1, 20, false, false));
			if (entity.isInWall()) {
				{
					BlockPos _pos = BlockPos.containing(x, y + 1, z);
					Block.dropResources(world.getBlockState(_pos), world, BlockPos.containing(x, y + 1, z), null);
					world.destroyBlock(_pos, false);
				}
				world.destroyBlock(BlockPos.containing(x, y + 1, z), false);
			}
			Level _world = entity.level();
			double _x = entity.getX();
			double _y = entity.getY();
			double _z = entity.getZ();
			PushAway.execute(_world, entity, _x, _y, _z, 4);
		}
	}
}