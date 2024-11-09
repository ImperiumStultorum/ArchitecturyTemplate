
architectury {
    common(rootProject.property("enabled_platforms").toString().split(","))
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

dependencies {
    modApi("net.fabricmc.fabric-api:fabric-api:${parseVarStr("{vFAPI}+{vMinecraft}")}")
    modApi("dev.architectury:architectury-fabric:${getVar("vArchitectury")}")

    compileOnly(project(":common", "namedElements")) {
        isTransitive = false
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