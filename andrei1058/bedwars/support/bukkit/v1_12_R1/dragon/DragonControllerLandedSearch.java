package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.EntityHuman;
import net.minecraft.server.v1_12_R1.MathHelper;
import net.minecraft.server.v1_12_R1.Vec3D;

public class DragonControllerLandedSearch extends AbstractDragonControllerLanded {
    private int b;

    public DragonControllerLandedSearch(EntityEnderDragon var1) {
        super(var1);
    }

    public void c() {
        ++this.b;
        EntityHuman var1 = this.a.world.a(this.a, 20.0D, 10.0D);
        if (var1 != null) {
            if (this.b > 25) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.h);
            } else {
                Vec3D var2 = (new Vec3D(var1.locX - this.a.locX, 0.0D, var1.locZ - this.a.locZ)).a();
                Vec3D var3 = (new Vec3D((double)MathHelper.sin(this.a.yaw * 0.017453292F), 0.0D, (double)(-MathHelper.cos(this.a.yaw * 0.017453292F)))).a();
                float var4 = (float)var3.b(var2);
                float var5 = (float)(Math.acos((double)var4) * 57.2957763671875D) + 0.5F;
                if (var5 < 0.0F || var5 > 10.0F) {
                    double var6 = var1.locX - this.a.bw.locX;
                    double var8 = var1.locZ - this.a.bw.locZ;
                    double var10 = MathHelper.a(MathHelper.g(180.0D - MathHelper.c(var6, var8) * 57.2957763671875D - (double)this.a.yaw), -100.0D, 100.0D);
                    this.a.bh *= 0.8F;
                    float var12 = MathHelper.sqrt(var6 * var6 + var8 * var8) + 1.0F;
                    float var13 = var12;
                    if (var12 > 40.0F) {
                        var12 = 40.0F;
                    }

                    this.a.bh = (float)((double)this.a.bh + var10 * (double)(0.7F / var12 / var13));
                    this.a.yaw += this.a.bh;
                }
            }
        } else if (this.b >= 100) {
            var1 = this.a.world.a(this.a, 150.0D, 150.0D);
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.e);
            if (var1 != null) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.i);
                (this.a.getDragonControllerManager().b(DragonControllerPhase.i)).a(new Vec3D(var1.locX, var1.locY, var1.locZ));
            }
        }

    }

    public void d() {
        this.b = 0;
    }

    public DragonControllerPhase<DragonControllerLandedSearch> getControllerPhase() {
        return DragonControllerPhase.g;
    }
}

