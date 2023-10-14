package azuazu3939.playerbuildtool.utils;

import azuazu3939.playerbuildtool.PlayerBuildTool;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WorldUtil {

    public static final List<World> worldList = new ArrayList<>();

    public void getWorlds() {

        for (String s : PlayerBuildTool.get().getConfig().getStringList("tools-enable-worlds")) {
            World w = Bukkit.getWorld(s);
            if (w == null) continue;

            worldList.add(w);
        }
    }
}
