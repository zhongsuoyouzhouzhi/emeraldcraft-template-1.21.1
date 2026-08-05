package com.zhouzhi.emeraldcraft.item.genesis_emerald;

import com.zhouzhi.emeraldcraft.init.ModItems;
import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import com.zhouzhi.emeraldcraft.procedures.net.Use;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.level.ServerLevel;
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

public class GenesisEmeraldSwordItem  extends SwordItem {
    private static final Tier SWORD_TIER = new Tier() {
        @Override
        public int getUses() {
            return 49152;
        }

        @Override
        public float getSpeed() {
            return 100f;
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

    public GenesisEmeraldSwordItem() {
        super(SWORD_TIER, new Item.Properties().attributes(SwordItem.createAttributes(SWORD_TIER, 149f, 0)).fireResistant().rarity(Rarity.EPIC));
    }

    @Override
    @MethodsReturnNonnullByDefault
    public InteractionResultHolder<ItemStack> use(@ParametersAreNonnullByDefault Level world, @ParametersAreNonnullByDefault Player entity, @ParametersAreNonnullByDefault InteractionHand hand) {
        InteractionResultHolder<ItemStack> itemStackInteractionResultHolder = super.use(world, entity, hand);
        ItemStack itemstack = itemStackInteractionResultHolder.getObject();
        if (!SimpleUse.GameTypeGetter.isCreativeOrSpectator(entity) && world instanceof ServerLevel serverLevel) {
            itemstack.hurtAndBreak(50, serverLevel,entity,ignore->{});
        }
        Use.EntityPause.pauseEntities(entity.level(),entity.position(),15,10);
        entity.getCooldowns().addCooldown(itemstack.getItem(), 300);
        return itemStackInteractionResultHolder;
    }
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
        return true;
    }
}