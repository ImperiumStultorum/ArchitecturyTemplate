plugins {
    id("com.github.johnrengelman.shadow")
}

repositories {
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    mods {
        create(getVar("mod_id")) {
            sourceSet("main")
        }
    }
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
val developmentFabric: Configuration by configurations.getting

configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    developmentFabric.extendsFrom(common)
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${getVar("vFabricLoader")}")
    modApi("net.fabricmc.fabric-api:fabric-api:${parseVarStr("{vFAPI}+{vMinecraft}")}")
    modApi("dev.architectury:architectury-fabric:${getVar("vArchitectury")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${parseVarStr("{vFabricKotlin}+kotlin.{vKotlin}")}")


    common(project(":common", "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(":common", "transformProductionFabric")){
        isTransitive = false
    }
    common(project(":fabric-like", "namedElements")){
        isTransitive = false
    }
    shadowCommon(project(":fabric-like", "transformProductionFabric")) {
        isTransitive = false
    }
}

tasks.processResources {
    inputs.property("group", getVar("maven_group"))
    inputs.property("version", project.version)

    filesMatching("fabric.mod.json") {
        expand(mapOf(
            "group" to getVar("maven_group"),
            "version" to project.version,
            "name" to getVar("human_name"),
            "desc" to getVar("human_desc"),
            "source" to getVar("source"),
            "license" to getVar("license"),

            "mod_id" to getVar("mod_id"),
            "entry_class" to getVar("entry_class"),
            "vMinecraft" to getVar("vMinecraft"),
            "vArchitectury" to getVar("vArchitectury"),
            "vFabricKotlin" to parseVarStr("{vFabricKotlin}+kotlin.{vKotlin}")
        ))
    }
    filesMatching("${getVar("mod_id")}.mixins.json") {
        expand(mapOf(
            "maven_group" to getVar("maven_group")
        ))
    }
}

tasks.shadowJar {
    exclude("architectury.common.json")
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    injectAccessWidener.set(true)
    inputFile.set(tasks.shadowJar.get().archiveFile)
    dependsOn(tasks.shadowJar)
    archiveClassifier.set(null as String?)
}

tasks.jar {
    archiveClassifier.set("dev")
}

tasks.sourcesJar {
    val commonSources = project(":common").tasks.getByName<Jar>("sourcesJar")
    dependsOn(commonSources)
    from(commonSources.archiveFile.map { zipTree(it) })
}

components.getByName("java") {
    this as AdhocComponentWithVariants
    this.withVariantsFromConfiguration(project.configurations["shadowRuntimeElements"]) {
        skip()
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