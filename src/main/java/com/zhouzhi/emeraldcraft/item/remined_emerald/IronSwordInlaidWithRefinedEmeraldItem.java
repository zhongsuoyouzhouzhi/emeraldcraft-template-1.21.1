package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class IronSwordInlaidWithRefinedEmeraldItem extends SwordItem {
    private static final Tier TOOL_TIER = new Tier() {
        @Override
        public int getUses() {
            return 1012;
        }

        @Override
        public float getSpeed() {
            return 10f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 10;
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(EmeraldcraftItems.REFINED_EMERALD.get()));
        }
    };

    public IronSwordInlaidWithRefinedEmeraldItem() {
        super(TOOL_TIER, new Item.Properties().attributes(SwordItem.createAttributes(TOOL_TIER, 6.25f, -2.4f)));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemstack, @NotNull LivingEntity entity, @NotNull LivingEntity sourceEntity) {
        boolean a = super.hurtEnemy(itemstack, entity, sourceEntity);
        Use.IronSwordHitLivingThings(entity,sourceEntity);
        return a;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@NotNull ItemStack itemstack) {
        return false;
    }
}