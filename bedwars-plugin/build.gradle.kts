plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    implementation("io.papermc:paperlib:1.0.7")
    implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation(project(":bedwars-api"))
    implementation(project(":versionsupport_1_8_R3"))
    implementation(project(":resetadapter-slime"))
    implementation(project(":versionsupport_1_12_R1"))
    implementation(project(":versionsupport_v1_16_R3"))
    implementation(project(":versionsupport_v1_17_R1"))
    implementation(project(":versionsupport_v1_18_R2"))
    implementation(project(":versionsupport_v1_19_R2"))
    implementation(project(":versionsupport-common"))
    implementation("com.andrei1058.vipfeatures:vipfeatures-api:[1.0,)")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("com.andrei1058.spigot.sidebar:sidebar-base:22.12")
    implementation("com.andrei1058.spigot.sidebar:sidebar-eight:22.12")
    implementation("com.andrei1058.spigot.sidebar:sidebar-twelve:22.12")
    implementation("com.andrei1058.spigot.sidebar:sidebar-sixteen:22.12")
    implementation("com.andrei1058.spigot.sidebar:sidebar-seventeen:22.12")
    implementation("com.andrei1058.spigot.sidebar:sidebar-eighteen:22.12")
    implementation("com.andrei1058.spigot.sidebar:sidebar-nineteen:22.12")
    compileOnly("de.simonsator:Party-and-Friends-MySQL-Edition-Spigot-API:1.5.4-RELEASE")
    compileOnly("de.simonsator:Spigot-Party-API-For-RedisBungee:1.0.3-SNAPSHOT")
    compileOnly("de.simonsator:DevelopmentPAFSpigot:1.0.67")
    compileOnly("com.alessiodp.parties:parties-api:3.2.6")
    compileOnly("net.citizensnpcs:citizens-main:2.0.30-SNAPSHOT")
    compileOnly("net.milkbowl.vault:VaultAPI:1.7")
    compileOnly("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.2")
}

description = "bedwars-plugin"
