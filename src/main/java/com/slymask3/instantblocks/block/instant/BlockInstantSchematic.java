package com.slymask3.instantblocks.block.instant;


import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.slymask3.instantblocks.InstantBlocks;
import com.slymask3.instantblocks.creativetab.InstantBlocksTab;
import com.slymask3.instantblocks.handler.ConfigurationHandler;
import com.slymask3.instantblocks.init.ModItems;
import com.slymask3.instantblocks.reference.Colors;
import com.slymask3.instantblocks.reference.GuiID;
import com.slymask3.instantblocks.reference.Names;
import com.slymask3.instantblocks.reference.Strings;
import com.slymask3.instantblocks.reference.Textures;
import com.slymask3.instantblocks.tileentity.TileEntitySchematic;
import com.slymask3.instantblocks.utility.IBHelper;
import com.slymask3.instantblocks.utility.SchematicHelper;

public class BlockInstantSchematic extends BlockContainer implements ITileEntityProvider {

	public BlockInstantSchematic() {
		//super(ModBlocks.ibSchematic, Names.Blocks.IB_SCHEMATIC, Material.cactus, Block.soundTypeAnvil, 1.5F);
		super(Material.wood);
		setCreativeTab(InstantBlocksTab.INSTANTBLOCKS_TAB);
		setBlockName("instantblocks:" + Names.Blocks.IB_SCHEMATIC);
		setHardness(1.5F);
		setResistance(2000F);
		setStepSound(Block.soundTypeWood);
        setBlockTextureName(Textures.Harvest.SIDE0);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntitySchematic();
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		ItemStack is = player.getCurrentEquippedItem();
    	
		if (ConfigurationHandler.useWands == true) {
			if (is != null && (is.getItem() == ModItems.ibWandWood || is.getItem() == ModItems.ibWandStone || is.getItem() == ModItems.ibWandIron || is.getItem() == ModItems.ibWandGold || is.getItem() == ModItems.ibWandDiamond)) {
				//is.damageItem(1, player);
			} else {
				IBHelper.msg(player, Strings.wandReq, Colors.c);
				return true;
			}
		}
		
		
		
		player.openGui(InstantBlocks.instance, GuiID.SCHEMATIC.ordinal(), world, x, y, z);
		
		return true;
	}
	
	public void build(World world, int x, int y, int z, int meta, String playerS, String schematic, boolean center) {
		EntityPlayer player = world.getPlayerEntityByName(playerS);
		
		try{
			File f = new File("schematics/"+schematic+".schematic");
			
			SchematicHelper.loadSchematic(world, x, y ,z, f, center);
			
			ItemStack is = player.getCurrentEquippedItem();
	    	
			if (ConfigurationHandler.useWands == true) {
				if (is != null && (is.getItem() == ModItems.ibWandWood || is.getItem() == ModItems.ibWandStone || is.getItem() == ModItems.ibWandIron || is.getItem() == ModItems.ibWandGold || is.getItem() == ModItems.ibWandDiamond)) {
					is.damageItem(1, player);
				}
			}
		} catch(Exception e) {}
		
	}

}