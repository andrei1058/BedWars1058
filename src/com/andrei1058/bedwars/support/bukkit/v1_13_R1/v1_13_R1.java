package com.andrei1058.bedwars.support.bukkit.v1_13_R1;

import com.andrei1058.bedwars.Main;
import com.andrei1058.bedwars.api.TeamColor;
import com.andrei1058.bedwars.arena.Arena;
import com.andrei1058.bedwars.arena.BedWarsTeam;
import com.andrei1058.bedwars.arena.SBoard;
import com.andrei1058.bedwars.arena.ShopHolo;
import com.andrei1058.bedwars.configuration.ConfigPath;
import com.andrei1058.bedwars.language.Language;
import com.andrei1058.bedwars.language.Messages;
import com.andrei1058.bedwars.exceptions.InvalidSoundException;
import com.andrei1058.bedwars.support.bukkit.NMS;
import com.google.common.collect.Sets;
import com.mojang.datafixers.types.Type;
import net.minecraft.server.v1_13_R1.*;
import net.minecraft.server.v1_13_R1.Item;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Bed;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_13_R1.CraftServer;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_13_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.andrei1058.bedwars.Main.*;
import static com.andrei1058.bedwars.arena.despawnables.TargetListener.owningTeam;
import static com.andrei1058.bedwars.language.Language.getMsg;

public class v1_13_R1 implements NMS {

    private Sound bedDestroy = Sound.valueOf("ENTITY_ENDER_DRAGON_GROWL"),
            playerKill = Sound.valueOf("ENTITY_WOLF_HURT"),
            countDown = Sound.valueOf("ENTITY_CHICKEN_EGG"),
            bought = Sound.valueOf("BLOCK_ANVIL_HIT"),
            insuffMoney = Sound.valueOf("ENTITY_ENDERMAN_TELEPORT");

    /**
     * ArenaList of despawnable entities aka special shop mobs
     */
    private static List<Despawnable> despawnables = new ArrayList();

    @Override
    public Sound bedDestroy() {
        return bedDestroy;
    }

