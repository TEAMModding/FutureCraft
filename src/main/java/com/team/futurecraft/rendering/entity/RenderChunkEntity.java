package com.team.futurecraft.rendering.entity;

import com.team.futurecraft.entity.ChunkEntity;
import com.team.futurecraft.entity.ChunkEntityAccess;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderChunkEntity extends Render
{
    public RenderChunkEntity(RenderManager p_i46177_1_)
    {
        super(p_i46177_1_);
        this.shadowSize = 0F;
    }

    public void doRender(ChunkEntity entity, double p_180557_2_, double p_180557_4_, double p_180557_6_, float p_180557_8_, float p_180557_9_)
    {	
        this.bindTexture(TextureMap.locationBlocksTexture);
        ChunkEntityAccess blockAccess = entity.blockAccess;
        
        for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
    			for (int z = 0; z < 16; z++) {
    				IBlockState blockstate = blockAccess.getBlockState(new BlockPos(x, y, z));
    				Block block = blockstate.getBlock();
    					
    				if (block.getRenderType() == 3)
    	            {
    	                GlStateManager.pushMatrix();
    	                GlStateManager.translate(p_180557_2_, p_180557_4_, p_180557_6_);
    	                GlStateManager.rotate((float)entity.rotationYaw, 0, 1, 0);
    	                GlStateManager.rotate((float)entity.rotationPitch, 1, 0, 0);
    	                GlStateManager.translate(-8.5, 0, -8.5);
    	                GlStateManager.disableLighting();
    	                Tessellator tessellator = Tessellator.getInstance();
    	                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
    	                worldrenderer.startDrawingQuads();
    	                worldrenderer.setVertexFormat(DefaultVertexFormats.BLOCK);
    	                Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(blockstate, new BlockPos(x, y, z), blockAccess, worldrenderer);
    	                tessellator.draw();
    	                worldrenderer.setTranslation(0, 0, 0);
    	                GlStateManager.enableLighting();
    	                GlStateManager.popMatrix();
    				}
    			}
    		}       
        }
        super.doRender(entity, p_180557_2_, p_180557_4_, p_180557_6_, p_180557_8_, p_180557_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityFallingBlock entity)
    {
        return TextureMap.locationBlocksTexture;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((ChunkEntity)entity);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity>) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doe
     */
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.doRender((ChunkEntity)entity, x, y, z, p_76986_8_, partialTicks);
    }
}