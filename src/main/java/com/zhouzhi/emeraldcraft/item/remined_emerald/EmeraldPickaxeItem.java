package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class EmeraldPickaxeItem extends PickaxeItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 1248;
		}

		@Override
		public float getSpeed() {
			return 12f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 0;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 12;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(EmeraldcraftItems.REFINED_EMERALD.get()));
		}
	};

	public EmeraldPickaxeItem() {
		super(TOOL_TIER, new Item.Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 4f, -2.8f)));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}
}