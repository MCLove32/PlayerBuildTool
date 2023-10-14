package azuazu3939.playerbuildtool.listener;

import azuazu3939.playerbuildtool.BuildInventory;
import azuazu3939.playerbuildtool.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static azuazu3939.playerbuildtool.BuildInventory.setBackFrame;
import static azuazu3939.playerbuildtool.BuildInventory.setFrameInv;
import static azuazu3939.playerbuildtool.utils.BuildItem.*;
import static azuazu3939.playerbuildtool.utils.WorldUtil.worldList;

public class InventoryListener implements Listener {

    public static final Map<Player, Inventory> inv = new HashMap<>();
    public static final Map<Player, Integer> line = new HashMap<>();
    public static final Map<Player, Integer> cube = new HashMap<>();
    private static final Map<Player, Integer> limitLine = new HashMap<>();
    private static final Map<Player, Integer> limitCube = new HashMap<>();

    @EventHandler
    public void onClose(@NotNull InventoryCloseEvent e) {

        if (!(e.getInventory().getHolder() instanceof BuildInventory)) return;

        Player p = (Player) e.getPlayer();
        if (!inv.containsKey(p)) create(p);
        inv.put(p, e.getInventory());
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {

        Player p = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof BuildInventory &&
                worldList.stream().anyMatch(Predicate.isEqual(p.getWorld()))) {

            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            if (item != null && item.getType().equals(Material.OAK_SIGN)) {

                int i = new ItemUtils().getAdminItem(item);

                if (i == 1) {

                    new ItemUtils().setAdminItem(item, 2);
                    setLore(item, 2, p);

                } else if (i == 2) {

                    new ItemUtils().setAdminItem(item, 1);
                    setLore(item, 1, p);
                }

            } else if (item != null && item.getType().equals(Material.OAK_LOG)) {

                int count = item.getAmount();
                if (count >= limitLine.get(p)) item.setAmount(1);
                else item.setAmount(count + 1);

                line.put(p, count);
                p.getWorld().playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);

            } else if (item != null && item.getType().equals(Material.SPRUCE_LOG)) {

                int count = item.getAmount();
                if (count >= limitCube.get(p)) item.setAmount(1);
                else item.setAmount(count + 1);

                cube.put(p, count);
                p.getWorld().playSound(p, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
            }
        }
    }

    public void setLore(@NotNull ItemStack item, int i, HumanEntity player) {

        ItemMeta meta = item.getItemMeta();
        meta.lore().clear();
        item.setItemMeta(meta);
        List<Component> list = new ArrayList<>();
        if (i == 1) list.add(0, Component.text("平面型設置にする", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        if (i == 2) list.add(0, Component.text("ライン型設置にする", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false));
        meta.lore(list);
        item.setItemMeta(meta);
        player.getWorld().playSound(player, Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
    }

    @EventHandler
    public void onDrag(@NotNull InventoryDragEvent event) {

        if (event.getInventory().getHolder() instanceof BuildInventory) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreate(@NotNull PlayerJoinEvent event) {

        Player p = event.getPlayer();
        create(p);
    }

    public static void create(Player player) {

        Inventory getInv = Bukkit.createInventory(new BuildInventory(), 54, Component.text("個別編集", NamedTextColor.WHITE));

        setBackFrame(getInv);
        setFrameInv(getInv);

        getInv.setItem(10, getItemGenre1());
        getInv.setItem(12, getLine());
        getInv.setItem(14, getSqu());

        inv.put(player, getInv);
        limitLine.put(player, 64);
        limitCube.put(player, 64);
    }
}
