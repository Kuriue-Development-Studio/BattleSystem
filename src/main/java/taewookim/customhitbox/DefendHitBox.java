package taewookim.customhitbox;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import taewookim.hitbox.HitBox;
import taewookim.hitbox.type.AttackBox;
import taewookim.hitbox.type.DefendBox;

public class DefendHitBox extends HitBox implements DefendBox {

    Player p;

    public DefendHitBox(Location mainloc, LivingEntity owner) {
        super(mainloc, owner);
        if(owner instanceof Player p) {
            this.p = p;
        }else {
            setEnd();
        }
    }

    @Override
    protected void enterHitBox(Entity entity) {

    }

    @Override
    protected void quitHitBox(Entity entity) {

    }

    @Override
    protected void collisionHitBox(HitBox hitBox) {
        if(hitBox instanceof AttackBox) {
            setEnd();
            World w = getChunk().getWorld();
            w.spawnParticle(Particle.SOUL_FIRE_FLAME, hitX, hitY, hitZ, 10, 0, 0, 0, 0.05);
            //w.playSound(new Location(w, hitX, hitY, hitZ), Sound.BLOCK_ANVIL_PLACE, 1, 2);
            p.setCooldown(Material.WOODEN_SWORD, 0);
            p.setCooldown(Material.DIAMOND_SWORD, 40);
        }
    }

    @Override
    public void update() {
        super.update();
    }
}
