package azuazu3939.playerbuildtool.utils;

import azuazu3939.playerbuildtool.PlayerBuildTool;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BuildItem {

    public ItemStack getItemStack() {

        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        meta.displayName((LegacyComponentSerializer.legacyAmpersand().deserialize("&e&lBuild&f&l-&b&lTool")).decoration(TextDecoration.ITALIC, false));

        List<Component> list = new ArrayList<>();
        int i = 0;
        for (String s: PlayerBuildTool.get().getConfig().getStringList("BuildItemLore")) {
            list.add(i, LegacyComponentSerializer.legacyAmpersand().deserialize(s).decoration(TextDecoration.ITALIC, false));
            i++;
        }
        meta.lore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getFrame() {

        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize("&f").decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getBack() {

        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize("&f").decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        item.setItemMeta(meta);

        return item;

    }

    public static @NotNull ItemStack getItemGenre1() {

        ItemStack item = new ItemStack(Material.OAK_SIGN);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize("&b&l建築モード").decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, 1);
        List<Component> list = new ArrayList<>();
        list.add(0, LegacyComponentSerializer.legacyAmpersand().deserialize("&fライン状設置にする").decoration(TextDecoration.ITALIC, false));

        meta.lore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getLine() {

        ItemStack item = new ItemStack(Material.OAK_LOG, 8);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize("ライン型設置の設置数").decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item_line"), PersistentDataType.STRING, "true");
        List<Component> list = new ArrayList<>();
        list.add(0, LegacyComponentSerializer.legacyAmpersand().deserialize("&fクリックで編集").decoration(TextDecoration.ITALIC, false));

        meta.lore(list);
        item.setItemMeta(meta);

        return item;
    }

    public static @NotNull ItemStack getSqu() {

        ItemStack item = new ItemStack(Material.SPRUCE_LOG, 9);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(LegacyComponentSerializer.legacyAmpersand().deserialize("平面型設置の設置数").decoration(TextDecoration.ITALIC, false));
        meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item_line"), PersistentDataType.STRING, "true");
        List<Component> list = new ArrayList<>();
        list.add(0, LegacyComponentSerializer.legacyAmpersand().deserialize("&fクリックで編集").decoration(TextDecoration.ITALIC, false));

        meta.lore(list);
        item.setItemMeta(meta);

        return item;
    }
}
