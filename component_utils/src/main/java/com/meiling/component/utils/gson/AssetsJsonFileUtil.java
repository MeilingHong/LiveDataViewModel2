package com.meiling.component.utils.gson;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.meiling.component.utils.log.Mlog;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import androidx.annotation.NonNull;

/**
 * @Author marisareimu
 * @time 2021-05-27 15:41
 */
public class AssetsJsonFileUtil {
    public static String readJsonFromAssets(@NonNull Context mContext, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = mContext.getAssets();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Mlog.e(Log.getStackTraceString(e));
        }
        return stringBuilder.toString();
    }
}
