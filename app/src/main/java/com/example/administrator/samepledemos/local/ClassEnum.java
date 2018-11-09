package com.example.administrator.samepledemos.local;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * SampleAndroidProject
 * com.app.sampleandroidproject.ui.Enum
 *
 * @Author: xie
 * @Time: 2016/11/24 10:35
 * @Description:
 */


public class ClassEnum {
    private static List<Pair<String, String>> names = new ArrayList<>();

    public static void addClass(Class clas) {
        Pair<String,String> pair = new Pair<>(clas.getSimpleName(),clas.getName());
        names.add(pair);
    }

    public static List<String> getNames() {
        List<String> list = new ArrayList<>(names.size());
        for (Pair<String,String> pair : names) {
            list.add(pair.first);
        }
        return list;
    }

    public static String getName(int position) {
        if (position < names.size())
            return names.get(position).second;
        else
            return null;
    }

    public static void recycleDatas() {
        names.clear();
    }
}
