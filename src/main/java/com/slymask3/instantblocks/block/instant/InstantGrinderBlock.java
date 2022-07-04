package com.slymask3.instantblocks.block.instant;

import com.slymask3.instantblocks.block.InstantBlock;
import com.slymask3.instantblocks.handler.Config;
import com.slymask3.instantblocks.reference.Strings;
import com.slymask3.instantblocks.util.Builder;
import com.slymask3.instantblocks.util.Helper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Random;

public class InstantGrinderBlock extends InstantBlock {
	public InstantGrinderBlock() {
		super(Block.Properties.of(Material.METAL)
				.strength(1.5F)
				.sound(SoundType.METAL)
				.noOcclusion()
		, Config.Common.DISABLE_GRINDER);
        setCreateMessage(Strings.CREATE_GRINDER);
    }

	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		for(int i=0; i<4; i++) {
			double d0 = (double)pos.getX() + random.nextDouble();
			double d1 = (double)pos.getY() + random.nextDouble();
			double d2 = (double)pos.getZ() + random.nextDouble();
			world.addParticle(ParticleTypes.HAPPY_VILLAGER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}

	public boolean canActivate(Level world, int x, int y, int z, Player player) {
		if(Helper.getBlock(world,x, y-1, z) != Blocks.SPAWNER) {
			Helper.sendMessage(player, Strings.ERROR_GRINDER);
			return false;
		}
		return true;
	}

	public boolean build(Level world, int x, int y, int z, Player player) {
		Block water = Blocks.WATER;
		Block torch = Blocks.WALL_TORCH;
		Block chest = Blocks.CHEST;
		Block craft = Blocks.CRAFTING_TABLE;
		Block door = Blocks.OAK_DOOR;
		Block glass = Blocks.GLASS;
		Block pane = Blocks.GLASS_PANE;
		Block sign = Blocks.OAK_WALL_SIGN;
		Block plate = Blocks.OAK_PRESSURE_PLATE;
		Block air = Blocks.AIR;

		/************************ Layer -5 : Stone ************************/
		Builder.Multiple.setup(world,x-5,y-5,z-5,11,1,11).setStone().build();
		Builder.Multiple.setup(world,x+6,y-5,z-1,3,1,3).setStone().build();
		Builder.Multiple.setup(world,x+9,y-5,z-3,8,1,7).setStone().build();

		/************************ Layer -4 to -2 : Air (Spawn Room) ************************/
		Builder.Multiple.setup(world,x-4,y-4,z-4,9,3,9).setBlock(air).build();

		/************************ Layer -1 to 0 : Air (Spawn Room) ************************/
		Builder.Multiple.setup(world,x-4,y-1,z-4,9,2,4).setBlock(air).build();
		Builder.Multiple.setup(world,x-4,y-1,z+1,9,2,4).setBlock(air).build();
		Builder.Multiple.setup(world,x-4,y-1,z,4,2,1).setBlock(air).build();
		Builder.Multiple.setup(world,x+1,y-1,z,4,2,1).setBlock(air).build();

		/************************ Layer 1 to 2 : Air (Spawn Room) ************************/
		Builder.Multiple.setup(world,x-4,y+1,z-4,9,2,9).setBlock(air).build();

		/************************ Layer -4 to -1 : Air (Output Room) ************************/
		Builder.Multiple.setup(world,x+10,y-4,z-2,6,3,5).setBlock(air).build();
		
		/************************ Layer -4 ************************/
		Builder.Multiple.setup(world,x-5,y-4,z-5,5,1,11).setStone().build();
		Builder.Multiple.setup(world,x,y-4,z-5,1,1,5).setStone().build();
		Builder.Multiple.setup(world,x,y-4,z+1,1,1,5).setStone().build();
		Builder.Multiple.setup(world,x+1,y-4,z-5,1,1,4).setStone().build();
		Builder.Multiple.setup(world,x+1,y-4,z+2,1,1,4).setStone().build();
		Builder.Multiple.setup(world,x+2,y-4,z-5,1,1,3).setStone().build();
		Builder.Multiple.setup(world,x+2,y-4,z+3,1,1,3).setStone().build();
		Builder.Multiple.setup(world,x+3,y-4,z-5,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+3,y-4,z+4,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+4,y-4,z-5,1,1,1).setStone().build();
		Builder.Multiple.setup(world,x+4,y-4,z+5,1,1,1).setStone().build();
		Builder.Multiple.setup(world,x+5,y-4,z-5,1,1,5).setStone().build();
		Builder.Multiple.setup(world,x+5,y-4,z+1,1,1,5).setStone().build();
		Builder.Multiple.setup(world,x+6,y-4,z-1,4,1,1).setStone().build();
		Builder.Multiple.setup(world,x+6,y-4,z+1,4,1,1).setStone().build();
		Builder.Multiple.setup(world,x+9,y-4,z-3,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+9,y-4,z+2,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+10,y-4,z-3,6,1,1).setStone().build();
		Builder.Multiple.setup(world,x+10,y-4,z+3,6,1,1).setStone().build();
		Builder.Multiple.setup(world,x+16,y-4,z-3,1,1,7).setStone().build();

		Builder.Single.setup(world,x+5,y-4,z).setBlock(air).build();
		Builder.Single.setup(world,x+6,y-4,z).setBlock(sign).setDirection(Direction.NORTH).build(); //SIGN
		Builder.Single.setup(world,x+8,y-4,z).setBlock(air).build();
		Builder.Single.setup(world,x+9,y-4,z).setBlock(door).setDirection(Direction.WEST).build(); //DOOR
		Builder.Single.setup(world,x+10,y-4,z-2).setStone().build();
		Builder.Single.setup(world,x+10,y-4,z+2).setStone().build();
		Builder.Single.setup(world,x+15,y-4,z+2).setBlock(chest).setDirection(Direction.WEST).build();
		Builder.Single.setup(world,x+15,y-4,z+1).setBlock(chest).setDirection(Direction.WEST).build();
		Builder.Single.setup(world,x+15,y-4,z).setBlock(craft).build();
		Builder.Single.setup(world,x+15,y-4,z-1).setBlock(chest).setDirection(Direction.WEST).build();
		Builder.Single.setup(world,x+15,y-4,z-2).setBlock(chest).setDirection(Direction.WEST).build();

		/************************ Layer -3 ************************/
		Builder.Multiple.setup(world,x-5,y-3,z-5,1,1,11).setStone().build();
		Builder.Multiple.setup(world,x-4,y-3,z-5,1,1,3).setStone().build();
		Builder.Multiple.setup(world,x-4,y-3,z+3,1,1,3).setStone().build();
		Builder.Multiple.setup(world,x-3,y-3,z-5,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x-3,y-3,z+4,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x-2,y-3,z-5,7,1,1).setStone().build();
		Builder.Multiple.setup(world,x-2,y-3,z+5,7,1,1).setStone().build();
		Builder.Multiple.setup(world,x+5,y-3,z-5,1,1,11).setStone().build();
		Builder.Multiple.setup(world,x+6,y-3,z-1,4,1,1).setStone().build();
		Builder.Multiple.setup(world,x+6,y-3,z+1,4,1,1).setStone().build();
		Builder.Multiple.setup(world,x+9,y-3,z-3,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+9,y-3,z+2,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+10,y-3,z-3,6,1,1).setStone().build();
		Builder.Multiple.setup(world,x+10,y-3,z+3,6,1,1).setStone().build();
		Builder.Multiple.setup(world,x+16,y-3,z-3,1,1,7).setStone().build();

		Builder.Single.setup(world,x+5,y-3,z).setBlock(sign).setDirection(Direction.NORTH).build(); //SIGN
		Builder.Single.setup(world,x+8,y-3,z).setBlock(air).build();
		Builder.Single.setup(world,x+6,y-3,z).setBlock(water).build();

		/************************ Layer -2 to 2 : Stone (Spawn Room Walls) ************************/
		Builder.Multiple.setup(world,x-5,y-2,z-5,1,5,11).setStone().build();
		Builder.Multiple.setup(world,x-4,y-2,z-5,9,5,1).setStone().build();
		Builder.Multiple.setup(world,x-4,y-2,z+5,9,5,1).setStone().build();
		Builder.Multiple.setup(world,x+5,y-2,z-5,1,5,11).setStone().build();

		/************************ Layer -2 ************************/
		Builder.Multiple.setup(world,x+6,y-2,z-1,4,1,3).setStone().build();
		Builder.Multiple.setup(world,x+9,y-2,z-3,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+9,y-2,z+2,1,1,2).setStone().build();
		Builder.Multiple.setup(world,x+10,y-2,z-3,6,1,1).setStone().build();
		Builder.Multiple.setup(world,x+10,y-2,z+3,6,1,1).setStone().build();
		Builder.Multiple.setup(world,x+16,y-2,z-3,1,1,7).setStone().build();

		Builder.Single.setup(world,x-4,y-2,z-4).setBlock(water).build();
		Builder.Single.setup(world,x-4,y-2,z+4).setBlock(water).build();
		Builder.Single.setup(world,x+10,y-2,z-1).setBlock(pane).build();
		Builder.Single.setup(world,x+10,y-2,z+1).setBlock(pane).build();
		Builder.Single.setup(world,x+11,y-2,z-2).setBlock(pane).build();
		Builder.Single.setup(world,x+11,y-2,z-1).setBlock(pane).build();
		Builder.Single.setup(world,x+11,y-2,z+2).setBlock(pane).build();
		Builder.Single.setup(world,x+11,y-2,z+1).setBlock(pane).build();
		Builder.Single.setup(world,x+10,y-2,z).setBlock(torch).setDirection(Direction.EAST).build();
		Builder.Single.setup(world,x+12,y-2,z-2).setBlock(torch).setDirection(Direction.SOUTH).build();
		Builder.Single.setup(world,x+12,y-2,z+2).setBlock(torch).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+15,y-2,z-1).setBlock(torch).setDirection(Direction.WEST).build();
		Builder.Single.setup(world,x+15,y-2,z+1).setBlock(torch).setDirection(Direction.WEST).build();
		Builder.Single.setup(world,x+6,y-2,z).setBlock(sign).setDirection(Direction.NORTH).build(); //SIGN

		/************************ Layer 3 : Stone (Spawn Room Roof) ************************/
		Builder.Multiple.setup(world,x-5,y+3,z-5,11,1,11).setStone().build();

		/************************ Layer -1 : Stone (Output Room Roof) ************************/
		Builder.Multiple.setup(world,x+9,y-1,z-3,8,1,7).setStone().build();

		/************************ Layer -1 to 21 : Stone (Input Tube) ************************/
		Builder.Multiple.setup(world,x+5,y-1,z-1,3,23,3).setStone().build();

		/************************ Layer -1 to 21 : Stone (Output Tube 1) ************************/
		Builder.Multiple.setup(world,x+9,y-1,z+1,3,23,3).setStone().build();

		/************************ Layer -1 to 21 : Stone (Output Tube 1) ************************/
		Builder.Multiple.setup(world,x+9,y-1,z-3,3,23,3).setStone().build();

		/************************ Layer 18 to 21 : Stone (Top Room) ************************/
		Builder.Multiple.setup(world,x+7,y+18,z-3,4,4,7).setStone().build();

		/************************ Layer -1 to 20 : Air (Input Tube) ************************/
		Builder.Multiple.setup(world,x+6,y-1,z,1,22,1).setBlock(air).build();

		/************************ Layer -1 to 20 : Air (Output Tube 1) ************************/
		Builder.Multiple.setup(world,x+10,y-1,z+2,1,22,1).setBlock(air).build();

		/************************ Layer -1 to 20 : Air (Output Tube 1) ************************/
		Builder.Multiple.setup(world,x+10,y-1,z-2,1,22,1).setBlock(air).build();

		/************************ Layer 19 to 20 : Stone (Top Room) ************************/
		Builder.Multiple.setup(world,x+7,y+19,z,1,2,1).setBlock(air).build();
		Builder.Multiple.setup(world,x+8,y+19,z-2,1,2,5).setBlock(air).build();
		Builder.Multiple.setup(world,x+9,y+19,z-2,2,2,1).setBlock(air).build();
		Builder.Multiple.setup(world,x+9,y+19,z+2,2,2,1).setBlock(air).build();

		/************************ Layer 19 (Top Room) ************************/
		Builder.Single.setup(world,x+8,y+19,z).setBlock(plate).build();
		Builder.Single.setup(world,x+8,y+19,z-1).setBlock(water).build();
		Builder.Single.setup(world,x+8,y+19,z+1).setBlock(water).build();
		Builder.Single.setup(world,x+10,y+19,z-2).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+10,y+19,z+2).setBlock(sign).setDirection(Direction.NORTH).build();

		/************************ Layer -1 to 19 (Input Tube) ************************/
		Builder.Single.setup(world,x+6,y-1,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+1,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+2,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+3,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+4,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+5,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+6,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+7,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+8,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+9,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+10,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+11,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+12,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+13,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+14,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+15,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+16,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+17,z).setBlock(water).build();
		Builder.Single.setup(world,x+6,y+18,z).setBlock(sign).setDirection(Direction.NORTH).build();
		Builder.Single.setup(world,x+6,y+19,z).setBlock(water).build();
		
		Builder.Single.setup(world,x,y,z).setBlock(air).build();
		
		/************************ Teleport ************************/
		if(Config.Common.TP_GRINDER.get()) {
			Builder.Single.setup(world,x+7,y-4,z).setBlock(glass).build();
			Builder.Single.setup(world,x+7,y-3,z).setBlock(glass).build();
			Helper.teleport(world,player,x+13,y-4,z);
		}

		return true;
	}
}