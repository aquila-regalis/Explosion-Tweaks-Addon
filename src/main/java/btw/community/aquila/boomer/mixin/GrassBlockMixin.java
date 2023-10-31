package btw.community.aquila.boomer.mixin;

import btw.block.BTWBlocks;
import btw.block.blocks.GrassBlock;
import net.minecraft.src.BlockGrass;
import net.minecraft.src.Explosion;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends BlockGrass {
    protected GrassBlockMixin(int par1) {
        super(par1);
    }

    @Shadow
    public abstract void setSparse(World world, int x, int y, int z);

    @Shadow
    public abstract boolean isSparse(IBlockAccess blockAccess, int x, int y, int z);

    public void onBlockQuake(World world, int x, int y, int z, int quakerX, int quakerY, int quakerZ) {
        int quakerId = world.getBlockId(quakerX, quakerY, quakerZ);
        if(quakerY < y || this.isSparse(world, x, y, z)) {
            world.setBlockWithNotify(x, y, z, BTWBlocks.looseDirt.blockID);
        } else {
            this.setSparse(world, x, y, z);
        }
    }

    /**
     * @author aquila-regalis
     * @reason Removed the call to loosen nearby dirt since that's handled by onBlockQuake
     * @param world
     * @param i
     * @param j
     * @param k
     * @param explosion
     */
    @Overwrite
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion explosion) {
        super.onBlockDestroyedByExplosion(world, i, j, k, explosion);
    }
}
