package com.zhouzhi.emeraldcraft.listening;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.init.ModTags;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import static com.zhouzhi.emeraldcraft.procedures.compress.TagChange.getOrCreateComponent;

public class MiningListening {
    @SubscribeEvent
    public void LavaEmeraldAndInfernoEmeraldToolMining(BlockEvent.BreakEvent event) {
        Player entity = event.getPlayer();
        if (SimpleUse.GameTypeGetter.isCreativeOrSpectator(entity)) {
            return;
        }
        Level level = entity.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        ItemStack mainHandItem = entity.getMainHandItem();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        if (level instanceof ServerLevel serverLevel) {
            ItemStack item = SimpleUse.getSmeltedResult(serverLevel, state.getBlock().asItem().getDefaultInstance());
            if (item.isEmpty()) return;
            if (mainHandItem.is(ModTags.LAVA_EMERALD_TOOLS) || mainHandItem.is(ModTags.LAVA_EMERALD_T2_TOOLS) || mainHandItem.is(ModTags.INFERNO_EMERALD_TOOLS)) {
                if (!SimpleUse.isCurrentTool(mainHandItem,state)) return;
            } else return;
            event.setCanceled(true);
            level.destroyBlock(event.getPos(), false, entity);
            mainHandItem.hurtAndBreak(1, serverLevel, entity, a -> {
            });
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity(serverLevel, x, y, z, item);
            itemEntity.setDefaultPickUpDelay();
            serverLevel.addFreshEntity(itemEntity);
            if (mainHandItem.is(ModTags.INFERNO_EMERALD_TOOLS)) {
                Vec3 center = new Vec3(x, y, z);
                Vec3 aimCenter = new Vec3(0.2,0.5,0);
                SimpleUse.Effect.round_plane(serverLevel, ParticleTypes.FLAME, center, 0.5, 30, aimCenter, 0.1, true);
            } else {
                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 24, 0.2, 0.2, 0.2, 0.05);
            }
        }
    }

    @SubscribeEvent
    public void InfernoEmeraldToolMining(BlockEvent.BreakEvent event) {
        Player entity = event.getPlayer();
        if (SimpleUse.GameTypeGetter.isCreativeOrSpectator(entity)) {
            return;
        }
        Level level = entity.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        ItemStack mainHandItem = entity.getMainHandItem();
        if (!getOrCreateComponent(mainHandItem, "Inferno", false)) {
            return;
        }
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        if (mainHandItem.is(ModTags.INFERNO_EMERALD_TOOLS)) {
            if (SimpleUse.isCurrentTool(mainHandItem, state)) {
                if (level instanceof ServerLevel serverLevel) {
                    if (SimpleUse.Random_static.nextPercent(60)) {
                        SimpleUse.OperateBlock(
                                serverLevel,
                                pos.getX(), pos.getY(), pos.getZ(),
                                1,
                                block -> SimpleUse.isCurrentTool(mainHandItem, block.defaultBlockState()),
                                (block, X, Y, Z) -> {
                                    if (SimpleUse.Random_static.nextPercent(40)) {
                                        if (X == pos.getX() && Y == pos.getY() && Z == pos.getZ()) { //这踏马有病吧换成block.defaultBlockState().equals(state)整个方法就断不了点了我恨你
                                            return; //我就re个turn也犯毛病是不是 缺心眼儿啊?
                                        }
                                        if (block.defaultBlockState().is(Blocks.CRYING_OBSIDIAN) || block.defaultBlockState().is(Blocks.MAGMA_BLOCK) || block.defaultBlockState().is(Blocks.COAL_BLOCK) || block.defaultBlockState().is(Blocks.MUD)) {
                                            return;
                                        } else if (block.defaultBlockState().is(Tags.Blocks.ORES)) {
                                            return;
                                        }

                                        if (block.defaultBlockState().is(Blocks.OBSIDIAN)) {
                                            serverLevel.setBlockAndUpdate(BlockPos.containing(X, Y, Z), Blocks.CRYING_OBSIDIAN.defaultBlockState());
                                        } else if (block.defaultBlockState().is(BlockTags.LOGS)) {
                                            serverLevel.setBlockAndUpdate(BlockPos.containing(X, Y, Z), Blocks.COAL_BLOCK.defaultBlockState());
                                        } else if (block.defaultBlockState().is(Blocks.DIRT)) {
                                            serverLevel.setBlockAndUpdate(BlockPos.containing(X, Y, Z), Blocks.MUD.defaultBlockState());
                                        } else {
                                            serverLevel.setBlockAndUpdate(BlockPos.containing(X, Y, Z), Blocks.MAGMA_BLOCK.defaultBlockState());
                                        }
                                        Vec3 center = new Vec3(X + 0.5, Y + 0.5, Z + 0.5);
                                        Vec3 aimCenter = new Vec3(0.1,0.5,0);
                                        SimpleUse.Effect.round_plane(serverLevel, ParticleTypes.SMALL_FLAME, center, 0.5, 30, aimCenter, 0.2, true);
                                    }
                                });
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void EmeraldToolBreakSpeed(PlayerEvent.BreakSpeed event){
        Player entity = event.getEntity();
        if (SimpleUse.GameTypeGetter.isCreativeOrSpectator(entity)) {
            return;
        }
        Level level = entity.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        ItemStack weapon = entity.getMainHandItem();
        BlockState state = event.getState();
        if (weapon.is(ItemTags.PICKAXES) && weapon.is(ModTags.EMERALD_TOOLS)) {
            if (state.is(BlockTags.EMERALD_ORES)) {
                event.setNewSpeed(event.getNewSpeed() * 5);
            }
            if (state.is(ModTags.EMERALD_BLOCKS)) {
                event.setNewSpeed(event.getNewSpeed() * 2);
            }
        }
    }

    @SubscribeEvent
    public void VoidEmeraldArmorBreakSpeed(PlayerEvent.BreakSpeed event){
        Player entity = event.getEntity();
        if (SimpleUse.GameTypeGetter.isCreativeOrSpectator(entity)) {
            return;
        }
        Level level = entity.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        if (SimpleUse.VoidArmorNumber(entity) == 4) {
            event.setNewSpeed(event.getNewSpeed() * 8);
        }
    }

    @SubscribeEvent
    public void GenesisEmeraldToolMining(BlockEvent.BreakEvent event){
        Player entity = event.getPlayer();
        if (SimpleUse.GameTypeGetter.isCreativeOrSpectator(entity)) {
            return;
        }
        Level level = entity.getCommandSenderWorld();
        if (level.isClientSide()) {
            return;
        }
        ItemStack weapon = entity.getMainHandItem();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        if (level instanceof ServerLevel serverLevel) {
            ItemStack[] emeralds = new ItemStack[]{
                    new ItemStack(Items.EMERALD),                           //0
                    new ItemStack(Items.EMERALD_BLOCK),                     //1
                    new ItemStack(ModItems.REFINED_EMERALD.get()),          //2
                    new ItemStack(ModItems.REFINED_EMERALD_BLOCK.get()),    //3
                    new ItemStack(ModItems.REFINED_EMERALD_T_2.get()),      //4
                    new ItemStack(ModItems.REFINED_EMERALD_BLOCK_2.get()),  //5
                    new ItemStack(ModItems.REFINED_EMERALD_T_3.get()),      //6
                    new ItemStack(ModItems.REFINED_EMERALD_BLOCK_3.get()),  //7
                    new ItemStack(ModItems.REFINED_EMERALD_PLUS.get())      //8
            };
            if (weapon.is(ModTags.GENESIS_EMERALD_TOOLS)) {
                if (weapon.is(ItemTags.AXES) && !state.is(BlockTags.MINEABLE_WITH_AXE)) return;
                 else if (weapon.is(ItemTags.PICKAXES) && !state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return;
                else if (weapon.is(ItemTags.SHOVELS) && !state.is(BlockTags.MINEABLE_WITH_SHOVEL)) return;
                else if (weapon.is(ItemTags.HOES) && !state.is(BlockTags.MINEABLE_WITH_HOE)) return;
            } else return;
            if (SimpleUse.Random_static.nextBoolean()) {
                weapon.hurtAndBreak(1, serverLevel, entity, a -> {
                });
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 0.5;
                double z = pos.getZ() + 0.5;
                int random = SimpleUse.Random_static.nextInt(45);
                int cumulative = 0;
                int index = 0;
                for (int i = 0; i < 9; i++) {
                    cumulative += (9 - i);
                    if (random < cumulative) {
                        index = i;
                        break;
                    }
                }
                ItemEntity itemEntity = new ItemEntity(serverLevel, x, y, z, emeralds[index]);
                itemEntity.setDefaultPickUpDelay();
                serverLevel.addFreshEntity(itemEntity);
            }
        }
    }
}