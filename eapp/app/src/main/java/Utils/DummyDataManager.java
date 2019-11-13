package Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.ufo.mobile.eapp.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ModelManager.AreaDao;
import ModelManager.DaoSession;
import ModelManager.Section;
import ModelManager.Area;
import ModelManager.Item;
import ModelManager.User;

public class DummyDataManager {

    private int[] userDrawables = {R.drawable.user1,R.drawable.user2,R.drawable.user3,R.drawable.user4,R.drawable.user5,R.drawable.user6};
    private String[] usersNames = {
            "Jayda Walker",
            "Summer-Louise Hirst",
            "Greyson Case",
            "Conan Halliday",
            "Nannie Brock",
            "Sion Mcculloch",
            "Vihaan Mccarty",
            "Kunal Khan",
            "Kasper Curran",
            "Larissa Hurley",
    };

    private DaoSession daoSession;

    private Context context;

    public DummyDataManager(DaoSession daoSession,Context context){
        this.daoSession = daoSession;
        this.context = context;
    }

    /**
     *
     */
    public boolean verifyDataExistence(){
        List sections = daoSession.getSectionDao().loadAll();
        List areas = daoSession.getAreaDao().loadAll();
        List items = daoSession.getItemDao().loadAll();

        if(!sections.isEmpty() && !areas.isEmpty() && !items.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     *
     */
    public List<Long> fillDummyData(){
        // Delete the registres
        daoSession.getSectionDao().deleteAll();
        daoSession.getAreaDao().deleteAll();
        daoSession.getItemDao().deleteAll();
        // Insert Dummy data
        List<Long> list = createSectionsAndAreas();
        list.addAll(createItems());
        list.addAll(createUsers());
        return list;
    }

    /**
     *
     */
    private List<Long> createItems(){
        List<Long> listItems= new ArrayList<>();
        int i = 0;
        //---------------------------------------------------------------------------
        i++;
        Item item1 = new Item();
        item1.setReference("30196091");
        item1.setName("GAFAS DE SEGURIADAD LENTE CLARO");
        item1.setDescription("GAFAS DE SEGURIADAD LENTE CLARO");
        item1.setImagePath("img"+i);
        item1.setStock(100);
        item1.setQtyDefault(1);
        item1.setQtyInSpecificTime(1);
        item1.setEachInDays(180);
        item1.setCreationDate(new Date());
        Long id1 = daoSession.getItemDao().insert(item1);
        listItems.add(id1);
        //---------------------------------------------------------------------------
        i++;
        Item item2 = new Item();
        item2.setReference("1290");
        item2.setName("PROTECTOR AUDITIVO SILICONA");
        item2.setDescription("PROTECTOR AUDITIVO SILICONA");
        item2.setImagePath("img"+i);
        item2.setStock(100);
        item2.setQtyDefault(1);
        item2.setQtyInSpecificTime(1);
        item2.setEachInDays(180);
        item2.setCreationDate(new Date());
        Long id2 = daoSession.getItemDao().insert(item2);
        listItems.add(id2);
        //---------------------------------------------------------------------------
        i++;
        Item item3 = new Item();
        item3.setReference("Peltor H9A");
        item3.setName("PROTECTOR AUDITIVO TIPO COPA");
        item3.setDescription("PROTECTOR AUDITIVO TIPO COPA");
        item3.setImagePath("img"+i);
        item3.setStock(100);
        item3.setQtyDefault(1);
        item3.setQtyInSpecificTime(1);
        item3.setEachInDays(180);
        item3.setCreationDate(new Date());
        Long id3 = daoSession.getItemDao().insert(item3);
        listItems.add(id3);
        //---------------------------------------------------------------------------
        i++;
        Item item4 = new Item();
        item4.setReference("7501-7502-7503");
        item4.setName("PROTECCIÓN RESPIRATORIA");
        item4.setDescription("POR DESGASTE A RAIZ DEL USO 1 VEZ AL AÑO");
        item4.setImagePath("img"+i);
        item4.setStock(100);
        item4.setQtyDefault(1);
        item4.setQtyInSpecificTime(180);
        item4.setEachInDays(1);
        item4.setCreationDate(new Date());
        Long id4 = daoSession.getItemDao().insert(item4);
        listItems.add(id4);
        //---------------------------------------------------------------------------
        i++;
        Item item5 = new Item();
        item5.setReference("7093C");
        item5.setName("PROTECCIÓN RESPIRATORIA");
        item5.setDescription("PROTECCIÓN RESPIRATORIA");
        item5.setImagePath("img"+i);
        item5.setStock(100);
        item5.setQtyDefault(1);
        item5.setQtyInSpecificTime(1);
        item5.setEachInDays(365);
        item5.setCreationDate(new Date());
        Long id5 = daoSession.getItemDao().insert(item5);
        listItems.add(id5);
        //---------------------------------------------------------------------------
        i++;
        Item item6 = new Item();
        item6.setReference("30209846");
        item6.setName("GUANTE G40 LATEX");
        item6.setDescription("GUANTE G40 LATEX");
        item6.setImagePath("img"+i);
        item6.setStock(100);
        item6.setQtyDefault(1);
        item6.setQtyInSpecificTime(1);
        item6.setEachInDays(180);
        item6.setCreationDate(new Date());
        Long id6 = daoSession.getItemDao().insert(item6);
        listItems.add(id6);
        //---------------------------------------------------------------------------
        i++;
        Item item7 = new Item();
        item7.setReference("30197728");
        item7.setName("GUANTES G10 NITRILO FLEX");
        item7.setDescription("GUANTES G10 NITRILO FLEX");
        item7.setImagePath("img"+i);
        item7.setStock(100);
        item7.setQtyDefault(1);
        item7.setQtyInSpecificTime(1);
        item7.setEachInDays(9999);
        item7.setCreationDate(new Date());
        Long id7 = daoSession.getItemDao().insert(item7);
        listItems.add(id7);
        //---------------------------------------------------------------------------
        i++;
        Item item8 = new Item();
        item8.setReference("7501-7502-7503");
        item8.setName("PROTECCION CORPORAL");
        item8.setDescription("POR DESGASTE A RAIZ DEL USO");
        item8.setImagePath("img"+i);
        item8.setStock(100);
        item8.setQtyDefault(1);
        item8.setQtyInSpecificTime(1);
        item8.setEachInDays(9999);
        item8.setCreationDate(new Date());
        Long id8 = daoSession.getItemDao().insert(item8);
        listItems.add(id8);
        //---------------------------------------------------------------------------
        i++;
        Item item9 = new Item();
        item9.setReference("PROTECCION RESPIRATORIA R10");
        item9.setName("PROTECCION RESPIRATORIA R10");
        item9.setDescription("PROTECCION RESPIRATORIA R10");
        item9.setImagePath("img"+i);
        item9.setStock(100);
        item9.setQtyDefault(1);
        item9.setQtyInSpecificTime(1);
        item9.setEachInDays(9999);
        item9.setCreationDate(new Date());
        Long id9 = daoSession.getItemDao().insert(item9);
        listItems.add(id9);


        return listItems;
    }
    /**
     *
     */
    private List<Long> createSectionsAndAreas(){
        List<Long> listSections = new ArrayList<>();
        // Section --------------------------------------------------------------------
        Section section = new Section();
        section.setName("Sección principal");
        Long spearsId = daoSession.insert(section);
        listSections.add(spearsId);

        // Areas ----------------------------------------------------------------------
        Area area1 = new Area();
        area1.setName("TM");
        Long idarea1 = daoSession.insert(area1);
        section.setAreas(Constants.appendIdToString("",idarea1));

        Area area2 = new Area();
        area2.setName("RF");
        Long idarea2 = daoSession.insert(area2);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea2));

        Area area3 = new Area();
        area3.setName("EFLUENTES");
        Long idarea3 = daoSession.insert(area3);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea3));

        Area area4 = new Area();
        area4.setName("CONVERCIONES");
        Long idarea4 = daoSession.insert(area4);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea4));

        Area area5 = new Area();
        area5.setName("SPEARS");
        Long idarea5 = daoSession.insert(area5);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea5));

        Area area6 = new Area();
        area6.setName("TEKNOWEB");
        Long idarea6 = daoSession.insert(area6);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea6));

        Area area7 = new Area();
        area7.setName("COFORM");
        Long idarea7 = daoSession.insert(area7);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea7));

        Area area8 = new Area();
        area8.setName("ALMACENES");
        Long idarea8 = daoSession.insert(area8);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea8));

        Area area9 = new Area();
        area9.setName("CEDI");
        Long idarea9 = daoSession.insert(area9);
        section.setAreas(Constants.appendIdToString(section.getAreas(),idarea9));

        daoSession.getSectionDao().update(section);

        return listSections;
    }

    /**
     *
     * @return
     */
    private List<Long> createUsers(){
        List<Long> users = new ArrayList<>();

        for (int i = 0; i < 20; i++){
            User user1 = new User();
            user1.setUsername("user"+users.size());
            user1.setPassword("123");
            user1.setIdentification("user"+users.size());
            user1.setName(generateRandomName());
            user1.setType(User.REGULAR_USER);
            Area a = daoSession.getAreaDao().queryBuilder().where(AreaDao.Properties.Id.eq(genetareRandomAreaId())).unique();
            if(a != null) {
                user1.setAreaId(a.getId());
                user1.setArea(a.getName());
            }else{
                user1.setAreaId(new Long(1));
                user1.setArea("");
            }
            user1.setImage(Constants.saveImageOnStorage(context,generateRandomUserDrawable()));
            user1.setCreationDate(new Date());
            users.add(daoSession.getUserDao().insert(user1));
        }

        return users;
    }

    /**
     *
     * @return
     */
    private Long genetareRandomAreaId(){
        long areasCount = daoSession.getAreaDao().count()-1;
        int i = (int) ((Math.random() * ((areasCount - 0) + 1)) + 0);

        return new Long(i);
    }

    private String generateRandomName(){
        long drawblesCount = usersNames.length-1;
        int i = (int) ((Math.random() * ((drawblesCount - 0) + 1)) + 0);

        return usersNames[i];
    }

    /**
     *
     * @return
     */
    private Bitmap generateRandomUserDrawable(){
        long drawblesCount = userDrawables.length-1;
        int i = (int) ((Math.random() * ((drawblesCount - 0) + 1)) + 0);
        Bitmap userImg = BitmapFactory.decodeResource(context.getResources(), userDrawables[i]);
        return userImg;
    }
}
