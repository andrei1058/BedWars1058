package com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.BossBattle.BarColor;
import net.minecraft.server.v1_12_R1.BossBattle.BarStyle;
import net.minecraft.server.v1_12_R1.EntityEnderDragon;
import net.minecraft.server.v1_12_R1.EnumDirection.EnumDirectionLimit;
import net.minecraft.server.v1_12_R1.ShapeDetector.ShapeDetectorCollection;
import net.minecraft.server.v1_12_R1.WorldGenEnder.Spike;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonBattle {
    private static final Logger a = LogManager.getLogger();
    private static final Predicate<EntityPlayer> b;
    private final BossBattleServer c;
    private final WorldServer d;
    private final List<Integer> e;
    private final ShapeDetector f;
    private int g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private boolean l;
    private UUID m;
    private boolean n;
    private BlockPosition o;
    private EnumDragonRespawn p;
    private int q;
    private List<EntityEnderCrystal> r;

    public EnderDragonBattle(WorldServer var1, NBTTagCompound var2) {
        this.c = (BossBattleServer)(new BossBattleServer(new ChatMessage("entity.EnderDragon.name", new Object[0]), BarColor.PINK, BarStyle.PROGRESS)).setPlayMusic(true).c(true);
        this.e = Lists.newArrayList();
        this.n = true;
        this.d = var1;
        if (var2.hasKeyOfType("DragonKilled", 99)) {
            if (var2.b("DragonUUID")) {
                this.m = var2.a("DragonUUID");
            }

            this.k = var2.getBoolean("DragonKilled");
            this.l = var2.getBoolean("PreviouslyKilled");
            if (var2.getBoolean("IsRespawning")) {
                this.p = EnumDragonRespawn.START;
            }

            if (var2.hasKeyOfType("ExitPortalLocation", 10)) {
                this.o = GameProfileSerializer.c(var2.getCompound("ExitPortalLocation"));
            }
        } else {
            this.k = true;
            this.l = true;
        }

        if (var2.hasKeyOfType("Gateways", 9)) {
            NBTTagList var3 = var2.getList("Gateways", 3);

            for(int var4 = 0; var4 < var3.size(); ++var4) {
                this.e.add(var3.c(var4));
            }
        } else {
            this.e.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
            Collections.shuffle(this.e, new Random(var1.getSeed()));
        }

        this.f = ShapeDetectorBuilder.a().a(new String[]{"       ", "       ", "       ", "   #   ", "       ", "       ", "       "}).a(new String[]{"       ", "       ", "       ", "   #   ", "       ", "       ", "       "}).a(new String[]{"       ", "       ", "       ", "   #   ", "       ", "       ", "       "}).a(new String[]{"  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  "}).a(new String[]{"       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       "}).a('#', ShapeDetectorBlock.a(BlockPredicate.a(Blocks.BEDROCK))).b();
    }

    public NBTTagCompound a() {
        NBTTagCompound var1 = new NBTTagCompound();
        if (this.m != null) {
            var1.a("DragonUUID", this.m);
        }

        var1.setBoolean("DragonKilled", this.k);
        var1.setBoolean("PreviouslyKilled", this.l);
        if (this.o != null) {
            var1.set("ExitPortalLocation", GameProfileSerializer.a(this.o));
        }

        NBTTagList var2 = new NBTTagList();
        Iterator var3 = this.e.iterator();

        while(var3.hasNext()) {
            int var4 = (Integer)var3.next();
            var2.add(new NBTTagInt(var4));
        }

        var1.set("Gateways", var2);
        return var1;
    }

    public void b() {
        this.c.setVisible(!this.k);
        if (++this.j >= 20) {
            this.j();
            this.j = 0;
        }

        if (!this.c.getPlayers().isEmpty()) {
            if (this.n) {
                a.info("Scanning for legacy world dragon fight...");
                this.i();
                this.n = false;
                boolean var1 = this.g();
                if (var1) {
                    a.info("Found that the dragon has been killed in this world already.");
                    this.l = true;
                } else {
                    a.info("Found that the dragon has not yet been killed in this world.");
                    this.l = false;
                    this.a(false);
                }

                List var2 = this.d.a(net.minecraft.server.v1_12_R1.EntityEnderDragon.class, IEntitySelector.a);
                if (var2.isEmpty()) {
                    this.k = true;
                } else {
                    net.minecraft.server.v1_12_R1.EntityEnderDragon var3 = (net.minecraft.server.v1_12_R1.EntityEnderDragon)var2.get(0);
                    this.m = var3.getUniqueID();
                    a.info("Found that there's a dragon still alive ({})", var3);
                    this.k = false;
                    if (!var1) {
                        a.info("But we didn't have a portal, let's remove it.");
                        var3.die();
                        this.m = null;
                    }
                }

                if (!this.l && this.k) {
                    this.k = false;
                }
            }

            if (this.p != null) {
                if (this.r == null) {
                    this.p = null;
                    this.e();
                }

                //this.p.a(this.d, this, this.r, this.q++, this.o);
            }

            if (!this.k) {
                if (this.m == null || ++this.g >= 1200) {
                    this.i();
                    List var4 = this.d.a(net.minecraft.server.v1_12_R1.EntityEnderDragon.class, IEntitySelector.a);
                    if (var4.isEmpty()) {
                        a.debug("Haven't seen the dragon, respawning it");
                        this.m();
                    } else {
                        a.debug("Haven't seen our dragon, but found another one to use.");
                        this.m = ((net.minecraft.server.v1_12_R1.EntityEnderDragon)var4.get(0)).getUniqueID();
                    }

                    this.g = 0;
                }

                if (++this.i >= 100) {
                    this.k();
                    this.i = 0;
                }
            }
        }

    }

    protected void a(EnumDragonRespawn var1) {
        if (this.p == null) {
            throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
        } else {
            this.q = 0;
            if (var1 == EnumDragonRespawn.END) {
                this.p = null;
                this.k = false;
                com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon var2 = this.m();
                Iterator var3 = this.c.getPlayers().iterator();

                while(var3.hasNext()) {
                    EntityPlayer var4 = (EntityPlayer)var3.next();
                    CriterionTriggers.m.a(var4, var2);
                }
            } else {
                this.p = var1;
            }

        }
    }

    private boolean g() {
        for(int var1 = -8; var1 <= 8; ++var1) {
            for(int var2 = -8; var2 <= 8; ++var2) {
                Chunk var3 = this.d.getChunkAt(var1, var2);
                Iterator var4 = var3.getTileEntities().values().iterator();

                while(var4.hasNext()) {
                    TileEntity var5 = (TileEntity)var4.next();
                    if (var5 instanceof TileEntityEnderPortal) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Nullable
    private ShapeDetectorCollection h() {
        int var1;
        int var2;
        for(var1 = -8; var1 <= 8; ++var1) {
            for(var2 = -8; var2 <= 8; ++var2) {
                Chunk var3 = this.d.getChunkAt(var1, var2);
                Iterator var4 = var3.getTileEntities().values().iterator();

                while(var4.hasNext()) {
                    TileEntity var5 = (TileEntity)var4.next();
                    if (var5 instanceof TileEntityEnderPortal) {
                        ShapeDetectorCollection var6 = this.f.a(this.d, var5.getPosition());
                        if (var6 != null) {
                            BlockPosition var7 = var6.a(3, 3, 3).getPosition();
                            if (this.o == null && var7.getX() == 0 && var7.getZ() == 0) {
                                this.o = var7;
                            }

                            return var6;
                        }
                    }
                }
            }
        }

        var1 = this.d.getHighestBlockYAt(WorldGenEndTrophy.a).getY();

        for(var2 = var1; var2 >= 0; --var2) {
            ShapeDetectorCollection var8 = this.f.a(this.d, new BlockPosition(WorldGenEndTrophy.a.getX(), var2, WorldGenEndTrophy.a.getZ()));
            if (var8 != null) {
                if (this.o == null) {
                    this.o = var8.a(3, 3, 3).getPosition();
                }

                return var8;
            }
        }

        return null;
    }

    private void i() {
        for(int var1 = -8; var1 <= 8; ++var1) {
            for(int var2 = -8; var2 <= 8; ++var2) {
                this.d.getChunkAt(var1, var2);
            }
        }

    }

    private void j() {
        HashSet var1 = Sets.newHashSet();
        Iterator var2 = this.d.b(EntityPlayer.class, b).iterator();

        while(var2.hasNext()) {
            EntityPlayer var3 = (EntityPlayer)var2.next();
            this.c.addPlayer(var3);
            var1.add(var3);
        }

        HashSet var5 = Sets.newHashSet(this.c.getPlayers());
        var5.removeAll(var1);
        Iterator var6 = var5.iterator();

        while(var6.hasNext()) {
            EntityPlayer var4 = (EntityPlayer)var6.next();
            this.c.removePlayer(var4);
        }

    }

    private void k() {
        this.i = 0;
        this.h = 0;
        Spike[] var1 = BiomeTheEndDecorator.a(this.d);
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Spike var4 = var1[var3];
            this.h += this.d.a(EntityEnderCrystal.class, var4.f()).size();
        }

        a.debug("Found {} end crystals still alive", this.h);
    }

    public void a(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon var1) {
        if (var1.getUniqueID().equals(this.m)) {
            this.c.setProgress(0.0F);
            this.c.setVisible(false);
            this.a(true);
            this.l();
            if (!this.l) {
                this.d.setTypeUpdate(this.d.getHighestBlockYAt(WorldGenEndTrophy.a), Blocks.DRAGON_EGG.getBlockData());
            }

            this.l = true;
            this.k = true;
        }

    }

    private void l() {
        if (!this.e.isEmpty()) {
            int var1 = (Integer)this.e.remove(this.e.size() - 1);
            int var2 = (int)(96.0D * Math.cos(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double)var1)));
            int var3 = (int)(96.0D * Math.sin(2.0D * (-3.141592653589793D + 0.15707963267948966D * (double)var1)));
            this.a(new BlockPosition(var2, 75, var3));
        }
    }

    private void a(BlockPosition var1) {
        this.d.triggerEffect(3000, var1, 0);
        (new WorldGenEndGateway()).generate(this.d, new Random(), var1);
    }

    private void a(boolean var1) {
        WorldGenEndTrophy var2 = new WorldGenEndTrophy(var1);
        if (this.o == null) {
            for(this.o = this.d.q(WorldGenEndTrophy.a).down(); this.d.getType(this.o).getBlock() == Blocks.BEDROCK && this.o.getY() > this.d.getSeaLevel(); this.o = this.o.down()) {
                ;
            }
        }

        var2.generate(this.d, new Random(), this.o);
    }

    private com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon m() {
        this.d.getChunkAtWorldCoords(new BlockPosition(0, 128, 0));
        com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon var1 = new com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon(this.d, null);
        var1.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
        var1.setPositionRotation(0.0D, 128.0D, 0.0D, this.d.random.nextFloat() * 360.0F, 0.0F);
        this.d.addEntity(var1);
        this.m = var1.getUniqueID();
        return var1;
    }

    public void b(com.andrei1058.bedwars.support.bukkit.v1_12_R1.dragon.EntityEnderDragon var1) {
        if (var1.getUniqueID().equals(this.m)) {
            this.c.setProgress(var1.getHealth() / var1.getMaxHealth());
            this.g = 0;
            if (var1.hasCustomName()) {
                this.c.a(var1.getScoreboardDisplayName());
            }
        }

    }

    public int c() {
        return this.h;
    }

    public void a(EntityEnderCrystal var1, DamageSource var2) {
        if (this.p != null && this.r.contains(var1)) {
            a.debug("Aborting respawn sequence");
            this.p = null;
            this.q = 0;
            this.f();
            this.a(true);
        } else {
            this.k();
            Entity var3 = this.d.getEntity(this.m);
            if (var3 instanceof net.minecraft.server.v1_12_R1.EntityEnderDragon) {
                ((EntityEnderDragon)var3).a(var1, new BlockPosition(var1), var2);
            }
        }

    }

    public boolean d() {
        return this.l;
    }

    public void e() {
        if (this.k && this.p == null) {
            BlockPosition var1 = this.o;
            if (var1 == null) {
                a.debug("Tried to respawn, but need to find the portal first.");
                ShapeDetectorCollection var2 = this.h();
                if (var2 == null) {
                    a.debug("Couldn't find a portal, so we made one.");
                    this.a(true);
                } else {
                    a.debug("Found the exit portal & temporarily using it.");
                }

                var1 = this.o;
            }

            ArrayList var7 = Lists.newArrayList();
            BlockPosition var3 = var1.up(1);
            Iterator var4 = EnumDirectionLimit.HORIZONTAL.iterator();

            while(var4.hasNext()) {
                EnumDirection var5 = (EnumDirection)var4.next();
                List var6 = this.d.a(EntityEnderCrystal.class, new AxisAlignedBB(var3.shift(var5, 2)));
                if (var6.isEmpty()) {
                    return;
                }

                var7.addAll(var6);
            }

            a.debug("Found all crystals, respawning dragon.");
            this.a((List)var7);
        }

    }

    private void a(List<EntityEnderCrystal> var1) {
        if (this.k && this.p == null) {
            for(ShapeDetectorCollection var2 = this.h(); var2 != null; var2 = this.h()) {
                for(int var3 = 0; var3 < this.f.c(); ++var3) {
                    for(int var4 = 0; var4 < this.f.b(); ++var4) {
                        for(int var5 = 0; var5 < this.f.a(); ++var5) {
                            ShapeDetectorBlock var6 = var2.a(var3, var4, var5);
                            if (var6.a().getBlock() == Blocks.BEDROCK || var6.a().getBlock() == Blocks.END_PORTAL) {
                                this.d.setTypeUpdate(var6.getPosition(), Blocks.END_STONE.getBlockData());
                            }
                        }
                    }
                }
            }

            this.p = EnumDragonRespawn.START;
            this.q = 0;
            this.a(false);
            this.r = var1;
        }

    }

    public void f() {
        Spike[] var1 = BiomeTheEndDecorator.a(this.d);
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Spike var4 = var1[var3];
            List var5 = this.d.a(EntityEnderCrystal.class, var4.f());
            Iterator var6 = var5.iterator();

            while(var6.hasNext()) {
                EntityEnderCrystal var7 = (EntityEnderCrystal)var6.next();
                var7.setInvulnerable(false);
                var7.setBeamTarget((BlockPosition)null);
            }
        }

    }

    static {
        b = Predicates.and(IEntitySelector.a, IEntitySelector.a(0.0D, 128.0D, 0.0D, 192.0D));
    }
}

