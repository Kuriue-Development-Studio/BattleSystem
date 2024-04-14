package util;

import java.util.ArrayList;
import java.util.Map;

public class TriangleMath {

    private static ArrayList<Double> cos = new ArrayList<>(3600);
    private static ArrayList<Double> sin = new ArrayList<>(3600);

    static {
        for(int i = 0; i<3600; i++) {
            double a = (((double)i)*Math.PI)/1800d;
            cos.add(Math.cos(a));
            sin.add(Math.sin(a));
        }
    }

    public static double cos(double degree) {
        int a = (int) (degree*10);
        while(a<0) {
            a+=3600;
        }
        a%=3600;
        return cos.get(a);
    }

    public static double sin(double degree) {
        int a = (int) (degree*10);
        while(a<0) {
            a+=3600;
        }
        a%=3600;
        return sin.get(a);
    }

}
