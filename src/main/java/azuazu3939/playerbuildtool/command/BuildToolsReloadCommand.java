package azuazu3939.playerbuildtool.command;

import azuazu3939.playerbuildtool.PlayerBuildTool;
import azuazu3939.playerbuildtool.utils.WorldUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildToolsReloadCommand implements CommandExecutor {

    private final PlayerBuildTool tool;
    public BuildToolsReloadCommand(PlayerBuildTool tool) {
        this.tool = tool;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player p) {

            if (!p.hasPermission("PlayerBuildTool.command.BuildToolsReload")) {
                p.sendMessage(Component.text("権限なし", NamedTextColor.RED));

            } else {
                tool.reloadConfig();
                new WorldUtil().getWorlds();
                p.sendMessage(Component.text("コンフィグをリロードしました。", NamedTextColor.GREEN));
            }

        } else {
            tool.reloadConfig();
            new WorldUtil().getWorlds();
            commandSender.sendMessage(Component.text("コンフィグをリロードしました。", NamedTextColor.GREEN));
        }
        return true;
    }
}
