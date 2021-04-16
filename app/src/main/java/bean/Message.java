package bean;

import android.content.Context;
import android.widget.Toast;

public class Message {

    public static void showMessage(Context activity, String message, int duration){
        Toast.makeText(activity,message, duration).show();
    }

}