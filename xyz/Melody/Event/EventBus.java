/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Event;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import xyz.Melody.Event.Event;
import xyz.Melody.Event.EventHandler;
import xyz.Melody.Event.events.Player.EventPostUpdate;
import xyz.Melody.Event.events.Player.EventPreUpdate;
import xyz.Melody.Event.events.world.EventTick;
import xyz.Melody.Utils.Helper;
import xyz.Melody.module.Module;

public class EventBus {
    private ConcurrentHashMap registry = new ConcurrentHashMap();
    private final Comparator comparator = EventBus::lambda$new$0;
    private final MethodHandles.Lookup lookup = MethodHandles.lookup();
    private static final EventBus instance = new EventBus();

    public static EventBus getInstance() {
        return instance;
    }

    public void register(Object ... objectArray) {
        for (Object object : objectArray) {
            for (Method method : object.getClass().getDeclaredMethods()) {
                if (method.getParameterCount() != 1 || !method.isAnnotationPresent(EventHandler.class)) continue;
                Class<?> clazz = method.getParameterTypes()[0];
                if (!this.registry.containsKey(clazz)) {
                    this.registry.put(clazz, new CopyOnWriteArrayList());
                }
                ((List)this.registry.get(clazz)).add(new Handler(this, method, object, method.getDeclaredAnnotation(EventHandler.class).priority()));
                ((List)this.registry.get(clazz)).sort(this.comparator);
            }
        }
    }

    public void unregister(Object ... objectArray) {
        for (Object object : objectArray) {
            for (List list : this.registry.values()) {
                for (Handler handler : list) {
                    if (Handler.access$000(handler) != object) continue;
                    list.remove(handler);
                }
            }
        }
    }

    public Event call(Event event) {
        boolean bl = event instanceof EventTick || event instanceof EventPreUpdate || event instanceof EventPostUpdate;
        List list = (List)this.registry.get(event.getClass());
        if (list != null && !list.isEmpty()) {
            for (Handler handler : list) {
                try {
                    if (list instanceof Module) {
                        if (((Module)((Object)list)).isEnabled()) {
                            if (bl) {
                                Helper.mc.field_71424_I.func_76320_a(((Module)((Object)list)).getName());
                            }
                            if (bl) {
                                Helper.mc.field_71424_I.func_76319_b();
                            }
                        }
                    } else {
                        if (bl) {
                            Helper.mc.field_71424_I.func_76320_a("non module");
                        }
                        if (bl) {
                            Helper.mc.field_71424_I.func_76319_b();
                        }
                    }
                    Handler.access$100(handler).invokeExact(Handler.access$000(handler), event);
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return event;
    }

    private static int lambda$new$0(Handler handler, Handler handler2) {
        return Byte.compare(Handler.access$300(handler), Handler.access$300(handler2));
    }

    static MethodHandles.Lookup access$200(EventBus eventBus) {
        return eventBus.lookup;
    }

    private class Handler {
        private MethodHandle handler;
        private Object parent;
        private byte priority;
        final EventBus this$0;

        public Handler(EventBus eventBus, Method method, Object object, byte by) {
            this.this$0 = eventBus;
            if (!method.isAccessible()) {
                method.setAccessible(true);
            }
            MethodHandle methodHandle = null;
            try {
                methodHandle = EventBus.access$200(eventBus).unreflect(method);
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            if (methodHandle != null) {
                this.handler = methodHandle.asType(methodHandle.type().changeParameterType(0, Object.class).changeParameterType(1, Event.class));
            }
            this.parent = object;
            this.priority = by;
        }

        static Object access$000(Handler handler) {
            return handler.parent;
        }

        static MethodHandle access$100(Handler handler) {
            return handler.handler;
        }

        static byte access$300(Handler handler) {
            return handler.priority;
        }
    }
}

