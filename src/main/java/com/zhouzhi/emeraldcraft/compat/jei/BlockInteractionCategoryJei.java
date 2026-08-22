package com.zhouzhi.emeraldcraft.compat.jei;

import com.zhouzhi.emeraldcraft.init.ModBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class BlockInteractionCategoryJei implements IRecipeCategory<BlockInteractionJeiRecipe> {

    private final IDrawable background;
    private final IDrawable icon;

    public BlockInteractionCategoryJei(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(100, 50);
        this.icon = guiHelper.createDrawableItemStack(ModBlocks.REFINED_EMERALD_BLOCK.toStack());
    }

    @Override
    public RecipeType<BlockInteractionJeiRecipe> getRecipeType() {
        return JeiRecipeTypes.BLOCK_INTERACTION;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.category.emeraldcraft.block_interaction");
    }

    @SuppressWarnings("deprecation")
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlockInteractionJeiRecipe recipe, IFocusGroup focuses) {
        // 输入
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 5)
                .addItemStack(recipe.inputItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 25)
                .addItemStack(recipe.targetBlock());

        // 输出
        builder.addSlot(RecipeIngredientRole.OUTPUT, 70, 15)
                .addItemStack(recipe.outputItem());
    }

    @Override
    public void draw(BlockInteractionJeiRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawString(
                Minecraft.getInstance().font,
                "→",
                45, 15,
                0xFFFFFFFF,
                false
        );
    }
}