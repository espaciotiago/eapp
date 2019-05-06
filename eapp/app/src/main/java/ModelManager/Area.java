package ModelManager;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ufo.mobile.eapp.DaoApp;
import com.ufo.mobile.eapp.R;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "area")
public class Area {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Generated(hash = 2044236626)
    public Area(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 179626505)
    public Area() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public static void insertArea(Area area,Context context, Application application){
        new InsertNewArea(context,application).execute(area);
    }

    private static class InsertNewArea extends AsyncTask<Area,Integer,Long> {

        private DaoSession daoSession;
        private Context context;

        private InsertNewArea(Context context, Application application){
            this.context = context;
            this.daoSession = ((DaoApp)application).getDaoSession();
        }

        @Override
        protected Long doInBackground(Area... areas) {
            return daoSession.getAreaDao().insert(areas[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if(aLong != null){
                Toast.makeText(context,context.getResources().getString(R.string.succesfully_created),Toast.LENGTH_LONG).show();
            }
        }
    }
}
