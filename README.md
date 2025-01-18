# YGWhitelist Plugin

---

[English](#english) | [中文](#中文)

> 测试环境：**Catserver** , **1.12.2**
> 插件使用**Bukkit**/**Spigot**开发
> 有问题和建议可以在 **Issues** 提交

- config.yml 
```yml
# 白名单玩家列表
whitelist: []

# 插件设置
settings:
  short-command: "wl"  # 短指令配置

# 消息配置
messages:
  plugin-enabled: "§a白名单插件已启用！"
  plugin-disabled: "§c白名单插件已禁用！"
  no-permission: "§c你没有权限使用这个命令！"
  kick-message: "§c [ %player% ] \n你没有这个服务器的白名单!"
  command-usage: "§c用法: /wlist <add|remove|reload|list> [玩家名]"
  player-required: "§c请指定玩家名！"
  player-already-whitelisted: "§e该玩家已在白名单中！"
  player-not-whitelisted: "§e该玩家不在白名单中！"
  player-added: "§a已将 %player% 添加到白名单。"
  player-removed: "§a已将 %player% 从白名单中移除。"
  whitelist-reloaded: "§a白名单已重新加载。"
  unknown-command: "§c未知的子命令！使用方法: /wlist <add|remove|reload|list> [玩家名]"
  list-header: "§a当前白名单玩家列表："
  list-empty: "§e白名单为空"
  list-format: "§f- %player%"
```
---

## 中文

### 简介
YGWhitelist 是一个轻量级的 Bukkit/Spigot 服务器白名单管理插件。它提供简单高效的白名单管理功能，支持自定义消息和命令。

### 功能特点
- 简单易用的白名单管理
- 可自定义的消息提示（支持颜色代码）
- 可配置的命令别名
- 基于权限的访问控制
- 支持多行踢出消息

### 命令
- `/wlist add <玩家名>` - 添加玩家到白名单
- `/wlist remove <玩家名>` - 从白名单移除玩家
- `/wlist list` - 显示所有白名单玩家
- `/wlist reload` - 重新加载配置
- 支持短命令 `/wl`（可在配置文件中修改）

### 权限
- `ygwhitelist.admin` - 使用所有白名单命令的权限（默认：op）

### 配置
插件可通过 `config.yml` 进行高度自定义：


## English

### Introduction
YGWhitelist is a lightweight whitelist management plugin for Bukkit/Spigot servers. It provides simple and efficient whitelist management functionality with customizable messages and commands.

### Features
- Easy to use whitelist management
- Customizable messages (supports color codes)
- Configurable command aliases
- Permission-based access control
- Multi-line kick messages support

### Commands
- `/wlist add <player>` - Add a player to whitelist
- `/wlist remove <player>` - Remove a player from whitelist
- `/wlist list` - Show all whitelisted players
- `/wlist reload` - Reload configuration
- Short command `/wl` is also available (configurable)

### Permissions
- `ygwhitelist.admin` - Access to all whitelist commands (default: op)

### Configuration
The plugin is highly configurable through `config.yml`:





---

### License
MIT License

Copyright (c) 2025 YuGe

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
