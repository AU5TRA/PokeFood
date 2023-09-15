package util;
import java.io.Serializable;
public class LogoutDTO implements Serializable {

    private String name;
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LogoutDTO(String Name) {
        this.name = Name;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }
}
