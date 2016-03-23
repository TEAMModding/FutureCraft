package com.team.futurecraft.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelWire extends ModelBase {
	public ModelRenderer center;
	public ModelRenderer[] sides = new ModelRenderer[6];
	
	public ModelWire() {
		this.center = (new ModelRenderer(this, 0, 0).setTextureSize(32, 32));
		this.center.addBox(6, 6, 6, 4, 4, 4, 0);
		
		for (int i = 0; i < 6; i++) {
			this.sides[i] = (new ModelRenderer(this, 0, 8).setTextureSize(32, 32));
			this.sides[i].addBox(-2, 2, -2, 4, 6, 4);
			this.sides[i].rotationPointX = 8;
			this.sides[i].rotationPointY = 8;
			this.sides[i].rotationPointZ = 8;
		}
		
		
		this.sides[1].rotateAngleX = (float) (Math.PI / 2);
		this.sides[2].rotateAngleX = (float) (Math.PI);
		this.sides[3].rotateAngleX = -(float) (Math.PI / 2);
		this.sides[4].rotateAngleZ = (float) (Math.PI / 2);
		this.sides[5].rotateAngleZ = -(float) (Math.PI / 2);
	}
	
	public void render(byte[] sides) {
		this.center.render(0.0625F);
		
		for (int i = 0; i < 6; i++) {
			if (sides[i] == 1) {
				this.sides[i].render(0.0625F);
			}
		}
	}
}
