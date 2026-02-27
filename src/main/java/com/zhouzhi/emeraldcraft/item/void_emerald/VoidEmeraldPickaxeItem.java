package com.zhouzhi.emeraldcraft.item.void_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class VoidEmeraldPickaxeItem extends PickaxeItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 10217;
		}

		@Override
		public float getSpeed() {
			return 450f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 1;
		}

		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 60;
		}

		@Override
		public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(EmeraldcraftItems.VOID_EMERALD.get()));
			}
	};

	public VoidEmeraldPickaxeItem() {
		super(TOOL_TIER, new Item.Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 6f, -2.4f)).fireResistant());
	}

    @Override
    public boolean mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity miningEntity) {
        Tool tool = stack.get(DataComponents.TOOL);
        if (tool == null) {
            return false;
        } else {
            if (!level.isClientSide && state.getDestroySpeed(level, pos) != 0.0F && tool.damagePerBlock() > 0) {
                stack.hurtAndBreak(tool.damagePerBlock(), miningEntity, EquipmentSlot.MAINHAND);
                if ((SimpleUse.isStone(state.getBlock()) || state.getBlock() == Blocks.EMERALD_ORE || state.getBlock() == Blocks.DEEPSLATE_EMERALD_ORE) && !TagChange.getOrCreateComponent(stack,"Scope",false)) {
                    SimpleUse.OperateBlock(
                            level,
                            pos.getX(),
                            pos.getY(),
                            pos.getZ(),
                            2,
                            (block) -> SimpleUse.isStone(block) || block == Blocks.EMERALD_ORE || block == Blocks.DEEPSLATE_EMERALD_ORE,
                            (block,x,y,z) -> {
                                BlockPos _pos = BlockPos.containing(x, y, z);
                                Block.dropResources(level.getBlockState(_pos), level, BlockPos.containing(x, y, z), null);
                                level.destroyBlock(_pos, false);
                    });
                }
            }
            return true;
        }
    }

}