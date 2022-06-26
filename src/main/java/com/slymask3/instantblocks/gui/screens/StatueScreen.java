package com.slymask3.instantblocks.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.slymask3.instantblocks.block.entity.StatueBlockEntity;
import com.slymask3.instantblocks.network.PacketHandler;
import com.slymask3.instantblocks.network.packet.StatuePacket;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class StatueScreen extends InstantScreen {
	private EditBox input;
	private Checkbox head, body, armLeft, armRight, legLeft, legRight;
	private Checkbox rgbMode;
	private final StatueBlockEntity tileEntity;

	public StatueScreen(Player player, Level world, int x, int y, int z) {
		super(player, world, x, y, z, "ib.gui.statue.title");
		this.tileEntity = (StatueBlockEntity)world.getBlockEntity(new BlockPos(x,y,z));
	}

	@Override
	public void init() {
		super.init();

		int x_left = this.width / 2 - 4 - 150;
		int x_right = this.width / 2 + 4;
		int y = this.height / 4 + 32;
		int slot = 22;

		this.head = new Checkbox(x_left, y, 150, 20, new TranslatableComponent("ib.gui.statue.head"), true);
		this.body = new Checkbox(x_right, y, 150, 20, new TranslatableComponent("ib.gui.statue.body"), true);
		this.armLeft = new Checkbox(x_left, y+(slot), 150, 20, new TranslatableComponent("ib.gui.statue.arm.left"), true);
		this.armRight = new Checkbox(x_right, y+(slot), 150, 20, new TranslatableComponent("ib.gui.statue.arm.right"), true);
		this.legLeft = new Checkbox(x_left, y+(slot*2), 150, 20, new TranslatableComponent("ib.gui.statue.leg.left"), true);
		this.legRight = new Checkbox(x_right, y+(slot*2), 150, 20, new TranslatableComponent("ib.gui.statue.leg.right"), true);
		this.rgbMode = new Checkbox(x_left, y+(slot*3), 150, 20, new TranslatableComponent("ib.gui.statue.rgb"), true);

		this.input = new EditBox(this.font, this.width / 2 - 4 - 150, 50, 300+8, 20, new TextComponent("Input"));

		this.addRenderableWidget(this.head);
		this.addRenderableWidget(this.body);
		this.addRenderableWidget(this.armLeft);
		this.addRenderableWidget(this.armRight);
		this.addRenderableWidget(this.legLeft);
		this.addRenderableWidget(this.legRight);
		this.addRenderableWidget(this.rgbMode);
		this.addRenderableWidget(this.input);

		this.setInitialFocus(this.input);
	}

	protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
		this.font.draw(poseStack, new TranslatableComponent("ib.gui.statue.input"), this.width / 2 - 4 - 150, 37, 10526880);
		this.font.draw(poseStack, new TranslatableComponent("ib.gui.statue.select"), this.width / 2 - 3 - 150, this.height / 4 + 8 + 12, 10526880);
		this.font.draw(poseStack, new TranslatableComponent("ib.gui.statue.rgb.text"), this.width / 2 - 3 - 150, this.height / 4 + 32 + 88, 10526880);
	}
	
	public void sendInfo() {
		PacketHandler.sendToServer(new StatuePacket(this.x, this.y, this.z, input.getValue(), head.selected(), body.selected(), armLeft.selected(), armRight.selected(), legLeft.selected(), legRight.selected(), rgbMode.selected()));
	}
}