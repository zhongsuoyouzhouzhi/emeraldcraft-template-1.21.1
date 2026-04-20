package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.List;

@EventBusSubscriber
public abstract class EmeraldArmorItem extends ArmorItem {
	public static Holder<ArmorMaterial> ARMOR_MATERIAL = null;

	@SubscribeEvent
	public static void registerArmorMaterial(RegisterEvent event) {
		event.register(Registries.ARMOR_MATERIAL, registerHelper -> {
			ArmorMaterial armorMaterial = new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.BOOTS, 4);
				map.put(ArmorItem.Type.LEGGINGS, 10);
				map.put(ArmorItem.Type.CHESTPLATE, 12);
				map.put(ArmorItem.Type.HELMET, 4);
				map.put(ArmorItem.Type.BODY, 12);
			}), 12, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY), () -> Ingredient.of(new ItemStack(ModItems.REFINED_EMERALD.get())), List.of(new ArmorMaterial.Layer(ResourceLocation.parse("emeraldcraft:refined_emerald"))), 2.5f,
					0.04f);
			registerHelper.register(ResourceLocation.parse("emeraldcraft:emerald_armor"), armorMaterial);
			ARMOR_MATERIAL = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
		});
	}

	public EmeraldArmorItem(ArmorItem.Type type, Item.Properties properties) {
		super(ARMOR_MATERIAL, type, properties);
	}

	public static class Helmet extends EmeraldArmorItem {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(150))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
					.rarity(Rarity.UNCOMMON));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack,@ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}
	}

	public static class Chestplate extends EmeraldArmorItem {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(150))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
					.rarity(Rarity.UNCOMMON));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack,@ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}
	}

	public static class Leggings extends EmeraldArmorItem {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(150))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
					.rarity(Rarity.UNCOMMON));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack,@ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}
	}

	public static class Boots extends EmeraldArmorItem {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(150))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
					.rarity(Rarity.UNCOMMON));
		}

		@Override
		@OnlyIn(Dist.CLIENT)
		public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
			return true;
		}

		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack,@ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}
	}
}