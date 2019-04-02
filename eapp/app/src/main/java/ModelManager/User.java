package ModelManager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

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
    private int area;
    @Property(nameInDb = "section")
    private int section;
    @Property(nameInDb = "image")
    private int image;
    @Generated(hash = 485511572)
    public User(Long id, String username, String password, String identification,
            String name, int type, int area, int section, int image) {
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
    @Generated(hash = 586692638)
    public User() {
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
    public int getArea() {
        return this.area;
    }
    public void setArea(int area) {
        this.area = area;
    }
    public int getSection() {
        return this.section;
    }
    public void setSection(int section) {
        this.section = section;
    }
    public int getImage() {
        return this.image;
    }
    public void setImage(int image) {
        this.image = image;
    }

}
