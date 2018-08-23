package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import net.minecraft.server.v1_12_R1.SoundEffects;

public class DragonControllerLandedAttack extends AbstractDragonControllerLanded {
    private int b;

    public DragonControllerLandedAttack(EntityEnderDragon var1) {
        super(var1);
    }

    public void b() {
        this.a.world.a(this.a.locX, this.a.locY, this.a.locZ, SoundEffects.aY, this.a.bK(), 2.5F, 0.8F + this.a.getRandom().nextFloat() * 0.3F, false);
    }

    public void c() {
        if (this.b++ >= 40) {
            this.a.getDragonControllerManager().setControllerPhase(DragonControllerPhase.f);
        }

    }

    public void d() {
        this.b = 0;
    }

    public DragonControllerPhase<DragonControllerLandedAttack> getControllerPhase() {
        return DragonControllerPhase.h;
    }
}

