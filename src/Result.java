import org.primefaces.json.JSONObject;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.*;

@Entity
public class Result implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "userID")
    private String userID;
    @Column(name = "X")
    private double x;
    @Column(name = "Y")
    private double y;
    @Column(name = "R")
    private double r;
    @Column(name = "isIn")
    private boolean isIn;
    public  Result(){
        this.id = UUID.randomUUID().toString();
    }
    public Result(double x, double y, double r, boolean isIn) {
        this.x = x;
        this.r = r;
        this.y = y;
        this.isIn = isIn;
        this.id = UUID.randomUUID().toString();
    }

    public JSONObject createJSON(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("x", getX());
        jsonObject.put("y", getY());
        jsonObject.put("r", getR());
        jsonObject.put("isIn", isIn());
        return jsonObject;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }

    public String getUserIDId() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
