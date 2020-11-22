package stevekung.mods.moreplanets.planets.fronos.world.gen.biome.layer;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import stevekung.mods.moreplanets.init.MPBiomes;

public class GenLayerFronosRiverMix extends GenLayer
{
    private final GenLayer biomePatternGeneratorChain;
    private final GenLayer riverPatternGeneratorChain;

    public GenLayerFronosRiverMix(long p_i2129_1_, GenLayer p_i2129_3_, GenLayer p_i2129_4_)
    {
        super(p_i2129_1_);
        this.biomePatternGeneratorChain = p_i2129_3_;
        this.riverPatternGeneratorChain = p_i2129_4_;
    }

    @Override
    public void initWorldGenSeed(long seed)
    {
        this.biomePatternGeneratorChain.initWorldGenSeed(seed);
        this.riverPatternGeneratorChain.initWorldGenSeed(seed);
        super.initWorldGenSeed(seed);
    }

    @Override
    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight)
    {
        int[] aint = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint1 = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i = 0; i < areaWidth * areaHeight; ++i)
        {
            if (aint[i] != Biome.getIdForBiome(Biomes.OCEAN) && aint[i] != Biome.getIdForBiome(Biomes.DEEP_OCEAN))
            {
                if (aint1[i] == Biome.getIdForBiome(MPBiomes.FRONOS_RIVER))
                {
                    if (aint[i] == Biome.getIdForBiome(Biomes.ICE_PLAINS))
                    {
                        aint2[i] = Biome.getIdForBiome(Biomes.FROZEN_RIVER);
                    }
                    else if (aint[i] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND) && aint[i] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE))
                    {
                        aint2[i] = aint1[i] & 255;
                    }
                    else
                    {
                        aint2[i] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE);
                    }
                }
                else
                {
                    aint2[i] = aint[i];
                }
            }
            else
            {
                aint2[i] = aint[i];
            }
        }

        return aint2;
    }
}