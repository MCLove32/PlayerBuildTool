package azuazu3939.playerbuildtool.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemUtils {

    public boolean isAdminItem(ItemStack item) {
        if (item == null || !item.hasItemMeta() || item.getItemMeta() == null) return false;
        return item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER);
    }

    public int getAdminItem(ItemStack item) {

        if (isAdminItem(item)) {
            return Integer.parseInt(String.valueOf(item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER)));

        }
        return 0;
    }

    public void setAdminItem(ItemStack item, int i) {

        if (isAdminItem(item)) {
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(NamespacedKey.minecraft("admin_item"), PersistentDataType.INTEGER, i);
            item.setItemMeta(meta);
        }
    }
}
