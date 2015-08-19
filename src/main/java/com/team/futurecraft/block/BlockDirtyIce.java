package com.team.futurecraft.block;

import net.minecraft.block.material.Material;

public class BlockDirtyIce extends SimpleBlock {

	public BlockDirtyIce(Material materialIn, String name) {
		super(materialIn, name);
		slipperiness = 1.05f;
	}

}
