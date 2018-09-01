package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;

public class DragonControllerManager {
    private static final Logger a = LogManager.getLogger();
    private final com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon enderDragon;
    private final com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.IDragonController[] dragonControllers = new com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.IDragonController[DragonControllerPhase.c()];
    private com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.IDragonController currentDragonController;

    public DragonControllerManager(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon entityenderdragon) {
        this.enderDragon = entityenderdragon;
        this.setControllerPhase(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.DragonControllerPhase.a);
    }

    public void setControllerPhase(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.DragonControllerPhase<?> dragoncontrollerphase) {
        if (this.currentDragonController == null || dragoncontrollerphase != this.currentDragonController.getControllerPhase()) {
            if (this.currentDragonController != null) {
                this.currentDragonController.e();
            }

            /*EnderDragonChangePhaseEvent event = new EnderDragonChangePhaseEvent((CraftEnderDragon)this.enderDragon.getBukkitEntity(), this.currentDragonController == null ? null : CraftEnderDragon.getBukkitPhase(this.currentDragonController.getControllerPhase()), CraftEnderDragon.getBukkitPhase(dragoncontrollerphase));
            this.enderDragon.world.getServer().getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }*/

            dragoncontrollerphase = com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.CraftEnderDragon.getMinecraftPhase(CraftEnderDragon.getBukkitPhase(dragoncontrollerphase));
            this.currentDragonController = this.b(dragoncontrollerphase);
            if (!this.enderDragon.world.isClientSide) {
                this.enderDragon.getDataWatcher().set(EntityEnderDragon.PHASE, dragoncontrollerphase.b());
            }

            a.debug("Dragon is now in phase {} on the {}", dragoncontrollerphase, this.enderDragon.world.isClientSide ? "client" : "server");
            this.currentDragonController.d();
        }

    }

    public com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.IDragonController a() {
        return this.currentDragonController;
    }

    public <T extends com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.IDragonController> T b(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.DragonControllerPhase<T> dragoncontrollerphase) {
        int i = dragoncontrollerphase.b();
        if (this.dragonControllers[i] == null) {
            this.dragonControllers[i] = dragoncontrollerphase.a(this.enderDragon);
        }

        return (T) this.dragonControllers[i];
    }
}

