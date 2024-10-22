![LPRank](./icon.png "LPRank")
# LPRank
### A plugin based on the LuckPerms implementation of the ranking system

## Description
LPRank is a ranking system plugin based on LuckPerms that allows you to set a custom rank for each player, while enabling other plugins to access the player's rank via the PlaceHolderAPI.

## Features
- **Lightweight**: Rank data is stored via LuckPerms metadata, the plugin itself does not have a folder for data storage
- **User-friendly**: You can set a player's rank directly by command, while other players who are not set will have a default rank (which can also be set)
- **Compatible**: The plugin is compatible with PlaceholderAPI, through which you can access rank

## Commands & Permissions
| Command                   | Permission                | Aliases                   | Description                  |
|---------------------------|---------------------------|---------------------------|------------------------------|
| /rank get [player]        | lprank.command.get        | -                         | Get a specific player rank   |
| /rank set <player> <rank> | lprank.command.set        | -                         | Set a specific player rank   |
| /rank reset <player>      | lprank.command.reset      | /rank [remove, toDefault] | Reset a specific player rank |
| /rank setDefault <player> | lprank.command.setDefault | -                         | Set the default rank         |

## Future Plans
- Add configs so that they can be displayed in chat or on the tab
- Add interface for better rank setting
- Add a rank up feature where players can upgrade their rank in a specific way
