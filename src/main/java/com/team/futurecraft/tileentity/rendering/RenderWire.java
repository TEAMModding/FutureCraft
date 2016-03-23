package com.team.futurecraft.tileentity.rendering;

import org.lwjgl.opengl.GL11;

import com.team.futurecraft.model.ModelWire;
import com.team.futurecraft.tileentity.IElectric;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderWire extends TileEntitySpecialRenderer {
	
	private static ResourceLocation tex = new ResourceLocation("futurecraft", "textures/blocks/insulated_copper_wire.png");
	private ModelWire model = new ModelWire();

	@Override
	public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float partialTicks, int blockDamageProgress) {
	    byte[] sides = new byte[6];
	    World world = this.getWorld();
	    BlockPos pos = te.getPos();
	    
	    IElectric ite = (IElectric) te;
	    if (ite.canConnectTo(world, pos, EnumFacing.UP)) sides[0] = 1;
	    if (ite.canConnectTo(world, pos, EnumFacing.SOUTH)) sides[1] = 1;
	    if (ite.canConnectTo(world, pos, EnumFacing.DOWN)) sides[2] = 1;
	    if (ite.canConnectTo(world, pos, EnumFacing.NORTH)) sides[3] = 1;
	    if (ite.canConnectTo(world, pos, EnumFacing.WEST)) sides[4] = 1;
	    if (ite.canConnectTo(world, pos, EnumFacing.EAST)) sides[5] = 1;
	    
	    GL11.glPushMatrix();
	    GL11.glTranslated(posX, posY, posZ);
	    
	    this.bindTexture(tex);
	    model.render(sides);
	    
	    GL11.glPopMatrix();
	}

}