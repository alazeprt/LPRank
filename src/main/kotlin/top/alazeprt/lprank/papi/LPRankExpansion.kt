package top.alazeprt.lprank.papi

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull
import taboolib.common5.util.replace
import top.alazeprt.lprank.util.LPUtils


class LPRankExpansion : PlaceholderExpansion() {

    @NotNull
    override fun getAuthor(): String {
        return "alazeprt"
    }

    @NotNull
    override fun getIdentifier(): String {
        return "lprank"
    }

    @NotNull
    override fun getVersion(): String {
        return "1.0.1"
    }

    override fun persist(): Boolean {
        return true
    }

    override fun onRequest(player: OfflinePlayer, @NotNull params: String): String? {
        if (params == "rank" && player is Player) {
            return LPUtils.getPlayerRank(player).replace("&", "§")
        }
        return null
    }
}