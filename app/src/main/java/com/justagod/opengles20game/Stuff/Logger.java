package com.justagod.opengles20game.Stuff;

import android.util.Log;

/**
 * Создано Юрием в 01.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Logger {

    public static boolean ON = true;

    public static void e(String msg) {
        if (!ON) return;
        Log.e(getPreviousClassName(), msg);
    }

    public static void e(String msg, Throwable e) {
        if (!ON) return;
        Log.e(getPreviousClassName(), msg, e);
    }

    public static void i(String msg) {
        if (!ON) return;
        Log.i(getPreviousClassName(), msg);
    }

    public static void d(String msg) {
        if (!ON) return;
        Log.d(getPreviousClassName(), msg);
    }

    public static void d(String msg, Throwable e) {
        if (!ON) return;
        Log.d(getPreviousClassName(), msg, e);
    }



    private static String getPreviousClassName() {

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        for (int i = 2; i < trace.length; i++) {
            String res = trace[i].getClassName();

            if (!res.equals(Logger.class.getName())) return res;
        }

        throw new RuntimeException("Error while trying find previous class");
    }

    public static void v(String msg) {
        if (!ON) return;
        Log.v(getPreviousClassName(), msg);
    }
}
