package top.alazeprt.lprank.ui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import taboolib.module.ui.openMenu
import taboolib.module.ui.type.PageableChest
import taboolib.platform.util.nextChat
import top.alazeprt.lprank.util.LPUtils

object AdminUI {
    fun open(player: Player) {
        player.openMenu<PageableChest<Player>>("LPRank Admin Menu") {
            map(
                "#########",
                "#########",
                "LXXXRXXXN"
            )
            slotsBy('#')
            elements {
                Bukkit.getOnlinePlayers().toList()
            }
            onGenerate { player, _, _, _ ->
                val itemStack = ItemStack(Material.PLAYER_HEAD, 1)
                val skullMeta = itemStack.itemMeta as SkullMeta
                skullMeta.setOwningPlayer(player)
                skullMeta.setDisplayName("§a" + player.name)
                val list = mutableListOf<String>()
                list.add(LPUtils.getPlayerRank(player).replace("&", "§"))
                list.add("§bLeft click to set his rank")
                list.add("§eRight click to reset his rank")
                skullMeta.lore = list
                itemStack.itemMeta = skullMeta
                itemStack
            }
            onClick { event, element ->
                if (event.rawSlot < 18) {
                    if (element.player == null) {
                        player.sendMessage("§cPlayer not found!")
                        return@onClick
                    }
                    if (event.clickEvent().isLeftClick) {
                        player.closeInventory()
                        player.sendMessage("§eSet ${element.name}'s rank to (type \"ESC\" to stop this action): ")
                        player.nextChat {
                            if (it == "ESC") {
                                player.sendMessage("§cOperation canceled!")
                                open(player)
                                return@nextChat
                            }
                            LPUtils.setPlayerRank(element.player!!, it)
                            player.sendMessage("§aThe operation was successful!")
                            open(player)
                        }
                    } else if (event.clickEvent().isRightClick) {
                        LPUtils.resetPlayerRank(element.player!!)
                        player.closeInventory()
                        open(player)
                        player.sendMessage("§aThe operation was successful!")
                    }
                }
            }
            setNextPage(getFirstSlot('N')) { _, hasNextPage ->
                if (hasNextPage) {
                    val itemStack = ItemStack(Material.LIME_STAINED_GLASS_PANE, 1)
                    val itemMeta2 = itemStack.itemMeta!!
                    itemMeta2.setDisplayName("§aNext Page")
                    itemStack.itemMeta = itemMeta2
                    itemStack
                } else {
                    ItemStack(Material.AIR, 1)
                }
            }
            setPreviousPage(getFirstSlot('L')) { _, hasPreviousPage ->
                if (hasPreviousPage) {
                    val itemStack = ItemStack(Material.RED_STAINED_GLASS_PANE, 1)
                    val itemMeta2 = itemStack.itemMeta!!
                    itemMeta2.setDisplayName("§aPrevious Page")
                    itemStack.itemMeta = itemMeta2
                    itemStack
                } else {
                    ItemStack(Material.AIR, 1)
                }
            }
        }
    }
}