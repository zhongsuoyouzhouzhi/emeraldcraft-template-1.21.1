package com.zhouzhi.emeraldcraft.item.oblivion_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.TagChange;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OblivionEmeraldShieldItem extends ShieldItem {
    private static final int BAR_COLOR_BASE = FastColor.ARGB32.color(0, 174, 120, 255);
    private static final int BAR_COLOR_FINAL = FastColor.ARGB32.color(0, 255, 188, 255);
    private static final Tier SHIELD_TIER = new Tier() {
        @Override
        public int getUses() {
            return 28110;
        }

        @Override
        public float getSpeed() {
            return 0F;
        }

        @Override
        public float getAttackDamageBonus() {
            return 2;
        }

        @Override
        public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 150;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(ModItems.OBLIVION_EMERALD.get()));
        }
    };

    public OblivionEmeraldShieldItem() {
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
        return repair.is(ModItems.OBLIVION_EMERALD.get());
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, context, tooltipComponents, isAdvanced);
        tooltipComponents.add(Component.translatable("tooltip.emeraldcraft.oblivion_shield.desc")
                .append(TagChange.getOrCreateComponent(stack,"AbsorbedDamage","0.0")));
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        int rest = stack.getMaxDamage() - stack.getDamageValue();
        if (rest > 1) {
            return BAR_COLOR_BASE;
        } else return BAR_COLOR_FINAL;
    }
}
