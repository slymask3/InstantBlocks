package com.slymask3.instantblocks.gui;

import java.util.ArrayList;

import net.minecraft.client.renderer.Tessellator;
import cpw.mods.fml.client.GuiScrollingList;

public class GuiTreeSlot extends GuiScrollingList {
    private GuiTree parent;
    private int[] trees;
    private static int slotHeight = 15;

    public GuiTreeSlot(GuiTree parent, int[] trees, int x, int y, int width, int height) {
        super(parent.getMinecraftInstance(), width, height, y, y+height, x, slotHeight);
        this.parent=parent;
        this.trees=trees;
    }

    public void updateTrees(int[] trees) {
    	this.trees = trees;
    }
    
    @Override
    protected int getSize()
    {
        return trees.length;
    }

    @Override
    protected void elementClicked(int var1, boolean var2)
    {
        this.parent.selectSchematicIndex(var1);
    }

    @Override
    protected boolean isSelected(int var1)
    {
        return this.parent.schematicIndexSelected(var1);
    }

    @Override
    protected void drawBackground() {
        //draw nothing
    }

    @Override
    protected int getContentHeight() {
        return (this.getSize()) * slotHeight + 1;
    }

    @Override
    protected void drawSlot(int i, int var2, int var3, int var4, Tessellator var5) {
    	this.parent.getFontRenderer().drawString(this.parent.getFontRenderer().trimStringToWidth(treeToString(trees[i]), listWidth - 10), this.left + 3 , var3 + 2, treeToColor(trees[i]));
    }
    
    private String treeToString(int tree) {
    	switch(tree) {
    	case 0: return "Oak Tree";
    	case 1: return "Spruce Tree";
    	case 2: return "Birch Tree";
    	case 3: return "Jungle Tree";
    	case 4: return "Big Jungle Tree - Coming Soon";
    	case 5: return "Acacia Tree";
    	case 6: return "Dark Oak Tree";
    	case 7: return "Reverse Tree";
    	case 8: return "Glass Tree";
    	case 9: return "Coming Soon";
	    default: return "Error";
    	}
    }
    
    private int treeToColor(int tree) {
    	switch(tree) {
    	case 0: return 0xFFFFFF;
    	case 1: return 0xFFFFFF;
    	case 2: return 0xFFFFFF;
    	case 3: return 0xFFFFFF;
    	case 4: return 0xAA0000;
    	case 5: return 0xFFFFFF;
    	case 6: return 0xFFFFFF;
    	case 7: return 0xFFFFFF;
    	case 8: return 0xFFFFFF;
    	case 9: return 0xAA0000;
	    default: return 0xAA0000;
    	}
    }

}