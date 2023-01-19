plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    providedCompile(project(":bedwars-api"))
    providedCompile(project(":versionsupport-common"))
    compileOnly("org.spigotmc:spigot:1.12.2-R0.1-SNAPSHOT")
}

description = "versionsupport_1_12_R1"
