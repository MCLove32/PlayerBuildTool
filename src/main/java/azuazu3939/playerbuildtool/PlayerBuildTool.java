package azuazu3939.playerbuildtool;

import azuazu3939.playerbuildtool.command.BuildItemCommand;
import azuazu3939.playerbuildtool.command.BuildToolsReloadCommand;
import azuazu3939.playerbuildtool.listener.AcitonListener;
import azuazu3939.playerbuildtool.listener.InventoryListener;
import azuazu3939.playerbuildtool.utils.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PlayerBuildTool extends JavaPlugin {

    private static PlayerBuildTool tool;
    public PlayerBuildTool() {tool = this;}
    public static PlayerBuildTool get() {return tool;}

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        saveConfig();
        Objects.requireNonNull(getCommand("BuildTool")).setExecutor(new BuildItemCommand());
        getCommand("BuildToolsReload").setExecutor(new BuildToolsReloadCommand(this));
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new AcitonListener(), this);
        pm.registerEvents(new InventoryListener(), this);

        for (Player player: Bukkit.getOnlinePlayers()) {
            InventoryListener.create(player);
        }
        new WorldUtil().getWorlds();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
