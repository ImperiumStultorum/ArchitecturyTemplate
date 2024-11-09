// TODO change name of package (and directory)
package com.stultorum.architectury.templatemod.fabric

import com.stultorum.architectury.templatemod.fabriclike.TemplateModFabriclike
import net.fabricmc.api.ModInitializer

// TODO Change name of class
// TODO Don't forget to change the name of src/resources/templatemod.mixins.json
object TemplateModFabric: ModInitializer {
    override fun onInitialize() {
        TemplateModFabriclike.init()
    }
}
