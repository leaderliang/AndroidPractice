package com.android.ballot;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * TODO
 *
 * @author dev.liang <a href="mailto:dev.liang@outlook.com">Contact me.</a>
 * @version 1.0.0
 * @since 2020/12/03 23:16
 */
public class CollectionUtils {

    public static boolean isEmpty(java.util.Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static String join(java.util.List<?> list, String separator) {
        StringBuilder sb = new StringBuilder();

        for (Object obj : list) {
            sb.append(obj.toString()).append(separator);
        }

        return sb.substring(0, sb.length() - 1);
    }

    public static <E> ArrayList<E> asList(E... e) {
        return new ArrayList<>(Arrays.asList(e));
    }
}
