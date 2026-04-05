package com.zhouzhi.emeraldcraft.item.lava_emerald;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.ParametersAreNonnullByDefault;

public class LavaEmeraldItem extends Item{
    public LavaEmeraldItem() {
        super(new Properties().rarity(Rarity.RARE));
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