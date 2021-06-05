package logic;

import java.io.Serializable;

public class OnAirTalent implements Serializable {
    private String name;
    private String info;

    public OnAirTalent(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
