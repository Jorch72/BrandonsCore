package com.brandon3055.brandonscore.client.gui.modulargui.markdown.mdelements;

import com.brandon3055.brandonscore.client.gui.modulargui.MGuiElementBase;
import com.brandon3055.brandonscore.client.gui.modulargui.lib.BCFontRenderer;
import com.brandon3055.brandonscore.client.gui.modulargui.markdown.LayoutHelper;
import com.brandon3055.brandonscore.client.gui.modulargui.markdown.reader.visitor.property.HAlign;
import com.brandon3055.brandonscore.client.gui.modulargui.markdown.reader.visitor.property.VAlign;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by brandon3055 on 5/31/2018.
 */
public abstract class MDElementBase<E extends MGuiElementBase<E>> extends MGuiElementBase<E> {

    public int size = 0;
    public int width = -1;
    public int height = -1;
    public List<String> tooltip = new ArrayList<>();
    public boolean enableTooltip = true;
    public boolean screenRelativeSize = false;
    public HAlign hAlign = HAlign.LEFT;
    public VAlign vAlign = VAlign.TOP;
    protected int colour = 0;
    protected boolean hasColour = false;
    protected int colourHover = 0;
    protected boolean hasColourHover = false;
    protected int colourBorder = 0;
    protected boolean hasColourBorder = false;
    protected int colourBorderHover = 0;
    protected boolean hasColourBorderHover = false;
    public int leftPad = 0;
    public int rightPad = 0;
    public int topPad = 0;
    public int bottomPad = 0;
    private String elementError = "";
    public boolean hasSubParts = false;
    public LinkedList<MDElementBase> subParts = new LinkedList<>();
    public Map<String, String> invalidProps = new HashMap<>();

    public MDElementBase() {
    }

    public MDElementBase(int xSize, int ySize) {
        setSize(xSize, ySize);
    }

    public void layoutElement(LayoutHelper layout, List<MDElementBase> lineElement) {
        Point pos = layout.nextElementPos(xSize(), ySize());
        setPos(pos.x, pos.y);

        //For compound elements like text this will be laying out each of the sub elements so the size of this element is irrelevant.
        //This can also be used to set the width and height of the element as needed for things like images with screen relative sizes
    }

    public void error(String errorMessage) {
        if (elementError.isEmpty()){
            this.elementError = errorMessage;
        }
        else {
            this.elementError += " and " + errorMessage;
        }
    }

    public String getError() {
        return elementError;
    }

    public void setColour(int colour) {
        this.colour = colour;
        hasColour = true;
    }

    public void setColourBorder(int colourBorder) {
        this.colourBorder = colourBorder;
        hasColourBorder = true;
    }

    public void setColourHover(int colourHover) {
        this.colourHover = colourHover;
        hasColourHover = true;
    }

    public void setColourBorderHover(int colourBorderHover) {
        this.colourBorderHover = colourBorderHover;
        hasColourBorderHover = true;
    }

    public int getColour(boolean mouseOver) {
        if (mouseOver && hasColourHover) {
            return colourHover;
        }

        return colour;
    }

    public int getColourBorder(boolean mouseOver) {
        if (mouseOver && hasColourBorderHover) {
            return colourBorderHover;
        }

        return colourBorder;
    }

    @Override
    public boolean renderOverlayLayer(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        if (!invalidProps.isEmpty()) {
            List<String> list = new ArrayList<>();
            invalidProps.forEach((s, s2) -> list.add("§cProperty \"" + s + "\" is invalid or not supported by this tag!§c"));
            zOffset += 100;
            BCFontRenderer.setStileToggleMode(true);
            drawHoveringText(list, xPos() - 8, yPos() + 15, fontRenderer, screenWidth, screenHeight);
            BCFontRenderer.setStileToggleMode(false);
            fontRenderer.resetStyles();
            zOffset -= 100;
        }

        return super.renderOverlayLayer(minecraft, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderElement(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        super.renderElement(minecraft, mouseX, mouseY, partialTicks);
//        drawBorderedRect(xPos(), yPos(), xSize(), ySize(), 1, 0, 0xFF00FF00);
    }
}