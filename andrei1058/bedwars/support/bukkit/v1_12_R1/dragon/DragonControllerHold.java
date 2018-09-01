package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.*;

import javax.annotation.Nullable;

public class DragonControllerHold extends AbstractDragonController {
    private PathEntity b;
    private Vec3D c;
    private boolean d;

    public DragonControllerHold(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon var1) {
        super(var1);
    }

    public DragonControllerPhase<DragonControllerHold> getControllerPhase() {
        return DragonControllerPhase.a;
    }

    public void c() {
        double var1 = this.c == null ? 0.0D : this.c.c(this.a.locX, this.a.locY, this.a.locZ);
        if (var1 < 100.0D || var1 > 22500.0D || this.a.positionChanged || this.a.B) {
            this.j();
        }

    }

    public void d() {
        this.b = null;
        this.c = null;
    }

    @Nullable
    public Vec3D g() {
        return this.c;
    }

    private void j() {
        int var2;
        if (this.b != null && this.b.b()) {
            BlockPosition var1 = this.a.world.q(new BlockPosition(WorldGenEndTrophy.a));
            var2 = this.a.df() == null ? 0 : this.a.df().c();
            if (this.a.getRandom().nextInt(var2 + 3) == 0) {
                this.a.getDragonControllerManager().setControllerPhase(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.DragonControllerPhase.c);
                return;
            }

            double var3 = 64.0D;
            EntityHuman var5 = this.a.world.a(var1, var3, var3);
            if (var5 != null) {
                var3 = var5.d(var1) / 512.0D;
            }

            if (var5 != null && (this.a.getRandom().nextInt(MathHelper.a((int)var3) + 2) == 0 || this.a.getRandom().nextInt(var2 + 2) == 0)) {
                this.a(var5);
                return;
            }
        }

        if (this.b == null || this.b.b()) {
            int var6 = this.a.p();
            var2 = var6;
            if (this.a.getRandom().nextInt(8) == 0) {
                this.d = !this.d;
                var2 = var6 + 6;
            }

            if (this.d) {
                ++var2;
            } else {
                --var2;
            }

            if (this.a.df() != null && this.a.df().c() >= 0) {
                var2 %= 12;
                if (var2 < 0) {
                    var2 += 12;
                }
            } else {
                var2 -= 12;
                var2 &= 7;
                var2 += 12;
            }

            this.b = this.a.a(var6, var2, null);
            if (this.b != null) {
                this.b.a();
            }
        }

        this.k();
    }

    private void a(EntityHuman var1) {
        this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.b);
        (this.a.getDragonControllerManager().b(DragonControllerPhase.b)).a(var1);
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

    public void a(EntityEnderCrystal var1, BlockPosition var2, DamageSource var3, @Nullable EntityHuman var4) {
        if (var4 != null && !var4.abilities.isInvulnerable) {
            this.a(var4);
        }

    }
}

