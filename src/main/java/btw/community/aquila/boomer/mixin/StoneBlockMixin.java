package btw.community.aquila.boomer.mixin;

import btw.block.BTWBlocks;
import btw.block.blocks.FullBlock;
import btw.block.blocks.StoneBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StoneBlock.class)
public abstract class StoneBlockMixin extends FullBlock {
    protected StoneBlockMixin(int iBlockID, Material material) {
        super(iBlockID, material);
    }

    @Shadow
    public abstract void setIsCracked(World world, int i, int j, int k, boolean bCracked);

    @Shadow
    public abstract int damageDropped(int metadata);

    public void onBlockQuake(World world, int x, int y, int z, int quakerX, int quakerY, int quakerZ) {
        int quakerId = world.getBlockId(quakerX, quakerY, quakerZ);
        if(quakerId > 0) {
            float resistance = this.getExplosionResistance(null, world, x, y, z);
            float quakerResistance = Block.blocksList[quakerId].getExplosionResistance(null, world, quakerX, quakerY, quakerZ);
            if (resistance <= quakerResistance) {
                int cobbleMetadata = this.damageDropped(world.getBlockMetadata(x, y, z));
                world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseCobblestone.blockID, cobbleMetadata);
            } else {
                this.setIsCracked(world, x, y, z, true);
            }
        } else {
            this.setIsCracked(world, x, y, z, true);
        }
    }
}
