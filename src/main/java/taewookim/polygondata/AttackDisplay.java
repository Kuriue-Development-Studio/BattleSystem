package taewookim.polygondata;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import taewookim.CustomDisplay;

public class AttackDisplay extends CustomDisplay {

    private final ItemStack i;
    private final ItemMeta m;
    private int tick = 1;

    public AttackDisplay(Location loc, double roll) {
        super(loc, null);
        rotation(roll);
        scale(6, 6, 6);
        i = new ItemStack(Material.DIAMOND_SWORD);
        m = i.getItemMeta();
        m.setCustomModelData(1);
        i.setItemMeta(m);
        setItem(i);
    }

    @Override
    public void update() {
        tick++;
        if(tick>8) {
            setEnd();
            return;
        }
        m.setCustomModelData(tick/2);
        i.setItemMeta(m);
        setItem(i);
    }
}
