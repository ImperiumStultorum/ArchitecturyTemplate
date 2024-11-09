// TODO change name of package (and directory)
package com.stultorum.architectury.templatemod.forge

import dev.architectury.platform.forge.EventBuses
import com.stultorum.architectury.templatemod.TemplateMod
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.MOD_CONTEXT

// TODO Change name of class
// TODO Don't forget to change the name of src/resources/templatemod.mixins.json
@Mod(TemplateMod.MOD_ID)
object TemplateModForge {
    init {
        EventBuses.registerModEventBus(TemplateMod.MOD_ID, MOD_BUS)
        TemplateMod.init()
    }
}