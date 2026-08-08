package com.zhouzhi.emeraldcraft.listening;

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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class MiningListening {
    @SubscribeEvent
    public void LavaEmeraldAndInfernoEmeraldToolMining(BlockEvent.BreakEvent event){
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
            ItemStack item = SimpleUse.getSmeltedResult(serverLevel, state.getBlock().asItem().getDefaultInstance());
            if (item.isEmpty()) return;
            if (weapon.is(ModTags.LAVA_EMERALD_TOOLS) || weapon.is(ModTags.LAVA_EMERALD_T2_TOOLS) || weapon.is(ModTags.INFERNO_EMERALD_TOOLS)) {
                if (weapon.is(ItemTags.AXES) && !state.is(BlockTags.MINEABLE_WITH_AXE)) return;
                else if (weapon.is(ItemTags.PICKAXES) && !state.is(BlockTags.MINEABLE_WITH_PICKAXE)) return;
                else if (weapon.is(ItemTags.SHOVELS) && !state.is(BlockTags.MINEABLE_WITH_SHOVEL)) return;
                else if (weapon.is(ItemTags.HOES) && !state.is(BlockTags.MINEABLE_WITH_HOE)) return;
            }
                else return;
            event.setCanceled(true);
            level.destroyBlock(event.getPos(), false, entity);
            weapon.hurtAndBreak(1, serverLevel, entity, a -> {
            });
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity(serverLevel, x, y, z, item);
            itemEntity.setDefaultPickUpDelay();
            serverLevel.addFreshEntity(itemEntity);
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, x, y, z, 24, 0.2, 0.2, 0.2, 0.05);
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
}