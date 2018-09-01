package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.*;

import javax.annotation.Nullable;

public class DragonControllerLanding extends AbstractDragonController {
    private Vec3D b;

    public DragonControllerLanding(EntityEnderDragon var1) {
        super(var1);
    }

    public void b() {
        Vec3D var1 = this.a.a(1.0F).a();
        var1.b(-0.7853982F);
        double var2 = this.a.bw.locX;
        double var4 = this.a.bw.locY + (double)(this.a.bw.length / 2.0F);
        double var6 = this.a.bw.locZ;

        for(int var8 = 0; var8 < 8; ++var8) {
            double var9 = var2 + this.a.getRandom().nextGaussian() / 2.0D;
            double var11 = var4 + this.a.getRandom().nextGaussian() / 2.0D;
            double var13 = var6 + this.a.getRandom().nextGaussian() / 2.0D;
            this.a.world.addParticle(EnumParticle.DRAGON_BREATH, var9, var11, var13, -var1.x * 0.07999999821186066D + this.a.motX, -var1.y * 0.30000001192092896D + this.a.motY, -var1.z * 0.07999999821186066D + this.a.motZ, new int[0]);
            var1.b(0.19634955F);
        }

    }

    public void c() {
        if (this.b == null) {
            this.b = new Vec3D(this.a.world.q(WorldGenEndTrophy.a));
        }

        if (this.b.c(this.a.locX, this.a.locY, this.a.locZ) < 1.0D) {
            ((DragonControllerLandedFlame)this.a.getDragonControllerManager().b(DragonControllerPhase.f)).j();
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.g);
        }

    }

    public float f() {
        return 1.5F;
    }

    public float h() {
        float var1 = MathHelper.sqrt(this.a.motX * this.a.motX + this.a.motZ * this.a.motZ) + 1.0F;
        float var2 = Math.min(var1, 40.0F);
        return var2 / var1;
    }

    public void d() {
        this.b = null;
    }

    @Nullable
    public Vec3D g() {
        return this.b;
    }

    public DragonControllerPhase<DragonControllerLanding> getControllerPhase() {
        return DragonControllerPhase.d;
    }
}

