package top.alazeprt.lprank

import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import top.alazeprt.lprank.papi.LPRankExpansion


object LPRankPlugin : Plugin() {
    lateinit var luckperms: LuckPerms

    override fun onEnable() {
        luckperms = LuckPermsProvider.get()
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            LPRankExpansion().register()
        }
    }
}