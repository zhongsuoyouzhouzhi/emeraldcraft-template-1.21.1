package com.zhouzhi.emeraldcraft.item.genesis_emerald;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class GenesisEmeraldItem extends Item {
    public GenesisEmeraldItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
        return true;
    }
}
