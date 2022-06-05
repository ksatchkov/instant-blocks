package com.slymask3.instantblocks.gui.instant;

import com.mojang.blaze3d.vertex.PoseStack;
import com.slymask3.instantblocks.block.BlockInstant;
import com.slymask3.instantblocks.gui.GuiInstant;
import com.slymask3.instantblocks.gui.module.ColorEditBox;
import com.slymask3.instantblocks.handler.Config;
import com.slymask3.instantblocks.network.PacketHandler;
import com.slymask3.instantblocks.network.PacketSkydive;
import com.slymask3.instantblocks.tileentity.TileEntitySkydive;
import com.slymask3.instantblocks.util.BuildHelper;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;


public class GuiSkydive extends GuiInstant {
    private Button random;
	private ColorEditBox[] color = new ColorEditBox[11];
	private Checkbox tp;
	private EditBox radius;
	private TileEntitySkydive tileEntity;

	public GuiSkydive(Player player, Level world, int x, int y, int z) {
		super(player, world, x, y, z, "Instant Skydive");
		this.tileEntity = (TileEntitySkydive)world.getBlockEntity(new BlockPos(x,y,z));
	}

	@Override
	public void init() {
		super.init();

		this.random = new Button(this.width / 2 + 4, this.height / 4 + 98 + 12, 150, 20, new TextComponent("Randomize"), (p_88642_) -> {
			this.setRandom();
		});

		this.tp = new Checkbox(this.width / 2 + 4, this.height / 4 + 76 + 12, 150, 20, new TextComponent("Teleport to top"), true);

		EditBox radiusField = new EditBox(this.font, this.width / 2 - 4 - 100 - 12, this.height / 4 + 100 + 12, 110, 16, new TextComponent("radius"));
		radiusField.setValue(String.valueOf(Config.Common.SKYDIVE_RADIUS.get()));
		this.radius = radiusField;

		int cutoff = 6;
		int color_x, color_y;
		for(int i=0; i<color.length; i++) {
			if(i < cutoff) {
				color_x = this.width / 2 - 4 - 100;
				color_y = 45 + (18*i);
			} else {
				color_x = this.width / 2 + 4 + 50;
				color_y = 45 + (18*(i- cutoff));
			}
			this.color[i] = new ColorEditBox(this.font, color_x, color_y, 60, 14, i, tileEntity);

			this.addRenderableWidget(this.color[i]);
		}

		this.addRenderableWidget(this.random);
		this.addRenderableWidget(this.tp);
		this.addRenderableWidget(this.radius);
	}

	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, "Enter colors by name or hexadecimal value.", this.width / 2 - 4 - 150, 33, 10526880); //aarrggbb
		this.font.draw(poseStack, "Radius:", this.width / 2 - 4 - 150, this.height / 4 + 104 + 12,14737632);

		for(ColorEditBox colorEditBox : color) {
			colorEditBox.renderLabel(poseStack,mouseX,mouseY);
		}
	}
	
	public void sendInfo() {
		int radius;
		try {
			radius = Integer.parseInt(this.radius.getValue());
		} catch (NumberFormatException e) {
			radius = Config.Common.SKYDIVE_RADIUS.get();
		}
		PacketHandler.sendToServer(new PacketSkydive(this.x, this.y, this.z, getColors(), radius, tp.selected()));

		BlockInstant block = (BlockInstant) BuildHelper.getBlock(world,x,y,z);
		block.afterBuild(world,x,y,z,player);
	}
	
	private int[] getColors() {
		int[] colors = new int[this.color.length];
		for(int i=0; i<colors.length; i++) {
			colors[i] = color[i].getColor().getRGB();
		}
		return colors;
	}

	private void setRandom() {
		for(ColorEditBox colorEditBox : color) {
			colorEditBox.setRandomHex();
		}
	}
}