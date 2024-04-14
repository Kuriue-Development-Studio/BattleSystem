package taewookim;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import taewookim.event.AttackOrDepend;

public class BattleSystemPlugin extends JavaPlugin {

    public static BattleSystemPlugin plugin;

    public void Update() {

    }

    @Override
    public void onEnable() {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new AttackOrDepend(), this);
    }
}
