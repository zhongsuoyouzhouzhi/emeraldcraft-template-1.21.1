package com.zhouzhi.emeraldcraft.procedures.compress;

import com.zhouzhi.emeraldcraft.procedures.net.function.Function_BlockOperation;
import com.zhouzhi.emeraldcraft.procedures.net.function.Function_BlockPosOperation;
import com.zhouzhi.emeraldcraft.procedures.net.function.Function_EntityOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;


public class SimpleUse {
    public static int getEffectLevel(Entity entity, Holder<MobEffect> effect) {
        if (entity instanceof LivingEntity livingEntity) {
            MobEffectInstance effectInstance = livingEntity.getEffect(effect);
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
                    }
                        count++;
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

    public static int OperateBlock(int x, int y, int z, int radius, Function_BlockPosOperation Operate) {
        int count = 0;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int blockX = x + dx;
                    int blockY = y + dy;
                    int blockZ = z + dz;
                    Operate.run(BlockPos.containing(blockX, blockY, blockZ), blockX, blockY, blockZ);
                    count++;
                }
            }
        }
        return count;
    }

    public static void OperateEntity(Level world, Entity source, int XRadius, int YRadius, int ZRadius, Function_EntityOperation Operate) {
        if (world instanceof ServerLevel serverLevel) {
            int minX = (int) (source.getX() - XRadius);
            int minY = (int) (source.getY() - YRadius);
            int minZ = (int) (source.getZ() - ZRadius);
            int maxX = (int) (source.getX() + XRadius);
            int maxY = (int) (source.getY() + YRadius);
            int maxZ = (int) (source.getZ() + ZRadius);

            BlockPos minPos = new BlockPos(minX, minY, minZ);
            BlockPos maxPos = new BlockPos(maxX, maxY, maxZ);

            Iterable<Entity> entities = serverLevel.getEntities().getAll();

            for (Entity entity : entities) {
                if (isEntityInRange(entity, minPos, maxPos)) {
                    Operate.run(entity);
                }
            }
        }
    }

    private static boolean isEntityInRange(Entity entity, BlockPos minPos, BlockPos maxPos) {
        double entityX = entity.getX();
        double entityY = entity.getY();
        double entityZ = entity.getZ();

        return entityX >= minPos.getX() && entityX <= maxPos.getX() &&
                entityY >= minPos.getY() && entityY <= maxPos.getY() &&
                entityZ >= minPos.getZ() && entityZ <= maxPos.getZ();
    }

}
