package util;


import java.io.Serializable;

public class stopDTO implements Serializable {
    private boolean status;

    public stopDTO(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
