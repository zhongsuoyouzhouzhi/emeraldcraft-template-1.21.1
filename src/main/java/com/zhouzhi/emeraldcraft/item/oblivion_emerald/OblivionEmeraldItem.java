package com.zhouzhi.emeraldcraft.item.oblivion_emerald;

import com.zhouzhi.emeraldcraft.procedures.compress.SimpleUse;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.zhouzhi.emeraldcraft.item.void_emerald.VoidEmeraldItem.explode;

public class OblivionEmeraldItem extends Item {
    private static final int BAR_COLOR = FastColor.ARGB32.color(0, 90, 0, 125);

    public OblivionEmeraldItem() {
        super(new Properties().rarity(Rarity.EPIC).fireResistant().durability(500));
    }
    @Override
    @MethodsReturnNonnullByDefault
    public InteractionResult useOn(@ParametersAreNonnullByDefault UseOnContext context) {
        super.useOn(context);
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        explode(world,pos,player,9);
        context.getItemInHand().shrink(1);
        if (player != null) {
            if (!SimpleUse.GameTypeGetter.isCreativeOrSpectator(player)) {
                context.getItemInHand().hurtAndBreak(1,player, EquipmentSlot.MAINHAND);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, @ParametersAreNonnullByDefault BlockState state) {
        Tool tool = stack.get(DataComponents.TOOL);
        return tool != null ? tool.getMiningSpeed(state) : 1.8F;
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return BAR_COLOR;
    }
}
