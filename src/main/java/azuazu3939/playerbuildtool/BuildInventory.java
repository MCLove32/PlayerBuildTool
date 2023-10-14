package azuazu3939.playerbuildtool;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import static azuazu3939.playerbuildtool.utils.BuildItem.getBack;
import static azuazu3939.playerbuildtool.utils.BuildItem.getFrame;

public class BuildInventory implements InventoryHolder {
    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }

    public static void setFrameInv(Inventory inv) {

        for (int i = 0; i <= 9; i++) {
            inv.setItem(i, getFrame());
        }
        inv.setItem(17, getFrame());
        inv.setItem(18, getFrame());
        inv.setItem(26, getFrame());
        inv.setItem(27, getFrame());
        inv.setItem(35, getFrame());
        inv.setItem(36, getFrame());
        for (int i = 44; i <= 53; i++) {
            inv.setItem(i, getFrame());
        }
    }

    public static void setBackFrame(Inventory inv) {

        for (int i = 0; i <= 53; i++) {
            inv.setItem(i, getBack());
        }
    }
}
