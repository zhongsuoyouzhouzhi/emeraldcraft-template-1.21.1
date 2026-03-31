package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldT3Right_clickOnBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class RefinedEmeraldT3Item extends Item {
	public RefinedEmeraldT3Item() {
		super(new Item.Properties().fireResistant().rarity(Rarity.RARE));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public boolean isFoil(@ParametersAreNonnullByDefault ItemStack itemstack) {
		return true;
	}

	@Override
	@MethodsReturnNonnullByDefault
	public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
		super.useOn(context);
		RefinedEmeraldT3Right_clickOnBlock.execute(context.getLevel(), context.getClickedPos().getX(), context.getClickedPos().getY(), context.getClickedPos().getZ(), context.getPlayer(), context.getItemInHand());
		return InteractionResult.SUCCESS;
	}
}