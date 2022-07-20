package com.slymask3.instantblocks.block.instant;

import com.slymask3.instantblocks.Common;
import com.slymask3.instantblocks.block.InstantLiquidBlock;
import com.slymask3.instantblocks.reference.Strings;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class InstantWaterBlock extends InstantLiquidBlock {
    public InstantWaterBlock() {
        super(Block.Properties.of(Material.WATER)
                .strength(0.5F)
                .sound(new LiquidSoundType(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY))
                .noOcclusion()
                .isSuffocating((state, world, pos) -> false)
                .isValidSpawn((state, world, pos, entityType) -> false)
                .isRedstoneConductor((state, world, pos) -> false)
                .isViewBlocking((state, world, pos) -> false)
        , Blocks.AIR, Blocks.WATER);
        setErrorMessage(Strings.ERROR_WATER_MAX);
		this.create = Strings.CREATE_WATER;
		this.create1 = Strings.CREATE_WATER_1;
    }

    public boolean isEnabled() {
        return Common.CONFIG.ENABLE_WATER();
    }

    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        entity.clearFire();
    }
}