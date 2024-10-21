package top.alazeprt.lprank.util

import net.luckperms.api.node.NodeType
import net.luckperms.api.node.types.MetaNode
import org.bukkit.entity.Player
import top.alazeprt.lprank.LPRankPlugin.luckperms

object LPUtils {
    fun getPlayerRank(player: Player): String {
        val user = luckperms.getPlayerAdapter(Player::class.java).getUser(player)
        if (user.cachedData.metaData.getMetaValue("rank") != null) {
            return user.cachedData.metaData.getMetaValue("rank")!!
        }
        val group = luckperms.groupManager.getGroup("LPRankDefault")
        if (group != null && group.cachedData.metaData.getMetaValue("rank") != null) {
            return group.cachedData.metaData.getMetaValue("rank")!!;
        } else {
            return "DEFAULT"
        }
    }

    fun setPlayerRank(player: Player, rank: String) {
        val user = luckperms.getPlayerAdapter(Player::class.java).getUser(player)
        val node = MetaNode.builder("rank", rank).build()
        user.data().clear(NodeType.META.predicate { mn -> mn.metaKey == "rank" })
        user.data().add(node)
        luckperms.userManager.saveUser(user)
    }

    fun setDefault(rank: String) {
        val group = luckperms.groupManager.createAndLoadGroup("LPRankDefault").get()
        val node = MetaNode.builder("rank", rank).build()
        if (group == null) {
            throw NullPointerException("Group LPRankDefault is null!")
        }
        group.data().clear(NodeType.META.predicate { mn -> mn.metaKey == "rank" })
        group.data().add(node)
        luckperms.groupManager.saveGroup(group)
    }

    fun reset(player: Player) {
        val user = luckperms.getPlayerAdapter(Player::class.java).getUser(player)
        user.data().clear(NodeType.META.predicate { mn -> mn.metaKey == "rank" })
        luckperms.userManager.saveUser(user)
    }
}