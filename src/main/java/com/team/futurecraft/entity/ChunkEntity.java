package com.team.futurecraft.entity;

import java.util.ArrayList;

import com.team.futurecraft.BlockList;
import com.team.futurecraft.Mat4f;
import com.team.futurecraft.OBB;
import com.team.futurecraft.RayTrace;
import com.team.futurecraft.RayTrace.EnumAxis;
import com.team.futurecraft.Vec3f;
import com.team.futurecraft.Vec4f;

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
		Mat4f entity = new Mat4f().translate((float)this.posX, (float)this.posY - 1.5f, (float)this.posZ);
		Mat4f blockPos = new Mat4f().translate((float)pos.getX() - 8.5f, (float)pos.getY(), (float)pos.getZ() - 8.5f);
		Mat4f rot = new Mat4f().rotate(this.rotationYaw, 0.0f, 1.0f, 0.0f).rotate(this.rotationPitch, 1.0f, 0.0f, 0.0f);
		return new OBB(new Vec3f(0, 0, 0), new Vec3f(1, 1, 1), entity.multiply(rot).multiply(blockPos));
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
					Vec3f velocity = new Vec3f(this.riddenByEntity.getLookVec());
					this.setPosition(this.posX + velocity.x * entityLiving.moveForward, this.posY + velocity.y * entityLiving.moveForward, this.posZ + velocity.z * entityLiving.moveForward);
					entityLiving.setPosition(entityLiving.posX + velocity.x * entityLiving.moveForward, entityLiving.posY + velocity.y * entityLiving.moveForward, entityLiving.posZ + velocity.z * entityLiving.moveForward);
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
		
		Vec3f look = new Vec3f(playerIn.getLookVec());
		Vec3f pos = new Vec3f(playerIn.getPositionVector());
		Vec4f nearestBlock = new Vec4f(0, 0, 0, 1000);
		EnumAxis blockAxis = null;
		for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
    			for (int z = 0; z < 16; z++) {
    				if (!this.blockAccess.isAirBlock(new BlockPos(x, y, z))) {
    					RayTrace trace = this.getOBB(new BlockPos(x, y, z)).rayTrace(pos, look);
    					if (trace != null) {
    						double distance = trace.distance;
    						if (distance > 0 && distance < 5) {
    							if (distance < nearestBlock.w) {
    								nearestBlock = new Vec4f(x, y, z, (float)distance);
    								blockAxis = trace.axis;
    							}
    						}
    					}
    				}
    			}
        	}
		}
		Vec3f rotLook = look.rotateYaw((float) Math.toRadians(-this.rotationYaw)).rotatePitch((float) Math.toRadians(-this.rotationPitch));
		System.out.println(rotLook.x + ", " + rotLook.z);
		
		if (blockAxis == EnumAxis.X) {
			System.out.println("axis is X");
			if (rotLook.x < 0)
				if (nearestBlock.x < 15)
					this.blocks[(int)nearestBlock.x + 1][(int)nearestBlock.y][(int)nearestBlock.z] = stateToPlace;
			if (rotLook.x > 0)
				if (nearestBlock.x > 1)
					this.blocks[(int)nearestBlock.x - 1][(int)nearestBlock.y][(int)nearestBlock.z] = stateToPlace;
		}
		if (blockAxis == EnumAxis.Y) {
			System.out.println("axis is Y");
			if (rotLook.y < 0)
				if (nearestBlock.y < 15)
					this.blocks[(int)nearestBlock.x][(int)nearestBlock.y + 1][(int)nearestBlock.z] = stateToPlace;
			if (rotLook.y > 0)
				if (nearestBlock.y > 1)
					this.blocks[(int)nearestBlock.x][(int)nearestBlock.y - 1][(int)nearestBlock.z] = stateToPlace;
		}
		if (blockAxis == EnumAxis.Z) {
			System.out.println("axis is Z");
			if (rotLook.z < 0)
				if (nearestBlock.z < 15)
					this.blocks[(int)nearestBlock.x][(int)nearestBlock.y][(int)nearestBlock.z + 1] = stateToPlace;
			if (rotLook.z > 0)
				if (nearestBlock.z > 1)
					this.blocks[(int)nearestBlock.x][(int)nearestBlock.y][(int)nearestBlock.z - 1] = stateToPlace;
		}
		return true;
    }
	
	public boolean hitByEntity(Entity entityIn)
    {
		System.out.println("hitting");
		Vec3f look = new Vec3f(entityIn.getLookVec());
		Vec3f pos = new Vec3f(entityIn.getPositionVector());
		Vec4f nearestBlock = new Vec4f(0, 0, 0, 1000);
		for (int x = 0; x < 16; x++) {
        	for (int y = 0; y < 16; y++) {
    			for (int z = 0; z < 16; z++) {
    				if (!this.blockAccess.isAirBlock(new BlockPos(x, y, z))) {
    					RayTrace trace = this.getOBB(new BlockPos(x, y, z)).rayTrace(pos, look);
    					if (trace != null) {
    						double distance = trace.distance;
    						if (distance > 0 && distance < 5) {
    							if (distance < nearestBlock.w) {
    								nearestBlock = new Vec4f(x, y, z, (float)distance);
    							}
    						}
    					}
    				}
    			}
        	}
		}
		this.blocks[(int)nearestBlock.x][(int)nearestBlock.y][(int)nearestBlock.z] = Blocks.air.getDefaultState();
		
		
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
