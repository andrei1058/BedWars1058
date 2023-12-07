package com.andrei1058.bedwars.support.version.v1_19_R2;

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
import com.andrei1058.bedwars.support.version.v1_19_R2.despawnable.DespawnableAttributes;
import com.andrei1058.bedwars.support.version.v1_19_R2.despawnable.DespawnableFactory;
import com.andrei1058.bedwars.support.version.v1_19_R2.despawnable.DespawnableType;
import com.mojang.datafixers.util.Pair;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.core.particles.ParticleParamRedstone;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.projectile.EntityFireball;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBase;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftFireball;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("unused")
@Deprecated
public class v1_19_R2 extends VersionSupport {

    private final DespawnableFactory despawnableFactory;

    public v1_19_R2(Plugin plugin, String name) {
        super(plugin, name);
        loadDefaultEffects();
        this.despawnableFactory = new DespawnableFactory(this);
    }

    @Override
    public void registerVersionListeners() {
        new VersionCommon(this);
    }

    @Override
    public void registerCommand(String name, Command cmd) {
        ((CraftServer) getPlugin().getServer()).getCommandMap().register(name, cmd);
    }

    @Override
    public String getTag(org.bukkit.inventory.ItemStack itemStack, String key) {
        var tag = getTag(itemStack);
        return tag == null ? null : tag.e(key) ? tag.l(key) : null;
    }

    @Override
    public void sendTitle(@NotNull Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        p.sendTitle(title == null ? " " : title, subtitle == null ? " " : subtitle, fadeIn, stay, fadeOut);
    }

    public void spawnSilverfish(Location loc, ITeam bedWarsTeam, double speed, double health, int despawn, double damage) {
        var attr = new DespawnableAttributes(DespawnableType.SILVERFISH, speed, health, damage, despawn);
        var entity = despawnableFactory.spawn(attr, loc, bedWarsTeam);

        new Despawnable(
                entity,
                bedWarsTeam, despawn,
                Messages.SHOP_UTILITY_NPC_SILVERFISH_NAME,
                PlayerKillEvent.PlayerKillCause.SILVERFISH_FINAL_KILL,
                PlayerKillEvent.PlayerKillCause.SILVERFISH
        );
    }

    @Override
    public void spawnIronGolem(Location loc, ITeam bedWarsTeam, double speed, double health, int despawn) {
        var attr = new DespawnableAttributes(DespawnableType.IRON_GOLEM, speed, health,4, despawn);
        var entity = despawnableFactory.spawn(attr, loc, bedWarsTeam);
        new Despawnable(
                entity,
                bedWarsTeam, despawn,
                Messages.SHOP_UTILITY_NPC_IRON_GOLEM_NAME,
                PlayerKillEvent.PlayerKillCause.IRON_GOLEM_FINAL_KILL,
                PlayerKillEvent.PlayerKillCause.IRON_GOLEM
        );
    }

