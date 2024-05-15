package taewookim.event;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import taewookim.BattleSystemPlugin;
import taewookim.ItemDisplayPlugin;
import taewookim.customhitbox.AttackHitBox;
import taewookim.customhitbox.CritAttackHitBox;
import taewookim.customhitbox.DefendHitBox;
import taewookim.polygondata.AttackDisplay;
import taewookim.polygondata.AttackPolygon;
import taewookim.polygondata.DefendPolygon;
import util.HitBoxBuilder;
import util.ParryLate;

public class AttackOrDepend implements Listener {

    public static boolean isCooldown(Player p) {
        return p.getCooldown(Material.WOODEN_SWORD)>0;
    }

    public static void left(Player p) {
        if(!isCooldown(p)&&p.getItemInHand().getType().equals(Material.WOODEN_SWORD)) {
            Location loc = p.getLocation();
            HitBoxBuilder builder = new HitBoxBuilder();
            builder
                    .addPolygonDetector(AttackPolygon.getPolygon(p))
                    .setLocation(loc)
                    .setTick(6)
                    .setOwner(p).build(p.getCooldown(Material.DIAMOND_SWORD)>0? CritAttackHitBox.class: AttackHitBox.class);
            p.getWorld().playSound(loc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
            p.setCooldown(Material.WOODEN_SWORD, 20);
        }
    }

    public static void right(Player p) {
        if(!isCooldown(p)&&p.getItemInHand().getType().equals(Material.WOODEN_SWORD)) {
            Location loc = p.getLocation();
            p.setCooldown(Material.WOODEN_SWORD, 60);
            BattleSystemPlugin.plugin.addParryLate(new ParryLate(5, p) {
                @Override
                public void run() {
                    new HitBoxBuilder().addPolygonDetector(DefendPolygon.getPolygon(p))
                            .setLocation(p.getLocation())
                            .setTick(2)
                            .setOwner(p).build(DefendHitBox.class);
                }
            });
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
    public void interentity(PlayerInteractEntityEvent e) {
        right(e.getPlayer());
    }

}
