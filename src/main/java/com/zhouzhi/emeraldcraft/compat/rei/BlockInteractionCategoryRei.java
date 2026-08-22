package com.zhouzhi.emeraldcraft.compat.rei;

import com.zhouzhi.emeraldcraft.init.ModBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class BlockInteractionCategoryRei implements DisplayCategory<BlockInteractionDisplayRei> {

    @Override
    public CategoryIdentifier<? extends BlockInteractionDisplayRei> getCategoryIdentifier() {
        return EmeraldcraftReiPlugin.BLOCK_INTERACTION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category.emeraldcraft.block_interaction");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.REFINED_EMERALD_BLOCK);
    }

    @Override
    public int getDisplayHeight() {
        return 50;
    }

    @Override
    public int getDisplayWidth(BlockInteractionDisplayRei display) {
        return 100;
    }

    @Override
    public List<Widget> setupDisplay(BlockInteractionDisplayRei display, Rectangle bounds) {
        int centerX = bounds.getCenterX();
        int centerY = bounds.getCenterY();
        Point startPoint = new Point(centerX - 50, centerY - 20);
        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5, startPoint.y + 5))
                .entries(display.getInputEntries().get(0)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 5, startPoint.y + 25))
                .entries(display.getInputEntries().get(1)));

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 35, startPoint.y + 10)));

        widgets.add(Widgets.createSlot(new Point(startPoint.x + 70, startPoint.y + 15))
                .entries(display.getOutputEntries().get(0))
                .markOutput());

        return widgets;
    }
}