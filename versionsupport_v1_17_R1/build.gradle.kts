plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    implementation(project(":bedwars-api"))
    implementation(project(":versionsupport-common"))
    compileOnly("org.spigotmc:spigot:1.17.1-R0.1-SNAPSHOT")
}

description = "versionsupport_v1_17_R1"
