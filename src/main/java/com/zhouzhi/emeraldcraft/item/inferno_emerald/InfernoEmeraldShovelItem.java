package com.zhouzhi.emeraldcraft.item.inferno_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class InfernoEmeraldShovelItem extends ShovelItem {
    private static final int BAR_COLOR = FastColor.ARGB32.color(0, 240, 119, 53);
    private static final Tier TIER = new Tier() {
        @Override
        public int getUses() {
            return 40313;
        }

        @Override
        public float getSpeed() {
            return 80f;
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
            return 80;
        }

        @Override
        @MethodsReturnNonnullByDefault
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(ModItems.INFERNO_EMERALD.get()));
        }
    };

    public InfernoEmeraldShovelItem() {
        super(TIER, new Properties()
                .attributes(DiggerItem.createAttributes(TIER, 9f, -3f))
                .component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                .fireResistant()
                .rarity(Rarity.EPIC));
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

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return BAR_COLOR;
    }
}
