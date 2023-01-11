package com.andrei1058.bedwars.api.util;

/**
 * @author 77mod
 * This class is made to convert simple config numbers to primitve types.
 * There is a NumberConversions class in the bukkit api, but it deeply searches for the number,
 * which is useless for config objects.
 */
public class PrimitiveUtil {
    public static int toInt(Object number){
        return number instanceof Number ? ((Number) number).intValue() : 0;
    }
    public static long toLong(Object number){
        return number instanceof Number? ((Number)number).longValue() : 0;
    }
    public static float toFloat(Object number){
        return number instanceof Number? ((Number)number).floatValue() : 0;
    }
    public static short toShort(Object number){
        return number instanceof Number? ((Number)number).shortValue() : 0;
    }
}
