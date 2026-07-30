package com.zhouzhi.emeraldcraft.item.void_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class VoidEmeraldShieldItem extends ShieldItem {
    private static final int BAR_COLOR_BASE = FastColor.ARGB32.color(0, 25, 58, 33);
    private static final int BAR_COLOR_ENDANGER = FastColor.ARGB32.color(0, 95, 245, 190);
    private static final int BAR_COLOR_FINAL = FastColor.ARGB32.color(0, 255, 255, 255);
    private static final Tier SHIELD_TIER = new Tier() {
        @Override
        public int getUses() {
            return 1836;
        }

        @Override
        public float getSpeed() {
            return 0;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 90;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(ModItems.VOID_EMERALD.get()));
        }
    };

    public VoidEmeraldShieldItem() {
        super(new Properties()
                        .durability(SHIELD_TIER.getUses())
                        .setNoRepair()
                        .component(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY)
                        .fireResistant()
                        .rarity(Rarity.EPIC)
        );
    }

    @Override
    public int getEnchantmentValue() {
        return SHIELD_TIER.getEnchantmentValue();
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack toRepair, ItemStack repair) {
        return repair.is(ModItems.VOID_EMERALD.get());
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        int rest = stack.getMaxDamage() - stack.getDamageValue();
        if (rest > 250) {
            return BAR_COLOR_BASE;
        } else if (rest > 200) {
            return BAR_COLOR_ENDANGER;
        } else return BAR_COLOR_FINAL;
    }
}
