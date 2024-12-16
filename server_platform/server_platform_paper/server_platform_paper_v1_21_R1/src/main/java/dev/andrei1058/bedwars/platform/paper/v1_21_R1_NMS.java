package dev.andrei1058.bedwars.platform.paper;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.arena.team.TeamColor;
import com.andrei1058.bedwars.api.server.VersionSupport;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Vector;

public class v1_21_R1_NMS extends VersionSupport {

    public v1_21_R1_NMS(Plugin plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void registerCommand(String name, Command clasa) {

    }

    @Override
    public void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {

    }

    @Override
    public void playAction(Player p, String text) {

    }

    @Override
    public boolean isBukkitCommandRegistered(String command) {
        return false;
    }

    @Override
    public ItemStack getItemInHand(Player p) {
        return null;
    }

    @Override
    public void hideEntity(Entity e, Player p) {

    }

    @Override
    public boolean isArmor(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isTool(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isSword(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isAxe(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isBow(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isProjectile(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isInvisibilityPotion(ItemStack itemStack) {
        return false;
    }

    @Override
    public void registerEntities() {

    }

    @Override
    public void spawnShop(Location loc, String name1, List<Player> players, IArena arena) {

    }

    @Override
    public double getDamage(ItemStack i) {
        return 0;
    }

    @Override
    public void spawnSilverfish(Location loc, ITeam team, double speed, double health, int despawn, double damage) {

    }

    @Override
    public void spawnIronGolem(Location loc, ITeam team, double speed, double health, int despawn) {

    }

    @Override
    public void minusAmount(Player p, ItemStack i, int amount) {

    }

    @Override
    public void setSource(TNTPrimed tnt, Player owner) {

    }

    @Override
    public void voidKill(Player p) {

    }

    @Override
    public void hideArmor(Player victim, Player receiver) {

    }

    @Override
    public void showArmor(Player victim, Player receiver) {

    }

    @Override
    public void spawnDragon(Location l, ITeam team) {

    }

    @Override
    public void colorBed(ITeam team) {

    }

    @Override
    public void registerTntWhitelist(float endStoneBlast, float glassBlast) {

    }

    @Override
    public void setBlockTeamColor(Block block, TeamColor teamColor) {

    }

    @Override
    public void setCollide(Player p, IArena a, boolean value) {

    }

    @Override
    public ItemStack addCustomData(ItemStack i, String data) {
        return null;
    }

    @Override
    public ItemStack setTag(ItemStack itemStack, String key, String value) {
        return null;
    }

    @Override
    public String getTag(ItemStack itemStack, String key) {
        return "";
    }

    @Override
    public boolean isCustomBedWarsItem(ItemStack i) {
        return false;
    }

    @Override
    public String getCustomData(ItemStack i) {
        return "";
    }

    @Override
    public ItemStack colourItem(ItemStack itemStack, ITeam bedWarsTeam) {
        return null;
    }

    @Override
    public ItemStack createItemStack(String material, int amount, short data) {
        return null;
    }

    @Override
    public Material materialFireball() {
        return null;
    }

    @Override
    public Material materialPlayerHead() {
        return null;
    }

    @Override
    public Material materialSnowball() {
        return null;
    }

    @Override
    public Material materialGoldenHelmet() {
        return null;
    }

    @Override
    public Material materialGoldenChestPlate() {
        return null;
    }

    @Override
    public Material materialGoldenLeggings() {
        return null;
    }

    @Override
    public Material materialNetheriteHelmet() {
        return null;
    }

    @Override
    public Material materialNetheriteChestPlate() {
        return null;
    }

    @Override
    public Material materialNetheriteLeggings() {
        return null;
    }

    @Override
    public Material materialElytra() {
        return null;
    }

    @Override
    public Material materialCake() {
        return null;
    }

    @Override
    public Material materialCraftingTable() {
        return null;
    }

    @Override
    public Material materialEnchantingTable() {
        return null;
    }

    @Override
    public void setJoinSignBackground(BlockState b, Material material) {

    }

    @Override
    public Material woolMaterial() {
        return null;
    }

    @Override
    public String getShopUpgradeIdentifier(ItemStack itemStack) {
        return "";
    }

    @Override
    public ItemStack setShopUpgradeIdentifier(ItemStack itemStack, String identifier) {
        return null;
    }

    @Override
    public ItemStack getPlayerHead(Player player, ItemStack copyTagFrom) {
        return null;
    }

    @Override
    public void sendPlayerSpawnPackets(Player player, IArena arena) {

    }

    @Override
    public String getInventoryName(InventoryEvent e) {
        return "";
    }

    @Override
    public void setUnbreakable(ItemMeta itemMeta) {

    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void registerVersionListeners() {

    }

    @Override
    public String getMainLevel() {
        return "";
    }

    @Override
    public Fireball setFireballDirection(Fireball fireball, Vector vector) {
        return null;
    }

    @Override
    public void playRedStoneDot(Player player) {

    }

    @Override
    public void clearArrowsFromPlayerBody(Player player) {

    }

    @Override
    public void placeTowerBlocks(Block b, IArena a, TeamColor color, int x, int y, int z) {

    }

    @Override
    public void placeLadder(Block b, int x, int y, int z, IArena a, int ladderdata) {

    }

    @Override
    public void playVillagerEffect(Player player, Location location) {

    }
}
