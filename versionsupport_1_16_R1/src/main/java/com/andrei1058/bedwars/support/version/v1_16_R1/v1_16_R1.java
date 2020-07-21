package com.andrei1058.bedwars.support.version.v1_16_R1;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.shop.ShopHolo;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.entity.Despawnable;
import com.andrei1058.bedwars.api.events.player.PlayerKillEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import com.andrei1058.bedwars.api.server.VersionSupport;
import com.andrei1058.bedwars.support.version.common.VersionCommon;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_16_R1.CraftServer;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class v1_16_R1 extends VersionSupport {

    private static int renderDistance = 48;
    private static final UUID chatUUID = new UUID(0L, 0L);

    public v1_16_R1(Plugin plugin, String name) {
        super(plugin, name);
        loadDefaultEffects();
    }

    @Override
    public void registerVersionListeners() {
        new VersionCommon(this);
    }

    @Override
    public void registerCommand(String name, Command clasa) {
        ((CraftServer) getPlugin().getServer()).getCommandMap().register(name, clasa);
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

    public void spawnSilverfish(Location loc, ITeam bedWarsTeam, double speed, double health, int despawn, double damage) {
        new Despawnable(Silverfish.spawn(loc, bedWarsTeam, speed, health, despawn, damage), bedWarsTeam, despawn,
                Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME, PlayerKillEvent.PlayerKillCause.SILVERFISH_FINAL_KILL, PlayerKillEvent.PlayerKillCause.SILVERFISH);
    }

    @Override
    public void spawnIronGolem(Location loc, ITeam bedWarsTeam, double speed, double health, int despawn) {
        new Despawnable(IGolem.spawn(loc, bedWarsTeam, speed, health, despawn), bedWarsTeam, despawn, Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME,
                PlayerKillEvent.PlayerKillCause.IRON_GOLEM_FINAL_KILL, PlayerKillEvent.PlayerKillCause.IRON_GOLEM);
    }

    @Override
    public void hidePlayer(Player player, List<Player> players) {
        for (Player p : players) {
            if (p == player) continue;
            p.hidePlayer(getPlugin(), player);
        }
    }

    @Override
    public void hidePlayer(Player victim, Player p) {
        if (victim == p) return;
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(victim.getEntityId());
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void playAction(Player p, String text) {
        CraftPlayer cPlayer = (CraftPlayer) p;
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}");
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.GAME_INFO, chatUUID);
        cPlayer.getHandle().playerConnection.sendPacket(ppoc);
    }

    @Override
    public boolean isBukkitCommandRegistered(String name) {
        return ((CraftServer) getPlugin().getServer()).getCommandMap().getCommand(name) != null;
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
    public boolean isAxe(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ItemAxe;
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
        return CraftItemStack.asNMSCopy(itemStack).A() instanceof IProjectile;
    }

    @SuppressWarnings({"unchecked", "ResultOfMethodCallIgnored"})
    @Override
    public void registerEntities() {
        Map<String, Type<?>> types = (Map<String, Type<?>>) DataConverterRegistry.a().getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion())).findChoiceType(DataConverterTypes.ENTITY).types();
        types.put("minecraft:bwvillager", types.get("minecraft:villager"));
        /*EntityTypes.a<net.minecraft.server.com.andrei1058.bedwars.support.version.v1_16_R1.v1_16_R1.Entity> a =*/
        //IRegistry.a(IRegistry.ENTITY_TYPE, "bwvillager", EntityTypes.Builder.a(VillagerShop::new, EnumCreatureType.CREATURE).a("bwvillager"));
        //customEntities = IRegistry.a(IRegistry.ENTITY_TYPE, "bwvillager", a.a("bwvillager"));
        EntityTypes.Builder.a(VillagerShop::new, EnumCreatureType.CREATURE).a("bwvillager");


        types.put("minecraft:bwsilverfish", types.get("minecraft:silverfish"));
        //IRegistry.a(IRegistry.ENTITY_TYPE, "bwsilverfish", EntityTypes.Builder.a(VillagerShop::new, EnumCreatureType.MONSTER).a("bwsilverfish"));
        //EntityTypes.a.a(Silverfish::new, EnumCreatureType.MONSTER);
        EntityTypes.Builder.a(Silverfish::new, EnumCreatureType.MONSTER).a("bwsilverfish");


        types.put("minecraft:bwgolem", types.get("minecraft:iron_golem"));
        //EntityTypes.a.a(IGolem::new, EnumCreatureType.AMBIENT);
        EntityTypes.Builder.a(IGolem::new, EnumCreatureType.AMBIENT).a("bwgolem");
    }

    @Override
    public void spawnShop(Location loc, String name1, List<Player> players, IArena arena) {
        Location l = loc.clone();
        spawnVillager(l);
        for (Player p : players) {
            String[] nume = Language.getMsg(p, name1).split(",");
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
        //noinspection ConstantConditions
        return compound.getDouble("generic.attackDamage");
    }

    private static ArmorStand createArmorStand(String name, Location loc) {
        if (loc == null) return null;
        if (loc.getWorld() == null) return null;
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
    public static class VillagerShop extends EntityVillager {

        @Override
        public void openTrade(EntityHuman entityhuman, IChatBaseComponent ichatbasecomponent, int i) {
        }

        @Override
        public void setTradingPlayer(EntityHuman entityhuman) {
        }

        @SuppressWarnings("unchecked")
        VillagerShop(EntityTypes entityTypes, World world) {
            super(entityTypes, world);
        }

        @Override
        public void collide(net.minecraft.server.v1_16_R1.Entity entity) {
        }

        @Override
        public boolean damageEntity(DamageSource damagesource, float f) {
            return false;
        }

        @Override
        public void setVillagerData(VillagerData villagerdata) {
        }

        @Override
        public void a(MemoryModuleType<GlobalPos> memorymoduletype) {
        }

        @Override
        public void onLightningStrike(EntityLightning entitylightning) {
        }

        @Override
        public void a(EntityVillager entityvillager, long i) {
        }

        @Override
        public void a(ReputationEvent reputationevent, net.minecraft.server.v1_16_R1.Entity entity) {
        }

        @Override
        public int getExperience() {
            return 0;
        }

        public static AttributeProvider.Builder eX() {
            return EntityInsentient.p().a(GenericAttributes.MOVEMENT_SPEED, 0.0D).a(GenericAttributes.FOLLOW_RANGE, 0.0D);
        }

        @Override
        public void move(EnumMoveType enummovetype, Vec3D vec3d) {

        }

        @Override
        public void playSound(SoundEffect soundeffect, float f, float f1) {
        }

        @Override
        public void fd() {
        }
    }

    /**
     * Spawn shop npc
     */
    private void spawnVillager(Location loc) {
        //noinspection ConstantConditions
        WorldServer mcWorld = ((CraftWorld) loc.getWorld()).getHandle();
        VillagerShop customEnt = new VillagerShop(EntityTypes.VILLAGER, mcWorld);
        customEnt.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftLivingEntity) customEnt.getBukkitEntity()).setRemoveWhenFarAway(false);
        mcWorld.addEntity(customEnt, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public void voidKill(Player p) {
        ((CraftPlayer) p).getHandle().damageEntity(DamageSource.OUT_OF_WORLD, 1000);
    }

    @Override
    public void hideArmor(Player p, Player p2) {
        List<Pair<EnumItemSlot, ItemStack>> items = new ArrayList<>();
        List<Pair<EnumItemSlot, ItemStack>> hands = new ArrayList<>();
        hands.add(new Pair<>(EnumItemSlot.MAINHAND, new ItemStack(net.minecraft.server.v1_16_R1.Item.getById(0))));
        hands.add(new Pair<>(EnumItemSlot.OFFHAND, new ItemStack(net.minecraft.server.v1_16_R1.Item.getById(0))));

        items.add(new Pair<>(EnumItemSlot.HEAD, new ItemStack(net.minecraft.server.v1_16_R1.Item.getById(0))));
        items.add(new Pair<>(EnumItemSlot.CHEST, new ItemStack(net.minecraft.server.v1_16_R1.Item.getById(0))));
        items.add(new Pair<>(EnumItemSlot.LEGS, new ItemStack(net.minecraft.server.v1_16_R1.Item.getById(0))));
        items.add(new Pair<>(EnumItemSlot.FEET, new ItemStack(net.minecraft.server.v1_16_R1.Item.getById(0))));
        PacketPlayOutEntityEquipment packet1 = new PacketPlayOutEntityEquipment(p.getEntityId(), items);
        PacketPlayOutEntityEquipment packet2 = new PacketPlayOutEntityEquipment(p.getEntityId(), hands);
        EntityPlayer pc = ((CraftPlayer) p2).getHandle();
        if (p != p2) {
            pc.playerConnection.sendPacket(packet2);
        }
        pc.playerConnection.sendPacket(packet1);
    }

    @Override
    public void showArmor(Player p, Player p2) {
        List<Pair<EnumItemSlot, ItemStack>> items = new ArrayList<>();
        List<Pair<EnumItemSlot, ItemStack>> hands = new ArrayList<>();

        hands.add(new Pair<>(EnumItemSlot.MAINHAND, CraftItemStack.asNMSCopy(p.getInventory().getItemInMainHand())));
        hands.add(new Pair<>(EnumItemSlot.OFFHAND, CraftItemStack.asNMSCopy(p.getInventory().getItemInOffHand())));

        items.add(new Pair<>(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(p.getInventory().getHelmet())));
        items.add(new Pair<>(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(p.getInventory().getChestplate())));
        items.add(new Pair<>(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(p.getInventory().getLeggings())));
        items.add(new Pair<>(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(p.getInventory().getBoots())));
        PacketPlayOutEntityEquipment packet1 = new PacketPlayOutEntityEquipment(p.getEntityId(), items);
        PacketPlayOutEntityEquipment packet2 = new PacketPlayOutEntityEquipment(p.getEntityId(), hands);
        EntityPlayer pc = ((CraftPlayer) p2).getHandle();
        if (p != p2) {
            pc.playerConnection.sendPacket(packet2);
        }
        pc.playerConnection.sendPacket(packet1);
    }

    @Override
    public void spawnDragon(Location l, ITeam bwt) {
        if (l == null || l.getWorld() == null) {
            getPlugin().getLogger().log(Level.WARNING, "Could not spawn Dragon. Location is null");
            return;
        }
        EnderDragon ed = (EnderDragon) l.getWorld().spawnEntity(l, EntityType.ENDER_DRAGON);
        ed.setPhase(EnderDragon.Phase.CIRCLING);
    }

    @Override
    public void colorBed(ITeam bwt) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockState bed = bwt.getBed().clone().add(x, 0, z).getBlock().getState();
                if (bed instanceof Bed) {
                    bed.setType(bwt.getColor().bedMaterial());
                    bed.update();
                }
            }
        }
    }

    @Override
    public void registerTntWhitelist() {
        try {
            Field field = net.minecraft.server.v1_16_R1.BlockBase.class.getDeclaredField("durability");
            field.setAccessible(true);
            field.set(Blocks.END_STONE, 12f);
            field.set(Blocks.GLASS, 300f);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showPlayer(Player victim, Player p) {
        if (victim == p) return;
        if (victim.getLocation().getWorld() == null) return;
        if (!victim.getLocation().getWorld().equals(p.getWorld())) return;
        if (victim.getLocation().distanceSquared(p.getLocation()) <= renderDistance) {
            PacketPlayOutNamedEntitySpawn s = new PacketPlayOutNamedEntitySpawn(((CraftPlayer) victim).getHandle());
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(s);
        }
    }

    @Override
    public void showPlayer(Player whoToShow, List<Player> p) {
        for (Player p1 : p) {
            if (p1.equals(whoToShow)) continue;
            p1.showPlayer(getPlugin(), whoToShow);
        }
    }

    @Override
    public void setBlockTeamColor(Block block, TeamColor teamColor) {
        if (block.getType().toString().contains("STAINED_GLASS") || block.getType().toString().equals("GLASS")) {
            block.setType(teamColor.glassMaterial());
        } else if (block.getType().toString().contains("_TERRACOTTA")) {
            block.setType(teamColor.glazedTerracottaMaterial());
        } else if (block.getType().toString().contains("_WOOL")) {
            block.setType(teamColor.woolMaterial());
        }
    }

    @Override
    public void setCollide(Player p, IArena a, boolean value) {
        p.setCollidable(value);
        if (a == null) return;
        a.updateSpectatorCollideRule(p, value);
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
        if (!(i.getItemMeta() instanceof SkullMeta)) return i;
        SkullMeta sm = (SkullMeta) i.getItemMeta();
        sm.setOwningPlayer(p);
        i.setItemMeta(sm);
        return i;
    }

    @Override
    public org.bukkit.inventory.ItemStack colourItem(org.bukkit.inventory.ItemStack itemStack, ITeam bedWarsTeam) {
        if (itemStack == null) return null;
        String type = itemStack.getType().toString();
        if (type.contains("_BED")) {
            return new org.bukkit.inventory.ItemStack(bedWarsTeam.getColor().bedMaterial(), itemStack.getAmount());
        } else if (type.contains("_STAINED_GLASS_PANE")) {
            return new org.bukkit.inventory.ItemStack(bedWarsTeam.getColor().glassPaneMaterial(), itemStack.getAmount());
        } else if (type.contains("STAINED_GLASS") || type.equals("GLASS")) {
            return new org.bukkit.inventory.ItemStack(bedWarsTeam.getColor().glassMaterial(), itemStack.getAmount());
        } else if (type.contains("_TERRACOTTA")) {
            return new org.bukkit.inventory.ItemStack(bedWarsTeam.getColor().glazedTerracottaMaterial(), itemStack.getAmount());
        } else if (type.contains("_WOOL")) {
            return new org.bukkit.inventory.ItemStack(bedWarsTeam.getColor().woolMaterial(), itemStack.getAmount());
        }
        return itemStack;
    }

    @Override
    public org.bukkit.inventory.ItemStack createItemStack(String material, int amount, short data) {
        org.bukkit.inventory.ItemStack i;
        try {
            i = new org.bukkit.inventory.ItemStack(org.bukkit.Material.valueOf(material), amount);
        } catch (Exception ex) {
            getPlugin().getLogger().log(Level.WARNING, material + " is not a valid " + getName() + " material!");
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
    public org.bukkit.Material materialFireball() {
        return org.bukkit.Material.FIRE_CHARGE;
    }

    @Override
    public org.bukkit.Material materialPlayerHead() {
        return org.bukkit.Material.PLAYER_HEAD;
    }

    @Override
    public org.bukkit.Material materialSnowball() {
        return org.bukkit.Material.SNOWBALL;
    }

    @Override
    public org.bukkit.Material materialGoldenHelmet() {
        return org.bukkit.Material.GOLDEN_HELMET;
    }

    @Override
    public org.bukkit.Material materialGoldenChestPlate() {
        return org.bukkit.Material.GOLDEN_CHESTPLATE;
    }

    @Override
    public org.bukkit.Material materialGoldenLeggings() {
        return org.bukkit.Material.GOLDEN_LEGGINGS;
    }

    @Override
    public org.bukkit.Material materialCake() {
        return org.bukkit.Material.CAKE;
    }

    @Override
    public org.bukkit.Material materialCraftingTable() {
        return org.bukkit.Material.CRAFTING_TABLE;
    }

    @Override
    public org.bukkit.Material materialEnchantingTable() {
        return org.bukkit.Material.ENCHANTING_TABLE;
    }

    @Override
    public org.bukkit.Material woolMaterial() {
        return org.bukkit.Material.WHITE_WOOL;
    }

    @Override
    public String getShopUpgradeIdentifier(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = i.getTag();
        return tag == null ? "null" : tag.hasKey("tierIdentifier") ? tag.getString("tierIdentifier") : "null";
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
    public org.bukkit.inventory.ItemStack getPlayerHead(Player player, org.bukkit.inventory.ItemStack copyTagFrom) {
        org.bukkit.inventory.ItemStack head = new org.bukkit.inventory.ItemStack(materialPlayerHead());

        if (copyTagFrom != null) {
            ItemStack i = CraftItemStack.asNMSCopy(head);
            i.setTag(CraftItemStack.asNMSCopy(copyTagFrom).getTag());
            head = CraftItemStack.asBukkitCopy(i);
        }

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        Field profileField;
        try {
            //noinspection ConstantConditions
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, ((CraftPlayer) player).getProfile());
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);

        return head;
    }

    @Override
    public void invisibilityFix(Player player, IArena arena) {

        EntityPlayer pc = ((CraftPlayer) player).getHandle(), pc2;

        for (Player pl : arena.getPlayers()) {
            if (pl.equals(player)) continue;
            if (arena.getRespawn().containsKey(pl)) continue;
            //if (arena.getShowTime().containsKey(pl)) continue;
            if (pl.getLocation().distance(player.getLocation()) <= renderDistance) {
                pc2 = ((CraftPlayer) pl).getHandle();
                if (!arena.getShowTime().containsKey(pl)) {
                    pc.playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(pc2));
                    pc.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, pc2));
                    showArmor(pl, player);
                }

                pc2.playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(pc));
                pc2.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, pc));
                showArmor(player, pl);
            }
        }
    }

    @Override
    public String getInventoryName(InventoryEvent e) {
        return e.getView().getTitle();
    }

    @Override
    public void setUnbreakable(ItemMeta itemMeta) {
        itemMeta.setUnbreakable(true);
    }

    @Override
    public String getMainLevel() {
        //noinspection deprecation
        return ((DedicatedServer) MinecraftServer.getServer()).getDedicatedServerProperties().levelName;
    }

    @Override
    public int getVersion() {
        return 8;
    }

    @Override
    public void setJoinSignBackground(BlockState b, org.bukkit.Material material) {
        if (b.getBlockData() instanceof WallSign) {
            b.getBlock().getRelative(((WallSign) b.getBlockData()).getFacing().getOppositeFace()).setType(material);
        }
    }
}
