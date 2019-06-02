package com.andrei1058.bedwars.support.bukkit.v1_9_R1;

import com.andrei1058.bedwars.arena.BedWarsTeam;
import net.minecraft.server.v1_9_R1.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.util.CraftMagicNumbers;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dragon extends EntityEnderDragon {
    private static final Logger bH = LogManager.getLogger();
    public static final DataWatcherObject<Integer> a;
    public double[][] b = new double[64][3];
    public int c = -1;
    public EntityComplexPart[] children;
    public EntityComplexPart bu;
    public EntityComplexPart bv;
    public EntityComplexPart bw;
    public EntityComplexPart bx;
    public EntityComplexPart by;
    public EntityComplexPart bz;
    public EntityComplexPart bA;
    public EntityComplexPart bB;
    public float bC;
    public float bD;
    public boolean bE;
    public int bF;
    public EntityEnderCrystal currentEnderCrystal;
    private final EnderDragonBattle bI;
    private final DragonControllerManager bJ;
    private int bK = 200;
    private int bL;
    private final PathPoint[] bM = new PathPoint[24];
    private final int[] bN = new int[24];
    private final Path bO = new Path();
    private Explosion explosionSource = new Explosion((World) null, this, 0.0D / 0.0, 0.0D / 0.0, 0.0D / 0.0, (float) (0.0F / 0.0), true, true);

    static {
        a = DataWatcher.a(Dragon.class, DataWatcherRegistry.b);
    }

    public Dragon(World world) {
        super(world);
        this.children = new EntityComplexPart[]{this.bu = new EntityComplexPart(this, "head", 6.0F, 6.0F), this.bv = new EntityComplexPart(this, "neck", 6.0F, 6.0F), this.bw = new EntityComplexPart(this, "body", 8.0F, 8.0F), this.bx = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.by = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bz = new EntityComplexPart(this, "tail", 4.0F, 4.0F), this.bA = new EntityComplexPart(this, "wing", 4.0F, 4.0F), this.bB = new EntityComplexPart(this, "wing", 4.0F, 4.0F)};
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0F, 8.0F);
        this.noclip = true;
        this.fireProof = true;
        this.bK = 100;
        this.ah = true;
        if (!world.isClientSide && world.worldProvider instanceof WorldProviderTheEnd) {
            this.bI = ((WorldProviderTheEnd) world.worldProvider).s();
        } else {
            this.bI = null;
        }

        this.bJ = new DragonControllerManager(this);
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(200.0D);
    }

    protected void i() {
        super.i();
        this.getDataWatcher().register(a, DragonControllerPhase.k.b());
    }

    public double[] a(int i, float f) {
        if (this.getHealth() <= 0.0F) {
            f = 0.0F;
        }

        f = 1.0F - f;
        int j = this.c - i & 63;
        int k = this.c - i - 1 & 63;
        double[] adouble = new double[3];
        double d0 = this.b[j][0];
        double d1 = MathHelper.g(this.b[k][0] - d0);
        adouble[0] = d0 + d1 * (double) f;
        d0 = this.b[j][1];
        d1 = this.b[k][1] - d0;
        adouble[1] = d0 + d1 * (double) f;
        adouble[2] = this.b[j][2] + (this.b[k][2] - this.b[j][2]) * (double) f;
        return adouble;
    }

    public void n() {
        float f;
        float f1;
        if (this.world.isClientSide) {
            this.setHealth(this.getHealth());
            if (!this.ad()) {
                f = MathHelper.cos(this.bD * 6.2831855F);
                f1 = MathHelper.cos(this.bC * 6.2831855F);
                if (f1 <= -0.3F && f >= -0.3F) {
                    this.world.a(this.locX, this.locY, this.locZ, SoundEffects.aP, this.bz(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
                }

                if (!this.bJ.a().a() && --this.bK < 0) {
                    this.world.a(this.locX, this.locY, this.locZ, SoundEffects.aQ, this.bz(), 2.5F, 0.8F + this.random.nextFloat() * 0.3F, false);
                    this.bK = 200 + this.random.nextInt(200);
                }
            }
        }

        this.bC = this.bD;
        float f2;
        if (this.getHealth() <= 0.0F) {
            f = (this.random.nextFloat() - 0.5F) * 8.0F;
            f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
            f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX + (double) f, this.locY + 2.0D + (double) f1, this.locZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
        } else {
            this.cV();
            f = 0.2F / (MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ) * 10.0F + 1.0F);
            f *= (float) Math.pow(2.0D, this.motY);
            if (this.bJ.a().a()) {
                this.bD += 0.1F;
            } else if (this.bE) {
                this.bD += f * 0.5F;
            } else {
                this.bD += f;
            }

            this.yaw = MathHelper.g(this.yaw);
            if (this.cR()) {
                this.bD = 0.5F;
            } else {
                if (this.c < 0) {
                    for (int i = 0; i < this.b.length; ++i) {
                        this.b[i][0] = (double) this.yaw;
                        this.b[i][1] = this.locY;
                    }
                }

                if (++this.c == this.b.length) {
                    this.c = 0;
                }

                this.b[this.c][0] = (double) this.yaw;
                this.b[this.c][1] = this.locY;
                double d0;
                double d1;
                double d2;
                float f3;
                float f16;
                if (this.world.isClientSide) {
                    if (this.bg > 0) {
                        double d3 = this.locX + (this.bh - this.locX) / (double) this.bg;
                        d0 = this.locY + (this.bi - this.locY) / (double) this.bg;
                        d1 = this.locZ + (this.bj - this.locZ) / (double) this.bg;
                        d2 = MathHelper.g(this.bk - (double) this.yaw);
                        this.yaw = (float) ((double) this.yaw + d2 / (double) this.bg);
                        this.pitch = (float) ((double) this.pitch + (this.bl - (double) this.pitch) / (double) this.bg);
                        --this.bg;
                        this.setPosition(d3, d0, d1);
                        this.setYawPitch(this.yaw, this.pitch);
                    }

                    this.bJ.a().b();
                } else {
                    IDragonController idragoncontroller = this.bJ.a();
                    idragoncontroller.c();
                    if (this.bJ.a() != idragoncontroller) {
                        idragoncontroller = this.bJ.a();
                        idragoncontroller.c();
                    }

                    Vec3D vec3d = idragoncontroller.g();
                    if (vec3d != null && idragoncontroller.i() != DragonControllerPhase.k) {
                        d0 = vec3d.x - this.locX;
                        d1 = vec3d.y - this.locY;
                        d2 = vec3d.z - this.locZ;
                        double d4 = d0 * d0 + d1 * d1 + d2 * d2;
                        f3 = idragoncontroller.f();
                        d1 = MathHelper.a(d1 / (double) MathHelper.sqrt(d0 * d0 + d2 * d2), (double) (-f3), (double) f3);
                        this.motY += d1 * 0.10000000149011612D;
                        this.yaw = MathHelper.g(this.yaw);
                        double d5 = MathHelper.a(MathHelper.g(180.0D - MathHelper.b(d0, d2) * 57.2957763671875D - (double) this.yaw), -50.0D, 50.0D);
                        Vec3D vec3d1 = (new Vec3D(vec3d.x - this.locX, vec3d.y - this.locY, vec3d.z - this.locZ)).a();
                        Vec3D vec3d2 = (new Vec3D((double) MathHelper.sin(this.yaw * 0.017453292F), this.motY, (double) (-MathHelper.cos(this.yaw * 0.017453292F)))).a();
                        float f4 = Math.max(((float) vec3d2.b(vec3d1) + 0.5F) / 1.5F, 0.0F);
                        this.bf *= 0.8F;
                        this.bf = (float) ((double) this.bf + d5 * (double) idragoncontroller.h());
                        this.yaw += this.bf * 0.1F;
                        float f5 = (float) (2.0D / (d4 + 1.0D));
                        float f6 = 0.06F;
                        this.a(0.0F, -1.0F, f6 * (f4 * f5 + (1.0F - f5)));
                        if (this.bE) {
                            this.move(this.motX * 0.800000011920929D, this.motY * 0.800000011920929D, this.motZ * 0.800000011920929D);
                        } else {
                            this.move(this.motX, this.motY, this.motZ);
                        }

                        Vec3D vec3d3 = (new Vec3D(this.motX, this.motY, this.motZ)).a();
                        f16 = ((float) vec3d3.b(vec3d2) + 1.0F) / 2.0F;
                        f16 = 0.8F + 0.15F * f16;
                        this.motX *= (double) f16;
                        this.motZ *= (double) f16;
                        this.motY *= 0.9100000262260437D;
                    }
                }

                this.aM = this.yaw;
                this.bu.width = this.bu.length = 1.0F;
                this.bv.width = this.bv.length = 3.0F;
                this.bx.width = this.bx.length = 2.0F;
                this.by.width = this.by.length = 2.0F;
                this.bz.width = this.bz.length = 2.0F;
                this.bw.length = 3.0F;
                this.bw.width = 5.0F;
                this.bA.length = 2.0F;
                this.bA.width = 4.0F;
                this.bB.length = 3.0F;
                this.bB.width = 4.0F;
                f1 = (float) (this.a(5, 1.0F)[1] - this.a(10, 1.0F)[1]) * 10.0F * 0.017453292F;
                f2 = MathHelper.cos(f1);
                float f8 = MathHelper.sin(f1);
                float f9 = this.yaw * 0.017453292F;
                float f10 = MathHelper.sin(f9);
                float f11 = MathHelper.cos(f9);
                this.bw.m();
                this.bw.setPositionRotation(this.locX + (double) (f10 * 0.5F), this.locY, this.locZ - (double) (f11 * 0.5F), 0.0F, 0.0F);
                this.bA.m();
                this.bA.setPositionRotation(this.locX + (double) (f11 * 4.5F), this.locY + 2.0D, this.locZ + (double) (f10 * 4.5F), 0.0F, 0.0F);
                this.bB.m();
                this.bB.setPositionRotation(this.locX - (double) (f11 * 4.5F), this.locY + 2.0D, this.locZ - (double) (f10 * 4.5F), 0.0F, 0.0F);
                if (!this.world.isClientSide && this.hurtTicks == 0) {
                    this.a(this.world.getEntities(this, this.bA.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
                    this.a(this.world.getEntities(this, this.bB.getBoundingBox().grow(4.0D, 2.0D, 4.0D).c(0.0D, -2.0D, 0.0D)));
                    this.b(this.world.getEntities(this, this.bu.getBoundingBox().g(1.0D)));
                    this.b(this.world.getEntities(this, this.bv.getBoundingBox().g(1.0D)));
                }

                double[] adouble = this.a(5, 1.0F);
                float f12 = MathHelper.sin(this.yaw * 0.017453292F - this.bf * 0.01F);
                float f13 = MathHelper.cos(this.yaw * 0.017453292F - this.bf * 0.01F);
                this.bu.m();
                this.bv.m();
                float f14 = this.q(1.0F);
                this.bu.setPositionRotation(this.locX + (double) (f12 * 6.5F * f2), this.locY + (double) f14 + (double) (f8 * 6.5F), this.locZ - (double) (f13 * 6.5F * f2), 0.0F, 0.0F);
                this.bv.setPositionRotation(this.locX + (double) (f12 * 5.5F * f2), this.locY + (double) f14 + (double) (f8 * 5.5F), this.locZ - (double) (f13 * 5.5F * f2), 0.0F, 0.0F);

                for (int j = 0; j < 3; ++j) {
                    EntityComplexPart entitycomplexpart = null;
                    if (j == 0) {
                        entitycomplexpart = this.bx;
                    }

                    if (j == 1) {
                        entitycomplexpart = this.by;
                    }

                    if (j == 2) {
                        entitycomplexpart = this.bz;
                    }

                    double[] adouble1 = this.a(12 + j * 2, 1.0F);
                    f3 = this.yaw * 0.017453292F + this.c(adouble1[0] - adouble[0]) * 0.017453292F;
                    float f15 = MathHelper.sin(f3);
                    f16 = MathHelper.cos(f3);
                    float f17 = 1.5F;
                    float f18 = (float) (j + 1) * 2.0F;
                    entitycomplexpart.m();
                    entitycomplexpart.setPositionRotation(this.locX - (double) ((f10 * f17 + f15 * f18) * f2), this.locY + (adouble1[1] - adouble[1]) - (double) ((f18 + f17) * f8) + 1.5D, this.locZ + (double) ((f11 * f17 + f16 * f18) * f2), 0.0F, 0.0F);
                }

                if (!this.world.isClientSide) {
                    this.bE = this.b(this.bu.getBoundingBox()) | this.b(this.bv.getBoundingBox()) | this.b(this.bw.getBoundingBox());
                    if (this.bI != null) {
                        this.bI.b(this);
                    }
                }
            }
        }

    }

    private float q(float f) {
        double d0 = 0.0D;
        if (this.bJ.a().a()) {
            d0 = -1.0D;
        } else {
            double[] adouble = this.a(5, 1.0F);
            double[] adouble1 = this.a(0, 1.0F);
            d0 = adouble[1] - adouble1[0];
        }

        return (float) d0;
    }

    private void cV() {
        if (this.currentEnderCrystal != null) {
            if (this.currentEnderCrystal.dead) {
                this.currentEnderCrystal = null;
            } else if (this.ticksLived % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                EntityRegainHealthEvent event = new EntityRegainHealthEvent(this.getBukkitEntity(), 1.0D, EntityRegainHealthEvent.RegainReason.ENDER_CRYSTAL);
                this.world.getServer().getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    this.setHealth((float) ((double) this.getHealth() + event.getAmount()));
                }
            }
        }

        if (this.random.nextInt(10) == 0) {
            List list = this.world.a(EntityEnderCrystal.class, this.getBoundingBox().g(32.0D));
            EntityEnderCrystal entityendercrystal = null;
            double d0 = 1.7976931348623157E308D;
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityEnderCrystal entityendercrystal1 = (EntityEnderCrystal) iterator.next();
                double d1 = entityendercrystal1.h(this);
                if (d1 < d0) {
                    d0 = d1;
                    entityendercrystal = entityendercrystal1;
                }
            }

            this.currentEnderCrystal = entityendercrystal;
        }

    }

    private void a(List<Entity> list) {
        double d0 = (this.bw.getBoundingBox().a + this.bw.getBoundingBox().d) / 2.0D;
        double d1 = (this.bw.getBoundingBox().c + this.bw.getBoundingBox().f) / 2.0D;
        Iterator iterator = list.iterator();

        while (iterator.hasNext()) {
            Entity entity = (Entity) iterator.next();
            if (entity instanceof EntityLiving) {
                double d2 = entity.locX - d0;
                double d3 = entity.locZ - d1;
                double d4 = d2 * d2 + d3 * d3;
                entity.g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
                if (!this.bJ.a().a() && ((EntityLiving) entity).bH() < entity.ticksLived - 2) {
                    entity.damageEntity(DamageSource.mobAttack(this), 5.0F);
                    this.a(this, entity);
                }
            }
        }

    }

    private void b(List<Entity> list) {
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity) list.get(i);
            if (entity instanceof EntityLiving) {
                entity.damageEntity(DamageSource.mobAttack(this), 10.0F);
                this.a(this, entity);
            }
        }

    }

    private float c(double d0) {
        return (float) MathHelper.g(d0);
    }

    @SuppressWarnings("deprecation")
    private boolean b(AxisAlignedBB axisalignedbb) {
        int i = MathHelper.floor(axisalignedbb.a);
        int j = MathHelper.floor(axisalignedbb.b);
        int k = MathHelper.floor(axisalignedbb.c);
        int l = MathHelper.floor(axisalignedbb.d);
        int i1 = MathHelper.floor(axisalignedbb.e);
        int j1 = MathHelper.floor(axisalignedbb.f);
        boolean flag = false;
        boolean flag1 = false;
        List<Block> destroyedBlocks = new ArrayList();
        CraftWorld craftWorld = this.world.getWorld();

        for (int k1 = i; k1 <= l; ++k1) {
            for (int l1 = j; l1 <= i1; ++l1) {
                for (int i2 = k; i2 <= j1; ++i2) {
                    BlockPosition blockposition = new BlockPosition(k1, l1, i2);
                    IBlockData iblockdata = this.world.getType(blockposition);
                    net.minecraft.server.v1_9_R1.Block block = iblockdata.getBlock();
                    if (iblockdata.getMaterial() != Material.AIR && iblockdata.getMaterial() != Material.FIRE) {
                        if (block != Blocks.BARRIER && block != Blocks.BEDROCK && block != Blocks.END_PORTAL) {
                            if (block != Blocks.COMMAND_BLOCK && block != Blocks.dc && block != Blocks.dd) {
                                flag1 = true;
                                destroyedBlocks.add(craftWorld.getBlockAt(k1, l1, i2));
                            } else {
                                flag = true;
                            }
                        } else {
                            flag = true;
                        }
                    }
                }
            }
        }

        org.bukkit.entity.Entity bukkitEntity = this.getBukkitEntity();
        EntityExplodeEvent event = new EntityExplodeEvent(bukkitEntity, bukkitEntity.getLocation(), destroyedBlocks, 0.0F);
        bukkitEntity.getServer().getPluginManager().callEvent(event);

        Block block;
        Iterator var30;
        if (event.getYield() == 0.0F) {
            var30 = event.blockList().iterator();

            while (var30.hasNext()) {
                block = (Block) var30.next();
                this.world.setAir(new BlockPosition(block.getX(), block.getY(), block.getZ()));
            }
        } else {
            var30 = event.blockList().iterator();

            while (var30.hasNext()) {
                block = (Block) var30.next();
                org.bukkit.Material blockId = block.getType();
                if (blockId != org.bukkit.Material.AIR) {
                    int blockX = block.getX();
                    int blockY = block.getY();
                    int blockZ = block.getZ();
                    net.minecraft.server.v1_9_R1.Block nmsBlock = CraftMagicNumbers.getBlock(blockId);
                    if (nmsBlock.a(this.explosionSource)) {
                        nmsBlock.dropNaturally(this.world, new BlockPosition(blockX, blockY, blockZ), nmsBlock.fromLegacyData(block.getData()), event.getYield(), 0);
                    }

                    nmsBlock.wasExploded(this.world, new BlockPosition(blockX, blockY, blockZ), this.explosionSource);
                    this.world.setAir(new BlockPosition(blockX, blockY, blockZ));
                }
            }
        }

        if (flag1) {
            double d0 = axisalignedbb.a + (axisalignedbb.d - axisalignedbb.a) * (double) this.random.nextFloat();
            double d1 = axisalignedbb.b + (axisalignedbb.e - axisalignedbb.b) * (double) this.random.nextFloat();
            double d2 = axisalignedbb.c + (axisalignedbb.f - axisalignedbb.c) * (double) this.random.nextFloat();
            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        return flag;

    }

    public boolean a(EntityComplexPart entitycomplexpart, DamageSource damagesource, float f) {
        f = this.bJ.a().a(entitycomplexpart, damagesource, f);
        if (entitycomplexpart != this.bu) {
            f = f / 4.0F + Math.min(f, 1.0F);
        }

        if (f < 0.01F) {
            return false;
        } else {
            if (damagesource.getEntity() instanceof EntityHuman || damagesource.isExplosion()) {
                float f1 = this.getHealth();
                this.dealDamage(damagesource, f);
                if (this.getHealth() <= 0.0F && !this.bJ.a().a()) {
                    this.setHealth(1.0F);
                    this.bJ.a(DragonControllerPhase.j);
                }

                if (this.bJ.a().a()) {
                    this.bL = (int) ((float) this.bL + (f1 - this.getHealth()));
                    if ((float) this.bL > 0.25F * this.getMaxHealth()) {
                        this.bL = 0;
                        this.bJ.a(DragonControllerPhase.e);
                    }
                }
            }

            return true;
        }
    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (damagesource instanceof EntityDamageSource && ((EntityDamageSource) damagesource).x()) {
            this.a(this.bw, damagesource, f);
        }

        return false;
    }

    protected boolean dealDamage(DamageSource damagesource, float f) {
        return super.damageEntity(damagesource, f);
    }

    public void Q() {
        this.die();
        if (this.bI != null) {
            this.bI.b(this);
            this.bI.a(this);
        }

    }

    @SuppressWarnings("deprecation")
    protected void bC() {
        if (this.bI != null) {
            this.bI.b(this);
        }

        ++this.bF;
        if (this.bF >= 180 && this.bF <= 200) {
            float f = (this.random.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.random.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.random.nextFloat() - 0.5F) * 8.0F;
            this.world.addParticle(EnumParticle.EXPLOSION_HUGE, this.locX + (double) f, this.locY + 2.0D + (double) f1, this.locZ + (double) f2, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        boolean flag = false;
        short short0 = 500;
        if (this.bI != null && !this.bI.d()) {
            short0 = 12000;
        }

        if (!this.world.isClientSide) {
            if (this.bF > 150 && this.bF % 5 == 0 && flag) {
                this.a(MathHelper.d((float) short0 * 0.08F));
            }

            if (this.bF == 1) {
                int viewDistance = this.world.getServer().getViewDistance() * 16;
                Iterator var4 = MinecraftServer.getServer().getPlayerList().players.iterator();

                label58:
                while (true) {
                    EntityPlayer player;
                    double deltaX;
                    double deltaZ;
                    double distanceSquared;
                    do {
                        if (!var4.hasNext()) {
                            break label58;
                        }

                        player = (EntityPlayer) var4.next();
                        deltaX = this.locX - player.locX;
                        deltaZ = this.locZ - player.locZ;
                        distanceSquared = deltaX * deltaX + deltaZ * deltaZ;
                    }
                    while (this.world.spigotConfig.dragonDeathSoundRadius > 0 && distanceSquared > (double) (this.world.spigotConfig.dragonDeathSoundRadius * this.world.spigotConfig.dragonDeathSoundRadius));

                    if (distanceSquared > (double) (viewDistance * viewDistance)) {
                        double deltaLength = Math.sqrt(distanceSquared);
                        double relativeX = player.locX + deltaX / deltaLength * (double) viewDistance;
                        double relativeZ = player.locZ + deltaZ / deltaLength * (double) viewDistance;
                        player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1028, new BlockPosition((int) relativeX, (int) this.locY, (int) relativeZ), 0, true));
                    } else {
                        player.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1028, new BlockPosition((int) this.locX, (int) this.locY, (int) this.locZ), 0, true));
                    }
                }
            }
        }

        this.move(0.0D, 0.10000000149011612D, 0.0D);
        this.aM = this.yaw += 20.0F;
        if (this.bF == 200 && !this.world.isClientSide) {
            if (flag) {
                this.a(MathHelper.d((float) short0 * 0.2F));
            }

            if (this.bI != null) {
                this.bI.a(this);
            }

            this.die();
        }

    }

    private void a(int i) {
        while (i > 0) {
            int j = EntityExperienceOrb.getOrbValue(i);
            i -= j;
            this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
        }

    }

    public int o() {
        if (this.bM[0] == null) {
            for (int i = 0; i < 24; ++i) {
                int j = 5;
                int k;
                int l;
                int i1;
                if (i < 12) {
                    k = (int) (60.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.2617994F * (float) i)));
                    l = (int) (60.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.2617994F * (float) i)));
                } else if (i < 20) {
                    i1 = i - 12;
                    k = (int) (40.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.3926991F * (float) i1)));
                    l = (int) (40.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.3926991F * (float) i1)));
                    j += 10;
                } else {
                    i1 = i - 20;
                    k = (int) (20.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.7853982F * (float) i1)));
                    l = (int) (20.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.7853982F * (float) i1)));
                }

                i1 = Math.max(this.world.K() + 10, this.world.q(new BlockPosition(k, 0, l)).getY() + j);
                this.bM[i] = new PathPoint(k, i1, l);
            }

            this.bN[0] = 6146;
            this.bN[1] = 8197;
            this.bN[2] = 8202;
            this.bN[3] = 16404;
            this.bN[4] = 32808;
            this.bN[5] = 32848;
            this.bN[6] = 65696;
            this.bN[7] = 131392;
            this.bN[8] = 131712;
            this.bN[9] = 263424;
            this.bN[10] = 526848;
            this.bN[11] = 525313;
            this.bN[12] = 1581057;
            this.bN[13] = 3166214;
            this.bN[14] = 2138120;
            this.bN[15] = 6373424;
            this.bN[16] = 4358208;
            this.bN[17] = 12910976;
            this.bN[18] = 9044480;
            this.bN[19] = 9706496;
            this.bN[20] = 15216640;
            this.bN[21] = 13688832;
            this.bN[22] = 11763712;
            this.bN[23] = 8257536;
        }

        return this.l(this.locX, this.locY, this.locZ);
    }

    public int l(double d0, double d1, double d2) {
        float f = 10000.0F;
        int i = 0;
        PathPoint pathpoint = new PathPoint(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2));
        byte b0 = 0;
        if (this.bI == null || this.bI.c() == 0) {
            b0 = 12;
        }

        for (int j = b0; j < 24; ++j) {
            if (this.bM[j] != null) {
                float f1 = this.bM[j].b(pathpoint);
                if (f1 < f) {
                    f = f1;
                    i = j;
                }
            }
        }

        return i;
    }

    public PathEntity a(int i, int j, PathPoint pathpoint) {
        PathPoint pathpoint1;
        for (int k = 0; k < 24; ++k) {
            pathpoint1 = this.bM[k];
            pathpoint1.i = false;
            pathpoint1.g = 0.0F;
            pathpoint1.e = 0.0F;
            pathpoint1.f = 0.0F;
            pathpoint1.h = null;
            pathpoint1.d = -1;
        }

        PathPoint pathpoint2 = this.bM[i];
        pathpoint1 = this.bM[j];
        pathpoint2.e = 0.0F;
        pathpoint2.f = pathpoint2.a(pathpoint1);
        pathpoint2.g = pathpoint2.f;
        this.bO.a();
        this.bO.a(pathpoint2);
        PathPoint pathpoint3 = pathpoint2;
        byte b0 = 0;
        if (this.bI == null || this.bI.c() == 0) {
            b0 = 12;
        }

        while (!this.bO.e()) {
            PathPoint pathpoint4 = this.bO.c();
            if (pathpoint4.equals(pathpoint1)) {
                if (pathpoint != null) {
                    pathpoint.h = pathpoint1;
                    pathpoint1 = pathpoint;
                }

                return this.a(pathpoint2, pathpoint1);
            }

            if (pathpoint4.a(pathpoint1) < pathpoint3.a(pathpoint1)) {
                pathpoint3 = pathpoint4;
            }

            pathpoint4.i = true;
            int l = 0;

            int i1;
            for (i1 = 0; i1 < 24; ++i1) {
                if (this.bM[i1] == pathpoint4) {
                    l = i1;
                    break;
                }
            }

            for (i1 = b0; i1 < 24; ++i1) {
                if ((this.bN[l] & 1 << i1) > 0) {
                    PathPoint pathpoint5 = this.bM[i1];
                    if (!pathpoint5.i) {
                        float f = pathpoint4.e + pathpoint4.a(pathpoint5);
                        if (!pathpoint5.a() || f < pathpoint5.e) {
                            pathpoint5.h = pathpoint4;
                            pathpoint5.e = f;
                            pathpoint5.f = pathpoint5.a(pathpoint1);
                            if (pathpoint5.a()) {
                                this.bO.a(pathpoint5, pathpoint5.e + pathpoint5.f);
                            } else {
                                pathpoint5.g = pathpoint5.e + pathpoint5.f;
                                this.bO.a(pathpoint5);
                            }
                        }
                    }
                }
            }
        }

        if (pathpoint3 == pathpoint2) {
            return null;
        } else {
            bH.debug("Failed to find path from {} to {}", new Object[]{i, j});
            if (pathpoint != null) {
                pathpoint.h = pathpoint3;
                pathpoint3 = pathpoint;
            }

            return this.a(pathpoint2, pathpoint3);
        }
    }

    private PathEntity a(PathPoint pathpoint, PathPoint pathpoint1) {
        int i = 1;

        PathPoint pathpoint2;
        for (pathpoint2 = pathpoint1; pathpoint2.h != null; pathpoint2 = pathpoint2.h) {
            ++i;
        }

        PathPoint[] apathpoint = new PathPoint[i];
        pathpoint2 = pathpoint1;
        --i;

        for (apathpoint[i] = pathpoint1; pathpoint2.h != null; apathpoint[i] = pathpoint2) {
            pathpoint2 = pathpoint2.h;
            --i;
        }

        return new PathEntity(apathpoint);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setInt("DragonPhase", this.bJ.a().i().b());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKey("DragonPhase")) {
            this.bJ.a(DragonControllerPhase.a(nbttagcompound.getInt("DragonPhase")));
        }

    }

    protected void L() {
    }

    public Entity[] aR() {
        return this.children;
    }

    public boolean isInteractable() {
        return false;
    }

    public World a() {
        return this.world;
    }

    public SoundCategory bz() {
        return SoundCategory.HOSTILE;
    }

    protected SoundEffect G() {
        return SoundEffects.aM;
    }

    protected SoundEffect bR() {
        return SoundEffects.aR;
    }

    protected float cd() {
        return 5.0F;
    }

    public Vec3D a(float f) {
        IDragonController idragoncontroller = this.bJ.a();
        DragonControllerPhase dragoncontrollerphase = idragoncontroller.i();
        float f1;
        Vec3D vec3d;
        if (dragoncontrollerphase != DragonControllerPhase.d && dragoncontrollerphase != DragonControllerPhase.e) {
            if (idragoncontroller.a()) {
                float f2 = this.pitch;
                f1 = 1.5F;
                this.pitch = -6.0F * f1 * 5.0F;
                vec3d = this.f(f);
                this.pitch = f2;
            } else {
                vec3d = this.f(f);
            }
        } else {
            BlockPosition blockposition = this.world.q(WorldGenEndTrophy.a);
            f1 = Math.max(MathHelper.sqrt(this.d(blockposition)) / 4.0F, 1.0F);
            float f3 = 6.0F / f1;
            float f4 = this.pitch;
            float f5 = 1.5F;
            this.pitch = -f3 * f5 * 5.0F;
            vec3d = this.f(f);
            this.pitch = f4;
        }

        return vec3d;
    }

    public void a(EntityEnderCrystal entityendercrystal, BlockPosition blockposition, DamageSource damagesource) {
        EntityHuman entityhuman;
        if (damagesource.getEntity() instanceof EntityHuman) {
            entityhuman = (EntityHuman) damagesource.getEntity();
        } else {
            entityhuman = this.world.a(blockposition, 64.0D, 64.0D);
        }

        if (entityendercrystal == this.currentEnderCrystal) {
            this.a(this.bu, DamageSource.b(entityhuman), 10.0F);
        }

        this.bJ.a().a(entityendercrystal, blockposition, damagesource, entityhuman);
    }

    public void a(DataWatcherObject<?> datawatcherobject) {
        if (a.equals(datawatcherobject) && this.world.isClientSide) {
            this.bJ.a(DragonControllerPhase.a((Integer) this.getDataWatcher().get(a)));
        }

        super.a(datawatcherobject);
    }

    public DragonControllerManager cT() {
        return this.bJ;
    }

    public EnderDragonBattle cU() {
        return this.bI;
    }

    public void addEffect(MobEffect mobeffect) {
    }

    protected boolean n(Entity entity) {
        return false;
    }

    public boolean aV() {
        return false;
    }
}
