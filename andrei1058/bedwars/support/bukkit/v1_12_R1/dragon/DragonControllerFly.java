package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.*;

import javax.annotation.Nullable;

public class DragonControllerFly extends AbstractDragonController {
    private boolean b;
    private PathEntity c;
    private Vec3D d;

    public DragonControllerFly(EntityEnderDragon var1) {
        super(var1);
    }

    public void c() {
        if (!this.b && this.c != null) {
            BlockPosition var1 = this.a.world.q(WorldGenEndTrophy.a);
            double var2 = this.a.d(var1);
            if (var2 > 100.0D) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
            }
        } else {
            this.b = false;
            this.j();
        }

    }

    public void d() {
        this.b = true;
        this.c = null;
        this.d = null;
    }

    private void j() {
        int var1 = this.a.p();
        Vec3D var2 = this.a.a(1.0F);
        int var3 = this.a.k(-var2.x * 40.0D, 105.0D, -var2.z * 40.0D);
        if (this.a.df() != null && this.a.df().c() > 0) {
            var3 %= 12;
            if (var3 < 0) {
                var3 += 12;
            }
        } else {
            var3 -= 12;
            var3 &= 7;
            var3 += 12;
        }

        this.c = this.a.a(var1, var3, null);
        if (this.c != null) {
            this.c.a();
            this.k();
        }

    }

    private void k() {
        Vec3D var1 = this.c.f();
        this.c.a();

        double var2;
        do {
            var2 = var1.y + (double)(this.a.getRandom().nextFloat() * 20.0F);
        } while(var2 < var1.y);

        this.d = new Vec3D(var1.x, var2, var1.z);
    }

    @Nullable
    public Vec3D g() {
        return this.d;
    }

    public DragonControllerPhase<DragonControllerFly> getControllerPhase() {
        return DragonControllerPhase.e;
    }
}
