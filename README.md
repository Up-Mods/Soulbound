# Soulbound

[![Build Status](https://img.shields.io/github/workflow/status/UpcraftLP/Soulbound/Build%20Status?label=Build%20Status&logo=GitHub&style=flat-square)](https://github.com/UpcraftLP/Soulbound/actions?query=workflow%3A%22Build+Status%22 "GitHub Actions") [![Latest Release](https://img.shields.io/github/v/release/Up-Mods/Soulbound?include_prereleases&label=Latest%20Release&logo=GitHub&style=flat-square)](https://github.com/Up-Mods/Soulbound/releases/latest "GitHub Releases") [![Maven](https://img.shields.io/maven-metadata/v?label=Download%20from%20Maven&metadataUrl=https%3A%2F%2Fmaven.uuid.gg%2Freleases%2Fdev%2Fupcraft%2FSoulbound%2Fmaven-metadata.xml&style=flat-square)](https://maven.uuid.gg/#/releases/dev/upcraft/Soulbound "maven.uuid.gg")

Soulbound is a mod for Minecraft that adds an enchantment for keeping items upon death. It is built for the 
[QuiltMC](https://quiltmc.org) modding toolchain.

## Addons

Out of the box, Soulbound supports [Trinkets](https://www.curseforge.com/minecraft/mc-mods/trinkets) and [Universal Graves](https://www.curseforge.com/minecraft/mc-mods/universal-graves).

If your mod adds an additional player inventory, consider adding integration.

### Maven

```groovy
repositories {
    maven {
        name = "Up-Mods"
        url = "https://maven.uuid.gg/releases"
    }
}

dependencies {
    modImplementation "dev.upcraft:Soulbound:${soulbound_version}"
}
```

The API is documented with JavaDocs in the [api package](./src/main/java/dev/upcraft/soulbound/api).
