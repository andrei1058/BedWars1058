plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    providedCompile(project(":bedwars-api"))
    compileOnly("org.spigotmc:spigot:1.14.4-R0.1-SNAPSHOT")
}

description = "versionsupport-common"
