package ModelManager;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import com.ufo.mobile.eapp.DaoApp;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity(nameInDb = "item")
public class Item {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "reference")
    private String reference;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "description")
    private String description;
    @Property(nameInDb = "imagePath")
    private String imagePath;
    @Property(nameInDb = "stock")
    private double stock;
    @Property(nameInDb = "qtyDefault")
    private double qtyDefault;
    @Property(nameInDb = "qtyInSpecificTime")
    private double qtyInSpecificTime;
    @Property(nameInDb = "eachInDays")
    private int eachInDays;
    @Property(nameInDb = "creationDate")
    private Date creationDate;
    @Property(nameInDb = "nextAvailable")
    private Date nextAvailable;
    @Property(nameInDb = "isAvailable")
    private boolean isAvailable;

    public Item() {
        this.isAvailable = true;
    }

    @Generated(hash = 179704164)
    public Item(Long id, String reference, String name, String description,
            String imagePath, double stock, double qtyDefault,
            double qtyInSpecificTime, int eachInDays, Date creationDate,
            Date nextAvailable, boolean isAvailable) {
        this.id = id;
        this.reference = reference;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.stock = stock;
        this.qtyDefault = qtyDefault;
        this.qtyInSpecificTime = qtyInSpecificTime;
        this.eachInDays = eachInDays;
        this.creationDate = creationDate;
        this.nextAvailable = nextAvailable;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getReference() {
        return this.reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public double getStock() {
        return this.stock;
    }
    public void setStock(double stock) {
        this.stock = stock;
    }
    public double getQtyDefault() {
        return this.qtyDefault;
    }
    public void setQtyDefault(double qtyDefault) {
        this.qtyDefault = qtyDefault;
    }
    public Date getCreationDate() {
        return this.creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public double getQtyInSpecificTime() {
        return this.qtyInSpecificTime;
    }
    public void setQtyInSpecificTime(double qtyInSpecificTime) {
        this.qtyInSpecificTime = qtyInSpecificTime;
    }
    public int getEachInDays() {
        return this.eachInDays;
    }
    public void setEachInDays(int eachInDays) {
        this.eachInDays = eachInDays;
    }
    public Date getNextAvailable() {
        return this.nextAvailable;
    }
    public void setNextAvailable(Date nextAvailable) {
        this.nextAvailable = nextAvailable;
    }

    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
