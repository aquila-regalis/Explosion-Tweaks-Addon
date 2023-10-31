package btw.community.aquila.boomer;

import btw.AddonHandler;
import btw.BTWAddon;

public class ExplosionTweaksAddon extends BTWAddon {
    public ExplosionTweaksAddon() {
        super("Explosion Tweaks", "1.0.0", "EXTW");
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
    }
}
