plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
    compileOnly("org.spigotmc:spigot:1.19.3-R0.1-SNAPSHOT")
}

description = "v1_19_R2"
