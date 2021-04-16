package bean;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class GsonUtils<T> {

    public T returnObject(String json) {
        try {
            Gson gson = new Gson();
            Type storeListType = new TypeToken<T>() {
            }.getType();
            T result = gson.fromJson(json, storeListType);

            return result;
        } catch (Exception e) {
            Log.e("GsonUtils", "returnObject", e);

            return null;
        }
    }

    public String returnJson(T object) {
        GsonUtils gson = new GsonUtils();

        String json = gson.returnJson(object);

        return new String();
    }

}