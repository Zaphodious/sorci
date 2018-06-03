package io.github.zaphodious.sorci

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import java.util.*
/*
object Blocksbad {

    val congealedMotes =
            BlockWithPreloadMeta(
                    "congealed_motes",
                    "Congealed Motes",
                    object : Block(Material.WOOD) {
                       *//* init {
                            unlocalizedName = "congealed_motes"
                            registryName = ResourceLocation(modid, "congealed_motes")
                            setCreativeTab(CreativeTabInfo.tab)
                        }*//*

                        override fun getItemDropped(state: IBlockState?, rand: Random?, fortune: Int): Item = Items.sorcimote.item
                        override fun quantityDropped(random: Random?): Int = 9
                    })

    val list = mutableListOf<BlockWithPreloadMeta>()
}*/


enum class Blocks(englishName: String, block: Block) : PreloadMetaBlock{
    solidified_motes(englishName = "Vita Solidatae",
            block = object : Block(Material.WOOD) {
                override fun quantityDropped(random: Random?) = 9
                override fun getItemDropped(state: IBlockState?, rand: Random?, fortune: Int): Item = Items.sorcerous_mote.item
            });

    override val baseName = this.name
    override val englishName = englishName
    override val thingType = "Item"
    override val block = block
    override lateinit var item : Item



    init {
        println("This is me item!")
        initBlock()
    }
}
