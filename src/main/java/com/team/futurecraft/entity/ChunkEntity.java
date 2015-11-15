package com.team.futurecraft.entity;

import java.util.ArrayList;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.Mat4;
import com.team.futurecraft.OBB;
import com.team.futurecraft.RayTrace;
import com.team.futurecraft.RayTrace.EnumAxis;
import com.team.futurecraft.Vec4;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ChunkEntity extends Entity {
	
	public IBlockState[][][] blocks = new IBlockState[16][16][16];
	public ChunkEntityAccess blockAccess;
	
	public ChunkEntity(World worldIn) {
		super(worldIn);
		this.setSize(32, 32);
		this.rotationPitch = 0;
		this.rotationYaw = 0;
		this.ignoreFrustumCheck = true;
		
		blockAccess = new ChunkEntityAccess(this);
		
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				for (int z = 0; z < 16; z++) {
					blocks[x][y][z] = Blocks.air.getDefaultState();
				}
			}
		}
		blocks[8][0][8] = BlockList.rocket_core.getDefaultState();
	}

	public OBB getOBB(BlockPos pos) {
		Mat4 entity = Mat4.translate(this.posX, this.posY - 1.5, this.posZ);
		Mat4 blockPos = Mat4.translate(pos.getX() - 8.5, pos.getY(), pos.getZ() - 8.5);
		Mat4 rot = Mat4.rotate(this.rotationYaw, 0.0, 1.0, 0.0).multiply(Mat4.rotate(this.rotationPitch, 1, 0, 0));
		return new OBB(new Vec3(0, 0, 0), new Vec3(1, 1, 1), entity.multiply(rot).multiply(blockPos));
	}
	
	public double getMountedYOffset()
    {
		return 0;
    }
	
	public void updateRiderPosition()
    {
		this.riddenByEntity.setPosition(this.posX, this.posY, this.posZ);
    }

	public void onEntityUpdate()
    {	
		if (this.riddenByEntity != null) {
			Entity entity = this.riddenByEntity;
			this.setSize(1, 1);
			if (entity instanceof EntityLivingBase) {
				EntityLivingBase entityLiving = (EntityLivingBase)entity;
				if (!this.worldObj.isRemote) {
					Vec3 velocity = this.riddenByEntity.getLookVec();
					this.setPosition(this.posX + velocity.xCoord * entityLiving.moveForward, this.posY + velocity.yCoord * entityLiving.moveForward, this.posZ + velocity.zCoord * entityLiving.moveForward);
					entityLiving.setPosition(entityLiving.posX + velocity.xCoord * entityLiving.moveForward, entityLiving.posY + velocity.yCoord * entityLiving.moveForward, entityLiving.posZ + velocity.zCoord * entityLiving.moveForward);
				}
				this.rotationPitch = this.riddenByEntity.rotationPitch;
				this.rotationYaw = -this.riddenByEntity.rotationYaw;
				
				if (this.posY > 250 && entityLiving instanceof EntityPlayer) {
					EntityPlayer entityPlayer = (EntityPlayer)entityLiving;
					entityPlayer.openGui("futurecraft", 101, this.worldObj, (int)posX, (int)posY, (int)posZ);
				}
			}
		}
		else {
			this.setSize(32, 32);
		}
		super.onEntityUpdate();
    }
	
	public boolean canBeCollidedWith()
    {
        return true;
    }


	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}
	
	private void testForEmpty() {
		System.out.println("testing for empty chunk");
		boolean airFlag = false;
		boolean coreFlag = false;
		
		for (int x = 0; x < 16; x++) {
			for (int y = 0; y < 16; y++) {
				for (int z = 0; z < 16; z++) {
					if (blocks[x][y][z] != Blocks.air.getDefaultState()) {
						System.out.println("found a non-air block");
						airFlag = true;
					}
					if (blocks[x][y][z] == BlockList.rocket_core.getDefaultState()) {
						coreFlag = true;
					}
				}
			}
		}
		
		if (!airFlag || !coreFlag) {
			System.out.println("killing entity");
			this.kill();
		}
	}
	
	public boolean interactFirst(EntityPlayer playerIn)
    {
		Block blockToPlace = null;
		IBlockState stateToPlace = null;
		ItemStack stack = playerIn.getHeldItem();
		if (stack != null) {
			Item item = stack.getItem();
			Block block = Block.getBlockFromItem(item);
			if (block != null) {
				blockToPlace = block;
				stateToPlace = blockToPlace.getStateFromMeta(stack.getMetadata());
			}
			else {
				if (!this.worldObj.isRemote)
		        {
		            playerIn.mountEntity(this);
		        }

		        return true;
			}
				
		}
		else {
			if (!this.worldObj.isRemote) {
				System.out.println("mounting entity");
				playerIn.mountEntity(this);
			}
			
			return true;
		}
		
		Vec3 look = playerIn.getLookVec();
		Vec3 pos = playerIn.getPositionVector();
		Vec4 nearestBlock = new Vec4(0, 0, 0, 1000);
		EnumAxis blockAxis = null;
		for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
    			for (int z = 0; z < 16; z++) {
    				if (!this.blockAccess.isAirBlock(new BlockPos(x, y, z))) {
    					RayTrace trace = this.getOBB(new BlockPos(x, y, z)).rayTrace(pos, look);
    					if (trace != null) {
    						double distance = trace.distance;
    						if (distance > 0 && distance < 5) {
    							if (distance < nearestBlock.wCoord) {
    								nearestBlock = new Vec4(x, y, z, distance);
    								blockAxis = trace.axis;
    							}
    						}
    					}
    				}
    			}
        	}
		}
		Vec3 rotLook = look.rotateYaw((float) Math.toRadians(-this.rotationYaw)).rotatePitch((float) Math.toRadians(-this.rotationPitch));
		System.out.println(rotLook.xCoord + ", " + rotLook.zCoord);
		
		if (blockAxis == EnumAxis.X) {
			System.out.println("axis is X");
			if (rotLook.xCoord < 0)
				if (nearestBlock.xCoord < 15)
					this.blocks[(int)nearestBlock.xCoord + 1][(int)nearestBlock.yCoord][(int)nearestBlock.zCoord] = stateToPlace;
			if (rotLook.xCoord > 0)
				if (nearestBlock.xCoord > 1)
					this.blocks[(int)nearestBlock.xCoord - 1][(int)nearestBlock.yCoord][(int)nearestBlock.zCoord] = stateToPlace;
		}
		if (blockAxis == EnumAxis.Y) {
			System.out.println("axis is Y");
			if (rotLook.yCoord < 0)
				if (nearestBlock.yCoord < 15)
					this.blocks[(int)nearestBlock.xCoord][(int)nearestBlock.yCoord + 1][(int)nearestBlock.zCoord] = stateToPlace;
			if (rotLook.yCoord > 0)
				if (nearestBlock.yCoord > 1)
					this.blocks[(int)nearestBlock.xCoord][(int)nearestBlock.yCoord - 1][(int)nearestBlock.zCoord] = stateToPlace;
		}
		if (blockAxis == EnumAxis.Z) {
			System.out.println("axis is Z");
			if (rotLook.zCoord < 0)
				if (nearestBlock.zCoord < 15)
					this.blocks[(int)nearestBlock.xCoord][(int)nearestBlock.yCoord][(int)nearestBlock.zCoord + 1] = stateToPlace;
			if (rotLook.zCoord > 0)
				if (nearestBlock.zCoord > 1)
					this.blocks[(int)nearestBlock.xCoord][(int)nearestBlock.yCoord][(int)nearestBlock.zCoord - 1] = stateToPlace;
		}
		return true;
    }
	
	public boolean hitByEntity(Entity entityIn)
    {
		System.out.println("hitting");
		Vec3 look = entityIn.getLookVec();
		Vec3 pos = entityIn.getPositionVector();
		Vec4 nearestBlock = new Vec4(0, 0, 0, 1000);
		for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
    			for (int z = 0; z < 16; z++) {
    				if (!this.blockAccess.isAirBlock(new BlockPos(x, y, z))) {
    					RayTrace trace = this.getOBB(new BlockPos(x, y, z)).rayTrace(pos, look);
    					if (trace != null) {
    						double distance = trace.distance;
    						if (distance > 0 && distance < 5) {
    							if (distance < nearestBlock.wCoord) {
    								nearestBlock = new Vec4(x, y, z, distance);
    							}
    						}
    					}
    				}
    			}
        	}
		}
		this.blocks[(int)nearestBlock.xCoord][(int)nearestBlock.yCoord][(int)nearestBlock.zCoord] = Blocks.air.getDefaultState();
		
		
		this.testForEmpty();
		
		//System.out.println(this.getOBB().rayTrace(pos, look));
		return true;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompound) {
		int[] states = tagCompound.getIntArray("blocks");
		
		int i = 0;
		for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
    			for (int z = 0; z < 16; z++) {
    				blocks[x][y][z] = Block.getStateById(states[i]);
    				i++;
    			}
        	}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) {
		ArrayList<Integer> states = new ArrayList<Integer>();
		
		for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
    			for (int z = 0; z < 16; z++) {
    				states.add(Block.getStateId(blocks[x][y][z]));
    			}
        	}
		}
		Integer[] objects = states.toArray(new Integer[] {});
		int[] intStates = new int[objects.length];
		
		for (int i = 0; i < objects.length; i++) {
			intStates[i] = (int)objects[i];
		}
		System.out.println(intStates.length);
		
		tagCompound.setIntArray("blocks", intStates);
	}

	public void handleEntity(Entity entity) {
		//System.out.println("handling entity in bounds");
		AxisAlignedBB entityBB = entity.getEntityBoundingBox().offset(2, 0, 2);
		
		if (entityBB != null) {
			//OBB entityOBB = OBB.fromAABB(entityBB);
			for (int x = 0; x < 16; x++) {
	        	for (int y = 0; y < 16; y++) {
	    			for (int z = 0; z < 16; z++) {
	    				if (!this.blockAccess.isAirBlock(new BlockPos(x, y, z))) {
	    					
	    				}
	    			}
	        	}
			}
		}
	}
}
