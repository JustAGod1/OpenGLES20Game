package com.justagod.opengles20game.Stuff;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.Locale;

/**
 * Создано Юрием в 01.02.17.
 * <p>
 * =====================================================
 * =            Магия! Руками не трогать!!!           =
 * =====================================================
 */

public class Logger {

    public static boolean ON = true;
    private static StringBuffer buffer = new StringBuffer();
    private static HashSet<MessageID> printed = new HashSet<>();

    static
    {
        Log.e("Logger", ON ? "Logger is on" : "Logger is off!");
    }

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

            res = res.substring(res.lastIndexOf('.') + 1);
            if (!res.equals(Logger.class.getSimpleName()) && !res.equals(MessageID.class.getSimpleName())) return res + "::" + trace[i].getMethodName();
        }

        throw new RuntimeException("Error while trying find previous class");
    }

    public static void v(String msg) {
        if (!ON) return;
        Log.v(getPreviousClassName(), msg);
    }

    public static void w(String msg) {
        if (!ON) return;
        Log.w(getPreviousClassName(), msg);
    }

    public static void infoOneTime(String comment, String msg, String key) {
        infoOneTime(String.format(Locale.ENGLISH, " %s:\n%s", comment, msg), key);
    }

    public static void infoOneTime(String msg, String key) {
        if (!ON) return;
        MessageID id = new MessageID(getPreviousClassName(), key);

        if (printed.contains(id)) return;

        printed.add(id);

        Log.i(getPreviousClassName(), msg);
    }

    public static void infoWithDelay(String msg, String key, int delay) {
        if (!ON) return;
        MessageID id = new MessageID(getPreviousClassName(), key, delay);

        if (printed.contains(id)) {
            for (MessageID messageID : printed) {
                if (messageID.equals(id)) {
                    messageID.tick();
                    if (messageID.isCanPrint()) {
                        Log.i(getPreviousClassName(), msg);
                    }
                }
            }

        }
        else {
            printed.add(id);
        }
    }

    public static void printToFile(String s) {
        buffer.append(s);
    }

    public static void sendFile() {
        (new AsyncTask<String, String, Boolean>() {

            @Override
            protected Boolean doInBackground(String... params) {
                try {
                    Socket socket = new Socket(InetAddress.getByName("95.55.109.232"), 9009);
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.writeUTF(params[0]);
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

        }).execute(buffer.toString());
    }

    private static class MessageID {
        private String procedureName;
        private String key;
        private int delay = -1;
        private int ticks = 0;

        public MessageID(String procedureName, String key, int delay) {
            this.procedureName = procedureName;
            this.key = key;
            this.delay = delay;
        }

        public MessageID(String procedureName, String key) {
            this.procedureName = procedureName;
            this.key = key;
        }

        public void tick() {
            ticks++;
            if (ticks >= delay) ticks = 0;
        }

        public boolean isCanPrint() {
            if (delay == -1) return false;
            if (ticks == 0) return true;
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MessageID messageID = (MessageID) o;

            if (delay != messageID.delay) return false;
            if (procedureName != null ? !procedureName.equals(messageID.procedureName) : messageID.procedureName != null)
                return false;
            return key != null ? key.equals(messageID.key) : messageID.key == null;

        }

        @Override
        public int hashCode() {
            int result = procedureName != null ? procedureName.hashCode() : 0;
            result = 31 * result + (key != null ? key.hashCode() : 0);
            result = 31 * result + delay;
            return result;
        }
    }
}
