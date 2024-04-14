package taewookim.polygondata;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import taewookim.collisiondetector.PolygonCollisionDetector;
import util.PolygonDetectorBuilder;
import util.TriangleMath;

public class AttackPolygon {

    private final static double[] polygon;

    static {
        double sqrt2 = 3d/Math.sqrt(2);
        polygon = new double[]{0,0, 3,
                            -sqrt2, 0, sqrt2,
                            -3, 0, 0,
                            0, 0, 1,
                            3, 0, 0,
                            sqrt2, 0, sqrt2
                            };
    }

    public static Vector rotate(double x, double y, double z, double yaw, double pitch, double roll) {
        double sY = TriangleMath.sin(yaw);
        double cY = TriangleMath.cos(yaw);
        double sP = TriangleMath.sin(pitch);
        double cP = TriangleMath.cos(pitch);
        double sR = TriangleMath.sin(roll);
        double cR = TriangleMath.cos(roll);
        double xcRmysR = x*cR-y*sR;
        double xsRycR = x*sR+y*cR;
        return new Vector(cY*xcRmysR-sY*(sP*xsRycR+z*cP),
                cP*(x*sR+y*cR)-z*sP,
                sY*xcRmysR+cY*(sP*xsRycR+z*cP));
    }

    public static PolygonCollisionDetector getPolygon(Player p) {
        PolygonDetectorBuilder builder = new PolygonDetectorBuilder();
        Location ploc = p.getLocation();
        double yaw = ploc.getYaw();
        double pitch = ploc.getPitch();
        Vector loc = ploc.toVector();
        for(int i = 0; i<6; i++) {
            int a = i*3;
            builder.addPoint(rotate(polygon[a], polygon[a+1], polygon[a+2], yaw, pitch, 0).add(loc));
        }
        return builder.build();
    }

}
