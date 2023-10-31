package btw.community.aquila.boomer.mixin;

import btw.block.BTWBlocks;
import btw.block.blocks.DirtBlock;
import btw.block.blocks.FullBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Explosion;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DirtBlock.class)
public abstract class DirtBlockMixin extends FullBlock {
    protected DirtBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    public void onBlockQuake(World world, int x, int y, int z, int quakerX, int quakerY, int quakerZ) {
        world.setBlockWithNotify(x, y, z, BTWBlocks.looseDirt.blockID);
    }

    /**
     * @author aquila-regalis
     * @reason Remove the call to loosen nearby dirt since that's handled by onBlockQuake
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
