package taewookim.polygondata;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import taewookim.CustomDisplay;

public class DefendDisplay extends CustomDisplay {

    private int tick = 0;

    public DefendDisplay(Location loc) {
        super(loc, new ItemStack(Material.DIAMOND_SWORD));
        rotation(90);
        scale(3, 3, 3);
    }

    @Override
    public void update() {
        tick++;
        if(tick>2) {
            setEnd();
        }
    }
}
