package ModelManager;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "user_session")
public class UserSession {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "identification")
    private String identification;
    @Property(nameInDb = "username")
    private String username;
    @Property(nameInDb = "password")
    private String password;
    @Property(nameInDb = "name")
    private String name;
    @Generated(hash = 818689190)
    public UserSession(Long id, String identification, String username,
            String password, String name) {
        this.id = id;
        this.identification = identification;
        this.username = username;
        this.password = password;
        this.name = name;
    }
    @Generated(hash = 875065627)
    public UserSession() {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdentification() {
        return this.identification;
    }
    public void setIdentification(String identification) {
        this.identification = identification;
    }
}
