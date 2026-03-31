package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldT2ToolIsBeingDamagedPerTick;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class EmeraldAxeT2Item extends AxeItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 2400;
		}

		@Override
		public float getSpeed() {
			return 21f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 0;
		}

		@Override
		public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 15;
		}

		@Override
		public @NotNull Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(EmeraldcraftItems.REFINED_EMERALD_T_2.get()));
		}
	};

	public EmeraldAxeT2Item() {
		super(TOOL_TIER, new Item.Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 10f, -3f)));
	}

	@Override
	public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
		super.inventoryTick(itemstack, world, entity, slot, selected);
		if (selected)
			RefinedEmeraldT2ToolIsBeingDamagedPerTick.execute(world, entity, itemstack);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@NotNull ItemStack itemstack) {
		return true;
	}
}