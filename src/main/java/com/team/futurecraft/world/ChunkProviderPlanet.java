package com.team.futurecraft.world;

import java.util.List;
import java.util.Random;

import com.team.futurecraft.Noise;
import com.team.futurecraft.space.PlanetType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

/**
 * This is the class that physically generates the world chunks from ground up.
 * It simply generates stone from bedrock to the a random level determined by the noise functions.
 * The biome generates everything from there.
 * 
 * @author Joseph
 *
 */
public class ChunkProviderPlanet implements IChunkProvider {
    private Random rand;
    private Noise noise;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    private World worldObj;
    private double[] stoneNoise;
    private BiomeGenBase[] biomesForGeneration;
    private Block stoneBlock;
    public PlanetType type;

    public ChunkProviderPlanet(World worldIn, long seed, PlanetType type) {
    	this.noise = new Noise(worldIn.getSeed());
        this.stoneBlock = type.getStoneBlock();
        this.stoneNoise = new double[256];
        this.type = type;
        
        this.worldObj = worldIn;
        this.rand = new Random(seed);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
    }

    public void setBlocksInChunk(int chunkX, int chunkZ, ChunkPrimer world) {
        for (int x = 0; x < 16; x++) {
        	for (int z = 0; z < 16; z++) {
        		//int height = (int)Math.floor(noise.defaultNoise(new Vec3((chunkX * 16) + x, 0, (chunkZ * 16) + z), 3, 0.002f, 0.8f) * 35);
        		int height = type.getHeight((chunkX * 16) + x, (chunkZ * 16) + z, noise);
        		for (int y = 0; y < height; y++) {
        			world.setBlockState(x, y, z, this.stoneBlock.getDefaultState());
        		}
        	}
        }
    }

    public void func_180517_a(int p_180517_1_, int p_180517_2_, ChunkPrimer p_180517_3_, BiomeGenBase[] p_180517_4_) {
    	 ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_180517_1_, p_180517_2_, p_180517_3_, this.worldObj);
         MinecraftForge.EVENT_BUS.post(event);
         if (event.getResult() == Result.DENY) return;
         
         for (int k = 0; k < 16; ++k) {
             for (int l = 0; l < 16; ++l) {
                 BiomeGenBase biomegenbase = p_180517_4_[l + k * 16];
                 biomegenbase.genTerrainBlocks(this.worldObj, this.rand, p_180517_3_, p_180517_1_ * 16 + k, p_180517_2_ * 16 + l, this.stoneNoise[l + k * 16]);
             }
         }
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int x, int z) {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.setBlocksInChunk(x, z, chunkprimer);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.func_180517_a(x, z, chunkprimer, this.biomesForGeneration);

        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int k = 0; k < abyte.length; ++k) {
            abyte[k] = (byte)this.biomesForGeneration[k].biomeID;
        }
        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * Checks to see if a chunk exists at x, z
     */
    public boolean chunkExists(int x, int z) {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider p_73153_1_, int p_73153_2_, int p_73153_3_) {
        BlockFalling.fallInstantly = true;
        int k = p_73153_2_ * 16;
        int l = p_73153_3_ * 16;
        BlockPos blockpos = new BlockPos(k, 0, l);
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16));
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)p_73153_2_ * i1 + (long)p_73153_3_ * j1 ^ this.worldObj.getSeed());

        biomegenbase.decorate(this.worldObj, this.rand, new BlockPos(k, 0, l));
    }

    public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_) {
        return false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean p_73151_1_, IProgressUpdate p_73151_2_) {
        return true;
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unimplemented.
     */
    public void saveExtraData() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks() {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave() {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString() {
        return "RandomLevelSource";
    }

    @SuppressWarnings("rawtypes")
	public List func_177458_a(EnumCreatureType p_177458_1_, BlockPos p_177458_2_) {
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(p_177458_2_);
        return biomegenbase.getSpawnableList(p_177458_1_);
    }

    public BlockPos getStrongholdGen(World worldIn, String p_180513_2_, BlockPos p_180513_3_) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
        
    }

    public Chunk provideChunk(BlockPos blockPosIn) {
        return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
    }
}