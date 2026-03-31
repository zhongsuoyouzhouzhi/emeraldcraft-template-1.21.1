package com.zhouzhi.emeraldcraft.item.remined_emerald;

import com.zhouzhi.emeraldcraft.procedures.others.RefinedEmeraldT2Right_clickOnBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

public class RefinedEmeraldT2Item extends Item {
	public RefinedEmeraldT2Item() {
		super(new Item.Properties().rarity(Rarity.UNCOMMON));
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
        BlockPos a = context.getClickedPos();
		RefinedEmeraldT2Right_clickOnBlock.execute(context.getLevel(), a.getX(), a.getY(), a.getZ(),context.getItemInHand());
		return InteractionResult.SUCCESS;
	}
}