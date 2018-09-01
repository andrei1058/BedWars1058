package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public class DragonControllerPhase<T extends IDragonController> {
    private static DragonControllerPhase<?>[] l = new DragonControllerPhase[0];
    public static final DragonControllerPhase<DragonControllerHold> a = a(DragonControllerHold.class, "HoldingPattern");
    public static final DragonControllerPhase<DragonControllerStrafe> b = a(DragonControllerStrafe.class, "StrafePlayer");
    public static final DragonControllerPhase<DragonControllerLandingFly> c = a(DragonControllerLandingFly.class, "LandingApproach");
    public static final DragonControllerPhase<DragonControllerLanding> d = a(DragonControllerLanding.class, "Landing");
    public static final DragonControllerPhase<DragonControllerFly> e = a(DragonControllerFly.class, "Takeoff");
    public static final DragonControllerPhase<DragonControllerLandedFlame> f = a(DragonControllerLandedFlame.class, "SittingFlaming");
    public static final DragonControllerPhase<DragonControllerLandedSearch> g = a(DragonControllerLandedSearch.class, "SittingScanning");
    public static final DragonControllerPhase<DragonControllerLandedAttack> h = a(DragonControllerLandedAttack.class, "SittingAttacking");
    public static final DragonControllerPhase<DragonControllerChange> i = a(DragonControllerChange.class, "ChargingPlayer");
    public static final DragonControllerPhase<DragonControllerDying> j = a(DragonControllerDying.class, "Dying");
    public static final DragonControllerPhase<DragonControllerHover> k = a(DragonControllerHover.class, "Hover");
    private final Class<? extends IDragonController> m;
    private final int n;
    private final String o;

    private DragonControllerPhase(int var1, Class<? extends IDragonController> var2, String var3) {
        this.n = var1;
        this.m = var2;
        this.o = var3;
    }

    public IDragonController a(EntityEnderDragon var1) {
        try {
            Constructor var2 = this.a();
            return (IDragonController)var2.newInstance(var1);
        } catch (Exception var3) {
            throw new Error(var3);
        }
    }

    protected Constructor<? extends IDragonController> a() throws NoSuchMethodException {
        return this.m.getConstructor(EntityEnderDragon.class);
    }

    public int b() {
        return this.n;
    }

    public String toString() {
        return this.o + " (#" + this.n + ")";
    }

    public static DragonControllerPhase<?> getById(int var0) {
        return var0 >= 0 && var0 < l.length ? l[var0] : a;
    }

    public static int c() {
        return l.length;
    }

    private static <T extends IDragonController> DragonControllerPhase<T> a(Class<T> var0, String var1) {
        DragonControllerPhase var2 = new DragonControllerPhase(l.length, var0, var1);
        l = Arrays.copyOf(l, l.length + 1);
        l[var2.b()] = var2;
        return var2;
    }
}

