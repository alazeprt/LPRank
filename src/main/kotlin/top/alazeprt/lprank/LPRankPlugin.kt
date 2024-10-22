package top.alazeprt.lprank

import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import top.alazeprt.lprank.papi.LPRankExpansion
import top.alazeprt.lprank.util.StorageMode
import java.util.Objects


object LPRankPlugin : Plugin() {
    lateinit var luckperms: LuckPerms

    @Config("config.yml")
    lateinit var config: ConfigFile

    lateinit var mode: StorageMode
    lateinit var key: String

    override fun onEnable() {
        luckperms = LuckPermsProvider.get()
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            LPRankExpansion().register()
        }
        if (Objects.equals(config.getString("metadata"), "prefix")) {
            mode = StorageMode.PREFIX
        } else if (Objects.equals(config.getString("metadata"), "suffix")) {
            mode = StorageMode.SUFFIX
        } else {
            mode = StorageMode.CUSTOM
            key = config.getString("key") ?: "rank"
        }
    }
}