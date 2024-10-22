package top.alazeprt.lprank.util

import net.luckperms.api.node.NodeType
import net.luckperms.api.node.types.MetaNode
import net.luckperms.api.node.types.PrefixNode
import net.luckperms.api.node.types.SuffixNode
import org.bukkit.entity.Player
import top.alazeprt.lprank.LPRankPlugin.luckperms
import top.alazeprt.lprank.LPRankPlugin.mode

object LPUtils {
    fun getPlayerRank(player: Player): String {
        val user = luckperms.getPlayerAdapter(Player::class.java).getUser(player)
        if (mode == StorageMode.CUSTOM) {
            if (user.cachedData.metaData.getMetaValue("rank") != null) {
                return user.cachedData.metaData.getMetaValue("rank")!!
            }
        } else if (mode == StorageMode.PREFIX) {
            if (user.cachedData.metaData.prefix != null) {
                return user.cachedData.metaData.prefix!!
            }
        } else if (mode == StorageMode.SUFFIX) {
            if (user.cachedData.metaData.suffix != null) {
                return user.cachedData.metaData.suffix!!
            }
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
        if (mode == StorageMode.CUSTOM) {
            val node = MetaNode.builder("rank", rank).build()
            user.data().clear(NodeType.META.predicate { mn -> mn.metaKey == "rank" })
            user.data().add(node)
        } else if (mode == StorageMode.PREFIX) {
            val node = PrefixNode.builder(rank, 1).build()
            user.data().clear(NodeType.PREFIX.predicate())
            user.data().add(node)
        } else if (mode == StorageMode.SUFFIX) {
            val node = SuffixNode.builder(rank, 1).build()
            user.data().clear(NodeType.SUFFIX.predicate())
            user.data().add(node)
        }
        luckperms.userManager.saveUser(user)
    }

    fun setDefaultRank(rank: String) {
        val group = luckperms.groupManager.createAndLoadGroup("LPRankDefault").get()
        val node = MetaNode.builder("rank", rank).build()
        if (group == null) {
            throw NullPointerException("Group LPRankDefault is null!")
        }
        group.data().clear(NodeType.META.predicate { mn -> mn.metaKey == "rank" })
        group.data().add(node)
        luckperms.groupManager.saveGroup(group)
    }

    fun resetPlayerRank(player: Player) {
        val user = luckperms.getPlayerAdapter(Player::class.java).getUser(player)
        if (mode == StorageMode.CUSTOM) {
            user.data().clear(NodeType.META.predicate { mn -> mn.metaKey == "rank" })
        } else if (mode == StorageMode.PREFIX) {
            user.data().clear(NodeType.PREFIX.predicate())
        } else if (mode == StorageMode.SUFFIX) {
            user.data().clear(NodeType.SUFFIX.predicate())
        }
        luckperms.userManager.saveUser(user)
    }
}