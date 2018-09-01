package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;


import net.minecraft.server.v1_12_R1.*;

import javax.annotation.Nullable;

public class DragonControllerLandingFly extends AbstractDragonController {
    private PathEntity b;
    private Vec3D c;

    public DragonControllerLandingFly(EntityEnderDragon var1) {
        super(var1);
    }

    public DragonControllerPhase<DragonControllerLandingFly> getControllerPhase() {
        return DragonControllerPhase.c;
    }

    public void d() {
        this.b = null;
        this.c = null;
    }

    public void c() {
        double var1 = this.c == null ? 0.0D : this.c.c(this.a.locX, this.a.locY, this.a.locZ);
        if (var1 < 100.0D || var1 > 22500.0D || this.a.positionChanged || this.a.B) {
            this.j();
        }

    }

    @Nullable
    public Vec3D g() {
        return this.c;
    }

    private void j() {
        if (this.b == null || this.b.b()) {
            int var1 = this.a.p();
            BlockPosition var2 = this.a.world.q(WorldGenEndTrophy.a);
            EntityHuman var3 = this.a.world.a(var2, 128.0D, 128.0D);
            int var4;
            if (var3 != null) {
                Vec3D var5 = (new Vec3D(var3.locX, 0.0D, var3.locZ)).a();
                var4 = this.a.k(-var5.x * 40.0D, 105.0D, -var5.z * 40.0D);
            } else {
                var4 = this.a.k(40.0D, (double)var2.getY(), 0.0D);
            }

            PathPoint var6 = new PathPoint(var2.getX(), var2.getY(), var2.getZ());
            this.b = this.a.a(var1, var4, var6);
            if (this.b != null) {
                this.b.a();
            }
        }

        this.k();
        if (this.b != null && this.b.b()) {
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.d);
        }

    }

    private void k() {
        if (this.b != null && !this.b.b()) {
            Vec3D var1 = this.b.f();
            this.b.a();
            double var2 = var1.x;
            double var4 = var1.z;

            double var6;
            do {
                var6 = var1.y + (double)(this.a.getRandom().nextFloat() * 20.0F);
            } while(var6 < var1.y);

            this.c = new Vec3D(var2, var6, var4);
        }

    }
}

