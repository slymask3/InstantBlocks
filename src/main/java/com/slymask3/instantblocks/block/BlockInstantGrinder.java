package com.slymask3.instantblocks.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.slymask3.instantblocks.InstantBlocks;
import com.slymask3.instantblocks.creativetab.InstantBlocksTab;
import com.slymask3.instantblocks.handler.ConfigurationHandler;
import com.slymask3.instantblocks.init.ModBlocks;
import com.slymask3.instantblocks.init.ModItems;
import com.slymask3.instantblocks.reference.Colors;
import com.slymask3.instantblocks.reference.Names;
import com.slymask3.instantblocks.utility.InstantBlocksFunctions;

public class BlockInstantGrinder extends Block {
	private InstantBlocksFunctions ibf = new InstantBlocksFunctions();
	private ConfigurationHandler config = new ConfigurationHandler();
	private InstantBlocks ib = new InstantBlocks();
	private ModBlocks mb = new ModBlocks();
	private ModItems mi = new ModItems();
	
    public BlockInstantGrinder() {
        super(Material.rock);
        setCreativeTab(InstantBlocksTab.INSTANTBLOCKS_TAB);
        setBlockName("instantblocks:" + Names.Blocks.IB_GRINDER);
        setHardness(1.5F);
        setResistance(2000F);
        setStepSound(Block.soundTypeMetal);
    }
    
    public int quantityDropped(Random r) {
        return 1;
    }
	
    public static IIcon[] textures = new IIcon[6];
    
	public void registerBlockIcons(IIconRegister ir) {
		textures[0] = ir.registerIcon("instantblocks:grinder");
		textures[1] = ir.registerIcon("instantblocks:grinder");
		textures[2] = ir.registerIcon("instantblocks:grinder");
		textures[3] = ir.registerIcon("instantblocks:grinder");
		textures[4] = ir.registerIcon("instantblocks:grinder");
		textures[5] = ir.registerIcon("instantblocks:grinder"); //instantblocks:grinder
	}
    
	public IIcon getIcon(int side, int meta) {
		return textures[side];
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
    	if (world.getBlock(x, y-1, z) != Blocks.mob_spawner) {
    		ibf.msg(player, ibf.grinderError, Colors.c);
			return true;
    	}
    	
    	ItemStack is = player.getCurrentEquippedItem();
    	
		if (config.useWands == true) {
			if (is != null && (is.getItem() == mi.ibWandWood || is.getItem() == mi.ibWandStone || is.getItem() == mi.ibWandIron || is.getItem() == mi.ibWandGold || is.getItem() == mi.ibWandDiamond)) {
				is.damageItem(1, player);
				//player.triggerAchievement(ib.achGrinder);
			} else {
				ibf.msg(player, ibf.wandReq, Colors.c);
				return true;
			}
		}
		
		build(world, x, y, z, player);

		ibf.keepBlocks(world, x, y, z, mb.ibGrinder);
		ibf.xp(world, player, config.xp);
    	
		ibf.sound(world, config.sound, x, y, z);
		ibf.effectFull(world, "reddust", x, y, z);
		ibf.msg(player, ibf.grinderCreate, Colors.a);
    	
		return true;
    }

