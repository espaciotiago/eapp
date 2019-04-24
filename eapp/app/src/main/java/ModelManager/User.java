package ModelManager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity(nameInDb = "user")
public class User {

    public static int ADMIN_USER = 0;
    public static int REGULAR_USER = 1;

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "username")
    private String username;
    @Property(nameInDb = "password")
    private String password;
    @Property(nameInDb = "identification")
    private String identification;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "type")
    private int type;
    @Property(nameInDb = "area")
    private String area;
    @Property(nameInDb = "section")
    private String section;
    @Property(nameInDb = "image")
    private String image;
    @Property(nameInDb = "creationDate")
    private Date creationDate;
    public User(Long id, String username, String password, String identification,
            String name, int type, String area, String section, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.identification = identification;
        this.name = name;
        this.type = type;
        this.area = area;
        this.section = section;
        this.image = image;
    }
    public User() {
    }
    @Generated(hash = 1612014610)
    public User(Long id, String username, String password, String identification,
            String name, int type, String area, String section, String image,
            Date creationDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.identification = identification;
        this.name = name;
        this.type = type;
        this.area = area;
        this.section = section;
        this.image = image;
        this.creationDate = creationDate;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getIdentification() {
        return this.identification;
    }
    public void setIdentification(String identification) {
        this.identification = identification;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getSection() {
        return this.section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public String getImage() {
        return this.image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public Date getCreationDate() {
        return this.creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
