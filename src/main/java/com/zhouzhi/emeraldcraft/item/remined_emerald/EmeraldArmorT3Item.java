package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.google.common.collect.Iterables;
import com.zhouzhi.emeraldcraft.init.EmeraldcraftBlocks;
import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.others.EmeraldArmorT3PerTick;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

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
			}), 30, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY), () -> Ingredient.of(new ItemStack(EmeraldcraftBlocks.REFINEDEMERALD_BLOCK_3.get()), new ItemStack(EmeraldcraftItems.REFINED_EMERALD_T_3.get())),
					List.of(new ArmorMaterial.Layer(ResourceLocation.parse("emeraldcraft:emerald"))), 6f, 0.5f);
			registerHelper.register(ResourceLocation.parse("emeraldcraft:emerald_armor_t_3"), armorMaterial);
			ARMOR_MATERIAL = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
		});
	}

	public EmeraldArmorT3Item(ArmorItem.Type type, Item.Properties properties) {
		super(ARMOR_MATERIAL, type, properties);
	}

	public static class Helmet extends EmeraldArmorT3Item {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(520)).fireResistant());
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(ItemStack itemstack, LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}

	public static class Chestplate extends EmeraldArmorT3Item {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(520)).fireResistant());
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(ItemStack itemstack, LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}

	public static class Leggings extends EmeraldArmorT3Item {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(520)).fireResistant());
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(ItemStack itemstack, LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}

	public static class Boots extends EmeraldArmorT3Item {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(520)).fireResistant());
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(ItemStack itemstack, LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				EmeraldArmorT3PerTick.execute(world, entity.getX(), entity.getY(), entity.getZ(), entity, itemstack);
			}
		}
	}
}