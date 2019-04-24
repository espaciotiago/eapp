package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ufo.mobile.eapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Constants {

    public static String DB_NAME = "eapp_dao.db";
    public static String DIR_PHOTOS = "photos";
    public static String IMAGE_EXTENSSION = ".png";

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
        if(activity.getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Saves a image in a folder of the app
     * @return
     */
    public static String saveImageOnStorage(Context context, Bitmap imageBitmap){
        String path = "";
        String name = UUID.randomUUID().toString();

        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(DIR_PHOTOS, Context.MODE_PRIVATE);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, name + IMAGE_EXTENSSION);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            path = name + IMAGE_EXTENSSION;
        } catch (Exception e) {
            Log.e("Saving image error", e.getMessage());
        }

        return path;
    }

    /**
     * Gets a image from storage
     * @param path
     * @return
     */
    public static Bitmap loadImageFromStorage(Context context, String path) {
        Bitmap image = null;
        if(path != null && !path.isEmpty()) {
            try {
                ContextWrapper cw = new ContextWrapper(context);
                File path1 = cw.getDir(DIR_PHOTOS, Context.MODE_PRIVATE);
                File f = new File(path1, path);
                image = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    /**
     * Get a formatted date
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        String dateStr = "";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateStr = dateFormat.format(date);
        }catch (Exception e){
            e.printStackTrace();
        }

        return dateStr;
    }
}
