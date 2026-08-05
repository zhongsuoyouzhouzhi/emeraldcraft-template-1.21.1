package com.zhouzhi.emeraldcraft.item.void_emerald;

import com.zhouzhi.emeraldcraft.init.ModBlocks;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

public class VoidEmeraldItem extends Item {
    public VoidEmeraldItem() {
        super(new Item.Properties().rarity(Rarity.RARE));
    }

    @Override
    @MethodsReturnNonnullByDefault
    public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
        super.useOn(context);
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        explode(world,pos,player,3);
        if (SimpleUse.GameTypeGetter.isCreativeOrSpectator(player)) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, @ParametersAreNonnullByDefault BlockState state) {
        Tool tool = stack.get(DataComponents.TOOL);
        return tool != null ? tool.getMiningSpeed(state) : 1.2F;
    }

    public static void explode(Level world, BlockPos pos, LivingEntity livingEntity,int radius) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        BlockState state = world.getBlockState(pos);
        int a = radius;
        if (state.getBlock() == ModBlocks.REFINED_EMERALD_BLOCK_3.value()) {
            a += 3;
            world.destroyBlock(pos, false);
            world.explode(livingEntity, x, y, z, 32, Level.ExplosionInteraction.BLOCK);
        }
        SimpleUse.OperateBlock(x, y, z, a, (blockPos, bx, by, bz) -> {
            if (world.getBlockState(blockPos).getBlock() == net.minecraft.world.level.block.Blocks.AIR) return;
            if (!world.isClientSide() && world instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.END_ROD,
                        bx, by, bz,
                        64,
                        0.5, 0.5, 0.5,
                        0
                );
            }
            if (Math.random() >= 0.75) {
                Block.dropResources(world.getBlockState(blockPos), world, blockPos, null);
            }
            world.setBlockAndUpdate(blockPos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState());
        });
    }
}