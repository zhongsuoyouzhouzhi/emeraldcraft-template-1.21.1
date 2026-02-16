package com.zhouzhi.emeraldcraft.procedures.compress;

import com.zhouzhi.emeraldcraft.procedures.net.function.Function_BlockOperation;
import com.zhouzhi.emeraldcraft.procedures.net.function.Function_BlockPosOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;


public class SimpleUse {
    public static int getEffectLevel(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            MobEffectInstance effectInstance = livingEntity.getEffect(MobEffects.MOVEMENT_SPEED);
            if (effectInstance != null) {
                return effectInstance.getAmplifier();// 0 = 等级1，1 = 等级2
            }
        }
        return -1;
    }

    public static GameType getEntityGameType(Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer) {
            return serverPlayer.gameMode.getGameModeForPlayer();
        } else if (entity instanceof Player player && player.level().isClientSide()) {
            try {
                PlayerInfo playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(player.getGameProfile().getId());
                if (playerInfo != null)
                    return playerInfo.getGameMode();
            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static int destroyLog(LevelAccessor world, int x, int y, int z, int radius, boolean drop){
        int count = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int blockX = x + dx;
                    int blockY = y + dy;
                    int blockZ = z + dz;
                    Block block = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
                    if (isLog(block)){
                        BlockPos pos = BlockPos.containing(blockX, blockY, blockZ);
                        Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY,blockZ), null);
                        world.destroyBlock(pos, drop);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static boolean isLog(Block block){
        return block.builtInRegistryHolder().is(BlockTags.LOGS);
    }

    public static int destroyStone(LevelAccessor world, int x, int y, int z, int radius, boolean drop){
        int count = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int blockX = x + dx;
                    int blockY = y + dy;
                    int blockZ = z + dz;
                    Block block = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
                    if (isStone(block)){
                        BlockPos pos = BlockPos.containing(blockX, blockY, blockZ);
                        Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY, blockZ), null);
                        world.destroyBlock(pos, drop);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static boolean isStone(Block block){
        boolean overworld = block.builtInRegistryHolder().is(BlockTags.BASE_STONE_OVERWORLD);
        boolean nether = block.builtInRegistryHolder().is(BlockTags.BASE_STONE_NETHER);
        boolean end = block == Blocks.END_STONE;
        return overworld || nether || end || block == Blocks.COBBLESTONE || block == Blocks.COBBLED_DEEPSLATE;
    }

    public static int destroyDirt(LevelAccessor world, int x, int y, int z, int radius, boolean drop){
        int count = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int blockX = x + dx;
                    int blockY = y + dy;
                    int blockZ = z + dz;
                    Block block = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
                    if (isDirt(block)){
                        BlockPos pos = BlockPos.containing(blockX, blockY, blockZ);
                        Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY, blockZ), null);
                        world.destroyBlock(pos, drop);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static boolean isDirt(Block block){
        return block.builtInRegistryHolder().is(BlockTags.MINEABLE_WITH_SHOVEL);
    }

    public static int OperateBlock(LevelAccessor world, int x, int y, int z, int radius , Predicate<Block> condition, Function_BlockOperation lambdaOperate){
        int count = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int blockX = x + dx;
                    int blockY = y + dy;
                    int blockZ = z + dz;
                    Block block = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
                    if (condition.test(block)){
                        lambdaOperate.run(block,blockX,blockY,blockZ);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int OperateBlock(LevelAccessor world, int x, int y, int z, int xRadius, int yRadius, int zRadius, Predicate<Block> condition, Function_BlockOperation lambdaOperate){
        int count = 0;
        for (int dx = -xRadius; dx <= xRadius; dx++) {
            for (int dy = -yRadius; dy <= yRadius; dy++) {
                for (int dz = -zRadius; dz <= zRadius; dz++) {
                    int blockX = x + dx;
                    int blockY = y + dy;
                    int blockZ = z + dz;
                    Block block = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
                    if (condition.test(block)){
                        lambdaOperate.run(block,blockX,blockY,blockZ);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int OperateBlockPos(LevelAccessor world, int x, int y, int z, int xRadius, int yRadius, int zRadius, Predicate<BlockPos> condition, Function_BlockOperation lambdaOperate){
        int count = 0;
        for (int dx = -xRadius; dx <= xRadius; dx++) {
            for (int dy = -yRadius; dy <= yRadius; dy++) {
                for (int dz = -zRadius; dz <= zRadius; dz++) {
                    int blockX = x + dx;
                    int blockY = y + dy;
                    int blockZ = z + dz;
                    Block block = (world.getBlockState(BlockPos.containing(blockX, blockY, blockZ))).getBlock();
                    if (condition.test(BlockPos.containing(blockX, blockY, blockZ))){
                        lambdaOperate.run(block,blockX,blockY,blockZ);
                        count++;
                    }
                }
            }
        }
        return count;
    }


    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment) {
        if (stack.isEmpty() || enchantment == null) {
            return false;
        }
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null) {
            return false;
        }

        var enchantmentMap = enchantments.entrySet();
        for (var entry : enchantmentMap) {
            Enchantment currentEnchantment = entry.getKey().value();
            if (currentEnchantment.equals(enchantment)) {
                return true;
            }
        }

        return false;
    }

    public static int getEnchantmentLevel(ItemStack stack, Enchantment enchantment) {
        if (stack.isEmpty() || enchantment == null) {
            return 0;
        }
        ItemEnchantments enchantments = stack.get(DataComponents.ENCHANTMENTS);
        if (enchantments == null) {
            return 0;
        }
        for (var entry : enchantments.entrySet()) {
            Enchantment currentEnchantment = entry.getKey().value();
            if (currentEnchantment.equals(enchantment)) {
                return entry.getIntValue();
            }
        }

        return 0;
    }

}
