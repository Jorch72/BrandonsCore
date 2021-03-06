package com.brandon3055.brandonscore.client.gui.modulargui.guielements;

import cofh.redstoneflux.api.IEnergyHandler;
import com.brandon3055.brandonscore.blocks.TileEnergyBase;
import com.brandon3055.brandonscore.client.ResourceHelperBC;
import com.brandon3055.brandonscore.client.gui.modulargui.MGuiElementBase;
import com.brandon3055.brandonscore.utils.InfoHelper;
import com.brandon3055.brandonscore.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brandon3055 on 18/10/2016.
 */
public class GuiEnergyBar extends MGuiElementBase<GuiEnergyBar> {

    private int energy = 0;
    private int maxEnergy = 0;
    private boolean drawHoveringText = true;
    private IEnergyHandler energyHandler;
    private boolean horizontal = false;

    public GuiEnergyBar() {}

    public GuiEnergyBar(int xPos, int yPos) {
        super(xPos, yPos);
    }

    public GuiEnergyBar(int xPos, int yPos, int xSize, int ySize) {
        super(xPos, yPos, xSize, ySize);
    }

    public GuiEnergyBar setEnergy(int energy, int maxEnergy) {
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        return this;
    }

    public GuiEnergyBar setEnergy(int energy) {
        this.energy = energy;
        return this;
    }

    public GuiEnergyBar setDrawHoveringText(boolean drawHoveringText) {
        this.drawHoveringText = drawHoveringText;
        return this;
    }

    public GuiEnergyBar setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        return this;
    }

    public GuiEnergyBar setEnergyHandler(IEnergyHandler energyHandler) {
        this.energyHandler = energyHandler;
        return this;
    }

    @Override
    public void renderElement(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        super.renderElement(minecraft, mouseX, mouseY, partialTicks);
        ResourceHelperBC.bindTexture("textures/gui/energy_gui.png");
        int size = horizontal ? xSize() : ySize();
        int draw = (int) ((double) energy / (double) maxEnergy * (size - 2));

        int posY = yPos();
        int posX = xPos();

        if (horizontal) {
            int x = posY;
            posY = posX;
            posX = x;
            GlStateManager.pushMatrix();
            GlStateManager.translate(size + (posY * 2), 0, 0);
            GlStateManager.rotate(90, 0, 0, 1);
        }

        GlStateManager.color(1F, 1F, 1F);
        drawTexturedModalRect(posX, posY, 0, 0, 14, size);
        drawTexturedModalRect(posX, posY + size - 1, 0, 255, 14, 1);
        drawTexturedModalRect(posX + 1, posY + size - draw - 1, 14, size - draw, 12, draw);

        if (horizontal) {
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean renderOverlayLayer(Minecraft minecraft, int mouseX, int mouseY, float partialTicks) {
        if (drawHoveringText && isMouseOver(mouseX, mouseY)) {
            List<String> list = new ArrayList<>();
            list.add(InfoHelper.ITC() + I18n.format("gui.de.energyStorage.txt"));
            list.add(InfoHelper.HITC() + Utils.formatNumber(energy) + " / " + Utils.formatNumber(maxEnergy));
            list.add(TextFormatting.GRAY + "[" + Utils.addCommas(energy) + " RF]");
            drawHoveringText(list, mouseX, mouseY, fontRenderer, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
            return true;
        }

        return super.renderOverlayLayer(minecraft, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean onUpdate() {
        if (energyHandler != null) {
            maxEnergy = energyHandler.getMaxEnergyStored(EnumFacing.UP);
            energy = energyHandler.getEnergyStored(EnumFacing.UP);
        }
        if (energyHandler instanceof TileEnergyBase) {
            energy = ((TileEnergyBase) energyHandler).energySync.value;
        }
        return super.onUpdate();
    }
}
