package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftBlocks;
import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.others.EmeraldAxeT3Right_clickOnAir;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class EmeraldAxeT3Item extends AxeItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 9482;
		}

		@Override
		public float getSpeed() {
			return 100f;
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
			return 50;
		}

		@Override
		public @NotNull Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(EmeraldcraftBlocks.REFINED_EMERALD_BLOCK_3.get()), new ItemStack(EmeraldcraftItems.REFINED_EMERALD_T_3.get()));
		}
	};

	public EmeraldAxeT3Item() {
		super(TOOL_TIER, new Item.Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 19f, -3f)).fireResistant());
	}

	@Override
	public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
		EmeraldAxeT3Right_clickOnAir.execute(entity);
		return ar;
	}

    @Override
    public void inventoryTick(@NotNull ItemStack itemstack, @NotNull Level world, @NotNull Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            Use.RefinedEmeraldT3ToolIsBeingDamagedPerTick(world, entity, itemstack);
    }

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@NotNull ItemStack itemstack) {
		return true;
	}
}