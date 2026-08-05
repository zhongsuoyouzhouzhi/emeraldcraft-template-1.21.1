package com.zhouzhi.emeraldcraft.item.inferno_emerald;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
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
        if (!context.getLevel().isClientSide) {
            if (context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.CAULDRON)) {
                context.getLevel().setBlockAndUpdate(context.getClickedPos(), Blocks.LAVA_CAULDRON.defaultBlockState());
            } else if (context.getLevel().getBlockState(context.getClickedPos()).is(Blocks.SNOW)) {
                context.getLevel().setBlockAndUpdate(context.getClickedPos(), Blocks.AIR.defaultBlockState());
            } else return InteractionResult.PASS;
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
