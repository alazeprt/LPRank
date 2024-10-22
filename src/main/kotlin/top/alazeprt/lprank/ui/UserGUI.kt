package top.alazeprt.lprank.ui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.Chest
import top.alazeprt.lprank.util.LPUtils

object UserGUI {

    private lateinit var background: ItemStack

    fun init() {
        background = ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, 1)
    }

    fun open(player: Player) {
        player.openMenu<Chest>("LPRank") {
            map(
                "#########",
                "#   M   #",
                "########X",
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
            if (player.hasPermission("lprank.ui.admin")) {
                val adminI = ItemStack(Material.REDSTONE_BLOCK, 1)
                val adminIM = adminI.itemMeta!!
                adminIM.setDisplayName("§cAdmin Menu")
                adminI.itemMeta = adminIM
                set('X', adminI)
            } else {
                set('X', background)
            }
            onClick { event ->
                if (event.rawSlot in 0..26) {
                    event.isCancelled = true
                }
                if (event.rawSlot == 26 && player.hasPermission("lprank.ui.admin")) {
                    player.closeInventory()
                    AdminUI.open(player)
                }
            }
        }
    }
}