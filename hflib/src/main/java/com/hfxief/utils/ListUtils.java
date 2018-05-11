package com.hfxief.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fan
 * @Title: ListUtils
 * @Package com.xingfuhuaxia.app.util
 * @Description: List工具类
 * @date 2016/10/9 16:51
 */
public class ListUtils {


    public static <T> boolean isEmpty(List<T> mList) {
        if (mList != null && mList.size() > 0) {
            return false;
        } else {
            return true;
        }

    }

    public static <T> int getListSize(List<T> mList) {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
    }

    public static Object[] List2Array(List<Object> oList) {
        Object[] oArray = oList.toArray(new Object[]{});
        return oArray;
    }

    public static String[] set2Array(Set<String> oSet) {
        String[] oArray = oSet.toArray(new String[]{});
        return oArray;
    }

    public static <T extends Object> List<T> Set2List(Set<T> oSet) {
        List<T> tList = new ArrayList<T>(oSet);
        return tList;
    }

    public static <T extends Object> List<T> Array2List(T[] tArray) {
        List<T> tList = Arrays.asList(tArray);
        // 因为Arrays$ArrayList和java.util.ArrayList一样，都是继承AbstractList，
        // 但是Arrays$ArrayList没有override这些方法，而java.util.ArrayList实现了。
        // List<T> tList = new ArrayList<T>(Arrays.asList(tArray));
        return tList;
    }

    public static <T extends Object> Set<T> List2Set(List<T> tList) {
        Set<T> tSet = new HashSet<T>(tList);
        return tSet;
    }

    public static <T extends Object> Set<T> Array2Set(T[] tArray) {
        Set<T> tSet = new HashSet<T>(Arrays.asList(tArray));
        return tSet;
    }
}
