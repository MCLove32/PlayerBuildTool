package azuazu3939.playerbuildtool.listener;

import azuazu3939.playerbuildtool.PlayerBuildTool;
import azuazu3939.playerbuildtool.utils.ItemUtils;
import azuazu3939.playerbuildtool.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static azuazu3939.playerbuildtool.listener.InventoryListener.*;
import static azuazu3939.playerbuildtool.utils.WorldUtil.worldList;

public class AcitonListener implements Listener {

    private final Set<Player> ct = new HashSet<>();
    @EventHandler
    public void onInteract(@NotNull PlayerInteractEvent event) {

        Action action = event.getAction();
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if (item == null) return;
        if (!new ItemUtils().isAdminItem(item)) return;
        if (worldList.stream().noneMatch(Predicate.isEqual(player.getWorld()))) return;

        event.setCancelled(true);

        if ((action.equals(Action.LEFT_CLICK_AIR) ||
                action.equals(Action.LEFT_CLICK_BLOCK)) &&
                player.isSneaking()) {

            if (!inv.containsKey(player)) create(player);
            player.closeInventory();
            player.openInventory(inv.get(player));

        } else if (action.equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null && !isCt(player)) {

            Block block = event.getClickedBlock();
            Block front = block.getRelative(event.getBlockFace());

            Map<Block, Integer> map = new Utils().hasBlock(block, player);
            if (map.isEmpty() || !map.containsKey(block)) return;

            BlockState state = block.getState();
            state.setBlockData(block.getBlockData());

            int i = new ItemUtils().getAdminItem(inv.get(player).getItem(10));
            if (i == 1) setLoopBlock(player, block, event.getBlockFace(), front, item, state);
            if (i == 2) setLoopBlock(player, block, state, front, item, event.getBlockFace());
        }
    }

    public void setCt(Player player) {

        if (!ct.contains(player)) {
            ct.add(player);

            Bukkit.getScheduler().runTaskLaterAsynchronously(PlayerBuildTool.get(), () -> {
                ct.remove(player);
                player.getWorld().playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);

            }, PlayerBuildTool.get().getConfig().getInt("Place-Cooldown"));
        }
    }

    public boolean isCt(Player player) {

        if (ct.isEmpty()) return false;
        return ct.contains(player);
    }

    public void remove(@NotNull Player player, Material material, int count) {

        int check;
        for (ItemStack stack : player.getInventory().getContents()) {
            if (stack == null) continue;
            if (stack.getType() != material) continue;

            check = stack.getAmount();
            if (check < count) {
                stack.subtract(check);
                count =- check;
            } else {
                stack.subtract(count);
                count = 0;
            }
        }
    }

    public boolean hasRemoveItem(@NotNull Player player, Material material) {

        for (ItemStack stack: player.getInventory().getContents()) {
            if (stack == null) continue;
            if (stack.getType() != material) continue;
            return false;
        }
        return true;
    }

    public void setLoopBlock(Player player, @NotNull Block block, BlockFace face, Block replaced, ItemStack item, BlockState state) {

        //blockのマテリアルとcheck
        if (hasRemoveItem(player, block.getType())) return;
        //Ct設置
        setCt(player);
        //処理
        for (int i = 1; i <= line.get(player); i++) {

            if (hasRemoveItem(player, block.getType())) return;
            if (!replaced.getType().isAir() || !block.getType().isSolid()) return;

            replaced.setType(block.getType());
            if (new BlockPlaceEvent(replaced, state, block, item, player, true, EquipmentSlot.HAND).callEvent()) {
                remove(player, block.getType(), 1);
            } else {
                replaced.setType(Material.AIR);
            }
            block = replaced;
            replaced = replaced.getRelative(face);
        }
    }

    public void setLoopBlock(Player player, @NotNull Block block, BlockState state, Block replaced, ItemStack item, BlockFace face) {

        //blockのマテリアルとcheck
        if (hasRemoveItem(player, block.getType())) return;
        //Ct設置
        setCt(player);
        //処理

        int count =1;
        if (face.equals(BlockFace.NORTH)) count = 1;
        if (face.equals(BlockFace.EAST)) count = 2;
        if (face.equals(BlockFace.SOUTH)) count = 3;
        if (face.equals(BlockFace.WEST)) count = 4;
        if (face.equals(BlockFace.UP)) count = 5;
        if (face.equals(BlockFace.DOWN)) count = 6;

        for (int i = 1; i <= cube.get(player); i++) {

            if (block == null) continue;
            if (hasRemoveItem(player, block.getType())) {
                block = setting(block, i, count);
                if (block == null) continue;
                replaced = block.getRelative(face);
                continue;
            }
            if (!replaced.getType().isAir() || !block.getType().isSolid()) {
                block = setting(block, i, count);
                if (block == null) continue;
                replaced = block.getRelative(face);
                continue;
            }
            replaced.setType(block.getType());
            if (new BlockPlaceEvent(replaced, state, block, item, player, true, EquipmentSlot.HAND).callEvent()) {
                remove(player, block.getType(), 1);
            } else {
                replaced.setType(Material.AIR);
            }
            block = setting(block, i, count);
            if (block == null) continue;
            replaced = block.getRelative(face);
        }
    }

    @Nullable
    public Block setting(Block block, int i, int face) {

        BlockFace right;
        BlockFace left;
        BlockFace up;
        BlockFace down;
        if (face == 1) {
            right = BlockFace.WEST;
            left = BlockFace.EAST;
            up = BlockFace.UP;
            down = BlockFace.DOWN;
        } else if(face == 2) {
            right = BlockFace.NORTH;
            left = BlockFace.SOUTH;
            up = BlockFace.UP;
            down = BlockFace.DOWN;
        } else if (face == 3) {
            right = BlockFace.EAST;
            left = BlockFace.WEST;
            up = BlockFace.UP;
            down = BlockFace.DOWN;
        } else if (face == 4) {
            right = BlockFace.SOUTH;
            left = BlockFace.NORTH;
            up = BlockFace.UP;
            down = BlockFace.DOWN;
        } else if (face == 5) {
            right = BlockFace.EAST;
            left = BlockFace.WEST;
            up = BlockFace.NORTH;
            down = BlockFace.SOUTH;
        } else {
            right = BlockFace.WEST;
            left = BlockFace.EAST;
            up = BlockFace.SOUTH;
            down = BlockFace.NORTH;
        }
        switch (i) {
            case 1, 7, 8, 9, 21, 22, 23, 24 -> {
                return block.getRelative(up);
            }
            case 2, 10, 11, 12 -> {
                return block.getRelative(right);
            }
            case 3, 4, 13, 14, 15, 16 -> {
                return block.getRelative(down);
            }
            case 5, 6, 17, 18, 19, 20 -> {
                return block.getRelative(left);
            }
        }
        return null;
    }
}
