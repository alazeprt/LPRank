package top.alazeprt.lprank.ui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.material.Skull
import taboolib.library.reflex.Reflex.Companion.setProperty
import taboolib.module.ui.ClickEvent
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import top.alazeprt.lprank.util.LPUtils

object UserGUI {

    private lateinit var background: ItemStack

    fun init() {
        background = ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1)
        background.itemMeta?.setDisplayName("§bHello!")
    }

    fun open(player: Player) {
        player.openMenu<Chest>("LPRank") {
            map(
                "#########",
                "#   M   #",
                "#########",
            )
            set('#', background)
            val playerHead = ItemStack(Material.PLAYER_HEAD, 1)
            val skullMeta: SkullMeta = playerHead.itemMeta as SkullMeta
            skullMeta.setDisplayName("§aYour Rank is:")
            val list = mutableListOf<String>()
            list.add(LPUtils.getPlayerRank(player).replace("&", "§"))
            skullMeta.lore = list
            skullMeta.setOwningPlayer(player)
            playerHead.itemMeta = skullMeta
            set('M', playerHead)
            onClick { event ->
                if (event.rawSlot in 0..26) {
                    event.isCancelled = true
                }
            }
        }
    }
}