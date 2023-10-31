package btw.community.aquila.boomer.mixin;

import btw.block.BTWBlocks;
import btw.block.blocks.FullBlock;
import btw.block.blocks.RoughStoneBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RoughStoneBlock.class)
public abstract class RoughStoneBlockMixin extends FullBlock {
    protected RoughStoneBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    public void onBlockQuake(World world, int x, int y, int z, int quakerX, int quakerY, int quakerZ) {
        int quakerId = world.getBlockId(quakerX, quakerY, quakerZ);
        if(quakerId > 0) {
            float resistance = this.getExplosionResistance(null, world, x, y, z);
            float quakerResistance = Block.blocksList[quakerId].getExplosionResistance(null, world, quakerX, quakerY, quakerZ);
            if (resistance <= quakerResistance) {
                world.setBlockAndMetadataWithNotify(x, y, z, 0, 0);
            }
        }
    }
}
