package Utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class Constants {

    public static String DB_NAME = "eapp_dao.db";
    public static String DIR_PHOTOS = "photos";
    public static String IMAGE_EXTENSSION = ".png";

    public static int DAYS_IN_YEAR = 365;
    public static int DAYS_IN_MONTH = 30;
    public static int DAYS_IN_WEEK = 7;

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
        /*
        Bitmap image = null;
        if(path != null && !path.isEmpty()) {
            try {
                if(path.contains("img")){
                    Resources resources = context.getResources();
                    int resourceId = resources.getIdentifier(path,"drawable",co2ntext.getPackageName());
                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceId);
                    return bitmap;
                }
                ContextWrapper cw = new ContextWrapper(context);
                File path1 = cw.getDir(DIR_PHOTOS, Context.MODE_PRIVATE);
                File f = new File(path1, path);
                image = decodeFile(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return image;
        */
        return decodeSampledBitmapFromResource(context, path,100,100);
    }

    /**
     * Calculate the size of a image
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Decode scaled image
     * @param context
     * @param path
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromResource(Context context, String path,
                                                         int reqWidth, int reqHeight) {
        if(path != null && !path.isEmpty()) {
            try {
                if(path.contains("img")){
                    Resources resources = context.getResources();
                    int resourceId = resources.getIdentifier(path,"drawable",context.getPackageName());
                    // First decode with inJustDecodeBounds=true to check dimensions
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeResource(resources, resourceId, options);
                    // Calculate inSampleSize
                    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                    return BitmapFactory.decodeResource(resources, resourceId, options);
                }
                ContextWrapper cw = new ContextWrapper(context);
                File path1 = cw.getDir(DIR_PHOTOS, Context.MODE_PRIVATE);
                File f = new File(path1, path);
                return decodeFile(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Decodes image and scales it to reduce memory consumption
     */
    private static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE= 150;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;

            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
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

    /**
     * Adds days to a given date
     * @param dateToAdd
     * @param numberOfDays
     * @return
     */
    public static Date calculateDatePlusDays(Date dateToAdd, int numberOfDays){
        Calendar c = Calendar.getInstance();
        c.setTime(dateToAdd);
        c.add(Calendar.DATE, numberOfDays);
        return c.getTime();
    }

    /**
     * Get the list of ids, from a string splited by ,
     * @param ids
     * @return
     */
    public static List<Long> getIdsSplitedIds(String ids){
        List<Long> idsLong = new ArrayList<>();
        String[] splited = ids.split(",");

        try {
            for (int i = 0; i < splited.length; i++) {
                Long id = Long.parseLong(splited[i]);
                idsLong.add(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return idsLong;
    }

    /**
     * Append an id to a string
     * @param toAppend
     * @param newId
     * @return
     */
    public static String appendIdToString(String toAppend, Long newId){

        String appended;

        if(toAppend.equals("")){
            appended = newId.toString();
        }else{
            appended = toAppend + "," + newId;
        }

        return appended;
    }

    /**
     * Puts the orientation on portrait if is phone or landscape if is tablet
     * @param activity
     */
    public static void setLayoutOrientation(Activity activity){
        if(activity.getResources().getBoolean(R.bool.portrait_only)){
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     *
     * @return
     */
    public static boolean isPortrait(Activity activity){
        return activity.getResources().getBoolean(R.bool.portrait_only);
    }
}
