package com.slymask3.instantblocks.block;

import java.io.*;
import java.util.Map;
import java.util.Random;

import com.slymask3.instantblocks.InstantBlocks;
import com.slymask3.instantblocks.creativetab.InstantBlocksTab;
import com.slymask3.instantblocks.handler.ConfigurationHandler;
import com.slymask3.instantblocks.init.ModBlocks;
import com.slymask3.instantblocks.init.ModItems;
import com.slymask3.instantblocks.reference.Colors;
import com.slymask3.instantblocks.reference.Names;
import com.slymask3.instantblocks.utility.InstantBlocksFunctions;


import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.src.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.*;

public class BlockInstantFall extends BlockDirectional {
	private InstantBlocksFunctions ibf = new InstantBlocksFunctions();
	private ConfigurationHandler config = new ConfigurationHandler();
	private InstantBlocks ib = new InstantBlocks();
	private ModBlocks mb = new ModBlocks();
	private ModItems mi = new ModItems();
	
    public BlockInstantFall() {
        super(Material.cloth);
        setCreativeTab(InstantBlocksTab.INSTANTBLOCKS_TAB);
        setBlockName("instantblocks:" + Names.Blocks.IB_RAINBOW_SKYDIVE);
        setHardness(1.5F);
        setResistance(2000F);
        setStepSound(Block.soundTypeCloth);
    }
    
    public int quantityDropped(Random random) {
        return 1;
    }

    public static IIcon bottom;
    public static IIcon top;
    public static IIcon side;
    public static IIcon side0;
    
	public void registerBlockIcons(IIconRegister ir) {
		if (config.animated.getBoolean(true)) {
		//if (true) {
			bottom = ir.registerIcon("instantblocks:skydive_bottom");
			top = ir.registerIcon("instantblocks:skydive_top");
			side = ir.registerIcon("instantblocks:skydive_side");
		} else {
			//textures[0] = ir.registerIcon("cloth_13");
			//textures[1] = ir.registerIcon("cloth_14");
			side0 = ir.registerIcon("instantblocks:skydive_side_0");
		}
	}
    
