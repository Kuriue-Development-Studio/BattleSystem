package taewookim.customhitbox;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import taewookim.hitbox.HitBox;

public class AttackHitBox extends HitBox {
    public AttackHitBox(Location mainloc, LivingEntity owner) {
        super(mainloc, owner);
    }

    @Override
    protected void enterHitBox(Entity entity) {
        if(entity instanceof LivingEntity le) {
            le.damage(2);
        }
    }

    @Override
    protected void quitHitBox(Entity entity) {

    }

    @Override
    protected void collisionHitBox(HitBox hitBox) {
        getChunk().getWorld().spawnParticle(Particle.FLAME, hitX, hitY, hitZ, 10, 0, 0, 0, 0.05);
    }
}
