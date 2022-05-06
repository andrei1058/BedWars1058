package com.andrei1058.bedwars.support.version.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.MappedRegistry;
import net.minecraft.world.entity.EntityType;

import java.lang.reflect.Field;
import java.util.IdentityHashMap;

public class Registry {

    @SuppressWarnings({"JavaReflectionMemberAccess"})
    public static boolean isFrozen() throws IllegalAccessException, NoSuchFieldException {
        Field frozenField = MappedRegistry.class.getDeclaredField("bL");
        frozenField.setAccessible(true);
        return (boolean) frozenField.get(net.minecraft.core.Registry.ENTITY_TYPE);
    }

    @SuppressWarnings({"JavaReflectionMemberAccess"})
    public static void setFrozen(boolean frozen) throws NoSuchFieldException, IllegalAccessException {
        if (!frozen) {
            Field intrusiveHolderCache = MappedRegistry.class.getDeclaredField("bN");
            intrusiveHolderCache.setAccessible(true);
            intrusiveHolderCache.set(net.minecraft.core.Registry.ENTITY_TYPE, new IdentityHashMap<EntityType<?>, Holder.Reference<EntityType<?>>>());
        }

        Field frozenField = MappedRegistry.class.getDeclaredField("bL");
        frozenField.setAccessible(true);
        frozenField.set(net.minecraft.core.Registry.ENTITY_TYPE, frozen);
    }

    private Registry() {}

}
