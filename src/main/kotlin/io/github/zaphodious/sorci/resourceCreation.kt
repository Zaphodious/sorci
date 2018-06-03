package io.github.zaphodious.sorci

import io.github.zaphodious.sorci.CreativeTabInfo.tab
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader

fun compileEnglishLang() : String =
    (Items.values().toList() + Blocks.values().toList() as List<PreloadMetaItem>)
            .map { it.langString + it.englishName + "\n" }
            .reduce(String::plus)
            .plus("itemGroup.${CreativeTabInfo.baseName}=${CreativeTabInfo.englishName}")


interface PreloadMeta {
    val englishName: String
    val langString: String
    val baseName: String
    val thingType: String}

interface PreloadMetaItem : PreloadMeta {
    val item: Item
    override val langString: String
            get() = item.unlocalizedName + ".name="
    val tab
            get() = CreativeTabInfo.tab
    fun registerRenderer() {
        whenClient {
            println("Registering thing!")
            ModelLoader.setCustomModelResourceLocation(item, 0, ModelResourceLocation(item.registryName, "Inventory"))
        }
    }
    }

val Collection<PreloadMetaItem>.items
    get() = this.map { it.item }
val Array<PreloadMetaItem>.items
    get() = this.map { it.item }.toTypedArray()


interface PreloadMetaBlock : PreloadMetaItem {
    val block: Block
    override var item: Item
    fun initBlock() {
        block.unlocalizedName = baseName
        block.registryName = ResourceLocation(modid, baseName)
        block.setCreativeTab(tab)
        item = ItemBlock(block).setRegistryName(block.unlocalizedName)
    }

    override fun registerRenderer() {
        whenClient {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, ModelResourceLocation(block.registryName, "Inventory"))
        }
    }
}

val Collection<PreloadMetaBlock>.blocks
    get() = this.map { it.block }
val Array<PreloadMetaBlock>.blocks
    get() = this.map { it.block }.toTypedArray()
/*

data class ItemWithPreloadMeta(override val baseName: String, override val englishName: String, val tab: CreativeTabs = CreativeTabInfo.tab, override val item: Item): PreloadMetaItem {
    override val thingType = "item"
    init {
        item.unlocalizedName = baseName
        item.registryName = ResourceLocation(modid, baseName)
        item.creativeTab = tab
    }
}



data class BlockWithPreloadMeta(override val baseName: String, override val englishName: String, val block: Block, val tab: CreativeTabs = CreativeTabInfo.tab, override var item: Item = Item()) : PreloadMetaItem {
    override val thingType = "block"
    init {
        block.unlocalizedName = baseName
        block.registryName = ResourceLocation(modid, baseName)
        block.setCreativeTab(tab)
        item = ItemBlock(block).setRegistryName(block.unlocalizedName)
        Blocks.list.add(this)
        println("blockos are ${Blocks.list}")
    }
}*/