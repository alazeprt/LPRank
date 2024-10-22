package top.alazeprt.lprank.command

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand
import top.alazeprt.lprank.ui.UserGUI
import top.alazeprt.lprank.util.LPUtils

@CommandHeader("rank", ["lprank"])
object RankCommand {
    @CommandBody(permission = "lprank.command.get")
    val get = subCommand {
        player("player") {
            suggestion<CommandSender>(uncheck = true) { _, _ ->
                Bukkit.getOnlinePlayers().map { player -> player.name }
            }
            execute<CommandSender> { sender, context, _ ->
                if (!sender.hasPermission("lprank.command.get.other")) {
                    sender.sendMessage("§cYou don't have permission to do this!")
                    return@execute
                }
                val player = Bukkit.getPlayer(context.player("player").uniqueId)
                if (player == null) {
                    sender.sendMessage("§cThe player ${context["player"]} is not online!")
                    return@execute
                }
                sender.sendMessage("§aThe rank of ${context["player"]} is: ${LPUtils.getPlayerRank(player).replace("&", "§")}")
            }
        }
        execute<CommandSender> { sender, _, _ ->
            if (sender !is Player) {
                sender.sendMessage("§cThis command can only be used by players!")
                return@execute
            }
            sender.sendMessage("§aYour rank is: ${LPUtils.getPlayerRank(sender).replace("&", "§")}")
        }

    }

    @CommandBody(permission = "lprank.command.set")
    val set = subCommand {
        player("player") {
            suggestion<CommandSender>(uncheck = true) { _, _ ->
                Bukkit.getOnlinePlayers().map { player -> player.name }
            }
            dynamic("rank") {
                execute<CommandSender> { sender, context, _ ->
                    val player = Bukkit.getPlayer(context.player("player").uniqueId)
                    if (player == null) {
                        sender.sendMessage("§cThe player ${context["player"]} is not online!")
                        return@execute
                    }
                    LPUtils.setPlayerRank(player, context["rank"])
                    sender.sendMessage("§aSuccessfully set the rank of ${context["player"]} to ${context["rank"].replace("&", "§")}")
                }
            }
        }
    }

    @CommandBody(permission = "lprank.command.setDefault")
    val setDefault = subCommand {
        dynamic("rank") {
            execute<CommandSender> { sender, context, _ ->
                try {
                    LPUtils.setDefaultRank(context["rank"])
                } catch (e: Exception) {
                    sender.sendMessage("§cThere was an exception while setting the default rank: $e. Please try again or give us feedback!")
                    e.printStackTrace()
                    return@execute
                }
                sender.sendMessage("§aSuccessfully set the default rank to ${context["rank"].replace("&", "§")}")
            }
        }
    }

    @CommandBody(aliases = ["remove", "toDefault"], permission = "lprank.command.reset")
    val reset = subCommand {
        player("player") {
            suggestion<CommandSender>(uncheck = true) { _, _ ->
                Bukkit.getOnlinePlayers().map { player -> player.name }
            }
            execute<CommandSender> { sender, context, _ ->
                val player = Bukkit.getPlayer(context.player("player").uniqueId)
                if (player == null) {
                    sender.sendMessage("§cThe player ${context["player"]} is not online!")
                    return@execute
                }
                LPUtils.resetPlayerRank(player)
                sender.sendMessage("§aThe rank of ${context["player"]} reset successfully!")
            }
        }
        execute<CommandSender> { sender, _, _ ->
            if (sender !is Player) {
                sender.sendMessage("§cThis command can only be used by players!")
                return@execute
            }
            LPUtils.resetPlayerRank(sender)
            sender.sendMessage("§aSuccessfully reset your rank!")
        }
    }

    @CommandBody(aliases = ["gui", "menu"], permission = "lprank.command.ui")
    val ui = subCommand {
        execute<CommandSender> {sender, _, _ ->
            if (sender !is Player) {
                sender.sendMessage("§cThis command can only be used by players!")
                return@execute
            }
            UserGUI.open(sender)
        }
    }
}