package util;

import org.bukkit.entity.Player;

public abstract class ParryLate {

    private int tick = 0;
    public final Player p;

    public ParryLate(int tick, Player p) {
        this.tick = tick;
        this.p = p;
    }

    public abstract void run();

    public boolean isEnd() {
        return tick<1;
    }

    public void update() {
        tick--;
        if(isEnd()) {
            run();
        }
    }

}
