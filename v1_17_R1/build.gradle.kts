plugins {
    id("com.andrei1058.bedwars.java-conventions")
}

dependencies {
    implementation(project(":api"))
    implementation(project(":common"))
    compileOnly("org.spigotmc:spigot:1.17.1-R0.1-SNAPSHOT")
}


description = "v1_17_R1"
