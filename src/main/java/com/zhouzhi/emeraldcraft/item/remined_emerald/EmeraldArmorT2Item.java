package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
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
public abstract class EmeraldArmorT2Item extends ArmorItem {
	public static Holder<ArmorMaterial> ARMOR_MATERIAL = null;

	@SubscribeEvent
	public static void registerArmorMaterial(RegisterEvent event) {
		event.register(Registries.ARMOR_MATERIAL, registerHelper -> {
			ArmorMaterial armorMaterial = new ArmorMaterial(Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
				map.put(ArmorItem.Type.BOOTS, 8);
				map.put(ArmorItem.Type.LEGGINGS, 16);
				map.put(ArmorItem.Type.CHESTPLATE, 18);
				map.put(ArmorItem.Type.HELMET, 9);
				map.put(ArmorItem.Type.BODY, 18);
			}), 15, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY),
					() -> Ingredient.of(new ItemStack(EmeraldcraftItems.REFINED_EMERALD_T_2.get())),
					List.of(new ArmorMaterial.Layer(ResourceLocation.parse("emeraldcraft:refined_emerald_t2"))),
					3f,
					0.1f);
			registerHelper.register(ResourceLocation.parse("emeraldcraft:emerald_armor_t_2"), armorMaterial);
			ARMOR_MATERIAL = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
		});
	}

	public EmeraldArmorT2Item(ArmorItem.Type type, Item.Properties properties) {
		super(ARMOR_MATERIAL, type, properties);
	}

	public static class Helmet extends EmeraldArmorT2Item {
		public Helmet() {
			super(ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(240))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).rarity(Rarity.UNCOMMON));
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

	public static class Chestplate extends EmeraldArmorT2Item {
		public Chestplate() {
			super(ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(240))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).rarity(Rarity.UNCOMMON));
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

	public static class Leggings extends EmeraldArmorT2Item {
		public Leggings() {
			super(ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(240))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).rarity(Rarity.UNCOMMON));
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
	}

	public static class Boots extends EmeraldArmorT2Item {
		public Boots() {
			super(ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(240))
					.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY).rarity(Rarity.UNCOMMON));
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