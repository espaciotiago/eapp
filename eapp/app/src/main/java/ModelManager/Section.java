package ModelManager;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ufo.mobile.eapp.DaoApp;
import com.ufo.mobile.eapp.R;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "section")
public class Section {

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "costsCenter")
    private String costsCenter;
    @Property(nameInDb = "areas")
    private String areas; // {1,2,3} AreasIds splited by ,
    @Generated(hash = 889404185)
    public Section(Long id, String name, String costsCenter, String areas) {
        this.id = id;
        this.name = name;
        this.costsCenter = costsCenter;
        this.areas = areas;
    }
    @Generated(hash = 111791983)
    public Section() {
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
    public String getAreas() {
        return this.areas;
    }
    public void setAreas(String areas) {
        this.areas = areas;
    }

    public static void insertSection(Section section, Context context, Application application, boolean isNew){
        new InsertNewSection(context,application,isNew).execute(section);
    }
    public String getCostsCenter() {
        return this.costsCenter;
    }
    public void setCostsCenter(String costsCenter) {
        this.costsCenter = costsCenter;
    }

    private static class InsertNewSection extends AsyncTask<Section,Integer,Long> {

        private DaoSession daoSession;
        private Context context;
        private boolean isNew;

        private InsertNewSection(Context context, Application application, boolean isNew){
            this.context = context;
            this.daoSession = ((DaoApp)application).getDaoSession();
            this.isNew = isNew;
        }

        @Override
        protected Long doInBackground(Section... sections) {
            if(isNew) {
                return daoSession.getSectionDao().insert(sections[0]);
            }else{
                daoSession.getSectionDao().update(sections[0]);
                return sections[0].getId();
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            if(aLong != null && isNew){
                Toast.makeText(context,context.getResources().getString(R.string.succesfully_created),Toast.LENGTH_LONG).show();
            }
        }
    }
}
