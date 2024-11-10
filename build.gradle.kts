import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    java
    kotlin("jvm") version "2.0.21" // {vKotlin}
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.7-SNAPSHOT" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

architectury {
    minecraft = getVar("vMinecraft")
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    dependencies {
        "minecraft"("com.mojang:minecraft:${getVar("vMinecraft")}")
        "mappings"("net.fabricmc:yarn:${parseVarStr("{vMinecraft}+{vMappings}")}:v2")
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")

    base.archivesName.set(getVar("archives_base_name"))
    version = getVar("mod_version")
    group = getVar("maven_group")

    repositories {

    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect:${getVar("vKotlin")}")
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    java {
        withSourcesJar()
    }
}

fun parseVarStr(format: String): String {
    val parseVarRgx = Regex("\\{([^{}]+)\\}")
    return parseVarRgx.replace(format) { match ->
        return@replace getVar(match.groups[1]!!.value)
    }
}

fun getVar(name: String): String {
    return rootProject.property(name).toString()
}