	public IIcon getIcon(int side, int meta) {
		if (config.animated.getBoolean(true)) {
			if (side == 0) {
				return bottom;
			} else if (side == 1) {
				return top;
			} else if (side>=2 && side<=5) {
				return this.side;
			}
		} else {
			if (side == 0) {
				return Blocks.wool.getIcon(0, 13);
			} else if (side == 1) {
				return Blocks.wool.getIcon(0, 14);
			} else if (side>=2 && side<=5) {
				return this.side0;
			}
		}
		return blockIcon;
	}
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack par6ItemStack) {
        int meta = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        par1World.setBlockMetadataWithNotify(par2, par3, par4, meta, 2);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
    	ItemStack is = player.getCurrentEquippedItem();
    	
		if (config.useWands == true) {
			if (is != null && (is.getItem() == mi.ibWandWood || is.getItem() == mi.ibWandStone || is.getItem() == mi.ibWandIron || is.getItem() == mi.ibWandGold || is.getItem() == mi.ibWandDiamond)) {
				is.damageItem(1, player);
				//player.triggerAchievement(ib.achFall);
			} else {
				ibf.msg(player, ibf.wandReq, Colors.c);
				return true;
			}
		}
		
		build(world, x, y, z, player);
		
		ibf.keepBlocks(world, x, y, z, mb.ibFall);
		ibf.xp(world, player, config.xp);
		
		ibf.sound(world, config.sound, x, y, z);
		ibf.effectFull(world, "reddust", x, y, z);
		ibf.msg(player, ibf.fallCreate, Colors.a);
    	
		return true;
    }

	private void build(World world, int x, int y, int z, EntityPlayer player) {
		/*int wool = Block.cloth.blockID;
		int stone = Block.stone.blockID;
		int water = Block.waterStill.blockID;
		int ladder = Block.ladder.blockID;*/
		
		Block wool = Blocks.wool;
		Block stone = Blocks.stone;
		Block water = Blocks.water;
		Block ladder = Blocks.ladder;
		
		int meta = world.getBlockMetadata(x, y, z);
		
		/************************ 0 : Red (14) ************************/
		for (int c = 286; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[0], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[0], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[0], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[0], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[0], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[0], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[0], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[0], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[0], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[0], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[0], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[0], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 1 : Orange (1) ************************/
		for (int c = 283; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[1], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[1], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[1], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[1], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[1], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[1], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[1], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[1], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[1], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[1], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[1], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[1], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 2 : Yellow (4) ************************/
		for (int c = 280; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[2], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[2], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[2], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[2], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[2], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[2], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[2], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[2], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[2], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[2], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[2], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[2], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 3 : Lime (5) ************************/
		for (int c = 277; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[3], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[3], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[3], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[3], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[3], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[3], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[3], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[3], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[3], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[3], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[3], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[3], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 4 : Green (13) ************************/
		for (int c = 274; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[4], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[4], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[4], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[4], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[4], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[4], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[4], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[4], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[4], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[4], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[4], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[4], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 5 : Cyan (9) ************************/
		for (int c = 271; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[5], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[5], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[5], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[5], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[5], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[5], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[5], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[5], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[5], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[5], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[5], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[5], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 6 : Light Blue (3) ************************/
		for (int c = 268; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[6], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[6], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[6], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[6], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[6], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[6], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[6], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[6], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[6], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[6], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[6], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[6], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 7 : Blue (11) ************************/
		for (int c = 265; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[7], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[7], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[7], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[7], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[7], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[7], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[7], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[7], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[7], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[7], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[7], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[7], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 8 : Purple (10) ************************/
		for (int c = 262; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[8], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[8], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[8], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[8], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[8], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[8], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[8], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[8], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[8], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[8], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[8], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[8], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 9 : Light Purple (2) ************************/
		for (int c = 259; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[9], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[9], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[9], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[9], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[9], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[9], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[9], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[9], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[9], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[9], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[9], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[9], 0, 1, 3, 1); //CORNER
		}
		
		/************************ 10 : Pink (6) ************************/
		for (int c = 256; c >= 1; c =  c - 33) {
			ibf.buildMeta(world, x-5, c, z-2, wool, config.wool[10], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x+5, c, z-2, wool, config.wool[10], 0, 5, 3, 1); //WALL
			ibf.buildMeta(world, x-2, c, z+5, wool, config.wool[10], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x-2, c, z-5, wool, config.wool[10], 0, 1, 3, 5); //WALL
			ibf.buildMeta(world, x+3, c, z+4, wool, config.wool[10], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+3, c, z-4, wool, config.wool[10], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z+3, wool, config.wool[10], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x+4, c, z-3, wool, config.wool[10], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z+4, wool, config.wool[10], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-3, c, z-4, wool, config.wool[10], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z+3, wool, config.wool[10], 0, 1, 3, 1); //CORNER
			ibf.buildMeta(world, x-4, c, z-3, wool, config.wool[10], 0, 1, 3, 1); //CORNER
		}

		/************************ Air ************************/
		for (int c = 256; c >= 1; c--) {
			ibf.build(world, x-3, c, z-3, Blocks.air, 7, 1, 7); //CENTER
			ibf.build(world, x-4, c, z-2, Blocks.air, 5, 1, 1); //WALL
			ibf.build(world, x+4, c, z-2, Blocks.air, 5, 1, 1); //WALL
			ibf.build(world, x-2, c, z+4, Blocks.air, 1, 1, 5); //WALL
			ibf.build(world, x-2, c, z-4, Blocks.air, 1, 1, 5); //WALL
		}

		/************************ Stone (Prevent Lava Burning) ************************/
		for (int c = 50; c >= 1; c--) {
			ibf.build(world, x-6, c, z-3, stone, 7, 1, 1); //WALL
			ibf.build(world, x+6, c, z-3, stone, 7, 1, 1); //WALL
			ibf.build(world, x-3, c, z+6, stone, 1, 1, 7); //WALL
			ibf.build(world, x-3, c, z-6, stone, 1, 1, 7); //WALL
			
			ibf.build(world, x-5, c, z-4, stone, 2, 1, 1); //PRE-CORNER
			ibf.build(world, x-5, c, z+3, stone, 2, 1, 1); //PRE-CORNER

			ibf.build(world, x+5, c, z-4, stone, 2, 1, 1); //PRE-CORNER
			ibf.build(world, x+5, c, z+3, stone, 2, 1, 1); //PRE-CORNER
			
			ibf.build(world, x-4, c, z-5, stone, 1, 1, 2); //PRE-CORNER
			ibf.build(world, x+3, c, z-5, stone, 1, 1, 2); //PRE-CORNER

			ibf.build(world, x-4, c, z+5, stone, 1, 1, 2); //PRE-CORNER
			ibf.build(world, x+3, c, z+5, stone, 1, 1, 2); //PRE-CORNER

			ibf.build(world, x+4, c, z+4, stone, 1, 1, 1); //CORNER
			ibf.build(world, x+4, c, z-4, stone, 1, 1, 1); //CORNER
			ibf.build(world, x-4, c, z+4, stone, 1, 1, 1); //CORNER
			ibf.build(world, x-4, c, z-4, stone, 1, 1, 1); //CORNER
		}

		/************************ Water ************************/
		for (int c = 4; c > 1; c--) {
			ibf.build(world, x-3, c, z-3, water, 7, 1, 7); //CENTER
			ibf.build(world, x-4, c, z-2, water, 5, 1, 1); //WALL
			ibf.build(world, x+4, c, z-2, water, 5, 1, 1); //WALL
			ibf.build(world, x-2, c, z+4, water, 1, 1, 5); //WALL
			ibf.build(world, x-2, c, z-4, water, 1, 1, 5); //WALL
		}
		
		/************************ Floor ************************/
		ibf.buildMeta(world, x-3, 1, z-3, wool, config.wool[7], 0, 7, 1, 7); //CENTER
		ibf.buildMeta(world, x-4, 1, z-2, wool, config.wool[7], 0, 5, 1, 1); //WALL
		ibf.buildMeta(world, x+4, 1, z-2, wool, config.wool[7], 0, 5, 1, 1); //WALL
		ibf.buildMeta(world, x-2, 1, z+4, wool, config.wool[7], 0, 1, 1, 5); //WALL
		ibf.buildMeta(world, x-2, 1, z-4, wool, config.wool[7], 0, 1, 1, 5); //WALL

		/************************ Ladder ************************/
		if (meta == 0) {
			for (int c = 256; c >= 5; c--) {
				ibf.buildMeta(world, x-2, c, z-4, ladder, 3, 0, 1, 1, 5);
			}
		
			if (config.tpFall == true) {
				ibf.sound(world, config.sound, x, 256, z+5);
				if (!world.isRemote) { //IF SERVER
					player.setPositionAndUpdate(x + 0.5, 257 + 0.5, z+5 + 0.5);
				}
			}
		} else if (meta == 1) {
			for (int c = 256; c >= 5; c--) {
				ibf.buildMeta(world, x+4, c, z-2, ladder, 4, 0, 5, 1, 1);
			}
		
			if (config.tpFall == true) {
				ibf.sound(world, config.sound, x-5, 256, z);
				if (!world.isRemote) { //IF SERVER
					player.setPositionAndUpdate(x-5 + 0.5, 257 + 0.5, z + 0.5);
				}
			}
		} else if (meta == 2) {
			for (int c = 256; c >= 5; c--) {
				ibf.buildMeta(world, x-2, c, z+4, ladder, 2, 0, 1, 1, 5);
			}
		
			if (config.tpFall == true) {
				ibf.sound(world, config.sound, x, 256, z-5);
				if (!world.isRemote) { //IF SERVER
					player.setPositionAndUpdate(x + 0.5, 257 + 0.5, z-5 + 0.5);
				}
			}
		} else if (meta == 3) {
			for (int c = 256; c >= 5; c--) {
				ibf.buildMeta(world, x-4, c, z-2, ladder, 5, 0, 5, 1, 1);
			}
		
			if (config.tpFall == true) {
				ibf.sound(world, config.sound, x+5, 256, z);
				if (!world.isRemote) { //IF SERVER
					player.setPositionAndUpdate(x+5 + 0.5, 257 + 0.5, z + 0.5);
	        		ibf.sound(world, config.sound, (int)player.posX, (int)player.posY, (int)player.posZ);
				}
			}
		}
	}
}