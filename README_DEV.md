# Development Environment

This project includes a Docker-based development environment for easy testing and real-time development.

## Prerequisites

- [Docker](https://www.docker.com/) and Docker Compose
- Java 11 or newer (for building)
- Maven

## Getting Started

1. **Build the project with the `dev` profile:**
   ```bash
   mvn clean package -Pdev -DskipTests
   ```
   This will build the plugin and automatically copy the shaded JAR to `docker/plugins/BedWars1058.jar`.

2. **Start the Minecraft server:**
   ```bash
   docker-compose up -d
   ```
   The server will start on port `25565`. The first start might take some time as it downloads the server JAR.

3. **Check the logs:**
   ```bash
   docker-compose logs -f
   ```

## Real-time Development (Hotswap)

### Standard JVM Hotswap
The server is configured to allow remote debugging on port `5005`.

1. In IntelliJ IDEA, go to **Run > Edit Configurations**.
2. Add a new **Remote JVM Debug** configuration.
3. Set the host to `localhost` and port to `5005`.
4. Start the debug session.
5. After making changes to the code, go to **Build > Recompile 'file_name'** or **Build > Build Project**. IntelliJ will automatically hotswap the changed classes into the running JVM.
   *Note: Standard JVM hotswap only supports changing method bodies. For adding methods or fields, you need to restart the plugin or use HotswapAgent.*

### Automatic Reload
If you make significant changes and need to reload the entire plugin:
1. Rebuild the project: `mvn package -Pdev -DskipTests -pl bedwars-plugin`
2. Run `/reload` or use a plugin like `PlugManX` to reload BedWars1058 specifically.

## Server Configuration
- Server data (world, configs, etc.) is stored in the `docker/data` directory.
- You can change the Minecraft version or server type in `docker-compose.yml`.
