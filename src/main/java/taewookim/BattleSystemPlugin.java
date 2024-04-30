package taewookim;

import net.minecraft.world.damagesource.DamageSource;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import taewookim.event.AttackOrDepend;
import util.ParryLate;

import java.util.ArrayList;
import java.util.Objects;

public class BattleSystemPlugin extends JavaPlugin {

    public static BattleSystemPlugin plugin;

    //패링에 대한 업데이트
    private final ArrayList<ParryLate> parrys = new ArrayList<>();
    private boolean isupdating = false;
    private final ArrayList<ParryLate> adding = new ArrayList<>();

    public void addParryLate(ParryLate parryLate) {
        if(isupdating) {
            adding.add(parryLate);
        }else {
            parrys.add(parryLate);
        }
    }

    public static double getDamage(LivingEntity damager) {
        return Objects.requireNonNull(damager.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).getValue();
    }

    public static void Damage(Entity damager, LivingEntity target, double damage) {
        //아래 오류가 나지만 무시(무시하고 빌드)
        Bukkit.getPluginManager().callEvent(new EntityDamageByEntityEvent(damager, target, EntityDamageEvent.DamageCause.CUSTOM, damage));
    }

    private void update() {
        BukkitRunnable brun = new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<ParryLate> removing = new ArrayList<>();
                isupdating = true;
                for(ParryLate late : parrys) {
                    late.update();
                    if(late.isEnd()) {
                        removing.add(late);
                    }
                }
                isupdating = false;
                parrys.removeAll(removing);
                parrys.addAll(adding);
                adding.clear();
            }
        };brun.runTaskTimer(this, 0, 0);
    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new AttackOrDepend(), this);
        update();
    }
}
