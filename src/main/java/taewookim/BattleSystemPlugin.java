package taewookim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import taewookim.event.AttackOrDepend;
import util.ParryLate;

import java.util.ArrayList;

public class BattleSystemPlugin extends JavaPlugin {

    public static BattleSystemPlugin plugin;

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
