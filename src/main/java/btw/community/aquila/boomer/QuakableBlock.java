package btw.community.aquila.boomer;

import net.minecraft.src.World;

public interface QuakableBlock {
    public void onBlockQuake(World world, int x, int y, int z, int quakerX, int quakerY, int quakerZ);
}
