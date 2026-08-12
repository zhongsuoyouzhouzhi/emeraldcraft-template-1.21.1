package com.zhouzhi.emeraldcraft.item.inferno_emerald;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.ParametersAreNonnullByDefault;

public class InfernoEmeraldItem extends Item {
    public InfernoEmeraldItem() {
        super(new Properties().rarity(Rarity.EPIC).fireResistant());
    }
    @Override
    @MethodsReturnNonnullByDefault
    public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
        super.useOn(context);
        Level level = context.getLevel();
        BlockPos pos =  context.getClickedPos();
        if (!level.isClientSide) {
            if (level.getBlockState(pos).is(Blocks.CAULDRON)) {
                level.setBlockAndUpdate(pos, Blocks.LAVA_CAULDRON.defaultBlockState());
            } else if (level.getBlockState(pos).is(Blocks.SNOW)) {
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            } else return InteractionResult.PASS;
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
