package com.zhouzhi.emeraldcraft.item.genesis_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class GenesisEmeraldPickaxeItem extends PickaxeItem {
    private static final short radius = 5;
    private static final Tier TOOL_TIER = new Tier() {
        @Override
        public int getUses() {
            return 51691;
        }

        @Override
        public float getSpeed() {
            return 200f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        @MethodsReturnNonnullByDefault
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 100;
        }

        @Override
        @MethodsReturnNonnullByDefault
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(ModItems.GENESIS_EMERALD.get()));
        }
    };

    public GenesisEmeraldPickaxeItem() {
        super(TOOL_TIER, new Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 9f, -2.8f)).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    @MethodsReturnNonnullByDefault
    public InteractionResultHolder<ItemStack> use(@ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Player player, @ParametersAreNonnullByDefault InteractionHand hand) {
        InteractionResultHolder<ItemStack> itemStackInteractionResultHolder = super.use(world, player, hand);
        if (player instanceof ServerPlayer serverPlayer) {
            var position = player.blockPosition();
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    for (int dz = -radius; dz <= radius; dz++) {
                        var pos = position.north(dx).below(dy).west(dz);
                        var state = world.getBlockState(pos);
                        if (SimpleUse.isStone(state.getBlock())) {
                            serverPlayer.gameMode.destroyBlock(pos);
                            world.levelEvent(2001, pos, Block.getId(state));
                        }
                    }
                }
            }
        }
        return itemStackInteractionResultHolder;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
        return true;
    }
}