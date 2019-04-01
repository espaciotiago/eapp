package Utils;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ufo.mobile.eapp.R;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Constants {

    public static String DB_NAME = "eapp_dao.db";

    public static void closeKeyboardOnTouch(int res, final Activity activity){
        activity.findViewById(res).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                closeKeyboard(activity);
                return true;
            }
        });
    }

    public static void closeKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
