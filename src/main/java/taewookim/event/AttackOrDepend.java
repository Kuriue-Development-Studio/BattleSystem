package taewookim.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import taewookim.customhitbox.AttackHitBox;
import taewookim.polygondata.AttackPolygon;
import util.HitBoxBuilder;

public class AttackOrDepend implements Listener {

    public static boolean isCooldown(Player p) {
        return p.getCooldown(Material.WOODEN_SWORD)>0;
    }

    public static void left(Player p) {
        if(!isCooldown(p)&&p.getItemInHand()!=null&&p.getItemInHand().getType().equals(Material.WOODEN_SWORD)) {
            HitBoxBuilder builder = new HitBoxBuilder();
            builder
                    .addPolygonDetector(AttackPolygon.getPolygon(p))
                    .setLocation(p.getLocation())
                    .setTick(10)
                    .setOwner(p).build(AttackHitBox.class);
            p.setCooldown(Material.WOODEN_SWORD, 20);
        }
    }

    public static void right(Player p) {
        if(!isCooldown(p)) {

        }
    }

    @EventHandler
    public void inter(PlayerInteractEvent e) {
        switch(e.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                left(e.getPlayer());
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                right(e.getPlayer());
                break;
        }
    }

    @EventHandler
    public void damage(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player p) {
            left(p);
        }
    }

    @EventHandler
    public void interentity(PlayerInteractEntityEvent e) {
        right(e.getPlayer());
    }

}
