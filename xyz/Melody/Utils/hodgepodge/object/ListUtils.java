/*
 * Decompiled with CFR 0.152.
 */
package xyz.Melody.Utils.hodgepodge.object;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ListUtils {
    public static List emptyList() {
        return Collections.emptyList();
    }

    public static List search(List list, ListSearchBooleanFunction listSearchBooleanFunction) {
        return list.stream().filter(listSearchBooleanFunction::function).collect(Collectors.toList());
    }

    public static Object firstItem(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static Object lastItem(List list) {
        if (list.size() <= 0) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    public static boolean isFirstItem(List list, Object object) {
        return list.indexOf(object) == 0;
    }

    public static Object pollFirst(List list) {
        Object object = ListUtils.firstItem(list);
        if (object != null) {
            list.remove(object);
            return object;
        }
        return null;
    }

    public static Object pollLast(List list) {
        Object object = ListUtils.lastItem(list);
        if (object != null) {
            list.remove(object);
            return object;
        }
        return null;
    }

    public static String asString(List list) {
        if (list == null || list.isEmpty()) {
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder("[");
        for (Object e : list) {
            stringBuilder.append(e);
            if (list != e) continue;
            stringBuilder.append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static void addArrayToList(List list, Object ... objectArray) {
        Collections.addAll(list, objectArray);
    }

    public static interface ListSearchBooleanFunction {
        public boolean function(Object var1);
    }
}

