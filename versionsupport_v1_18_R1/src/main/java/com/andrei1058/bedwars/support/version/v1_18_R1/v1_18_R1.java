/*
 * BedWars1058 - A bed wars mini-game.
 * Copyright (C) 2021 Andrei Dascălu
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Contact e-mail: andrew.dascalu@gmail.com
 */

package com.andrei1058.bedwars.support.version.v1_18_R1;

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
import com.mojang.math.Vector3f;
import net.minecraft.SharedConstants;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.*;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class v1_18_R1 extends VersionSupport {

    private static final UUID chatUUID = new UUID(0L, 0L);

    public v1_18_R1(Plugin plugin, String name) {
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
    public String getTag(org.bukkit.inventory.ItemStack itemStack, String key) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tag = i.getTag();
        return tag == null ? null : tag.contains(key) ? tag.getString(key) : null;
    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        p.sendTitle(title == null ? " " : title, subtitle == null ? " " : subtitle, fadeIn, stay, fadeOut);
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
    public void playAction(Player p, String text) {
        CraftPlayer cPlayer = (CraftPlayer) p;
        MutableComponent cbc = Component.Serializer.fromJson("{\"text\": \"" + text + "\"}");
        ClientboundChatPacket ppoc = new ClientboundChatPacket(cbc, ChatType.GAME_INFO, chatUUID);
        cPlayer.getHandle().connection.send(ppoc);
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
        ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(e.getEntityId());
        ((CraftPlayer) p).getHandle().connection.send(packet);

    }

    @Override
    public void minusAmount(Player p, org.bukkit.inventory.ItemStack i, int amount) {
        if (i.getAmount() - amount <= 0) {
            if (p.getInventory().getItemInOffHand().equals(i)) {
                p.getInventory().setItemInOffHand(null);
            } else {
                p.getInventory().removeItem(i);
            }
            return;
        }
        i.setAmount(i.getAmount() - amount);
        p.updateInventory();
    }

    @Override
    public void setSource(TNTPrimed tnt, Player owner) {
        net.minecraft.world.entity.LivingEntity nmsEntityLiving = (((CraftLivingEntity) owner).getHandle());
        PrimedTnt nmsTNT = (((CraftTNTPrimed) tnt).getHandle());
        nmsTNT.owner = nmsEntityLiving;
    }

    @Override
    public boolean isArmor(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof ArmorItem;
    }

    @Override
    public boolean isTool(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof DiggerItem;
    }

    @Override
    public boolean isSword(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof SwordItem;
    }

    @Override
    public boolean isAxe(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof AxeItem;
    }

    @Override
    public boolean isBow(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getItem() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getItem() instanceof BowItem;
    }

    @Override
    public boolean isProjectile(org.bukkit.inventory.ItemStack itemStack) {
        if (CraftItemStack.asNMSCopy(itemStack) == null) return false;
        if (CraftItemStack.asNMSCopy(itemStack).getEntityRepresentation() == null) return false;
        return CraftItemStack.asNMSCopy(itemStack).getEntityRepresentation() instanceof net.minecraft.world.entity.projectile.Projectile;
    }

    @Override
    public boolean isInvisibilityPotion(org.bukkit.inventory.ItemStack itemStack) {
        if (!itemStack.getType().equals(org.bukkit.Material.POTION)) return false;

        org.bukkit.inventory.meta.PotionMeta pm = (org.bukkit.inventory.meta.PotionMeta) itemStack.getItemMeta();

        return pm != null && pm.hasCustomEffects() && pm.hasCustomEffect(org.bukkit.potion.PotionEffectType.INVISIBILITY);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void registerEntities() {
        //noinspection deprecation
        Map<String, Type<?>> types = (Map<String, Type<?>>) DataFixers.getDataFixer().getSchema(
                DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getWorldVersion())
        ).findChoiceType(References.ENTITY).types();
        types.put("minecraft:bwsilverfish", types.get("minecraft:silverfish"));
        net.minecraft.world.entity.EntityType.Builder.of(Silverfish::new, MobCategory.MONSTER).build("bwsilverfish");

        types.put("minecraft:bwgolem", types.get("minecraft:iron_golem"));
        net.minecraft.world.entity.EntityType.Builder.of(IGolem::new, MobCategory.MONSTER).build("bwgolem");
    }

    @Override
    public void spawnShop(Location loc, String name1, List<Player> players, IArena arena) {
        Location l = loc.clone();

        if (l.getWorld() == null) return;
        Villager vlg = (Villager) l.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        vlg.setAI(false);
        vlg.setRemoveWhenFarAway(false);
        vlg.setCollidable(false);
        vlg.setInvulnerable(true);
        vlg.setSilent(true);

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
        CompoundTag compound = (nmsStack.getTag() != null) ? nmsStack.getTag() : new CompoundTag();
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

    @Override
    public void voidKill(Player p) {
        ((CraftPlayer) p).getHandle().hurt(DamageSource.OUT_OF_WORLD, 1000);
    }

    @Override
    public void hideArmor(Player victim, Player receiver) {
        List<Pair<EquipmentSlot, ItemStack>> items = new ArrayList<>();
        List<Pair<EquipmentSlot, ItemStack>> hands = new ArrayList<>();
        hands.add(new Pair<>(EquipmentSlot.MAINHAND, new ItemStack(Item.byId(0))));
        hands.add(new Pair<>(EquipmentSlot.OFFHAND, new ItemStack(Item.byId(0))));
        items.add(new Pair<>(EquipmentSlot.LEGS, new ItemStack(Item.byId(0))));
        items.add(new Pair<>(EquipmentSlot.FEET, new ItemStack(Item.byId(0))));
        items.add(new Pair<>(EquipmentSlot.HEAD, new ItemStack(Item.byId(0))));
        items.add(new Pair<>(EquipmentSlot.CHEST, new ItemStack(Item.byId(0))));
        ClientboundSetEquipmentPacket packet1 = new ClientboundSetEquipmentPacket(victim.getEntityId(), items);
        ClientboundSetEquipmentPacket packet2 = new ClientboundSetEquipmentPacket(victim.getEntityId(), hands);
        ServerPlayer pc = ((CraftPlayer) receiver).getHandle();
        if (victim != receiver) {
            pc.connection.send(packet2);
        }
        pc.connection.send(packet1);
    }

    @Override
    public void showArmor(Player victim, Player receiver) {
        List<Pair<EquipmentSlot, ItemStack>> items = new ArrayList<>();
        List<Pair<EquipmentSlot, ItemStack>> hands = new ArrayList<>();

        hands.add(new Pair<>(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(victim.getInventory().getItemInMainHand())));
        hands.add(new Pair<>(EquipmentSlot.OFFHAND, CraftItemStack.asNMSCopy(victim.getInventory().getItemInOffHand())));

        items.add(new Pair<>(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(victim.getInventory().getHelmet())));
        items.add(new Pair<>(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(victim.getInventory().getChestplate())));
        items.add(new Pair<>(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(victim.getInventory().getLeggings())));
        items.add(new Pair<>(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(victim.getInventory().getBoots())));
        ClientboundSetEquipmentPacket packet1 = new ClientboundSetEquipmentPacket(victim.getEntityId(), items);
        ClientboundSetEquipmentPacket packet2 = new ClientboundSetEquipmentPacket(victim.getEntityId(), hands);
        ServerPlayer pc = ((CraftPlayer) receiver).getHandle();
        if (victim != receiver) {
            pc.connection.send(packet2);
        }
        pc.connection.send(packet1);
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
            Field field = net.minecraft.world.level.block.state.BlockBehaviour.class.getDeclaredField("aI");//internal name of Blast resistance
            field.setAccessible(true);
            field.set(Blocks.END_STONE, 12f);
            field.set(Blocks.WHITE_STAINED_GLASS, 300f);
            field.set(Blocks.ORANGE_STAINED_GLASS, 300f);
            field.set(Blocks.MAGENTA_STAINED_GLASS, 300f);
            field.set(Blocks.LIGHT_BLUE_STAINED_GLASS, 300f);
            field.set(Blocks.YELLOW_STAINED_GLASS, 300f);
            field.set(Blocks.LIME_STAINED_GLASS, 300f);
            field.set(Blocks.PINK_STAINED_GLASS, 300f);
            field.set(Blocks.GRAY_STAINED_GLASS, 300f);
            field.set(Blocks.LIGHT_GRAY_STAINED_GLASS, 300f);
            field.set(Blocks.CYAN_STAINED_GLASS, 300f);
            field.set(Blocks.PURPLE_STAINED_GLASS, 300f);
            field.set(Blocks.BLUE_STAINED_GLASS, 300f);
            field.set(Blocks.BROWN_STAINED_GLASS, 300f);
            field.set(Blocks.GREEN_STAINED_GLASS, 300f);
            field.set(Blocks.RED_STAINED_GLASS, 300f);
            field.set(Blocks.BLACK_STAINED_GLASS, 300f);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
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
        CompoundTag tag = itemStack.getTag();
        if (tag == null) {
            tag = new CompoundTag();
            itemStack.setTag(tag);
        }

        tag.putString("BedWars1058", data);
        return CraftItemStack.asBukkitCopy(itemStack);
    }

    @Override
    public org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack itemStack, String key, String value) {
        ItemStack is = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tag = is.getTag();
        if (tag == null) {
            tag = new CompoundTag();
            is.setTag(tag);
        }

        tag.putString(key, value);
        return CraftItemStack.asBukkitCopy(is);
    }

    @Override
    public boolean isCustomBedWarsItem(org.bukkit.inventory.ItemStack i) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        CompoundTag tag = itemStack.getTag();
        if (tag == null) return false;
        return tag.contains("BedWars1058");
    }

    @Override
    public String getCustomData(org.bukkit.inventory.ItemStack i) {
        ItemStack itemStack = CraftItemStack.asNMSCopy(i);
        CompoundTag tag = itemStack.getTag();
        if (tag == null) return "";
        return tag.getString("BedWars1058");
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
        CompoundTag tag = i.getTag();
        return tag == null ? "null" : tag.contains("tierIdentifier") ? tag.getString("tierIdentifier") : "null";
    }

    @Override
    public org.bukkit.inventory.ItemStack setShopUpgradeIdentifier(org.bukkit.inventory.ItemStack itemStack, String identifier) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tag = i.getTag();
        if (tag == null) {
            tag = new CompoundTag();
            i.setTag(tag);
        }
        tag.putString("tierIdentifier", identifier);
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
        assert headMeta != null;
        headMeta.setOwningPlayer(player);
        head.setItemMeta(headMeta);
        return head;
    }

    @Override
    public void sendPlayerSpawnPackets(Player respawned, IArena arena) {
        if (respawned == null) return;
        if (arena == null) return;
        if (!arena.isPlayer(respawned)) return;

        // if method was used when the player was still in re-spawning screen
        if (arena.getRespawnSessions().containsKey(respawned)) return;

        ServerPlayer entityPlayer = ((CraftPlayer) respawned).getHandle();
        ClientboundAddEntityPacket show = new ClientboundAddEntityPacket(entityPlayer);
        ClientboundSetEntityMotionPacket playerVelocity = new ClientboundSetEntityMotionPacket(entityPlayer);
        ClientboundRotateHeadPacket head = new ClientboundRotateHeadPacket(entityPlayer, getCompressedAngle(entityPlayer.getBukkitYaw()));

        List<Pair<EquipmentSlot, ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EquipmentSlot.MAINHAND, entityPlayer.getItemBySlot(EquipmentSlot.MAINHAND)));
        list.add(new Pair<>(EquipmentSlot.OFFHAND, entityPlayer.getItemBySlot(EquipmentSlot.OFFHAND)));
        list.add(new Pair<>(EquipmentSlot.HEAD, entityPlayer.getItemBySlot(EquipmentSlot.HEAD)));
        list.add(new Pair<>(EquipmentSlot.CHEST, entityPlayer.getItemBySlot(EquipmentSlot.CHEST)));
        list.add(new Pair<>(EquipmentSlot.LEGS, entityPlayer.getItemBySlot(EquipmentSlot.LEGS)));
        list.add(new Pair<>(EquipmentSlot.FEET, entityPlayer.getItemBySlot(EquipmentSlot.FEET)));


        for (Player p : arena.getPlayers()) {
            if (p == null) continue;
            if (p.equals(respawned)) continue;
            // if p is in re-spawning screen continue
            if (arena.getRespawnSessions().containsKey(p)) continue;

            ServerPlayer boundTo = ((CraftPlayer) p).getHandle();
            if (p.getWorld().equals(respawned.getWorld())) {
                if (respawned.getLocation().distance(p.getLocation()) <= arena.getRenderDistance()) {

                    // send respawned player to regular players
                    boundTo.connection.send(show);
                    boundTo.connection.send(head);
                    boundTo.connection.send(playerVelocity);
                    boundTo.connection.send(new ClientboundSetEquipmentPacket(respawned.getEntityId(), list));

                    // send nearby players to respawned player
                    // if the player has invisibility hide armor
                    if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        hideArmor(p, respawned);
                    } else {
                        ClientboundAddEntityPacket show2 = new ClientboundAddEntityPacket(boundTo);
                        ClientboundSetEntityMotionPacket playerVelocity2 = new ClientboundSetEntityMotionPacket(boundTo);
                        ClientboundRotateHeadPacket head2 = new ClientboundRotateHeadPacket(boundTo, getCompressedAngle(boundTo.getBukkitYaw()));
                        entityPlayer.connection.send(show2);
                        entityPlayer.connection.send(playerVelocity2);
                        entityPlayer.connection.send(head2);
                        showArmor(p, respawned);
                    }
                }
            }
        }

        for (Player spectator : arena.getSpectators()) {
            if (spectator == null) continue;
            if (spectator.equals(respawned)) continue;
            ServerPlayer boundTo = ((CraftPlayer) spectator).getHandle();
            respawned.hidePlayer(getPlugin(), spectator);
            if (spectator.getWorld().equals(respawned.getWorld())) {
                if (respawned.getLocation().distance(spectator.getLocation()) <= arena.getRenderDistance()) {

                    // send respawned player to spectator
                    boundTo.connection.send(show);
                    boundTo.connection.send(playerVelocity);
                    boundTo.connection.send(new ClientboundSetEquipmentPacket(respawned.getEntityId(), list));
                    boundTo.connection.send(new ClientboundRotateHeadPacket(entityPlayer, getCompressedAngle(entityPlayer.getBukkitYaw())));
                }
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
        return ((DedicatedServer) MinecraftServer.getServer()).settings.getProperties().levelName;
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

    @Override
    public void spigotShowPlayer(Player victim, Player receiver) {
        receiver.showPlayer(getPlugin(), victim);
    }

    @Override
    public void spigotHidePlayer(Player victim, Player receiver) {
        receiver.hidePlayer(getPlugin(), victim);
    }

    @Override
    public Fireball setFireballDirection(Fireball fireball, Vector vector) {
        net.minecraft.world.entity.projectile.Fireball fb = (net.minecraft.world.entity.projectile.Fireball) ((CraftFireball) fireball).getHandle();
        fb.xPower = vector.getX() * 0.1D;
        fb.yPower = vector.getY() * 0.1D;
        fb.zPower = vector.getZ() * 0.1D;
        return (Fireball) fb.getBukkitEntity();
    }

    @Override
    public void playRedStoneDot(Player player) {
        Color color = Color.RED;
        ClientboundLevelParticlesPacket particlePacket = new ClientboundLevelParticlesPacket(new DustParticleOptions(new Vector3f((float) color.getRed(), (float) color.getGreen(), (float) color.getBlue()), (float) 1),
                true, player.getLocation().getX(), player.getLocation().getY() + 2.6, player.getLocation().getZ(), 0, 0, 0, 0, 0);
        for (Player inWorld : player.getWorld().getPlayers()) {
            if (inWorld.equals(player)) continue;
            ((CraftPlayer) inWorld).getHandle().connection.send(particlePacket);
        }
    }

    @Override
    public void clearArrowsFromPlayerBody(Player player) {
        ((CraftLivingEntity)player).getHandle().getEntityData().set(new EntityDataAccessor<>(12, EntityDataSerializers.INT),-1);
    }
}
