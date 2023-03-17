plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    implementation(project(":api"))
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.grinderwolf:slimeworldmanager-api:[2.2.1,)")
}

description = "resetadapter_slime"
