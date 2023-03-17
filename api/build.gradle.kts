plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    compileOnly("org.spigotmc:spigot:1.16.3-R0.1-SNAPSHOT")
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")

    api("com.andrei1058.spigot.sidebar:sidebar-base:23.2")
    compileOnly("com.google.common:google-collect:1.0")
}

description = "api"
