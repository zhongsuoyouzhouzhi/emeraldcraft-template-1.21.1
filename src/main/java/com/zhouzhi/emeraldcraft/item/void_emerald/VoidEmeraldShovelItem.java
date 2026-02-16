package com.zhouzhi.emeraldcraft.item;

import com.zhouzhi.emeraldcraft.init.EmeraldcraftItems;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class VoidEmeraldShovelItem extends ShovelItem {
	private static final Tier TOOL_TIER = new Tier() {
		@Override
		public int getUses() {
			return 11243;
		}

		@Override
		public float getSpeed() {
			return 110f;
		}

		@Override
		public float getAttackDamageBonus() {
			return 1;
		}

		@Override
		public TagKey<Block> getIncorrectBlocksForDrops() {
			return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
		}

		@Override
		public int getEnchantmentValue() {
			return 60;
		}

		@Override
		public Ingredient getRepairIngredient() {
			return Ingredient.of(new ItemStack(EmeraldcraftItems.VOID_EMERALD.get()));
		}
	};

	public VoidEmeraldShovelItem() {
		super(TOOL_TIER, new Properties().attributes(DiggerItem.createAttributes(TOOL_TIER, 13f, -3f)).fireResistant());
	}
    /*空气中右键
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
		InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);

		return ar;
	}
    */
    /*拿着
    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (selected)
            Use.RefinedEmeraldT3ToolIsBeingDamagedPerTick(world, entity, itemstack);
    }
    */
	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(ItemStack itemstack) {
		return false;
	}
}