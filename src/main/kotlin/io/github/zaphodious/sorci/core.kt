package io.github.zaphodious.sorci

import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.io.File
import java.nio.charset.Charset

const val modid = "sorci"
const val name = "Sorcery Craft"
const val version = "0.1"
const val dev = true


@Mod(modid = modid, name = name, version = version, modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object Sorcerery {


    @Mod.EventHandler
    fun postInit(event: FMLPostInitializationEvent) {
        whendev { compileEnglishLang().toFile("en_US.lang") }
        println("Proxy is ${proxy.sideInfo}")

    }

    @SidedProxy(modId = modid)
    lateinit var proxy: CommonProxy

    open class CommonProxy {
        open val sideInfo = "Common"
        open fun registerItemRenderer(item: Item, meta: Int, id: String) = Unit
    }

    class ServerProxy : CommonProxy() {
        override val sideInfo = "Server"
    }

    class ClientProxy : CommonProxy() {
        override val sideInfo = "Client"
    }
}

@Mod.EventBusSubscriber(modid = modid)
object RegistryHandler {
    @JvmStatic
    @SubscribeEvent
    fun onItemRegister(event: RegistryEvent.Register<Item>) {
        event.registry.registerAll(*allItems)
    }

    @JvmStatic
    @SubscribeEvent
    fun onBlockRegister(event: RegistryEvent.Register<Block>) {
        println("blockhead says what?")
        event.registry.registerAll(*allBlocks)
    }

    @JvmStatic
    @SubscribeEvent
    fun onModelRegister(event: ModelRegistryEvent) {
        whenClient {
            Items.values().forEach(Items::registerRenderer)
            Blocks.values().forEach(Blocks::registerRenderer)
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onPlayerDo(event: PlayerEvent.BreakSpeed) {
        //println("Player breakin' a thing!")
        event.entityPlayer.inventory.getCurrentItem().item
        event.entityPlayer.world
        event.state.block
    }
}

fun whendev(runner: () -> Unit): Unit = if (dev) runner() else Unit

fun whenClient(runner: () -> Unit): Unit = if (Sorcerery.proxy.sideInfo == "Client") runner() else Unit

fun String.toFile(path: String) {
    File(path).writeText(this, Charset.defaultCharset())
}

object CreativeTabInfo {
    val baseName = "sorci_tab"
    val englishName = name
    val tab = object : CreativeTabs("sorci_tab") {
        override fun getTabIconItem(): ItemStack =
                ItemStack(net.minecraft.init.Items.GHAST_TEAR)
    }
}

val allBlocks
    get() = Blocks.values().map { it.block }.toTypedArray()
val allItems
    get() = (Items.values().toList() + Blocks.values().toList() as List<PreloadMetaItem>).map { it.item }.toTypedArray()