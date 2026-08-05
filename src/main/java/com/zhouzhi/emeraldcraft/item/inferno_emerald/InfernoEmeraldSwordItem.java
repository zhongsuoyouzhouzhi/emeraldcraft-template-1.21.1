package com.zhouzhi.emeraldcraft.item.inferno_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.ParametersAreNonnullByDefault;

public class InfernoEmeraldSwordItem extends SwordItem {
    private static final Tier TIER = new Tier() {
        @Override
        public int getUses() {
            return 42898;
        }

        @Override
        public float getSpeed() {
            return 60f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0f;
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

    public InfernoEmeraldSwordItem() {
        super(TIER,
                new Properties()
                        .attributes(SwordItem.createAttributes(TIER, 24f, -1.8f))
                        .component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                        .fireResistant()
                        .rarity(Rarity.EPIC)
        );
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
