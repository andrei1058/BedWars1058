package dev.andrei1058.bedwars.platform.paper;

import com.andrei1058.bedwars.api.server.VersionSupport;
import io.papermc.paper.ServerBuildInfo;
import org.bukkit.plugin.java.JavaPlugin;

public class v1_21_R1 extends PaperPlatform {

    @SuppressWarnings("unused")
    public v1_21_R1() {
        ServerBuildInfo build = ServerBuildInfo.buildInfo();

        var brandId = build.brandId().asString().equals("papermc:paper"); // example: "papermc:paper"
        var brandName = build.brandName().equals("Paper"); // example: "Paper"
        var minecraftVersionId = build.minecraftVersionId().equals("1.21.4"); // example: "1.20.6"
        var minecraftVersionName = build.minecraftVersionName().equals("1.21.4"); // example: "1.20.6" (some versions return a more "friendly" value)

        if (!(brandId && brandName && minecraftVersionId && minecraftVersionName)) {
            throw new IllegalStateException("Invalid server build info!");
        }
    }

    @Override
    public VersionSupport getOldWrapper(JavaPlugin plugin) {
        return new v1_21_R1_NMS(plugin, "v1.21.4");
    }
}
