package com.slymask3.instantblocks.block;

import com.slymask3.instantblocks.handler.Config;
import com.slymask3.instantblocks.init.ModBlocks;
import com.slymask3.instantblocks.reference.Colors;
import com.slymask3.instantblocks.reference.Strings;
import com.slymask3.instantblocks.util.BuildHelper;
import com.slymask3.instantblocks.util.Coords;
import com.slymask3.instantblocks.util.IBHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockInstantLiquid extends BlockInstant {
	public ArrayList<Coords> coordsList;
	public Block blockCheck;
	public Block blockReplace;
	public String create;
	public String create1;
	public boolean isSuction = false;

    public BlockInstantLiquid(Block block, String name, Material material, SoundType soundType, float hardness, Block blockCheck, Block blockReplace) {
        super(block, name, material, soundType, hardness);
		this.coordsList = new ArrayList<>();
		this.blockCheck = blockCheck;
		this.blockReplace = blockReplace;
    }

	private int getMax() {
		return isSuction ? Config.MAX_FILL : Config.MAX_LIQUID;
	}

	private Block getMainReplaceBlock() {
		if(isSuction) {
			return blockCheck == Blocks.water ? ModBlocks.ibWater : ModBlocks.ibLava;
		}
		return blockReplace;
	}

	public boolean canActivate(World world, int x, int y, int z, EntityPlayer player) {
		if(isSuction && Config.SHOW_EFFECTS) {
			world.spawnParticle("cloud", (double)x + 0.5D, (double)y + 1.2D, (double)z + 0.5D, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", (double)x + 1.2D, (double)y + 0.5D, (double)z + 0.5D, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", (double)x + 0.5D, (double)y + 0.5D, (double)z + 1.2D, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", (double)x + 0.5D, (double)y - 0.2D, (double)z + 0.5D, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", (double)x - 0.2D, (double)y + 0.5D, (double)z + 0.5D, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("cloud", (double)x + 0.5D, (double)y + 0.5D, (double)z - 0.2D, 0.0D, 0.0D, 0.0D);
		}
		checkForBlock(world,x,y,z);
		if(isSuction && coordsList.isEmpty()) {
			IBHelper.msg(player, Strings.ERROR_NO_LIQUID, Colors.c);
			return false;
		}
		return coordsList.size() < getMax();
	}

	public void build(World world, int x, int y, int z, EntityPlayer player) {
		for(Coords coords : coordsList) {
			BuildHelper.setBlock(world, coords.getX(), coords.getY(), coords.getZ(), blockReplace);
		}
		BuildHelper.setBlock(world,x, y, z, getMainReplaceBlock());
		if(coordsList.size() > 0) {
			setCreateMsg(create.replace("%i%",String.valueOf(isSuction ? coordsList.size() : coordsList.size()+1)).replace("%type%",blockCheck == Blocks.water ? "water" : "lava"));
		} else {
			setCreateMsg(create1.replace("%type%",blockCheck == Blocks.water ? "water" : "lava"));
		}
		coordsList = new ArrayList<>();
		if(isSuction) {
			this.blockCheck = null;
		}
	}

	private void checkForBlock(World world, int x, int y, int z) {
		check(world,x+1,y,z);
		check(world,x-1,y,z);
		check(world,x,y,z+1);
		check(world,x,y,z-1);
		if(!Config.SIMPLE_LIQUID || isSuction) {
			check(world,x,y-1,z);
		}
		if(isSuction) {
			check(world,x,y+1,z);
		}
	}

	private void check(World world, int x, int y, int z) {
		if(isCorrectBlock(BuildHelper.getBlock(world,x,y,z)) && coordsList.size() < getMax() && addCoords(x,y,z)) {
			if(blockCheck == null) {
				blockCheck = BuildHelper.getBlock(world,x,y,z);
			}
			checkForBlock(world,x,y,z);
		}
	}

	private boolean isCorrectBlock(Block block) {
		return blockCheck == null ? block == Blocks.water || block == Blocks.lava : block == blockCheck;
	}

	private boolean addCoords(int x, int y, int z) {
		Coords coords = new Coords(x,y,z);
		if(!coordsList.contains(coords)) {
			coordsList.add(coords);
			return true;
		}
		return false;
	}
}