    @Override
    public void playAction(@NotNull Player p, String text) {
        p.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(ChatColor.translateAlternateColorCodes('&', text)
                )
        );
    }

    @Override
    public boolean isBukkitCommandRegistered(String name) {
        return ((CraftServer) getPlugin().getServer()).getCommandMap().getCommand(name) != null;
    }

    @Override
    public org.bukkit.inventory.ItemStack getItemInHand(@NotNull Player p) {
        return p.getInventory().getItemInMainHand();
    }

    @Override
    public void hideEntity(@NotNull Entity e, Player p) {
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(e.getEntityId());
        ((CraftPlayer) p).getHandle().b.a(packet);

    }

    @Override
    public void minusAmount(Player p, org.bukkit.inventory.@NotNull ItemStack i, int amount) {
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
        EntityLiving nmsEntityLiving = (((CraftLivingEntity) owner).getHandle());
        EntityTNTPrimed nmsTNT = (((CraftTNTPrimed) tnt).getHandle());
        try {
            Field sourceField = EntityTNTPrimed.class.getDeclaredField("d");
            sourceField.setAccessible(true);
            sourceField.set(nmsTNT, nmsEntityLiving);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean isArmor(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemArmor || i instanceof ItemElytra;

    }

    @Override
    public boolean isTool(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemTool;
    }

    @Override
    public boolean isSword(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemSword;
    }

    @Override
    public boolean isAxe(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemAxe;
    }

    @Override
    public boolean isBow(org.bukkit.inventory.ItemStack itemStack) {
        var i = getItem(itemStack);
        if (null == i) return false;
        return i instanceof ItemBow;
    }

    @Override
    public boolean isProjectile(org.bukkit.inventory.ItemStack itemStack) {
        var entity = getEntity(itemStack);
        if (null == entity) return false;
        return entity instanceof IProjectile;
    }

    @Override
    public boolean isInvisibilityPotion(org.bukkit.inventory.@NotNull ItemStack itemStack) {
        if (!itemStack.getType().equals(org.bukkit.Material.POTION)) return false;

        org.bukkit.inventory.meta.PotionMeta pm = (org.bukkit.inventory.meta.PotionMeta) itemStack.getItemMeta();

        return pm != null && pm.hasCustomEffects() && pm.hasCustomEffect(org.bukkit.potion.PotionEffectType.INVISIBILITY);
    }

    @Override
    public void registerEntities() {
    }

    @Override
    public void spawnShop(@NotNull Location loc, String name1, List<Player> players, IArena arena) {
        Location l = loc.clone();

        if (l.getWorld() == null) return;
        Villager vlg = (Villager) l.getWorld().spawnEntity(loc, EntityType.VILLAGER);
        vlg.setAI(false);
        vlg.setRemoveWhenFarAway(false);
        vlg.setCollidable(false);
        vlg.setInvulnerable(true);
        vlg.setSilent(true);

        for (Player p : players) {
            String[] name = Language.getMsg(p, name1).split(",");
            if (name.length == 1) {
                ArmorStand a = createArmorStand(name[0], l.clone().add(0, 1.85, 0));
                new ShopHolo(Language.getPlayerLanguage(p).getIso(), a, null, l, arena);
            } else {
                ArmorStand a = createArmorStand(name[0], l.clone().add(0, 2.1, 0));
                ArmorStand b = createArmorStand(name[1], l.clone().add(0, 1.85, 0));
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
        var tag = getTag(i);
        if (null == tag) {
            throw new RuntimeException("Provided item has no Tag");
        }
        return tag.k("generic.attackDamage");
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
        getPlayer(p).a(DamageSource.m, 1000);
    }

    @Override
    public void hideArmor(@NotNull Player victim, Player receiver) {
        List<Pair<EnumItemSlot, ItemStack>> items = new ArrayList<>();
        items.add(new Pair<>(EnumItemSlot.f, new ItemStack(Item.b(0))));
        items.add(new Pair<>(EnumItemSlot.e, new ItemStack(Item.b(0))));
        items.add(new Pair<>(EnumItemSlot.d, new ItemStack(Item.b(0))));
        items.add(new Pair<>(EnumItemSlot.c, new ItemStack(Item.b(0))));
        PacketPlayOutEntityEquipment packet1 = new PacketPlayOutEntityEquipment(victim.getEntityId(), items);
        EntityPlayer pc = getPlayer(receiver);
        pc.b.a(packet1);
    }

    @Override
    public void showArmor(@NotNull Player victim, Player receiver) {
        List<Pair<EnumItemSlot, ItemStack>> items = new ArrayList<>();
        items.add(new Pair<>(EnumItemSlot.f, CraftItemStack.asNMSCopy(victim.getInventory().getHelmet())));
        items.add(new Pair<>(EnumItemSlot.e, CraftItemStack.asNMSCopy(victim.getInventory().getChestplate())));
        items.add(new Pair<>(EnumItemSlot.d, CraftItemStack.asNMSCopy(victim.getInventory().getLeggings())));
        items.add(new Pair<>(EnumItemSlot.c, CraftItemStack.asNMSCopy(victim.getInventory().getBoots())));
        PacketPlayOutEntityEquipment packet1 = new PacketPlayOutEntityEquipment(victim.getEntityId(), items);
        EntityPlayer pc = getPlayer(receiver);
        pc.b.a(packet1);
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
    public void registerTntWhitelist(float endStoneBlast, float glassBlast) {
        try {
            // blast resistance
            Field field = BlockBase.class.getDeclaredField("aH");
            field.setAccessible(true);
            // end stone
            field.set(Blocks.fj, endStoneBlast);
            // obsidian
            field.set(Blocks.ce, glassBlast);
            // standard glass
            field.set(Blocks.aH, glassBlast);

            var coloredGlass = new net.minecraft.world.level.block.Block[]{
                    Blocks.dU, Blocks.dV, Blocks.dW, Blocks.dX,
                    Blocks.dY, Blocks.dZ, Blocks.dZ, Blocks.ea,
                    Blocks.eb, Blocks.ec, Blocks.ed, Blocks.ee,
                    Blocks.ef, Blocks.eg, Blocks.eh, Blocks.ei,
                    Blocks.ej,
            };

            Arrays.stream(coloredGlass).forEach(
                    glass -> {
                        try {
                            field.set(glass, glassBlast);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBlockTeamColor(@NotNull Block block, TeamColor teamColor) {
        if (block.getType().toString().contains("STAINED_GLASS") || block.getType().toString().equals("GLASS")) {
            block.setType(teamColor.glassMaterial());
        } else if (block.getType().toString().contains("_TERRACOTTA")) {
            block.setType(teamColor.glazedTerracottaMaterial());
        } else if (block.getType().toString().contains("_WOOL")) {
            block.setType(teamColor.woolMaterial());
        }
    }

    @Override
    public void setCollide(@NotNull Player p, IArena a, boolean value) {
        p.setCollidable(value);
        if (a == null) return;
        a.updateSpectatorCollideRule(p, value);
    }

    @Override
    public org.bukkit.inventory.ItemStack addCustomData(org.bukkit.inventory.ItemStack i, String data) {
        var tag = getCreateTag(i);
        tag.a(VersionSupport.PLUGIN_TAG_GENERIC_KEY, data);
        return applyTag(i, tag);
    }

    @Override
    public org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack itemStack, String key, String value) {
        var tag = getCreateTag(itemStack);
        tag.a(key, value);
        return applyTag(itemStack, tag);
    }

    @Override
    public boolean isCustomBedWarsItem(org.bukkit.inventory.ItemStack i) {
        return getCreateTag(i).e(VersionSupport.PLUGIN_TAG_GENERIC_KEY);
    }

    @Override
    public String getCustomData(org.bukkit.inventory.ItemStack i) {
        return getCreateTag(i).l(VersionSupport.PLUGIN_TAG_GENERIC_KEY);
    }

    @Override
    public org.bukkit.inventory.ItemStack colourItem(org.bukkit.inventory.ItemStack itemStack, ITeam bedWarsTeam) {
        if (itemStack == null) return null;
        String type = itemStack.getType().toString();
        if (isBed(itemStack.getType())) {
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
    public org.bukkit.Material materialNetheriteHelmet() {
        return Material.NETHERITE_HELMET;
    }

    @Override
    public org.bukkit.Material materialNetheriteChestPlate() {
        return Material.NETHERITE_CHESTPLATE;
    }

    @Override
    public org.bukkit.Material materialNetheriteLeggings() {
        return Material.NETHERITE_LEGGINGS;
    }

    @Override
    public org.bukkit.Material materialElytra() {
        return Material.ELYTRA;
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
        var tag = getCreateTag(itemStack);
        return tag.e(VersionSupport.PLUGIN_TAG_TIER_KEY) ? tag.l(VersionSupport.PLUGIN_TAG_TIER_KEY) : "null";
    }

    @Override
    public org.bukkit.inventory.ItemStack setShopUpgradeIdentifier(org.bukkit.inventory.ItemStack itemStack, String identifier) {
        var tag = getCreateTag(itemStack);
        tag.a(VersionSupport.PLUGIN_TAG_TIER_KEY, identifier);
        return applyTag(itemStack, tag);
    }

    @Override
    public org.bukkit.inventory.ItemStack getPlayerHead(Player player, org.bukkit.inventory.ItemStack copyTagFrom) {
        org.bukkit.inventory.ItemStack head = new org.bukkit.inventory.ItemStack(materialPlayerHead());

        if (copyTagFrom != null) {
            var tag = getTag(copyTagFrom);
            head = applyTag(head, tag);
        }

        var meta = head.getItemMeta();
        if (meta instanceof SkullMeta) {
            ((SkullMeta) meta).setOwnerProfile(player.getPlayerProfile());
        }
        head.setItemMeta(meta);
        return head;
    }

    @Override
    public void sendPlayerSpawnPackets(Player respawned, IArena arena) {
        if (respawned == null) return;
        if (arena == null) return;
        if (!arena.isPlayer(respawned)) return;

        // if method was used when the player was still in re-spawning screen
        if (arena.getRespawnSessions().containsKey(respawned)) return;

        EntityPlayer entityPlayer = getPlayer(respawned);
        PacketPlayOutNamedEntitySpawn show = new PacketPlayOutNamedEntitySpawn(entityPlayer);
        PacketPlayOutEntityVelocity playerVelocity = new PacketPlayOutEntityVelocity(entityPlayer);
        // we send head rotation packet because sometimes on respawn others see him with bad rotation
        PacketPlayOutEntityHeadRotation head = new PacketPlayOutEntityHeadRotation(entityPlayer, getCompressedAngle(entityPlayer.getBukkitYaw()));

        // retrieve current armor and in-hand items
        // we send a packet later for timing issues where other players do not see them
        List<Pair<EnumItemSlot, ItemStack>> list = getPlayerEquipment(entityPlayer);


        for (Player p : arena.getPlayers()) {
            if (p == null) continue;
            if (p.equals(respawned)) continue;
            // if p is in re-spawning screen continue
            if (arena.getRespawnSessions().containsKey(p)) continue;

            EntityPlayer boundTo = getPlayer(p);
            if (p.getWorld().equals(respawned.getWorld())) {
                if (respawned.getLocation().distance(p.getLocation()) <= arena.getRenderDistance()) {

                    // send respawned player to regular players
                    boundTo.b.a(show);
                    boundTo.b.a(head);
                    boundTo.b.a(playerVelocity);
                    boundTo.b.a(new PacketPlayOutEntityEquipment(respawned.getEntityId(), list));

                    // send nearby players to respawned player
                    // if the player has invisibility hide armor
                    if (p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                        hideArmor(p, respawned);
                    } else {
                        PacketPlayOutNamedEntitySpawn show2 = new PacketPlayOutNamedEntitySpawn(boundTo);
                        PacketPlayOutEntityVelocity playerVelocity2 = new PacketPlayOutEntityVelocity(boundTo);
                        PacketPlayOutEntityHeadRotation head2 = new PacketPlayOutEntityHeadRotation(boundTo, getCompressedAngle(boundTo.getBukkitYaw()));
                        entityPlayer.b.a(show2);
                        entityPlayer.b.a(playerVelocity2);
                        entityPlayer.b.a(head2);
                        showArmor(p, respawned);
                    }
                }
            }
        }

        for (Player spectator : arena.getSpectators()) {
            if (spectator == null) continue;
            if (spectator.equals(respawned)) continue;
            EntityPlayer boundTo = ((CraftPlayer) spectator).getHandle();
            respawned.hidePlayer(getPlugin(), spectator);
            if (spectator.getWorld().equals(respawned.getWorld())) {
                if (respawned.getLocation().distance(spectator.getLocation()) <= arena.getRenderDistance()) {

                    // send respawned player to spectator
                    boundTo.b.a(show);
                    boundTo.b.a(playerVelocity);
                    boundTo.b.a(new PacketPlayOutEntityEquipment(respawned.getEntityId(), list));
                    boundTo.b.a(new PacketPlayOutEntityHeadRotation(entityPlayer, getCompressedAngle(entityPlayer.getBukkitYaw())));
                }
            }
        }
    }

    @Override
    public String getInventoryName(@NotNull InventoryEvent e) {
        return e.getView().getTitle();
    }

    @Override
    public void setUnbreakable(@NotNull ItemMeta itemMeta) {
        itemMeta.setUnbreakable(true);
    }

    @Override
    public String getMainLevel() {
        //noinspection deprecation
        return ((DedicatedServer) MinecraftServer.getServer()).a().m;
    }

    @Override
    public int getVersion() {
        return 9;
    }

    @Override
    public void setJoinSignBackground(@NotNull BlockState b, org.bukkit.Material material) {
        if (b.getBlockData() instanceof WallSign) {
            b.getBlock().getRelative(((WallSign) b.getBlockData()).getFacing().getOppositeFace()).setType(material);
        }
    }

    @Override
    public void spigotShowPlayer(Player victim, @NotNull Player receiver) {
        receiver.showPlayer(getPlugin(), victim);
    }

    @Override
    public void spigotHidePlayer(Player victim, @NotNull Player receiver) {
        receiver.hidePlayer(getPlugin(), victim);
    }

    @Override
    public Fireball setFireballDirection(Fireball fireball, @NotNull Vector vector) {
        EntityFireball fb = ((CraftFireball) fireball).getHandle();
        fb.b = vector.getX() * 0.1D;
        fb.c = vector.getY() * 0.1D;
        fb.d = vector.getZ() * 0.1D;
        return (Fireball) fb.getBukkitEntity();
    }

    @Override
    public void playRedStoneDot(@NotNull Player player) {
        Color color = Color.RED;
        PacketPlayOutWorldParticles particlePacket = new PacketPlayOutWorldParticles(
                new ParticleParamRedstone(
                        new Vector3f((float) color.getRed(),
                                (float) color.getGreen(),
                                (float) color.getBlue()), (float) 1
                ),
                true,
                player.getLocation().getX(),
                player.getLocation().getY() + 2.6,
                player.getLocation().getZ(),
                0, 0, 0, 0, 0
        );
        for (Player inWorld : player.getWorld().getPlayers()) {
            if (inWorld.equals(player)) continue;
            getPlayer(inWorld).b.a(particlePacket);
        }
    }

    @Override
    public void clearArrowsFromPlayerBody(Player player) {
        // minecraft clears them on death on newer version
    }

    /**
     * Gets the NMS Item from ItemStack
     */
    private @Nullable Item getItem(org.bukkit.inventory.ItemStack itemStack) {
        var i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            return null;
        }
        return i.c();
    }

    /**
     * Gets the NMS Entity from ItemStack
     */
    private @Nullable net.minecraft.world.entity.Entity getEntity(org.bukkit.inventory.ItemStack itemStack) {
        var i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            return null;
        }
        return i.G();
    }

    private @Nullable NBTTagCompound getTag(@NotNull org.bukkit.inventory.ItemStack itemStack) {
        var i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            return null;
        }
        return i.u();
    }

    private @Nullable NBTTagCompound getTag(@NotNull ItemStack itemStack) {
        return itemStack.u();
    }

    private @NotNull NBTTagCompound initializeTag(org.bukkit.inventory.ItemStack itemStack) {
        var i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            throw new RuntimeException("Cannot convert given item to a NMS item");
        }
        return initializeTag(i);
    }

    private @NotNull NBTTagCompound initializeTag(ItemStack itemStack) {

        var tag = getTag(itemStack);
        if (null != tag) {
            throw new RuntimeException("Provided item already has a Tag");
        }
        tag = new NBTTagCompound();
        itemStack.c(tag);

        return tag;
    }

    public NBTTagCompound getCreateTag(ItemStack itemStack) {
        var tag = getTag(itemStack);
        return null == tag ? initializeTag(itemStack) : tag;
    }

    public NBTTagCompound getCreateTag(org.bukkit.inventory.ItemStack itemStack) {
        var i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            throw new RuntimeException("Cannot convert given item to a NMS item");
        }
        return getCreateTag(i);
    }

    public org.bukkit.inventory.ItemStack applyTag(org.bukkit.inventory.ItemStack itemStack, NBTTagCompound tag) {
        return CraftItemStack.asBukkitCopy(applyTag(getNmsItemCopy(itemStack), tag));
    }

    public ItemStack applyTag(@NotNull ItemStack itemStack, NBTTagCompound tag) {
        itemStack.c(tag);
        return itemStack;
    }

    public ItemStack getNmsItemCopy(org.bukkit.inventory.ItemStack itemStack) {
        ItemStack i = CraftItemStack.asNMSCopy(itemStack);
        if (null == i) {
            throw new RuntimeException("Cannot convert given item to a NMS item");
        }
        return i;
    }

    public EntityPlayer getPlayer(Player player) {
        return ((CraftPlayer) player).getHandle();
    }

    public List<Pair<EnumItemSlot, ItemStack>> getPlayerEquipment(@NotNull Player player) {
        return getPlayerEquipment(getPlayer(player));
    }

    public List<Pair<EnumItemSlot, ItemStack>> getPlayerEquipment(@NotNull EntityPlayer entityPlayer) {
        List<Pair<EnumItemSlot, ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EnumItemSlot.a, entityPlayer.c(EnumItemSlot.a)));
        list.add(new Pair<>(EnumItemSlot.b, entityPlayer.c(EnumItemSlot.b)));
        list.add(new Pair<>(EnumItemSlot.f, entityPlayer.c(EnumItemSlot.f)));
        list.add(new Pair<>(EnumItemSlot.e, entityPlayer.c(EnumItemSlot.e)));
        list.add(new Pair<>(EnumItemSlot.d, entityPlayer.c(EnumItemSlot.d)));
        list.add(new Pair<>(EnumItemSlot.c, entityPlayer.c(EnumItemSlot.c)));

        return list;
    }

    @Override
    public void placeTowerBlocks(@NotNull Block b, @NotNull IArena a, @NotNull TeamColor color, int x, int y, int z){
        b.getRelative(x, y, z).setType(color.woolMaterial());
        a.addPlacedBlock(b.getRelative(x, y, z));
    }

    @Override
    public void placeLadder(@NotNull Block b, int x, int y, int z, @NotNull IArena a, int ladderData){
        Block block = b.getRelative(x,y,z);  //ladder block
        block.setType(Material.LADDER);
        Ladder ladder = (Ladder) block.getBlockData();
        a.addPlacedBlock(block);
        switch (ladderData) {
            case 2 -> {
                ladder.setFacing(BlockFace.NORTH);
                block.setBlockData(ladder);
            }
            case 3 -> {
                ladder.setFacing(BlockFace.SOUTH);
                block.setBlockData(ladder);
            }
            case 4 -> {
                ladder.setFacing(BlockFace.WEST);
                block.setBlockData(ladder);
            }
            case 5 -> {
                ladder.setFacing(BlockFace.EAST);
                block.setBlockData(ladder);
            }
        }
    }

    @Override
    public void playVillagerEffect(@NotNull Player player, Location location){
        player.spawnParticle(Particle.VILLAGER_HAPPY, location, 1);
    }

}