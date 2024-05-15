package taewookim.event;

import io.netty.channel.*;
import jdk.jfr.Enabled;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;

public class PacketReader implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent e) {
        ChannelDuplexHandler handler = new ChannelDuplexHandler() {
            @Override
            public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
                System.out.println(packet.getClass().getName());
                super.channelRead(channelHandlerContext, packet);
            }
        };
        PlayerConnection pc = ((CraftPlayer) e.getPlayer()).getHandle().c;
        try{
            NetworkManager manager = (NetworkManager) pc.getClass().getField("c").get(pc);
            ChannelPipeline pipeline = manager.n.pipeline();
            pipeline.addBefore("packet_handler", e.getPlayer().getName(), handler);
        }catch(Exception ea) {
            ea.printStackTrace();
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        PlayerConnection pc = ((CraftPlayer) e.getPlayer()).getHandle().c;
        try{
            NetworkManager manager = (NetworkManager) pc.getClass().getField("c").get(pc);
            Channel channel = manager.n;
            channel.eventLoop().submit(()->{
                channel.pipeline().remove(e.getPlayer().getName());
                return null;
            });
        }catch(Exception ea) {
            ea.printStackTrace();
        }
    }

}