	private void build(World world, int x, int y, int z, EntityPlayer player) {
		/*int stone = Block.stone.blockID;
		int water = Block.waterMoving.blockID;
		int torch = Block.torchWood.blockID;
		int chest = Block.chest.blockID;
		int craft = Block.workbench.blockID;
		int door = Block.doorWood.blockID;
		int glass = Block.glass.blockID;
		int pane = Block.thinGlass.blockID;
		int sign = Block.signWall.blockID;
		int air = 0;*/
		
		Block stone = Blocks.stone;
		Block water = Blocks.water;
		Block torch = Blocks.torch;
		Block chest = Blocks.chest;
		Block craft = Blocks.crafting_table;
		Block door = Blocks.wooden_door;
		Block glass = Blocks.glass;
		Block pane = Blocks.glass_pane;
		Block sign = Blocks.wall_sign;
		Block air = Blocks.air;
		
		/************************ Layer -5 : Stone ************************/
		ibf.build(world, x-5, y-5, z-5, stone, 11, 1, 11);
		ibf.build(world, x+6, y-5, z-1, stone, 3, 1, 3);
		ibf.build(world, x+9, y-5, z-3, stone, 7, 1, 8);

		/************************ Layer -4 to -2 : Air (Spawn Room) ************************/
		ibf.build(world, x-4, y-4, z-4, air, 9, 3, 9);

		/************************ Layer -1 to 0 : Air (Spawn Room) ************************/
		ibf.build(world, x-4, y-1, z-4, air, 4, 2, 9);
		ibf.build(world, x-4, y-1, z+1, air, 4, 2, 9);
		ibf.build(world, x-4, y-1, z, air, 1, 2, 4);
		ibf.build(world, x+1, y-1, z, air, 1, 2, 4);

		/************************ Layer 1 to 2 : Air (Spawn Room) ************************/
		ibf.build(world, x-4, y+1, z-4, air, 9, 2, 9);

		/************************ Layer -4 to -1 : Air (Output Room) ************************/
		ibf.build(world, x+10, y-4, z-2, air, 5, 3, 6);
		
		/************************ Layer -4 ************************/
		ibf.build(world, x-5, y-4, z-5, stone, 11, 1, 5);
		ibf.build(world, x, y-4, z-5, stone, 5, 1, 1);
		ibf.build(world, x, y-4, z+1, stone, 5, 1, 1);
		ibf.build(world, x+1, y-4, z-5, stone, 4, 1, 1);
		ibf.build(world, x+1, y-4, z+2, stone, 4, 1, 1);
		ibf.build(world, x+2, y-4, z-5, stone, 3, 1, 1);
		ibf.build(world, x+2, y-4, z+3, stone, 3, 1, 1);
		ibf.build(world, x+3, y-4, z-5, stone, 2, 1, 1);
		ibf.build(world, x+3, y-4, z+4, stone, 2, 1, 1);
		ibf.build(world, x+4, y-4, z-5, stone, 1, 1, 1);
		ibf.build(world, x+4, y-4, z+5, stone, 1, 1, 1);
		ibf.build(world, x+5, y-4, z-5, stone, 5, 1, 1);
		ibf.build(world, x+5, y-4, z+1, stone, 5, 1, 1);
		ibf.build(world, x+6, y-4, z-1, stone, 1, 1, 4);
		ibf.build(world, x+6, y-4, z+1, stone, 1, 1, 4);
		ibf.build(world, x+9, y-4, z-3, stone, 2, 1, 1);
		ibf.build(world, x+9, y-4, z+2, stone, 2, 1, 1);
		ibf.build(world, x+10, y-4, z-3, stone, 1, 1, 6);
		ibf.build(world, x+10, y-4, z+3, stone, 1, 1, 6);
		ibf.build(world, x+16, y-4, z-3, stone, 7, 1, 1);

		world.setBlock(x+5, y-4, z, air);
		world.setBlock(x+6, y-4, z, sign, 3, 0); //SIGN
		//world.setBlock(x+7, y-4, z, door, 5, 0); //DOOR
		world.setBlock(x+8, y-4, z, air);
		world.setBlock(x+9, y-4, z, door, 7, 0); //DOOR
		world.setBlock(x+10, y-4, z-2, stone);
		world.setBlock(x+10, y-4, z+2, stone);
		world.setBlock(x+15, y-4, z+2, chest);
		world.setBlock(x+15, y-4, z+1, chest);
		world.setBlock(x+15, y-4, z, craft);
		world.setBlock(x+15, y-4, z-1, chest);
		world.setBlock(x+15, y-4, z-2, chest);

		/************************ Layer -3 ************************/
		ibf.build(world, x-5, y-3, z-5, stone, 11, 1, 1);
		ibf.build(world, x-4, y-3, z-5, stone, 3, 1, 1);
		ibf.build(world, x-4, y-3, z+3, stone, 3, 1, 1);
		ibf.build(world, x-3, y-3, z-5, stone, 2, 1, 1);
		ibf.build(world, x-3, y-3, z+4, stone, 2, 1, 1);
		ibf.build(world, x-2, y-3, z-5, stone, 1, 1, 7);
		ibf.build(world, x-2, y-3, z+5, stone, 1, 1, 7);
		ibf.build(world, x+5, y-3, z-5, stone, 11, 1, 1);
		ibf.build(world, x+6, y-3, z-1, stone, 1, 1, 4);
		ibf.build(world, x+6, y-3, z+1, stone, 1, 1, 4);
		ibf.build(world, x+9, y-3, z-3, stone, 2, 1, 1);
		ibf.build(world, x+9, y-3, z+2, stone, 2, 1, 1);
		ibf.build(world, x+10, y-3, z-3, stone, 1, 1, 6);
		ibf.build(world, x+10, y-3, z+3, stone, 1, 1, 6);
		ibf.build(world, x+16, y-3, z-3, stone, 7, 1, 1);

		world.setBlock(x+5, y-3, z, sign, 3, 0); //SIGN
		//world.setBlock(x+7, y-3, z, door, 11, 0); //DOOR
		world.setBlock(x+8, y-3, z, air);
		world.setBlock(x+9, y-3, z, door, 9, 0); //DOOR
		world.setBlock(x+6, y-3, z, water);

		/************************ Layer -2 to 2 : Stone (Spawn Room Walls) ************************/
		ibf.build(world, x-5, y-2, z-5, stone, 11, 5, 1);
		ibf.build(world, x-4, y-2, z-5, stone, 1, 5, 9);
		ibf.build(world, x-4, y-2, z+5, stone, 1, 5, 9);
		ibf.build(world, x+5, y-2, z-5, stone, 11, 5, 1);

		/************************ Layer -2 ************************/
		ibf.build(world, x+6, y-2, z-1, stone, 3, 1, 4);
		ibf.build(world, x+9, y-2, z-3, stone, 2, 1, 1);
		ibf.build(world, x+9, y-2, z+2, stone, 2, 1, 1);
		ibf.build(world, x+10, y-2, z-3, stone, 1, 1, 6);
		ibf.build(world, x+10, y-2, z+3, stone, 1, 1, 6);
		ibf.build(world, x+16, y-2, z-3, stone, 7, 1, 1);

		world.setBlock(x-4, y-2, z-4, water);
		world.setBlock(x-4, y-2, z+4, water);
		world.setBlock(x+10, y-2, z-1, pane);
		world.setBlock(x+10, y-2, z+1, pane);
		world.setBlock(x+11, y-2, z-2, pane);
		world.setBlock(x+11, y-2, z-1, pane);
		world.setBlock(x+11, y-2, z+2, pane);
		world.setBlock(x+11, y-2, z+1, pane);
		world.setBlock(x+10, y-2, z, torch);
		world.setBlock(x+12, y-2, z-2, torch);
		world.setBlock(x+12, y-2, z+2, torch);
		world.setBlock(x+15, y-2, z-1, torch);
		world.setBlock(x+15, y-2, z+1, torch);
		world.setBlock(x+6, y-2, z, sign, 3, 0); //SIGN

		/************************ Layer 3 : Stone (Spawn Room Roof) ************************/
		ibf.build(world, x-5, y+3, z-5, stone, 11, 1, 11);

		/************************ Layer -1 : Stone (Output Room Roof) ************************/
		ibf.build(world, x+9, y-1, z-3, stone, 7, 1, 8);

		/************************ Layer -1 to 21 : Stone (Input Tube) ************************/
		ibf.build(world, x+5, y-1, z-1, stone, 3, 23, 3);

		/************************ Layer -1 to 21 : Stone (Output Tube 1) ************************/
		ibf.build(world, x+9, y-1, z+1, stone, 3, 23, 3);

		/************************ Layer -1 to 21 : Stone (Output Tube 1) ************************/
		ibf.build(world, x+9, y-1, z-3, stone, 3, 23, 3);

		/************************ Layer 18 to 21 : Stone (Top Room) ************************/
		ibf.build(world, x+7, y+18, z-3, stone, 7, 4, 4);

		/************************ Layer -1 to 20 : Air (Input Tube) ************************/
		ibf.build(world, x+6, y-1, z, air, 1, 22, 1);

		/************************ Layer -1 to 20 : Air (Output Tube 1) ************************/
		ibf.build(world, x+10, y-1, z+2, air, 1, 22, 1);

		/************************ Layer -1 to 20 : Air (Output Tube 1) ************************/
		ibf.build(world, x+10, y-1, z-2, air, 1, 22, 1);

		/************************ Layer 19 to 20 : Stone (Top Room) ************************/
		ibf.build(world, x+7, y+19, z, air, 1, 2, 1);
		ibf.build(world, x+8, y+19, z-2, air, 5, 2, 1);
		ibf.build(world, x+9, y+19, z-2, air, 1, 2, 2);
		ibf.build(world, x+9, y+19, z+2, air, 1, 2, 2);

		/************************ Layer 19 (Top Room) ************************/
		world.setBlock(x+8, y+19, z, sign, 4, 0);
		world.setBlock(x+8, y+19, z-1, water);
		world.setBlock(x+8, y+19, z+1, water);
		world.setBlock(x+10, y+19, z-2, sign, 4, 0);
		world.setBlock(x+10, y+19, z+2, sign, 4, 0);

		/************************ Layer -1 to 19 (Input Tube) ************************/
		world.setBlock(x+6, y-1, z, water);
		world.setBlock(x+6, y, z, sign, 3, 0);
		world.setBlock(x+6, y+1, z, water);
		world.setBlock(x+6, y+2, z, sign, 3, 0);
		world.setBlock(x+6, y+3, z, water);
		world.setBlock(x+6, y+4, z, sign, 3, 0);
		world.setBlock(x+6, y+5, z, water);
		world.setBlock(x+6, y+6, z, sign, 3, 0);
		world.setBlock(x+6, y+7, z, water);
		world.setBlock(x+6, y+8, z, sign, 3, 0);
		world.setBlock(x+6, y+9, z, water);
		world.setBlock(x+6, y+10, z, sign, 3, 0);
		world.setBlock(x+6, y+11, z, water);
		world.setBlock(x+6, y+12, z, sign, 3, 0);
		world.setBlock(x+6, y+13, z, water);
		world.setBlock(x+6, y+14, z, sign, 3, 0);
		world.setBlock(x+6, y+15, z, water);
		world.setBlock(x+6, y+16, z, sign, 3, 0);
		world.setBlock(x+6, y+17, z, water);
		world.setBlock(x+6, y+18, z, sign, 3, 0);
		world.setBlock(x+6, y+19, z, water);
		
		world.setBlock(x, y, z, air);
		
		/************************ Teleport ************************/
		if (config.tpGrinder == true) {
			//ibf.sound(world, config.sound, x+13, y-4, z);
			player.setPositionAndUpdate(x+13 + 0.5, y-4 + 0.5, z + 0.5);
    		ibf.sound(world, config.sound, (int)player.posX, (int)player.posY, (int)player.posZ);
			world.setBlock(x+7, y-4, z, glass);
			world.setBlock(x+7, y-3, z, glass);
		}
	}
}