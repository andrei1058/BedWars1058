plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/snapshots")
    }

    maven {
        url = uri("https://gitlab.com/api/v4/groups/4800570/-/packages/maven")
    }

    maven {
        url = uri("https://gitlab.com/api/v4/projects/6491858/packages/maven")
    }

    maven {
        url = uri("https://repo.codemc.io/repository/nms/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }

    maven {
        url = uri("https://simonsator.de/repo/")
    }

    maven {
        url = uri("https://maven.citizensnpcs.co/repo")
    }

    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        url = uri("https://repo.codemc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://repo.codemc.io/repository/maven-releases/")
    }

    maven {
        url = uri("https://repo.codemc.io/repository/maven-snapshots/")
    }

    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://repo.alessiodp.com/releases/")
    }

    maven {
        url = uri("https://repo.andrei1058.dev/releases/")
    }

    maven {
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        url = uri("https://repo.fusesource.com/nexus/content/repositories/releases-3rd-party/")
    }

    maven {
        url = uri("https://repo.glaremasters.me/repository/concuncan/")
    }
}

dependencies {
    compileOnly("net.md-5:bungeecord-chat:1.8-SNAPSHOT")
    compileOnly("commons-io:commons-io:2.11.0")
    compileOnly("org.jetbrains:annotations:23.1.0")
}

group = "com.andrei1058.bedwars"
version = "22.9-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
