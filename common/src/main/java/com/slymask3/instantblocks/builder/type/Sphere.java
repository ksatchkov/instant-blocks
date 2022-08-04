package com.slymask3.instantblocks.builder.type;

import com.slymask3.instantblocks.builder.BlockType;
import com.slymask3.instantblocks.builder.Builder;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

public class Sphere extends Base<Sphere> {
    final int radius;
    BlockType outerBlockType, innerBlockType;
    boolean half;
    Direction queueDirection;

    private Sphere(Builder builder, Level world, int x, int y, int z, int radius) {
        super(builder, world, x, y, z);
        this.radius = radius;
        this.half = false;
        this.queueDirection = null;
    }

    public static Sphere setup(Builder builder, Level world, int x, int y, int z, int radius) {
        return new Sphere(builder, world, x, y, z, radius);
    }

    public Sphere setBlock(BlockType type) {
        this.outerBlockType = type;
        this.innerBlockType = type;
        return this;
    }

    public Sphere setOuter(BlockType type) {
        this.outerBlockType = type;
        return this;
    }

    public Sphere setInner(BlockType type) {
        this.innerBlockType = type;
        return this;
    }

    public Sphere setHalf() {
        this.half = true;
        return this;
    }

    public Sphere setQueueDirection(Direction queueDirection) {
        this.queueDirection = queueDirection;
        return this;
    }

    public void build() {
        double distance;
        for(int yc = 0; yc <= 2 * radius; yc++) {
            for(int xc = 0; xc <= 2 * radius; xc++) {
                for(int zc = 0; zc <= 2 * radius; zc++) {
                    distance = Math.sqrt((xc - radius) * (xc - radius) + (zc - radius) * (zc - radius) + (yc - radius) * (yc - radius));
                    int y_pos = y + yc - radius;
                    if(y_pos >= y || !this.half) {
                        if(outerBlockType != null && distance > radius - 0.4 && distance < radius + 0.6) {
                            Single.setup(builder, world, x + xc - radius, y + yc - radius, z + zc - radius).setBlock(outerBlockType).queue(this.priority,this.replace);
                        } else if(innerBlockType != null && distance < radius - 0.3) {
                            Single.setup(builder, world, x + xc - radius, y + yc - radius, z + zc - radius).setBlock(innerBlockType).queue(this.priority,this.replace);
                        }
                    }
                }
            }
            if(this.queueDirection != null && this.queueDirection.equals(Direction.UP)) {
                this.priority++;
            }
        }
    }
}