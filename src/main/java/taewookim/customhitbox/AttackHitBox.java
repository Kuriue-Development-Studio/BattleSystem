package taewookim.customhitbox;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import taewookim.BattleSystemPlugin;
import taewookim.hitbox.HitBox;
import taewookim.hitbox.type.AttackBox;

public class AttackHitBox extends HitBox implements AttackBox {
    public AttackHitBox(Location mainloc, LivingEntity owner) {
        super(mainloc, owner);
    }

    @Override
    protected void enterHitBox(Entity entity) {
        if(entity instanceof LivingEntity le) {
            BattleSystemPlugin.Damage(getOwner(), le, BattleSystemPlugin.getDamage(getOwner()));
        }
    }

    @Override
    protected void quitHitBox(Entity entity) {

    }

    @Override
    protected void collisionHitBox(HitBox hitBox) {
        World w = getChunk().getWorld();
        w.spawnParticle(Particle.FLAME, hitX, hitY, hitZ, 10, 0, 0, 0, 0.05);
        w.playSound(new Location(w, hitX, hitY, hitZ), Sound.BLOCK_ANVIL_PLACE, 1, 2);
        setEnd();
    }

    @Override
    public void update() {
        super.update();
        //summonParticle();
    }
}
