package taewookim.event;

import net.minecraft.network.protocol.game.PacketPlayInUseEntity;
import net.minecraft.world.EnumHand;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.entity.Player;

public class C implements PacketPlayInUseEntity.c {

    private final Player p;
    private boolean b = false;

    public C(Player p) {
        this.p = p;
    }

    public boolean isB() {
        return b;
    }

    @Override
    public void a(EnumHand enumHand) {
    }

    @Override
    public void a(EnumHand enumHand, Vec3D vec3D) {
    }

    @Override
    public void a() {
        AttackOrDepend.left(p);
        b = true;
    }
}
