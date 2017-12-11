package com.power.kitchen.utils;


import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.power.kitchen.app.MyApplication;
import java.util.ArrayList;
import java.util.List;


public class SPUtils {

    private static final String SP_NAME = "sclm_sp";

    private static SPUtils instance = new SPUtils();

    public SPUtils() {
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new SPUtils();
        }
    }

    public static SPUtils getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    private android.content.SharedPreferences getSp() {
        return MyApplication.APP_CONTEXT.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }

    public int getInt(String key, int def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getInt(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putInt(String key, int val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                Editor e = sp.edit();
                e.putInt(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getLong(String key, long def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getLong(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putLong(String key, long val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                Editor e = sp.edit();
                e.putLong(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key, String def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getString(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putString(String key, String val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                Editor e = sp.edit();
                e.putString(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getBoolean(String key, boolean def) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getBoolean(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putBoolean(String key, boolean val) {
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null) {
                Editor e = sp.edit();
                e.putBoolean(key, val);

                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear(){
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null){
                Editor editor = sp.edit();
                editor.clear();
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteKey(String key){
        try {
            android.content.SharedPreferences sp = getSp();
            if (sp != null){
                Editor editor = sp.edit();
                editor.remove(key);
                editor.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
}
