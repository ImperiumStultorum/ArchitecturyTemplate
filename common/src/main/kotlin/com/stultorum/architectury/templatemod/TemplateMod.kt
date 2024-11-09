// TODO change name of package (and directory)

package com.stultorum.architectury.templatemod

import dev.architectury.registry.CreativeTabRegistry
import dev.architectury.registry.registries.RegistrarManager
import java.util.function.Supplier

// TODO Change name of class
// TODO Don't forget to change the name of src/resources/templatemod-common.mixins.json
// TODO Don't forget to change the name of src/resources/templatemod.accesswidener
// TODO Don't forget to change the contents of src/resources/architectury.common.json
object TemplateMod {
    // TODO change constant
    const val MOD_ID = "templatemod"

    private val registrar: RegistrarManager by lazy { RegistrarManager.get(MOD_ID) }

    fun init() {
        println("${MOD_ID} init")
    }
}