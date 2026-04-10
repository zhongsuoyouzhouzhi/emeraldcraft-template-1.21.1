package com.zhouzhi.emeraldcraft.item.lava_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.ParametersAreNonnullByDefault;

public class LavaEmeraldShovelItem extends ShovelItem {
	private static final Tier TIER = new Tier() {
		@Override
		public int getUses() {
			return 1340;
		}

		@Override
		public float getSpeed() {
			return 15f;
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
			return 30;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(EmeraldcraftItems.LAVA_EMERALD.get()));
			}
	};

	public LavaEmeraldShovelItem() {
		super(TIER, new Properties()
				.attributes(DiggerItem.createAttributes(TIER, 7f, -3f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}
	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		super.useOn(context);
		if (!context.getLevel().isClientSide) {
			if (context.getLevel().getBlockState(context.getClickedPos()) == Blocks.CAULDRON.defaultBlockState()) {
				context.getLevel().setBlockAndUpdate(context.getClickedPos(), Blocks.LAVA_CAULDRON.defaultBlockState());
			}
		}
		return InteractionResult.SUCCESS;
	}
}