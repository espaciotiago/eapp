package Utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import ModelManager.Area;
import ModelManager.AreaDao;
import ModelManager.DaoMaster;
import ModelManager.DaoSession;
import ModelManager.Item;
import ModelManager.ItemDao;
import ModelManager.Order;
import ModelManager.Section;
import ModelManager.SectionDao;
import ModelManager.User;
import ModelManager.UserDao;

public class ExcelExportManager {

    public static void setUpPOI(){
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
    }



    public static void createDataSheet(DaoSession daoSession, Context context){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Ordenes"); //Creating a sheet

        //Setup titles
        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue("Fecha");
        row.createCell(1).setCellValue("Orden");
        row.createCell(2).setCellValue("Item");
        row.createCell(3).setCellValue("Usuario");
        row.createCell(4).setCellValue("Cantidad");
        row.createCell(5).setCellValue("Causa");
        row.createCell(6).setCellValue("Centro de costos");

        //Get orders titles
        List<Order> orders = daoSession.getOrderDao().loadAll();
        for(int  i=0; i< orders.size(); i++){
            Order o = orders.get(i);
            String owner = "";
            String item = "";
            String qty = o.getQty()+"";
            String date = "";
            String cause = "";
            String costsCenter = "";
            if(o.getCreationDate() != null){
                date = Constants.formatDateWithTime(o.getCreationDate());
            }
            if(o.getCause() != null){
                cause = o.getCause();
            }
            if(o.getOwner() != null){
                User u = daoSession.getUserDao().queryBuilder().where(UserDao.Properties.Id.eq(o.getOwner())).unique();
                Area a = daoSession.getAreaDao().queryBuilder().where(AreaDao.Properties.Id.eq(u.getAreaId())).unique();
                Section s = daoSession.getSectionDao().queryBuilder().where(SectionDao.Properties.Id.eq(a.getSection())).unique();
                costsCenter = s.getCostsCenter();
                owner = u.getName();
            }else if(o.getAreaOwner() != null){
                Area a = daoSession.getAreaDao().queryBuilder().where(AreaDao.Properties.Id.eq(o.getAreaOwner())).unique();
                Section s = daoSession.getSectionDao().queryBuilder().where(SectionDao.Properties.Id.eq(a.getSection())).unique();
                costsCenter = s.getCostsCenter();
                owner = a.getName();
            }

            if(o.getItem() != null){
                Item itemObj = daoSession.getItemDao().queryBuilder().where(ItemDao.Properties.Id.eq(o.getItem())).unique();
                item = itemObj.getName();
            }

            Row rowi = sheet.createRow(i+2);
            rowi.createCell(0).setCellValue(date);
            rowi.createCell(1).setCellValue(o.getId()+"");
            rowi.createCell(2).setCellValue(item);
            rowi.createCell(3).setCellValue(owner);
            rowi.createCell(4).setCellValue(qty);
            rowi.createCell(5).setCellValue(cause);
            rowi.createCell(6).setCellValue(costsCenter);
        }

        String fileName = "ordenes.xlsx"; //Name of the file

        String extStorageDirectory = Environment.getExternalStorageDirectory()
                .toString();
        File folder = new File(extStorageDirectory, "EAPP");// Name of the folder you want to keep your file in the local storage.
        folder.mkdir(); //creating the folder
        File file = new File(folder, fileName);
        try {
            file.createNewFile(); // creating the file inside the folder
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            FileOutputStream fileOut = new FileOutputStream(file); //Opening the file
            workbook.write(fileOut); //Writing all your row column inside the file
            fileOut.close(); //closing the file and done
            Toast.makeText(context,"Se ha actualizado el archivo ordenes.xlsx en la carpeta EAPP",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,"No se ha podido crear el archivo, lo sentimos",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"No se ha podido crear el archivo, lo sentimos",Toast.LENGTH_LONG).show();
        }

    }
}
