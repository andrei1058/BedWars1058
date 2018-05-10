package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import javax.annotation.Nullable;

import net.minecraft.server.v1_12_R1.Vec3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonControllerChange extends AbstractDragonController {
    private static final Logger b = LogManager.getLogger();
    private Vec3D c;
    private int d;

    public DragonControllerChange(EntityEnderDragon var1) {
        super(var1);
    }

    public void c() {
        if (this.c == null) {
            b.warn("Aborting charge player as no target was set.");
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
        } else if (this.d > 0 && this.d++ >= 10) {
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
        } else {
            double var1 = this.c.c(this.a.locX, this.a.locY, this.a.locZ);
            if (var1 < 100.0D || var1 > 22500.0D || this.a.positionChanged || this.a.B) {
                ++this.d;
            }

        }
    }

    public void d() {
        this.c = null;
        this.d = 0;
    }

    public void a(Vec3D var1) {
        this.c = var1;
    }

    public float f() {
        return 3.0F;
    }

    @Nullable
    public Vec3D g() {
        return this.c;
    }

    public DragonControllerPhase<DragonControllerChange> getControllerPhase() {
        return DragonControllerPhase.i;
    }
}