    @Override
    public void setBedDestroySound(String sound) throws InvalidSoundException {
        try {
            bedDestroy = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public Sound playerKill() {
        return playerKill;
    }

    @Override
    public void setPlayerKillsSound(String sound) throws InvalidSoundException {
        try {
            playerKill = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public void registerCommand(String name, Command clasa) {
        ((CraftServer) plugin.getServer()).getCommandMap().register(name, clasa);
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (title != null) {
            if (!title.isEmpty()) {
                IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
                PacketPlayOutTitle tit = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, bc);
                PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tit);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
            }
        }
        if (subtitle != null) {
            if (!subtitle.isEmpty()) {
                IChatBaseComponent bc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
                PacketPlayOutTitle tit = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, bc);
                PacketPlayOutTitle length = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(tit);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(length);
            }
        }
    }

    @Override
    public Sound countdownTick() {
        return countDown;
    }

    @Override
    public void setCountdownSound(String sound) throws InvalidSoundException {
        try {
            countDown = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    public void spawnSilverfish(Location loc, BedWarsTeam bedWarsTeam) {
        new Despawnable(Silverfish.spawn(loc, bedWarsTeam), bedWarsTeam, shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_SILVERFISH_DESPAWN), Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME);
    }

    @Override
    public void spawnIronGolem(Location loc, BedWarsTeam bedWarsTeam) {
        new Despawnable(IGolem.spawn(loc, bedWarsTeam), bedWarsTeam, shop.getYml().getInt(ConfigPath.SHOP_SPECIAL_IRON_GOLEM_DESPAWN), Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME);
    }

    @Override
    public void hidePlayer(Player player, List<Player> players) {
        for (Player p : players){
            if (p == player) continue;
            p.hidePlayer(player);
        }
    }

    @Override
    public void hidePlayer(Player victim, Player p) {
        if (victim == p) return;
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(victim.getEntityId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void refreshDespawnables() {
        for (Despawnable d : new ArrayList<>(despawnables)) {
            d.regresh();
        }
    }

    @Override
    public boolean isDespawnable(Entity e) {
        for (Despawnable d : despawnables) {
            if (d.getE() == ((CraftEntity) e).getHandle()) return true;
        }
        return false;
    }

    @Override
    public BedWarsTeam ownDespawnable(Entity e) {
        for (Despawnable d : despawnables) {
            if (d.getE() == ((CraftEntity) e).getHandle()) return d.getTeam();
        }
        return null;
    }

    @Override
    public Sound insufficientMoney() {
        return insuffMoney;
    }

    @Override
    public void setInsuffMoneySound(String sound) throws InvalidSoundException {
        try {
            insuffMoney = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public Sound bought() {
        return bought;
    }

    @Override
    public void setBoughtSound(String sound) throws InvalidSoundException {
        try {
            bought = Sound.valueOf(sound);
        } catch (Exception ex) {
            throw new InvalidSoundException(sound);
        }
    }

    @Override
    public void playAction(Player p, String text) {
        CraftPlayer cPlayer = (CraftPlayer) p;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO);
        cPlayer.getHandle().playerConnection.sendPacket(ppoc);
    }

    @Override
    public boolean isBukkitCommandRegistered(String name) {
        return ((CraftServer) plugin.getServer()).getCommandMap().getCommand(name) != null;
    }

    @Override
    public org.bukkit.inventory.ItemStack getItemInHand(Player p) {
        return p.getInventory().getItemInMainHand();
    }

    @Override
    public void hideEntity(Entity e, Player p) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(e.getEntityId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

    }

    @Override
    public void minusAmount(Player p, org.bukkit.inventory.ItemStack i, int amount) {
        i.setAmount(i.getAmount() - amount);
    }

    @Override
    public void setSource(TNTPrimed tnt, Player owner) {
        EntityLiving nmsEntityLiving = (((CraftLivingEntity) owner).getHandle());
        EntityTNTPrimed nmsTNT = (((CraftTNTPrimed) tnt).getHandle());
        try {
            Field sourceField = EntityTNTPrimed.class.getDeclaredField("source");
            sourceField.setAccessible(true);
            sourceField.set(nmsTNT, nmsEntityLiving);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean isArmor(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemArmor;
    }

    @Override
    public boolean isTool(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemTool;
    }

    @Override
    public boolean isSword(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemSword;
    }

    @Override
    public boolean isBow(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemBow;
    }

    @Override
    public boolean isProjectile(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof IProjectile;
    }

    @Override
    public void registerEntities() {
        Map<Object, Type<?>> types = (Map<Object, Type<?>>) DataConverterRegistry.a().getSchema(15190).findChoiceType(DataConverterTypes.n).types();
        types.put("minecraft:bwvillager", types.get("minecraft:villager"));
        EntityTypes.a("bwvillager", EntityTypes.a.a(VillagerShop.class, VillagerShop::new));
        types.put("minecraft:bwsilverfish", types.get("minecraft:silverfish"));
        EntityTypes.a("bwsilverfish", EntityTypes.a.a(Silverfish.class, Silverfish::new));
        types.put("minecraft:bwgolem", types.get("minecraft:iron_golem"));
        EntityTypes.a("bwgolem", EntityTypes.a.a(IGolem.class, IGolem::new));
    }

    @Override
    public void spawnShop(Location loc, String name1, List<Player> players, Arena arena) {
        Location l = loc.clone();
        spawnVillager(l);
        for (Player p : players) {
            String[] nume = getMsg(p, name1).split(",");
            if (nume.length == 1) {
                ArmorStand a = createArmorStand(nume[0], l.clone().add(0, 1.85, 0));
                new ShopHolo(Language.getPlayerLanguage(p).getIso(), a, null, l, arena);
            } else {
                ArmorStand a = createArmorStand(nume[0], l.clone().add(0, 2.1, 0));
                ArmorStand b = createArmorStand(nume[1], l.clone().add(0, 1.85, 0));
                new ShopHolo(Language.getPlayerLanguage(p).getIso(), a, b, l, arena);
            }
        }
        for (ShopHolo sh : ShopHolo.getShopHolo()) {
            if (sh.getA() == arena) {
                sh.update();
            }
        }
    }

    @Override
    public double getDamage(org.bukkit.inventory.ItemStack i) {
        ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("generic.attackDamage");
    }

    @Override
    public double getProtection(org.bukkit.inventory.ItemStack i) {
        ItemStack nmsStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        return compound.getDouble("generic.armor");
    }

    private static ArmorStand createArmorStand(String name, Location loc) {
        ArmorStand a = loc.getWorld().spawn(loc, ArmorStand.class);
        a.setGravity(false);
        a.setVisible(false);
        a.setCustomNameVisible(true);
        a.setCustomName(name);
        return a;
    }

    /**
     * Custom villager class
     */
    public class VillagerShop extends EntityVillager {

        public VillagerShop(World world) {

            super(world);

            try {
                Field bField = PathfinderGoalSelector.class.getDeclaredField("b");
                bField.setAccessible(true);
                Field cField = PathfinderGoalSelector.class.getDeclaredField("c");
                cField.setAccessible(true);
                bField.set(this.goalSelector, Sets.newLinkedHashSet());
                bField.set(this.targetSelector, Sets.newLinkedHashSet());
                cField.set(this.goalSelector, Sets.newLinkedHashSet());
                cField.set(this.targetSelector, Sets.newLinkedHashSet());
            } catch (Exception bField) {
            }

            this.goalSelector.a(0, new PathfinderGoalFloat(this));
            this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0f, 1.0f));
            this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0f));
        }

        @Override
        public void collide(net.minecraft.server.v1_13_R1.Entity entity) {
        }

        @Override
        public boolean damageEntity(DamageSource damagesource, float f) {
            return false;
        }

        public void g(double d0, double d1, double d2) {
        }

        @Override
        public void move(EnumMoveType enummovetype, double d0, double d1, double d2) {
        }

        public void a(SoundEffect soundeffect, float f, float f1) {
        }

        @Override
        protected void initAttributes() {
            super.initAttributes();
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0D);
        }
    }

    /**
     * Spawn shop npc
     */
    private Villager spawnVillager(Location loc) {
        WorldServer mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        VillagerShop customEnt = new VillagerShop(mcWorld);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (Villager) customEnt.getBukkitEntity();
    }

    private class Despawnable {
        EntityLiving e;
        BedWarsTeam team;
        int despawn = 250;
        String namePath;

        public Despawnable(EntityLiving e, BedWarsTeam team, int despawn, String namePath) {
            this.e = e;
            this.team = team;
            if (despawn != 0) {
                this.despawn = despawn;
            }
            this.namePath = namePath;
            despawnables.add(this);
            setName();
            owningTeam.put(e.getUniqueID(), team.getName());
        }

        public void regresh() {
            if (!e.isAlive()) {
                despawnables.remove(this);
                return;
            }
            setName();
            despawn--;
            if (despawn == 0) {
                e.damageEntity(DamageSource.OUT_OF_WORLD, 9000);
                despawnables.remove(this);
            }
        }

        private void setName() {
            int percentuale = (int) ((e.getHealth() * 100) / e.getMaxHealth() / 10);
            (e.getBukkitEntity()).setCustomName(lang.m(namePath).replace("{despawn}", String.valueOf(despawn)).replace("{health}",
                    new String(new char[percentuale]).replace("\0", lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH)) + new String(new char[10 - percentuale]).replace("\0", "§7" + lang.m(Messages.FORMATTING_DESPAWNABLE_UTILITY_NPC_HEALTH))
            ).replace("{TeamColor}", TeamColor.getChatColor(team.getColor()).toString()).replace("{TeamName}", team.getName()));
        }

        public EntityLiving getE() {
            return e;
        }

        public BedWarsTeam getTeam() {
            return team;
        }
    }

    @Override
    public void unregisterCommand(String name) {
        if (isBukkitCommandRegistered(name)) {
            ((CraftServer) plugin.getServer()).getCommandMap().getCommand(name).unregister(((CraftServer) plugin.getServer()).getCommandMap());
        }
    }

    @Override
    public void voidKill(Player p) {
        ((CraftPlayer) p).getHandle().damageEntity(DamageSource.OUT_OF_WORLD, 1000);
    }

    @Override
    public void hideArmor(Player p, Player p2) {
        PacketPlayOutEntityEquipment hand1 = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.MAINHAND, new ItemStack(new Item(new Item.Info()).getById(0)));
        PacketPlayOutEntityEquipment hand2 = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.OFFHAND, new ItemStack(new Item(new Item.Info()).getById(0)));
        PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.HEAD, new ItemStack(new Item(new Item.Info()).getById(0)));
        PacketPlayOutEntityEquipment chest = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.CHEST, new ItemStack(new Item(new Item.Info()).getById(0)));
        PacketPlayOutEntityEquipment pants = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.LEGS, new ItemStack(new Item(new Item.Info()).getById(0)));
        PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.FEET, new ItemStack(new Item(new Item.Info()).getById(0)));
        EntityPlayer pc = ((CraftPlayer) p2).getHandle();
        if (p != p2) {
            pc.playerConnection.sendPacket(hand1);
            pc.playerConnection.sendPacket(hand2);
        }
        pc.playerConnection.sendPacket(helmet);
        pc.playerConnection.sendPacket(chest);
        pc.playerConnection.sendPacket(pants);
        pc.playerConnection.sendPacket(boots);
    }

    @Override
    public void showArmor(Player p, Player p2) {
        PacketPlayOutEntityEquipment hand1 = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(p.getInventory().getItemInMainHand()));
        PacketPlayOutEntityEquipment hand2 = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(p.getInventory().getItemInOffHand()));
        PacketPlayOutEntityEquipment helmet = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(p.getInventory().getHelmet()));
        PacketPlayOutEntityEquipment chest = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(p.getInventory().getChestplate()));
        PacketPlayOutEntityEquipment pants = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(p.getInventory().getLeggings()));
        PacketPlayOutEntityEquipment boots = new PacketPlayOutEntityEquipment(p.getEntityId(), EnumItemSlot.FEET, CraftItemStack.asNMSCopy(p.getInventory().getBoots()));
        EntityPlayer pc = ((CraftPlayer) p2).getHandle();
        if (p != p2) {
            pc.playerConnection.sendPacket(hand1);
            pc.playerConnection.sendPacket(hand2);
        }
        pc.playerConnection.sendPacket(helmet);
        pc.playerConnection.sendPacket(chest);
        pc.playerConnection.sendPacket(pants);
        pc.playerConnection.sendPacket(boots);
    }

    @Override
    public void spawnDragon(Location l, BedWarsTeam bwt) {
        EnderDragon ed = (EnderDragon) l.getWorld().spawnEntity(l, EntityType.ENDER_DRAGON);
        ed.setPhase(EnderDragon.Phase.CIRCLING);
        ed.setMetadata("DragonTeam", new FixedMetadataValue(plugin, bwt));
    }

    @Override
    public void colorBed(BedWarsTeam bwt) {
        for (int x = -1; x <= 1; x++){
            for (int z = -1; z <= 1; z++){
                BlockState bed = bwt.getBed().clone().add(x, 0, z).getBlock().getState();
                if (bed instanceof Bed) {
                    bed.setType(TeamColor.getBedBlock(bwt.getColor()));
                    bed.update();
                }
            }
        }
    }

    @Override
    public void registerTntWhitelist() {
        try {
            Field field = net.minecraft.server.v1_13_R2.Block.class.getDeclaredField("durability");
            field.setAccessible(true);
            field.setFloat(Blocks.END_STONE, 69f);
            field.setFloat(Blocks.GLASS, 300f);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showPlayer(Player victim, Player p) {
        if (victim == p) return;
        //noinspection
        //p.showPlayer(Main.plugin, victim);
        p.showPlayer(victim);
    }

    @Override
    public Effect eggBridge() {
        return Effect.MOBSPAWNER_FLAMES;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setBlockTeamColor(org.bukkit.block.Block block, TeamColor teamColor) {
        if (block.getType().toString().contains("STAINED_GLASS") || block.getType().toString().equals("GLASS")){
            block.setType(TeamColor.getGlass(teamColor));
        } else if (block.getType().toString().contains("_TERRACOTTA")){
            block.setType(TeamColor.getGlazedTerracotta(teamColor));
        } else if (block.getType().toString().contains("_WOOL")){
            block.setType(TeamColor.getWool(teamColor));
        }
    }

    @Override
    public void setCollide(Player p, Arena a, boolean value) {
        p.setCollidable(value);
        if (a == null) return;
        for (SBoard sb : new ArrayList<>(SBoard.getScoreboards())) {
            if (sb.getArena() == a) {
                sb.updateSpectators(p, value);
            }
        }
    }

    @Override
    public org.bukkit.inventory.ItemStack addCustomData(org.bukkit.inventory.ItemStack i, String data) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
            itemStack.setTag(tag);
        }

        tag.setString("BedWars1058", data);
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    @Override
    public boolean isCustomBedWarsItem(org.bukkit.inventory.ItemStack i) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) return false;
        return tag.hasKey("BedWars1058");
    }

    @Override
    public String getCustomData(org.bukkit.inventory.ItemStack i) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) return "";
        return tag.getString("BedWars1058");
    }

    @Override
    public org.bukkit.inventory.ItemStack setSkullOwner(org.bukkit.inventory.ItemStack i, Player p) {
        if (i.getType() != org.bukkit.Material.valueOf("PLAYER_HEAD")) return i;
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        //sm.setOwningPlayer(p);
        //noinspection deprecation
        sm.setOwner(p.getName());
        i.setItemMeta(sm);
        return i;
    }

    @Override
    public org.bukkit.inventory.ItemStack colourItem(org.bukkit.inventory.ItemStack itemStack, BedWarsTeam bedWarsTeam) {
        if (itemStack == null) return null;
        String type = itemStack.getType().toString();
        if (type.contains("_BED")) {
            return new org.bukkit.inventory.ItemStack(TeamColor.getBedBlock(bedWarsTeam.getColor()), itemStack.getAmount());
        } else if (type.contains("_STAINED_GLASS_PANE")) {
            return new org.bukkit.inventory.ItemStack(TeamColor.getGlassPane(bedWarsTeam.getColor()), itemStack.getAmount());
        } else if (type.contains("STAINED_GLASS") || type.equals("GLASS")){
            return new org.bukkit.inventory.ItemStack(TeamColor.getGlass(bedWarsTeam.getColor()), itemStack.getAmount());
        } else if (type.contains("_TERRACOTTA")){
            return new org.bukkit.inventory.ItemStack(TeamColor.getGlazedTerracotta(bedWarsTeam.getColor()), itemStack.getAmount());
        } else if (type.contains("_WOOL")){
            return new org.bukkit.inventory.ItemStack(TeamColor.getWool(bedWarsTeam.getColor()), itemStack.getAmount());
        }
        return itemStack;
    }

    @Override
    public org.bukkit.inventory.ItemStack createItemStack(String material, int amount, short data) {
        org.bukkit.inventory.ItemStack i;
        try {
            i = new org.bukkit.inventory.ItemStack(org.bukkit.Material.valueOf(material), amount);
        } catch (Exception ex) {
            Main.plugin.getLogger().severe(material + " is not a valid " + com.andrei1058.bedwars.Main.getServerVersion() + " material!");
            i = new org.bukkit.inventory.ItemStack(org.bukkit.Material.BEDROCK);
        }
        return i;
    }

    @Override
    public void teamCollideRule(Team team) {
        team.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        team.setCanSeeFriendlyInvisibles(true);
    }

    @Override
    public boolean isPlayerHead(String material, int data) {
        return material.equals("PLAYER_HEAD");
    }

    @Override
    public org.bukkit.Material materialFireball() {
        return org.bukkit.Material.valueOf("FIRE_CHARGE");
    }

    @Override
    public org.bukkit.Material materialPlayerHead() {
        return org.bukkit.Material.valueOf("PLAYER_HEAD");
    }

    @Override
    public org.bukkit.Material materialSnowball() {
        return org.bukkit.Material.valueOf("SNOWBALL");
    }

    @Override
    public org.bukkit.Material materialGoldenHelmet() {
        return org.bukkit.Material.valueOf("GOLDEN_HELMET");
    }

    @Override
    public org.bukkit.Material materialGoldenChestPlate() {
        return org.bukkit.Material.valueOf("GOLDEN_CHESTPLATE");
    }

    @Override
    public org.bukkit.Material materialGoldenLeggings() {
        return org.bukkit.Material.valueOf("GOLDEN_LEGGINGS");
    }

    @Override
    public org.bukkit.Material materialCake() {
        return org.bukkit.Material.valueOf("CAKE");
    }

    @Override
    public org.bukkit.Material materialCraftingTable() {
        return org.bukkit.Material.valueOf("CRAFTING_TABLE");
    }

    @Override
    public org.bukkit.Material materialEnchantingTable() {
        return org.bukkit.Material.valueOf("ENCHANTING_TABLE");
    }

    @Override
    public boolean isBed(org.bukkit.Material material) {
        return material.toString().contains("_BED");
    }

    @Override
    public boolean itemStackDataCompare(org.bukkit.inventory.ItemStack i, short data) {
        return true;
    }

    @Override
    public void setBlockData(Block block, byte data) {

    }

    @Override
    public void setBlockData(Block block, String data) {
        Method m = null;
        try {
            m = Block.class.getDeclaredMethod("getBlockData");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        BlockData result = null;
        try {
            assert m != null;
            result = (BlockData) m.invoke(block, (Object) null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        Directional d = (Directional) result;
        d.setFacing(BlockFace.valueOf(data));
        Method m2 = null;
        try {
            m2 = Block.class.getDeclaredMethod("setBlockData", BlockData.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            m2.invoke(block, result);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        block.getState().update(true);
    }

    @Override
    public org.bukkit.Material woolMaterial() {
        return org.bukkit.Material.valueOf("WHITE_WOOL");
    }


    @Override
    public String getShopUpgradeIdentifier(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = i.getTag();
        return tag == null ? "" : tag.hasKey("tierIdentifier") ? tag.getString("tierIdentifier") : "";
    }

    @Override
    public org.bukkit.inventory.ItemStack setShopUpgradeIdentifier(org.bukkit.inventory.ItemStack itemStack, String identifier) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = i.getTag();
        if (tag == null) {
            tag = new NBTTagCompound();
            i.setTag(tag);
        }
        tag.setString("tierIdentifier", identifier);
        return CraftItemStack.asBukkitCopy(i);
    }

    @Override
    public org.bukkit.inventory.ItemStack getPlayerHead(Player player) {
        org.bukkit.inventory.ItemStack head = new org.bukkit.inventory.ItemStack(materialPlayerHead());

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, ((CraftPlayer)player).getProfile());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    @Override
    public String getBlockData(Block block) {
        Method m = null;
        try {
            m = Block.class.getDeclaredMethod("getBlockData");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        BlockData result = null;
        try {
            assert m != null;
            result = (BlockData) m.invoke(block, (Object) null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if (result instanceof Directional) {
            Directional d = (Directional) result;
            return d.getFacing().name();
        }
        return "";
    }

    @Override
    public void invisibilityFix(Player player, Arena arena) {
        EntityPlayer pc = ((CraftPlayer) player).getHandle();

        for (Player pl : arena.getPlayers()){
            if (pl.equals(player)) continue;
            if (arena.getRespawn().containsKey(pl)) continue;
            if (pl.hasPotionEffect(PotionEffectType.INVISIBILITY)) continue;
            pc.playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(((CraftPlayer) pl).getHandle()));
            pc.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ((CraftPlayer) pl).getHandle()));
            showArmor(pl, player);
        }
    }

    @Override
    public String getInventoryName(InventoryEvent e) {
        return e.getInventory().getName();
    }

    @Override
    public void setUnbreakable(ItemMeta itemMeta) {
        itemMeta.setUnbreakable(true);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getLevelName() {
        return ((DedicatedServer) MinecraftServer.getServer()).propertyManager.properties.getProperty("level-name");
    }
}
