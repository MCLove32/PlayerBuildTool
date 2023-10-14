package azuazu3939.playerbuildtool.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public Map<Block, Integer> hasBlock(Block block, Player player) {

        Map<Block, Integer> map = new HashMap<>();
        if (block == null) return null;
        for (ItemStack item: player.getInventory().getContents()) {

            if (item == null) continue;
            if (item.getType() != block.getType()) continue;
            map.put(block, item.getAmount());
        }
        return map;
    }
}
