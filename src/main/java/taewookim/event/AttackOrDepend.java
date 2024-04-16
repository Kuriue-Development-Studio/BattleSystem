package taewookim.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import taewookim.ItemDisplayPlugin;
import taewookim.customhitbox.AttackHitBox;
import taewookim.customhitbox.DefendHitBox;
import taewookim.polygondata.AttackDisplay;
import taewookim.polygondata.AttackPolygon;
import taewookim.polygondata.DefendPolygon;
import util.HitBoxBuilder;

public class AttackOrDepend implements Listener {

    public static boolean isCooldown(Player p) {
        return p.getCooldown(Material.WOODEN_SWORD)>0;
    }

    public static void left(Player p) {
        if(!isCooldown(p)&&p.getItemInHand()!=null&&p.getItemInHand().getType().equals(Material.WOODEN_SWORD)) {
            Location loc = p.getLocation();
            if(p.getCooldown(Material.DIAMOND_SWORD)>0) {
                p.setVelocity(loc.getDirection().setY(0).multiply(2));
                p.getWorld().playSound(loc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 0);
                HitBoxBuilder builder = new HitBoxBuilder();
                builder
                        .addPolygonDetector(AttackPolygon.getPolygon(p))
                        .setLocation(loc.add(loc.getDirection().multiply(2)))
                        .setTick(9)
                        .setOwner(p).build(DefendHitBox.class);
                return;
            }
            HitBoxBuilder builder = new HitBoxBuilder();
            builder
                    .addPolygonDetector(AttackPolygon.getPolygon(p))
                    .setLocation(loc)
                    .setTick(9)
                    .setOwner(p).build(AttackHitBox.class);
            p.getWorld().playSound(loc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
            p.setCooldown(Material.WOODEN_SWORD, 20);
        }
    }

    public static void right(Player p) {
        if(!isCooldown(p)) {
            Location loc = p.getLocation();
            p.setCooldown(Material.WOODEN_SWORD, 60);
            p.getWorld().playSound(loc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
            new HitBoxBuilder().addPolygonDetector(DefendPolygon.getPolygon(p))
                    .setLocation(p.getLocation())
                    .setTick(4)
                    .setOwner(p).build(DefendHitBox.class);
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
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void interentity(PlayerInteractEntityEvent e) {
        right(e.getPlayer());
    }

}
