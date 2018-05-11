package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import javax.annotation.Nullable;

import net.minecraft.server.v1_12_R1.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonControllerStrafe extends com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.AbstractDragonController {
    private static final Logger b = LogManager.getLogger();
    private int c;
    private PathEntity d;
    private Vec3D e;
    private EntityLiving f;
    private boolean g;

    public DragonControllerStrafe(EntityEnderDragon var1) {
        super(var1);
    }

    public void c() {
        if (this.f == null) {
            b.warn("Skipping player strafe phase because no player was found");
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
        } else {
            double var1;
            double var3;
            double var9;
            if (this.d != null && this.d.b()) {
                var1 = this.f.locX;
                var3 = this.f.locZ;
                double var5 = var1 - this.a.locX;
                double var7 = var3 - this.a.locZ;
                var9 = (double)MathHelper.sqrt(var5 * var5 + var7 * var7);
                double var11 = Math.min(0.4000000059604645D + var9 / 80.0D - 1.0D, 10.0D);
                this.e = new Vec3D(var1, this.f.locY + var11, var3);
            }

            var1 = this.e == null ? 0.0D : this.e.c(this.a.locX, this.a.locY, this.a.locZ);
            if (var1 < 100.0D || var1 > 22500.0D) {
                this.j();
            }

            var3 = 64.0D;
            if (this.f.h(this.a) < 4096.0D) {
                if (this.a.hasLineOfSight(this.f)) {
                    ++this.c;
                    Vec3D var25 = (new Vec3D(this.f.locX - this.a.locX, 0.0D, this.f.locZ - this.a.locZ)).a();
                    Vec3D var6 = (new Vec3D((double)MathHelper.sin(this.a.yaw * 0.017453292F), 0.0D, (double)(-MathHelper.cos(this.a.yaw * 0.017453292F)))).a();
                    float var26 = (float)var6.b(var25);
                    float var8 = (float)(Math.acos((double)var26) * 57.2957763671875D);
                    var8 += 0.5F;
                    if (this.c >= 5 && var8 >= 0.0F && var8 < 10.0F) {
                        var9 = 1.0D;
                        Vec3D var27 = this.a.e(1.0F);
                        double var12 = this.a.bw.locX - var27.x * 1.0D;
                        double var14 = this.a.bw.locY + (double)(this.a.bw.length / 2.0F) + 0.5D;
                        double var16 = this.a.bw.locZ - var27.z * 1.0D;
                        double var18 = this.f.locX - var12;
                        double var20 = this.f.locY + (double)(this.f.length / 2.0F) - (var14 + (double)(this.a.bw.length / 2.0F));
                        double var22 = this.f.locZ - var16;
                        this.a.world.a((EntityHuman)null, 1017, new BlockPosition(this.a), 0);
                        EntityDragonFireball var24 = new EntityDragonFireball(this.a.world, this.a, var18, var20, var22);
                        var24.setPositionRotation(var12, var14, var16, 0.0F, 0.0F);
                        this.a.world.addEntity(var24);
                        this.c = 0;
                        if (this.d != null) {
                            while(!this.d.b()) {
                                this.d.a();
                            }
                        }

                        this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
                    }
                } else if (this.c > 0) {
                    --this.c;
                }
            } else if (this.c > 0) {
                --this.c;
            }

        }
    }

    private void j() {
        if (this.d == null || this.d.b()) {
            int var1 = this.a.p();
            int var2 = var1;
            if (this.a.getRandom().nextInt(8) == 0) {
                this.g = !this.g;
                var2 = var1 + 6;
            }

            if (this.g) {
                ++var2;
            } else {
                --var2;
            }

            if (this.a.df() != null && this.a.df().c() > 0) {
                var2 %= 12;
                if (var2 < 0) {
                    var2 += 12;
                }
            } else {
                var2 -= 12;
                var2 &= 7;
                var2 += 12;
            }

            this.d = this.a.a(var1, var2, null);
            if (this.d != null) {
                this.d.a();
            }
        }

        this.k();
    }

    private void k() {
        if (this.d != null && !this.d.b()) {
            Vec3D var1 = this.d.f();
            this.d.a();
            double var2 = var1.x;
            double var6 = var1.z;

            double var4;
            do {
                var4 = var1.y + (double)(this.a.getRandom().nextFloat() * 20.0F);
            } while(var4 < var1.y);

            this.e = new Vec3D(var2, var4, var6);
        }

    }

    public void d() {
        this.c = 0;
        this.e = null;
        this.d = null;
        this.f = null;
    }

    public void a(EntityLiving var1) {
        this.f = var1;
        int var2 = this.a.p();
        int var3 = this.a.k(this.f.locX, this.f.locY, this.f.locZ);
        int var4 = MathHelper.floor(this.f.locX);
        int var5 = MathHelper.floor(this.f.locZ);
        double var6 = (double)var4 - this.a.locX;
        double var8 = (double)var5 - this.a.locZ;
        double var10 = (double)MathHelper.sqrt(var6 * var6 + var8 * var8);
        double var12 = Math.min(0.4000000059604645D + var10 / 80.0D - 1.0D, 10.0D);
        int var14 = MathHelper.floor(this.f.locY + var12);
        PathPoint var15 = new PathPoint(var4, var14, var5);
        this.d = this.a.a(var2, var3, var15);
        if (this.d != null) {
            this.d.a();
            this.k();
        }

    }

    @Nullable
    public Vec3D g() {
        return this.e;
    }

    public DragonControllerPhase<DragonControllerStrafe> getControllerPhase() {
        return DragonControllerPhase.b;
    }
}
