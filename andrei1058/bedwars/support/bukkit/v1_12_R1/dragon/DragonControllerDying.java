package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.Vec3D;
import net.minecraft.server.v1_12_R1.WorldGenEndTrophy;

import javax.annotation.Nullable;

public class DragonControllerDying extends AbstractDragonController {
    private Vec3D b;
    private int c;

    public DragonControllerDying(EntityEnderDragon var1) {
        super(var1);
    }

    public void b() {
        if (this.c++ % 10 == 0) {
            float var1 = (this.a.getRandom().nextFloat() - 0.5F) * 8.0F;
            float var2 = (this.a.getRandom().nextFloat() - 0.5F) * 4.0F;
            float var3 = (this.a.getRandom().nextFloat() - 0.5F) * 8.0F;
            this.a.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.a.locX + (double)var1, this.a.locY + 2.0D + (double)var2, this.a.locZ + (double)var3, 0.0D, 0.0D, 0.0D, new int[0]);
        }

    }

    public void c() {
        ++this.c;
        if (this.b == null) {
            BlockPosition var1 = this.a.world.getHighestBlockYAt(WorldGenEndTrophy.a);
            this.b = new Vec3D((double)var1.getX(), (double)var1.getY(), (double)var1.getZ());
        }

        double var3 = this.b.c(this.a.locX, this.a.locY, this.a.locZ);
        if (var3 >= 100.0D && var3 <= 22500.0D && !this.a.positionChanged && !this.a.B) {
            this.a.setHealth(1.0F);
        } else {
            this.a.setHealth(0.0F);
        }

    }

    public void d() {
        this.b = null;
        this.c = 0;
    }

    public float f() {
        return 3.0F;
    }

    @Nullable
    public Vec3D g() {
        return this.b;
    }

    public DragonControllerPhase<DragonControllerDying> getControllerPhase() {
        return DragonControllerPhase.j;
    }
}
