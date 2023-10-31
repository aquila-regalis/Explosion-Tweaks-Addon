package btw.community.aquila.boomer.mixin;

import btw.block.BTWBlocks;
import btw.block.blocks.CobblestoneBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CobblestoneBlock.class)
public abstract class CobblestoneBlockMixin extends Block {
    protected CobblestoneBlockMixin(int par1, Material par2Material) {
        super(par1, par2Material);
    }

    public void onBlockQuake(World world, int x, int y, int z, int quakerX, int quakerY, int quakerZ) {
        int quakerId = world.getBlockId(quakerX, quakerY, quakerZ);
        if(quakerId > 0) {
            float resistance = this.getExplosionResistance(null, world, x, y, z);
            float quakerResistance = Block.blocksList[quakerId].getExplosionResistance(null, world, quakerX, quakerY, quakerZ);
            if (resistance <= quakerResistance) {
                int looseMetadata = this.damageDropped(world.getBlockMetadata(x, y, z));
                world.setBlockAndMetadataWithNotify(x, y, z, BTWBlocks.looseCobblestone.blockID, looseMetadata);
            }
        }
    }
}
