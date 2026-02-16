package com.zhouzhi.emeraldcraft.item;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class IronHoeInlaidWithRefinedEmeraldItemT2 extends HoeItem {
    private static final Tier TOOL_TIER = new Tier() {
        @Override
        public int getUses() {
            return 1579;
        }

        @Override
        public float getSpeed() {
            return 16f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 12;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(EmeraldcraftItems.REFINED_EMERALD_T_2.get()));
        }
    };

    public IronHoeInlaidWithRefinedEmeraldItemT2() {
        super(TOOL_TIER, new Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 0f, 0f)));
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            Use.IronToolBeingDamagedPerTick(world,entity, itemstack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(ItemStack itemstack) {
        return false;
    }
}