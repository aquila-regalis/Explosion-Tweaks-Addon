package btw.community.aquila.boomer.mixin;

import btw.block.blocks.FallingFullBlock;
import btw.block.blocks.LooseDirtBlock;
import net.minecraft.src.Explosion;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LooseDirtBlock.class)
public abstract class LooseDirtBlockMixin extends FallingFullBlock {
    public LooseDirtBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion explosion) {
        super.onBlockDestroyedByExplosion(world, i, j, k, explosion);
        onDirtDugWithImproperTool(world, i, j, k);
    }
}
