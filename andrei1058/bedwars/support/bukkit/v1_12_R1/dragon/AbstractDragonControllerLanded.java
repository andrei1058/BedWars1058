package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityArrow;
import net.minecraft.server.v1_12_R1.EntityComplexPart;

public abstract class AbstractDragonControllerLanded extends AbstractDragonController {

    public AbstractDragonControllerLanded(EntityEnderDragon var1) {
        super(var1);
    }

    public boolean a() {
        return true;
    }

    public float a(EntityComplexPart var1, DamageSource var2, float var3) {
        if (var2.i() instanceof EntityArrow) {
            var2.i().setOnFire(1);
            return 0.0F;
        } else {
            return super.a(var1, var2, var3);
        }
    }
}
