package azuazu3939.playerbuildtool.command;

import azuazu3939.playerbuildtool.utils.BuildItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPlayer;

public class BuildItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try {

            if (commandSender instanceof Player player) {

                if (!player.hasPermission("PlayerBuildTool.command.BuildTool")) {
                    player.sendMessage(Component.text("権限がありません。", NamedTextColor.RED));

                } else if (strings.length == 1) {
                    get(player, strings);

                }  else {
                    player.sendMessage(Component.text("Builder Toolを手に入れました。", NamedTextColor.GREEN));
                    player.getInventory().addItem(new BuildItem().getItemStack());
                }
            } else {

                get(commandSender, strings);
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            commandSender.sendMessage(Component.text("エラーが発生しました。", NamedTextColor.RED));
            return true;
        }
    }

    public void get(CommandSender sender, String @NotNull [] strings) {

        String playerName = strings[0];
        Player p = getPlayer(playerName);
        if (p == null) {
            sender.sendMessage(Component.text("そのプレイヤーはオフラインです。", NamedTextColor.RED));
            return;
        }

        p.getInventory().addItem(new BuildItem().getItemStack());
        p.sendMessage(Component.text("Builder Toolを手に入れました。", NamedTextColor.GREEN));
    }
}
