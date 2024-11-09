// TODO change name of package (and directory)
package com.stultorum.architectury.templatemod.quilt

import com.stultorum.architectury.templatemod.fabriclike.TemplateModFabriclike
import org.quiltmc.loader.api.ModContainer
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer

// TODO Change name of class
// TODO Don't forget to change the name of src/resources/templatemod.mixins.json
object TemplateModQuilt: ModInitializer {
    override fun onInitialize(mod: ModContainer) {
        TemplateModFabriclike.init()
    }
}