package com.andrei1058.bedwars.support.version.v1_20_R4;

import com.andrei1058.bedwars.support.version.v1_20_R3.v1_20_R3;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class v1_20_R4 extends v1_20_R3 {

    public v1_20_R4(Plugin plugin, String name) {
        super(plugin, name);
    }

    private void sendPacket(Player player, Packet<?> packet) {
        ((CraftPlayer) player).getHandle().c.b(packet);
    }

    private void sendPackets(Player player, Packet<?> @NotNull ... packets) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().c;
        for (Packet<?> p : packets) {
            connection.b(p);
        }
    }
}