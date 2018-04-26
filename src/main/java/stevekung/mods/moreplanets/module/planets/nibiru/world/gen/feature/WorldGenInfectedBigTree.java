package stevekung.mods.moreplanets.module.planets.nibiru.world.gen.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import stevekung.mods.moreplanets.module.planets.nibiru.blocks.NibiruBlocks;
import stevekung.mods.stevekunglib.utils.BlockStateProperty;

public class WorldGenInfectedBigTree extends WorldGenAbstractTree
{
    private Random rand;
    private World world;
    private BlockPos basePos = BlockPos.ORIGIN;
    int heightLimit;
    int height;
    double heightAttenuation = 0.618D;
    double branchSlope = 0.381D;
    double scaleWidth = 1.0D;
    double leafDensity = 1.0D;
    int trunkSize = 1;
    int heightLimitLimit = 12;
    int leafDistanceLimit = 4;
    List<WorldGenInfectedBigTree.FoliageCoordinates> field_175948_j;
    private boolean genLeaves;
    private Block log;
    private Block leaves;
    private int logMeta;
    private int leavesMeta;

    public WorldGenInfectedBigTree(boolean genLeaves, Block log, int logMeta, Block leaves, int leavesMeta)
    {
        super(false);
        this.genLeaves = genLeaves;
        this.log = log;
        this.logMeta = logMeta;
        this.leaves = leaves;
        this.leavesMeta = leavesMeta;
    }

