package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.BlockPosition.MutableBlockPosition;

public class DragonControllerLandedFlame extends AbstractDragonControllerLanded {
    private int b;
    private int c;
    private EntityAreaEffectCloud d;

    public DragonControllerLandedFlame(EntityEnderDragon var1) {
        super(var1);
    }

    public void b() {
        ++this.b;
        if (this.b % 2 == 0 && this.b < 10) {
            Vec3D var1 = this.a.a(1.0F).a();
            var1.b(-0.7853982F);
            double var2 = this.a.bw.locX;
            double var4 = this.a.bw.locY + (double)(this.a.bw.length / 2.0F);
            double var6 = this.a.bw.locZ;

            for(int var8 = 0; var8 < 8; ++var8) {
                double var9 = var2 + this.a.getRandom().nextGaussian() / 2.0D;
                double var11 = var4 + this.a.getRandom().nextGaussian() / 2.0D;
                double var13 = var6 + this.a.getRandom().nextGaussian() / 2.0D;

                for(int var15 = 0; var15 < 6; ++var15) {
                    this.a.world.addParticle(EnumParticle.DRAGON_BREATH, var9, var11, var13, -var1.x * 0.07999999821186066D * (double)var15, -var1.y * 0.6000000238418579D, -var1.z * 0.07999999821186066D * (double)var15, new int[0]);
                }

                var1.b(0.19634955F);
            }
        }

    }

    public void c() {
        ++this.b;
        if (this.b >= 200) {
            if (this.c >= 4) {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.e);
            } else {
                this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.g);
            }
        } else if (this.b == 10) {
            Vec3D var1 = (new Vec3D(this.a.bw.locX - this.a.locX, 0.0D, this.a.bw.locZ - this.a.locZ)).a();
            float var2 = 5.0F;
            double var3 = this.a.bw.locX + var1.x * 5.0D / 2.0D;
            double var5 = this.a.bw.locZ + var1.z * 5.0D / 2.0D;
            double var7 = this.a.bw.locY + (double)(this.a.bw.length / 2.0F);
            MutableBlockPosition var9 = new MutableBlockPosition(MathHelper.floor(var3), MathHelper.floor(var7), MathHelper.floor(var5));

            while(this.a.world.isEmpty(var9)) {
                --var7;
                var9.c(MathHelper.floor(var3), MathHelper.floor(var7), MathHelper.floor(var5));
            }

            var7 = (double)(MathHelper.floor(var7) + 1);
            this.d = new EntityAreaEffectCloud(this.a.world, var3, var7, var5);
            this.d.setSource(this.a);
            this.d.setRadius(5.0F);
            this.d.setDuration(200);
            this.d.setParticle(EnumParticle.DRAGON_BREATH);
            this.d.a(new MobEffect(MobEffects.HARM));
            this.a.world.addEntity(this.d);
        }

    }

    public void d() {
        this.b = 0;
        ++this.c;
    }

    public void e() {
        if (this.d != null) {
            this.d.die();
            this.d = null;
        }

    }

    public DragonControllerPhase<DragonControllerLandedFlame> getControllerPhase() {
        return DragonControllerPhase.f;
    }

    public void j() {
        this.c = 0;
    }
}

