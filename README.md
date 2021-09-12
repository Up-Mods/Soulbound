# Soulbound

[![Build Status](https://img.shields.io/github/workflow/status/UpcraftLP/Soulbound/Build%20Status?label=Build%20Status&logo=GitHub&style=flat-square)](https://github.com/UpcraftLP/Soulbound/actions?query=workflow%3A%22Build+Status%22 "GitHub Actions") [![Latest Release](https://img.shields.io/github/v/release/UpcraftLP/Soulbound?include_prereleases&label=Latest%20Release&logo=GitHub&style=flat-square)](https://github.com/UpcraftLP/Soulbound/releases/latest "GitHub Releases") [![OnyxStudios Maven](https://img.shields.io/maven-metadata/v?label=Download%20from%20OnyxStudios%20Maven&metadataUrl=https%3A%2F%2Fmaven.onyxstudios.dev%2Fdev%2Fupcraft%2FSoulbound%2Fmaven-metadata.xml&style=flat-square)](https://maven.onyxstudios.dev/dev/upcraft/Soulbound "maven.onyxstudios.dev") [![JitPack](https://jitpack.io/v/UpcraftLP/Soulbound.svg?label=Download%20from%20JitPack&style=flat-square)](https://jitpack.io/#UpcraftLP/Soulbound "Jitpack Build Status")

Soulbound is a mod for Minecraft that adds an enchantment for keeping items upon death. It is built for the 
[Fabric](https://fabricmc.net) modding toolchain.

## Addons

Out of the box, Soulbound supports [Trinkets](https://www.curseforge.com/minecraft/mc-mods/trinkets-fabric).

If your mod adds an additional player inventory, consider adding integration.

### Maven

```groovy
repositories {
    maven {
        name = "OnyxStudios"
        url = "https://maven.onyxstudios.dev"
    }
}

dependencies {
    modImplementation "dev.upcraft:Soulbound:${soulbound_version}"
}
```

The API is documented with JavaDocs in 
[`SoulboundContainer`](./src/main/java/dev/upcraft/soulbound/api/SoulboundContainer.java).
