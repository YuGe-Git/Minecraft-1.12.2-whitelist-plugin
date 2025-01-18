package org.yuge;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * 白名单插件主类
 * 实现了JavaPlugin和Listener接口，用于处理插件功能和事件监听
 */
public final class ygwhitelistplugin extends JavaPlugin implements Listener {

    // 存储白名单玩家名称的集合
    private Set<String> whitelist;
    // 配置文件对象
    private FileConfiguration config;
    private Map<String, String> messages;

    /**
     * 加载配置文件中的所有消息
     */
    private void loadMessages() {
        messages = new HashMap<>();
        for (String key : config.getConfigurationSection("messages").getKeys(false)) {
            messages.put(key, config.getString("messages." + key));
        }
    }

    /**
     * 获取消息并替换变量
     */
    private String formatMessage(String key, String... replacements) {
        String message = messages.getOrDefault(key, "Missing message: " + key);
        // 处理换行符
        message = message.replace("\\n", "\n")
                        .replace("\\r", "\r");
        if (replacements.length > 0) {
            message = message.replace("%player%", replacements[0]);
        }
        return message;
    }

    /**
     * 插件启用时执行的方法
     */
    @Override
    public void onEnable() {
        // 保存默认配置文件
        saveDefaultConfig();
        // 获取配置文件
        config = getConfig();
        
        // 确保配置文件中有whitelist节点
        if (!config.contains("whitelist")) {
            config.set("whitelist", new ArrayList<String>());
            saveConfig();
        }
        
        // 从配置文件中读取白名单列表
        whitelist = new HashSet<>(config.getStringList("whitelist"));
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(this, this);
        
        // 加载所有消息
        loadMessages();
        
        // 设置命令别名
        String shortCmd = config.getString("settings.short-command", "wl");
        try {
            getCommand("wlist").setAliases(Arrays.asList(shortCmd));
        } catch (Exception e) {
            getLogger().warning("设置短指令失败: " + e.getMessage());
        }
        
        // 使用缓存的消息
        getLogger().info(messages.get("plugin-enabled").replaceAll("§[0-9a-fk-or]", ""));
    }

    /**
     * 插件禁用时执行的方法
     */
    @Override
    public void onDisable() {
        // 保存白名单列表到配置文件
        config.set("whitelist", new ArrayList<>(whitelist));
        // 保存配置
        saveConfig();
        // 输出插件关闭信息
        getLogger().info(messages.get("plugin-disabled").replaceAll("§[0-9a-fk-or]", ""));
    }

    /**
     * 玩家登录事件处理器
     * @param event 玩家登录事件
     */
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String playerName = event.getPlayer().getName();
        // 如果玩家是OP，直接允许登录
        if (event.getPlayer().isOp()) {
            return;
        }
        // 检查玩家是否在白名单中，如果不在则踢出
        if (!whitelist.contains(playerName)) {
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, formatMessage("kick-message", playerName));
        }
    }

    /**
     * 命令处理方法
     * @param sender 命令发送者
     * @param command 命令对象
     * @param label 命令标签
     * @param args 命令参数
     * @return 命令是否执行成功
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 检查发送者是否有OP权限
        if (!sender.isOp()) {
            sender.sendMessage(messages.get("no-permission"));
            return true;
        }

        // 处理白名单命令
        if (command.getName().equalsIgnoreCase("wlist")) {
            if (args.length < 1) {
                sender.sendMessage(messages.get("command-usage"));
                return true;
            }

            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "add":
                    if (args.length < 2) {
                        sender.sendMessage(messages.get("player-required"));
                        return true;
                    }
                    if (whitelist.contains(args[1])) {
                        sender.sendMessage(messages.get("player-already-whitelisted"));
                        return true;
                    }
                    whitelist.add(args[1]);
                    // 保存到配置文件
                    config.set("whitelist", new ArrayList<>(whitelist));
                    saveConfig();
                    sender.sendMessage(formatMessage("player-added", args[1]));
                    break;

                case "remove":
                    if (args.length < 2) {
                        sender.sendMessage(messages.get("player-required"));
                        return true;
                    }
                    if (!whitelist.contains(args[1])) {
                        sender.sendMessage(messages.get("player-not-whitelisted"));
                        return true;
                    }
                    whitelist.remove(args[1]);
                    // 保存到配置文件
                    config.set("whitelist", new ArrayList<>(whitelist));
                    saveConfig();
                    sender.sendMessage(formatMessage("player-removed", args[1]));
                    break;

                case "list":
                    // 显示当前白名单列表
                    sender.sendMessage(messages.get("list-header"));
                    if (whitelist.isEmpty()) {
                        sender.sendMessage(messages.get("list-empty"));
                    } else {
                        for (String name : whitelist) {
                            sender.sendMessage(formatMessage("list-format", name));
                        }
                    }
                    break;

                case "reload":
                    reloadConfig();
                    config = getConfig();
                    whitelist = new HashSet<>(config.getStringList("whitelist"));
                    loadMessages(); // 重新加载消息
                    sender.sendMessage(messages.get("whitelist-reloaded"));
                    break;

                default:
                    sender.sendMessage(messages.get("unknown-command"));
                    break;
            }
            return true;
        }
        return false;
    }
}
