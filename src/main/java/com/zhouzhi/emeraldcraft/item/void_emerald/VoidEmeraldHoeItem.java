package com.zhouzhi.emeraldcraft.item.void_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

import static com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse.OperateBlockPos;

public class VoidEmeraldHoeItem extends HoeItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 8900;
		}

		@Override
		public float getSpeed() {
			return 240f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 1;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 60;
		}

		@Override
		@MethodsReturnNonnullByDefault
		public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(ModItems.VOID_EMERALD.get()));
		}
	};

	public VoidEmeraldHoeItem() {
		super(TOOL_TIER, new Properties()
				.attributes(DiggerItem.createAttributes(TOOL_TIER, 1.5f, -2.5f))
				.component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
				.fireResistant()
				.rarity(Rarity.EPIC));
	}

    @Override
	@MethodsReturnNonnullByDefault
    public InteractionResult useOn(UseOnContext context) {
        if (TagChange.getOrCreateComponent(context.getItemInHand(),"Scope", false)){
            BlockPos blockPos = context.getClickedPos();
            Level level = context.getLevel();
            OperateBlockPos(
                    context.getLevel(),
                    blockPos.getX(),
                    blockPos.getY(),
                    blockPos.getZ(),
                    2,
                    0,
                    2 ,
                    (blockpos) -> true,
                    (block,x,y,z) -> {
                        if (level.getBlockState(blockPos).getBlock() != Blocks.GRASS_BLOCK && level.getBlockState(blockPos).getBlock() != Blocks.DIRT) return;
                        if (x == blockPos.getX() && y == blockPos.getY() && z == blockPos.getZ()) return;
                        if (block == Blocks.FARMLAND) return;
                        if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT) {
                            BlockPos pos = BlockPos.containing(x, y, z);
                            BlockState newState = Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE, 5);
                            level.setBlockAndUpdate(pos, newState);
                            level.getChunk(pos).setBlockState(pos, newState, true);
                        }
                    });
        }
        return super.useOn (context);
    }
}