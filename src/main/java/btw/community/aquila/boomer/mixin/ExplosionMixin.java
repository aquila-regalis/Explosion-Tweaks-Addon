package btw.community.aquila.boomer.mixin;

import btw.community.aquila.boomer.QuakableBlock;
import btw.world.util.BlockPos;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkPosition;
import net.minecraft.src.Explosion;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.List;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {
    @Shadow
    public boolean isSmoking;
    @Shadow
    public List affectedBlockPositions;
    @Shadow
    private World worldObj;

    @Inject(method = "doExplosionB", at = @At("HEAD"))
    private void onDoExplosionB(CallbackInfo info) {
        if(!this.isSmoking) {
            return;
        }
        HashSet<ChunkPosition> positionsSet = new HashSet<ChunkPosition>(this.affectedBlockPositions);

        for(ChunkPosition pos : positionsSet) {
            for(int i = 0; i < 6; ++i) {
                BlockPos neighbor = new BlockPos(pos.x, pos.y, pos.z, i);
                if(!positionsSet.contains(new ChunkPosition(neighbor.x, neighbor.y, neighbor.z))) {
                    int neighborId = this.worldObj.getBlockId(neighbor.x, neighbor.y, neighbor.z);
                    if(neighborId > 0) {
                        QuakableBlock neighborBlock = (QuakableBlock) Block.blocksList[neighborId];
                        neighborBlock.onBlockQuake(this.worldObj, neighbor.x, neighbor.y, neighbor.z,
                                pos.x, pos.y, pos.z);
                    }
                }
            }
        }
    }
}
