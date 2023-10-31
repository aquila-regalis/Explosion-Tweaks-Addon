package btw.community.aquila.boomer.mixin;

import btw.block.BTWBlocks;
import btw.community.aquila.boomer.QuakableBlock;
import net.minecraft.src.Block;
import net.minecraft.src.Entity;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(Block.class)
public abstract class BlockMixin implements QuakableBlock {

    @Shadow
    public abstract boolean hasMortar(IBlockAccess blockAccess, int i, int j, int k);

    @Shadow
    public abstract float getExplosionResistance(Entity entity, World world, int i, int j, int k);

    @Shadow
    public abstract int idDropped(int par1, Random par2Random, int par3);

    @Shadow
    public abstract int damageDropped(int par1);

    @Override
    public void onBlockQuake(World world, int x, int y, int z, int quakerX, int quakerY, int quakerZ) {
        if(this.hasMortar(world, x, y, z)) {
            int quakerId = world.getBlockId(quakerX, quakerY, quakerZ);
            if(quakerId > 0) {
                float resistance = this.getExplosionResistance(null, world, x, y, z);
                float quakerResistance = Block.blocksList[quakerId].getExplosionResistance(null, world, quakerX, quakerY, quakerZ);
                if (resistance <= quakerResistance) {
                    int looseId = this.idDropped(world.getBlockId(x, y, z), world.rand, 0);
                    int looseMetadata = this.damageDropped(world.getBlockMetadata(x, y, z));
                    world.setBlockAndMetadataWithNotify(x, y, z, looseId, looseMetadata);
                }
            }
        }
    }
}
