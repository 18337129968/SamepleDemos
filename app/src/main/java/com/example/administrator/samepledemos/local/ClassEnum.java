package com.example.administrator.samepledemos.local;

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
    private static List<Class> classes = new ArrayList<>();
    public static Class<?> valueOf(int value) {
        Class c = classes.get(value);
        return c;
    }

    public static void addClass(Class clas){
        classes.add(clas);
    }
}
