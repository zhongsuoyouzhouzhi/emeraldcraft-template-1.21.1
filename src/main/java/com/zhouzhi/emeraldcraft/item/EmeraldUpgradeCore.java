package com.zhouzhi.emeraldcraft.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class EmeraldUpgradeCore extends Item {
    public EmeraldUpgradeCore() {
        super(new Item.Properties().rarity(Rarity.EPIC).fireResistant());
    }
}
