package top.alazeprt.lprank

import net.luckperms.api.LuckPerms
import net.luckperms.api.LuckPermsProvider
import taboolib.common.platform.Plugin


object LPRankPlugin : Plugin() {
    lateinit var luckperms: LuckPerms

    override fun onEnable() {
        luckperms = LuckPermsProvider.get()
    }
}