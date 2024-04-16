package taewookim.customhitbox;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import taewookim.hitbox.HitBox;
import taewookim.hitbox.type.AttackBox;
import util.RandomUtil;

import java.util.Random;

public class CritAttackHitBox extends HitBox implements AttackBox {

    public CritAttackHitBox(Location mainloc, LivingEntity owner) {
        super(mainloc, owner);
    }

    public static void spawnCitParticle(Location loc) {
        Random r = RandomUtil.random;
        for(int i = 0; i<10; i++) {
            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 0, r.nextFloat()-0.5, r.nextFloat()-0.5, r.nextFloat()-0.5, 1, new Particle.DustOptions(Color.RED, 1));
            loc.getWorld().spawnParticle(Particle.CRIT, loc, 0, r.nextFloat()-0.5, r.nextFloat()-0.5, r.nextFloat()-0.5, 1);
        }
    }

    @Override
    protected void enterHitBox(Entity entity) {
        if(entity instanceof LivingEntity le) {
            le.damage(10);
            World w = le.getWorld();
            Location loc = entity.getLocation().add(0, 1.5, 0);
            w.playSound(loc, Sound.ENTITY_ZOMBIE_VILLAGER_CURE, 1, 2);
            spawnCitParticle(loc);
        }
    }

    @Override
    protected void quitHitBox(Entity entity) {

    }

    @Override
    protected void collisionHitBox(HitBox hitBox) {

    }
}
