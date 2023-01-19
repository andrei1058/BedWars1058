plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    providedCompile(project(":bedwars-api"))
    providedCompile(project(":versionsupport-common"))
    compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT")
}

description = "versionsupport_v1_18_R2"
