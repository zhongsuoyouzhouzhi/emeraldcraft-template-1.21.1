package com.zhouzhi.emeraldcraft.procedures.compress;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.net.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
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

    public static class GameTypeGetter {
        public static @Nullable GameType getEntityGameType(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer) {
                return serverPlayer.gameMode.getGameModeForPlayer();
            } else if (entity instanceof Player player && player.level().isClientSide()) {
                PlayerInfo playerInfo = Objects.requireNonNull(Minecraft.getInstance().getConnection()).getPlayerInfo(player.getGameProfile().getId());
                if (playerInfo != null)
                    return playerInfo.getGameMode();
            }
            return null;
        }

        public static boolean isCreativeOrSpectator(Entity entity) {
            return GameTypeGetter.getEntityGameType(entity) == GameType.CREATIVE || GameTypeGetter.getEntityGameType(entity) == GameType.SPECTATOR;
        }
    }


    public static int destroyLog(LevelAccessor world, int x, int y, int z, int radius, boolean drop, ServerPlayer mining){
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
                        if (mining == null) {
                            Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY, blockZ), null);
                            world.destroyBlock(pos, drop);
                        } else mining.gameMode.destroyBlock(pos);
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

    public static int destroyStone(LevelAccessor world, int x, int y, int z, int radius, boolean drop, ServerPlayer mining){
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
                        if (mining == null) {
                            Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY, blockZ), null);
                            world.destroyBlock(pos, drop);
                        } else mining.gameMode.destroyBlock(pos);
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

    public static int destroyDirt(LevelAccessor world, int x, int y, int z, int radius, boolean drop, ServerPlayer mining){
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
                        if (mining == null) {
                            Block.dropResources(world.getBlockState(pos), world, BlockPos.containing(blockX, blockY, blockZ), null);
                            world.destroyBlock(pos, drop);
                        } else mining.gameMode.destroyBlock(pos);
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

    public static int OperateBlock(LevelAccessor world, int x, int y, int z, int radius , Predicate<Block> condition, Function.Function_BlockOperation lambdaOperate){
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

    public static int OperateBlock(LevelAccessor world, int x, int y, int z, int xRadius, int yRadius, int zRadius, Predicate<Block> condition, Function.Function_BlockOperation lambdaOperate){
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

    public static int OperateBlockPos(LevelAccessor world, int x, int y, int z, int xRadius, int yRadius, int zRadius, Predicate<BlockPos> condition, Function.Function_BlockOperation lambdaOperate){
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

    public static int OperateBlock(int x, int y, int z, int radius, Function.Function_BlockPosOperation Operate) {
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

    public static void OperateEntity(Level world, Entity source, int XRadius, int YRadius, int ZRadius, Function.Function_EntityOperation Operate) {
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

    public static Vec3 getPointAtDistance(
            double x1, double y1, double z1,
            double x2, double y2, double z2,
            double distance) {

        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;
        double totalDistance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        if (totalDistance == 0) {
            return new Vec3(x1, y1, z1);
        }

        double ratio = distance / totalDistance;

        double targetX = x1 + dx * ratio;
        double targetY = y1 + dy * ratio;
        double targetZ = z1 + dz * ratio;

        return new Vec3(targetX, targetY, targetZ);
    }
    public static void sendOpen(Player player,boolean isOpen) {
        if (isOpen)
            Message.send(player, Component.translatable("message.emeraldcraft.skill").append(Component.translatable("message.emeraldcraft.true")),true);
        else
            Message.send(player, Component.translatable("message.emeraldcraft.skill").append(Component.translatable("message.emeraldcraft.false")),true);
    }

    public static class Message {
        public static void send(Player player, String message, boolean isTranslate) {
            if (isTranslate)
                player.displayClientMessage(Component.translatable(message), true);
            else
                player.displayClientMessage(Component.literal(message), true);
        }

        public static void send(Player player, String message, boolean isTranslate, boolean actionBar) {
            if (isTranslate)
                player.displayClientMessage(Component.translatable(message), actionBar);
            else
                player.displayClientMessage(Component.literal(message), actionBar);
        }

        public static void send(Player player, Component message, boolean actionBar) {
            player.displayClientMessage(message, actionBar);
        }
    }

    public static ItemStack getSmeltedResult(Level level, ItemStack input) {
        if (level.isClientSide) {
            return ItemStack.EMPTY;
        }

        Optional<? extends AbstractCookingRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(RecipeType.SMELTING, new net.minecraft.world.item.crafting.SingleRecipeInput(input), level)
                .map(net.minecraft.world.item.crafting.RecipeHolder::value);

        return recipe.map(r -> r.assemble(new net.minecraft.world.item.crafting.SingleRecipeInput(input), level.registryAccess()))
                .orElse(ItemStack.EMPTY);
    }

    public static int VoidArmorNumber(LivingEntity entity) {
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        Item[] armorItems = {
            ModItems.VOID_EMERALD_ARMOR_HELMET.asItem(),
            ModItems.VOID_EMERALD_ARMOR_CHESTPLATE.asItem(),
            ModItems.VOID_EMERALD_ARMOR_LEGGINGS.asItem(),
            ModItems.VOID_EMERALD_ARMOR_BOOTS.asItem()
        };
        int count = 0;
        for (int i = 0; i < slots.length; i++) {
            ItemStack stack = entity.getItemBySlot(slots[i]);
            if (stack.is(armorItems[i])) {
                count++;
            }
        }
        return count;
    }
    /*
    public int LavaArmorNumber(LivingEntity entity) {
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        Item[] armorItems = {
            ModItems.LAVA_EMERALD_ARMOR_HELMET.asItem(),
            ModItems.LAVA_EMERALD_ARMOR_CHESTPLATE.asItem(),
            ModItems.LAVA_EMERALD_ARMOR_LEGGINGS.asItem(),
            ModItems.LAVA_EMERALD_ARMOR_BOOTS.asItem()
        };
        int count = 0;
        for (int i = 0; i < slots.length; i++) {
            ItemStack stack = entity.getItemBySlot(slots[i]);
            if (stack.is(armorItems[i])) {
                count++;
            }
        }
        return count;
    }
    */
}
