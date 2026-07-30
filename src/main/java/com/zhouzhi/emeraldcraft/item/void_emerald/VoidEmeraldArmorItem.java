package com.zhouzhi.emeraldcraft.item.void_emerald;

import com.google.common.collect.Iterables;
import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumMap;
import java.util.List;

@EventBusSubscriber
public abstract class VoidEmeraldArmorItem extends ArmorItem {
	private static final int BAR_COLOR = FastColor.ARGB32.color(0, 34, 153, 84);
	public static Holder<ArmorMaterial> ARMOR_MATERIAL = null;

	@SubscribeEvent
	public static void registerArmorMaterial(RegisterEvent event) {
		event.register(Registries.ARMOR_MATERIAL, registerHelper -> {
			ArmorMaterial armorMaterial = new ArmorMaterial(Util.make(new EnumMap<>(Type.class), map -> {
				map.put(Type.BOOTS, 18);
				map.put(Type.LEGGINGS, 35);
				map.put(Type.CHESTPLATE, 50);
				map.put(Type.HELMET, 30);
				map.put(Type.BODY, 50);
			}), 90, BuiltInRegistries.SOUND_EVENT.wrapAsHolder(SoundEvents.EMPTY),
					() -> Ingredient.of(new ItemStack(ModItems.VOID_EMERALD.get())),
					List.of(new ArmorMaterial.Layer(ResourceLocation.parse("emeraldcraft:void_emerald"))),
					7f,
					0.8f);
			registerHelper.register(ResourceLocation.parse("emeraldcraft:void_emerald"), armorMaterial);
			ARMOR_MATERIAL = BuiltInRegistries.ARMOR_MATERIAL.wrapAsHolder(armorMaterial);
		});
	}

	public VoidEmeraldArmorItem(Type type, Properties properties) {
		super(ARMOR_MATERIAL, type, properties);
	}

	public static class Helmet extends VoidEmeraldArmorItem {
		public Helmet() {
			super(Type.HELMET, new Properties().durability(Type.HELMET.getDurability(500)).fireResistant().rarity(Rarity.EPIC));
		}
		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}

		@Override
		public void inventoryTick(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Entity entity, int slot, boolean selected) {
			super.inventoryTick(itemstack, world, entity, slot, selected);
			if (entity instanceof Player player && Iterables.contains(player.getArmorSlots(), itemstack)) {
				Use.VoidEmeraldArmorPerTick(world, player, itemstack);
			}
		}
	}

	public static class Chestplate extends VoidEmeraldArmorItem {
		public Chestplate() {
			super(Type.CHESTPLATE, new Properties().durability(Type.CHESTPLATE.getDurability(500)).fireResistant().rarity(Rarity.EPIC));
		}
		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}

	}

	public static class Leggings extends VoidEmeraldArmorItem {
		public Leggings() {
			super(Type.LEGGINGS, new Properties().durability(Type.LEGGINGS.getDurability(500)).fireResistant().rarity(Rarity.EPIC));
		}
		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}

	}

	public static class Boots extends VoidEmeraldArmorItem {
		public Boots() {
			super(Type.BOOTS, new Properties().durability(Type.BOOTS.getDurability(500)).fireResistant().rarity(Rarity.EPIC));
		}
		@Override
		public boolean makesPiglinsNeutral(@ParametersAreNonnullByDefault ItemStack itemstack, @ParametersAreNonnullByDefault LivingEntity entity) {
			return true;
		}
	}

	@Override
	public int getBarColor(@NotNull ItemStack stack) {
		return BAR_COLOR;
	}
}