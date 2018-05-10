package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.*;

import javax.annotation.Nullable;

public abstract class AbstractDragonController implements IDragonController {
    protected final EntityEnderDragon a;

    public AbstractDragonController(EntityEnderDragon var1) {
        this.a = var1;
    }

    public boolean a() {
        return false;
    }

    public void b() {
    }

    public void c() {
    }

    public void a(EntityEnderCrystal var1, BlockPosition var2, DamageSource var3, @Nullable EntityHuman var4) {
    }

    public void d() {
    }

    public void e() {
    }

    public float f() {
        return 0.6F;
    }

    @Nullable
    public Vec3D g() {
        return null;
    }

    public float a(EntityComplexPart var1, DamageSource var2, float var3) {
        return var3;
    }

    public float h() {
        float var1 = MathHelper.sqrt(this.a.motX * this.a.motX + this.a.motZ * this.a.motZ) + 1.0F;
        float var2 = Math.min(var1, 40.0F);
        return 0.7F / var2 / var1;
    }
}

