package app.doscervezas.avocado.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonLoader {

    public static String loadJSONFromAsset(Activity currentActivity, String fileName) {
        String json = null;
        try {
            InputStream inputStream = currentActivity.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