    void generateLeafNodeList()
    {
        this.height = (int)(this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit)
        {
            this.height = this.heightLimit - 1;
        }

        int i = (int)(1.382D + Math.pow(this.leafDensity * this.heightLimit / 13.0D, 2.0D));

        if (i < 1)
        {
            i = 1;
        }

        int j = this.basePos.getY() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        this.field_175948_j = new ArrayList<>();
        this.field_175948_j.add(new WorldGenInfectedBigTree.FoliageCoordinates(this.basePos.up(k), j));

        for (; k >= 0; --k)
        {
            float f = this.layerSize(k);

            if (f >= 0.0F)
            {
                for (int l = 0; l < i; ++l)
                {
                    double d0 = this.scaleWidth * f * (this.rand.nextFloat() + 0.328D);
                    double d1 = this.rand.nextFloat() * 2.0F * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5D;
                    double d3 = d0 * Math.cos(d1) + 0.5D;
                    BlockPos blockpos = this.basePos.add(d2, k - 1, d3);
                    BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);

                    if (this.checkBlockLine(blockpos, blockpos1) == -1)
                    {
                        int i1 = this.basePos.getX() - blockpos.getX();
                        int j1 = this.basePos.getZ() - blockpos.getZ();
                        double d4 = blockpos.getY() - Math.sqrt(i1 * i1 + j1 * j1) * this.branchSlope;
                        int k1 = d4 > j ? j : (int)d4;
                        BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());

                        if (this.checkBlockLine(blockpos2, blockpos) == -1)
                        {
                            this.field_175948_j.add(new WorldGenInfectedBigTree.FoliageCoordinates(blockpos, blockpos2.getY()));
                        }
                    }
                }
            }
        }
    }

    void func_181631_a(BlockPos p_181631_1_, float p_181631_2_, IBlockState p_181631_3_)
    {
        int i = (int)(p_181631_2_ + 0.618D);

        for (int j = -i; j <= i; ++j)
        {
            for (int k = -i; k <= i; ++k)
            {
                if (Math.pow(Math.abs(j) + 0.5D, 2.0D) + Math.pow(Math.abs(k) + 0.5D, 2.0D) <= p_181631_2_ * p_181631_2_)
                {
                    BlockPos blockpos = p_181631_1_.add(j, 0, k);
                    IBlockState state = this.world.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, this.world, blockpos) || state.getBlock().isLeaves(state, this.world, blockpos))
                    {
                        this.setBlockAndNotifyAdequately(this.world, blockpos, p_181631_3_);
                    }
                }
            }
        }
    }

    float layerSize(int p_76490_1_)
    {
        if (p_76490_1_ < this.heightLimit * 0.3F)
        {
            return -1.0F;
        }
        else
        {
            float f = this.heightLimit / 2.0F;
            float f1 = f - p_76490_1_;
            float f2 = MathHelper.sqrt(f * f - f1 * f1);

            if (f1 == 0.0F)
            {
                f2 = f;
            }
            else if (Math.abs(f1) >= f)
            {
                return 0.0F;
            }
            return f2 * 0.5F;
        }
    }

    float leafSize(int p_76495_1_)
    {
        return p_76495_1_ >= 0 && p_76495_1_ < this.leafDistanceLimit ? p_76495_1_ != 0 && p_76495_1_ != this.leafDistanceLimit - 1 ? 3.0F : 2.0F : -1.0F;
    }

    void generateLeafNode(BlockPos pos)
    {
        if (this.genLeaves)
        {
            for (int i = 0; i < this.leafDistanceLimit; ++i)
            {
                this.func_181631_a(pos.up(i), this.leafSize(i), this.leaves.getStateFromMeta(this.leavesMeta).withProperty(BlockStateProperty.CHECK_DECAY, Boolean.valueOf(false)));
            }
        }
    }

    void func_175937_a(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_)
    {
        BlockPos blockpos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float)blockpos.getX() / (float)i;
        float f1 = (float)blockpos.getY() / (float)i;
        float f2 = (float)blockpos.getZ() / (float)i;

        for (int j = 0; j <= i; ++j)
        {
            BlockPos blockpos1 = p_175937_1_.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);
            BlockStateProperty.EnumAxis blocklog$enumaxis = this.func_175938_b(p_175937_1_, blockpos1);
            this.setBlockAndNotifyAdequately(this.world, blockpos1, p_175937_3_.getStateFromMeta(this.logMeta).withProperty(BlockStateProperty.AXIS, blocklog$enumaxis));
        }
    }

    private int getGreatestDistance(BlockPos posIn)
    {
        int i = MathHelper.abs(posIn.getX());
        int j = MathHelper.abs(posIn.getY());
        int k = MathHelper.abs(posIn.getZ());
        return k > i && k > j ? k : j > i ? j : i;
    }

    private BlockStateProperty.EnumAxis func_175938_b(BlockPos p_175938_1_, BlockPos p_175938_2_)
    {
        BlockStateProperty.EnumAxis blocklog$enumaxis = BlockStateProperty.EnumAxis.Y;
        int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
        int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
        int k = Math.max(i, j);

        if (k > 0)
        {
            if (i == k)
            {
                blocklog$enumaxis = BlockStateProperty.EnumAxis.X;
            }
            else if (j == k)
            {
                blocklog$enumaxis = BlockStateProperty.EnumAxis.Z;
            }
        }
        return blocklog$enumaxis;
    }

    void generateLeaves()
    {
        for (WorldGenInfectedBigTree.FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j)
        {
            this.generateLeafNode(worldgenbigtree$foliagecoordinates);
        }
    }

    boolean leafNodeNeedsBase(int p_76493_1_)
    {
        return p_76493_1_ >= this.heightLimit * 0.2D;
    }

    void generateTrunk()
    {
        BlockPos blockpos = this.basePos;
        BlockPos blockpos1 = this.basePos.up(this.height);
        Block block = this.log;
        this.func_175937_a(blockpos, blockpos1, block);

        if (this.trunkSize == 2)
        {
            this.func_175937_a(blockpos.east(), blockpos1.east(), block);
            this.func_175937_a(blockpos.east().south(), blockpos1.east().south(), block);
            this.func_175937_a(blockpos.south(), blockpos1.south(), block);
        }
    }

    void generateLeafNodeBases()
    {
        for (WorldGenInfectedBigTree.FoliageCoordinates worldgenbigtree$foliagecoordinates : this.field_175948_j)
        {
            int i = worldgenbigtree$foliagecoordinates.func_177999_q();
            BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());

            if (!blockpos.equals(worldgenbigtree$foliagecoordinates) && this.leafNodeNeedsBase(i - this.basePos.getY()))
            {
                this.func_175937_a(blockpos, worldgenbigtree$foliagecoordinates, this.log);
            }
        }
    }

    int checkBlockLine(BlockPos posOne, BlockPos posTwo)
    {
        BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float)blockpos.getX() / (float)i;
        float f1 = (float)blockpos.getY() / (float)i;
        float f2 = (float)blockpos.getZ() / (float)i;

        if (i == 0)
        {
            return -1;
        }
        else
        {
            for (int j = 0; j <= i; ++j)
            {
                BlockPos blockpos1 = posOne.add(0.5F + j * f, 0.5F + j * f1, 0.5F + j * f2);

                if (!this.isReplaceable(this.world, blockpos1))
                {
                    return j;
                }
            }
            return -1;
        }
    }

    @Override
    public void setDecorationDefaults()
    {
        this.leafDistanceLimit = 5;
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        this.world = worldIn;
        this.basePos = position;
        this.rand = new Random(rand.nextLong());

        if (this.heightLimit == 0)
        {
            this.heightLimit = 5 + this.rand.nextInt(this.heightLimitLimit);
        }

        if (!this.validTreeLocation())
        {
            this.world = null;
            return false;
        }
        else
        {
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateTrunk();
            this.generateLeafNodeBases();
            this.world = null;
            return true;
        }
    }

    private boolean validTreeLocation()
    {
        BlockPos down = this.basePos.down();
        IBlockState state = this.world.getBlockState(down);
        Block block = state.getBlock();

        if (block != NibiruBlocks.INFECTED_GRASS && block != NibiruBlocks.INFECTED_DIRT && block != NibiruBlocks.INFECTED_FARMLAND)
        {
            return false;
        }
        else
        {
            int i = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));

            if (i == -1)
            {
                return true;
            }
            else if (i < 6)
            {
                return false;
            }
            else
            {
                this.heightLimit = i;
                return true;
            }
        }
    }

    static class FoliageCoordinates extends BlockPos
    {
        private int field_178000_b;

        public FoliageCoordinates(BlockPos p_i45635_1_, int p_i45635_2_)
        {
            super(p_i45635_1_.getX(), p_i45635_1_.getY(), p_i45635_1_.getZ());
            this.field_178000_b = p_i45635_2_;
        }

        public int func_177999_q()
        {
            return this.field_178000_b;
        }
    }
}