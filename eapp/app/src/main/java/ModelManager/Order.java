package ModelManager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity(nameInDb = "order")
public class Order {

    public static int CREATED = 0;
    public static int PENDING_FOR_AUTORIZATION = 1;
    public static int REQUESTED = 2;
    public static int COMPLETED = 3;
    public static int EXPIRED = 4;

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "itemId")
    private Long item;
    @Property(nameInDb = "ownerId")
    private Long owner;
    @Property(nameInDb = "status")
    private int status;
    @Property(nameInDb = "qty")
    private double qty;
    @Property(nameInDb = "comments")
    private String comments;
    @Property(nameInDb = "userSignImage")
    private String userSignImage;
    @Property(nameInDb = "autorizationSignImage")
    private String autorizationSignImage;
    @Property(nameInDb = "creationDate")
    private Date creationDate;
    public Order(Long id, Long item, Long owner, int status, double qty,
            String comments, String userSignImage, String autorizationSignImage) {
        this.id = id;
        this.item = item;
        this.owner = owner;
        this.status = status;
        this.qty = qty;
        this.comments = comments;
        this.userSignImage = userSignImage;
        this.autorizationSignImage = autorizationSignImage;
    }
    public Order() {
    }
    @Generated(hash = 2060805732)
    public Order(Long id, Long item, Long owner, int status, double qty,
            String comments, String userSignImage, String autorizationSignImage,
            Date creationDate) {
        this.id = id;
        this.item = item;
        this.owner = owner;
        this.status = status;
        this.qty = qty;
        this.comments = comments;
        this.userSignImage = userSignImage;
        this.autorizationSignImage = autorizationSignImage;
        this.creationDate = creationDate;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getItem() {
        return this.item;
    }
    public void setItem(Long item) {
        this.item = item;
    }
    public Long getOwner() {
        return this.owner;
    }
    public void setOwner(Long owner) {
        this.owner = owner;
    }
    public double getQty() {
        return this.qty;
    }
    public void setQty(double qty) {
        this.qty = qty;
    }
    public String getComments() {
        return this.comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public String getUserSignImage() {
        return this.userSignImage;
    }
    public void setUserSignImage(String userSignImage) {
        this.userSignImage = userSignImage;
    }
    public String getAutorizationSignImage() {
        return this.autorizationSignImage;
    }
    public void setAutorizationSignImage(String autorizationSignImage) {
        this.autorizationSignImage = autorizationSignImage;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Date getCreationDate() {
        return this.creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
