import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.andrei1058.bedwars.java-conventions")
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

dependencies {
    api("io.papermc:paperlib:1.0.7")
    api("org.bstats:bstats-bukkit:3.0.0")

    api(project(":api"))

    // slime world manager support
    api(project(":resetadapter_slime"))

    // version support
    api(project(":v1_8_R3"))
    api(project(":v1_12_R1"))
    api(project(":v1_16_R3"))
    api(project(":v1_17_R1"))
    api(project(":v1_18_R2"))
    api(project(":v1_19_R2"))
    api(project(":common"))

    api("com.zaxxer:HikariCP:5.0.1")
    api("org.slf4j:slf4j-simple:2.0.6")

    api("com.andrei1058.vipfeatures:vipfeatures-api:[1.0,)")
    api("com.andrei1058.spigot.sidebar:sidebar-base:23.2")
    api("com.andrei1058.spigot.sidebar:sidebar-eight:23.2")
    api("com.andrei1058.spigot.sidebar:sidebar-twelve:23.2")
    api("com.andrei1058.spigot.sidebar:sidebar-sixteen:23.2")
    api("com.andrei1058.spigot.sidebar:sidebar-seventeen:23.2")
    api("com.andrei1058.spigot.sidebar:sidebar-eighteen:23.2")
    api("com.andrei1058.spigot.sidebar:sidebar-nineteen:23.2")

    compileOnly("de.simonsator:Party-and-Friends-MySQL-Edition-Spigot-API:1.5.4-RELEASE")
    compileOnly("de.simonsator:Spigot-Party-API-For-RedisBungee:1.0.3-SNAPSHOT")
    compileOnly("de.simonsator:DevelopmentPAFSpigot:1.0.67")
    compileOnly("com.alessiodp.parties:parties-api:3.2.9")
    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT")
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.2")
}

tasks.withType<ShadowJar> {
    archiveFileName.set("bedwars-plugin-${project.version}.jar")

    exclude(
        "META-INF/**",
    )

    val prefix = "com.andrei1058.bedwars.libs"
    listOf(
        "org.bstats",
        "com.zaxxer.hikari",
        "org.slf4j",
        "com.andrei1058.spigot.sidebar",
        "com.andrei1058.spigot.sidebarutils"
    ).forEach { pack ->
        relocate(pack, "$prefix.$pack")
    }
}

description = "plugin"
