package io.github.zaphodious.sorci

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemSpade
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTool
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader

/*object Items {
    val sorcimote =
            ItemWithPreloadMeta(
                    baseName = "sorcerous_mote",
                    englishName = "Sorcerous Mote",
                    item = object : Item() {

                    })
    val focus =
            ItemWithPreloadMeta(
                    baseName = "focus",
                    englishName = "Focus",
                    item = object : Item() {

                    })
    val list = mutableListOf<ItemWithPreloadMeta>()
}*/
//(float attackDamageIn, float attackSpeedIn, Item.ToolMaterial materialIn, Set<Block> effectiveBlocksIn)
enum class Items(englishName: String, item: Item) : PreloadMetaItem{
    sorcerous_mote(englishName = "Vita Magicae", item = object : Item() {}),
    shov_wood(englishName = "Wooden Extractor of Earth",
            item = object : ItemSpade(Item.ToolMaterial.WOOD)
            {
                override fun onBlockStartBreak(itemstack: ItemStack?, pos: BlockPos?, player: EntityPlayer?): Boolean {
                    println("drop result is " +
                        player?.world?.spawnEntity(EntityItem(player?.world, pos!!.x.toFloat()?.plus(0.5), pos!!.y.plus(0.5), pos!!.z.plus(0.5)))
                    )
                    return false
                }

            }),
    focus(englishName = "Focus", item = object : Item() {});

    override val baseName = this.name
    override val englishName = englishName
    override val thingType = "Item"
    override val item = item


    init {
        println("This is me item!")
        initItem()

    }
    fun initItem() {
        item.unlocalizedName = baseName
        item.registryName = ResourceLocation(modid, baseName)
        item.creativeTab = tab
    }
}
