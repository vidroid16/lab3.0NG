import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@ManagedBean(name = "areacheckbean")
@SessionScoped
public class AreaCheckBean implements Serializable{
    private double x = -4;
    private double y = 0;
    private static Map<String, Double> radMap = new TreeMap<>();
    private String message="";
    private static Map<String,ArrayList<Result>> historyMap = new TreeMap<>();
    private static JSONArray historyJSON;
    private boolean isValid = false;

    public Result check(){
        boolean isGoted=false;
        if((Math.pow(x, 2)+Math.pow(y, 2)<=Math.pow((0.5*getR()),2)) && x<=0 && y>=0){
            isGoted = true;
        }
        if((y>=2*x-getR()) && x>=0 && y<=0){
            isGoted = true;
        }
        if((y<=0 && x<=0 && y>=(-getR()/2) && x>=-getR())){
            isGoted = true;
        }
        return new Result(x,y ,getR(),isGoted);
    }
    public void update(){
        System.out.println("update");
        if(getY()<5 && getY()>-5){
            System.out.println("valid");
            ArrayList<Result> h = getHistory();
            Result adResult = new Result(x, y, getR(), check().isIn());
            FacesContext fCtx = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
            String sessionId = session.getId();
            adResult.setUserID(sessionId);
            h.add(adResult);
            JSONArray jsonArray = new JSONArray();
            h.forEach(p->jsonArray.put(p.createJSON()));
            historyJSON = jsonArray;
            setHistory(h);
            ResultDAO pointDAO = new ResultDAO();
            pointDAO.insertPoint(adResult);
            System.out.println(x+" "+y+" "+getR()+" "+check().isIn());
            String is = check().isIn()?"Входит":"Не Входит";
            message = new String("X="+x+" Y="+y+" R="+getR()+" - "+is);
        }
    }
    //todo
    public void updateLoad(){
        ArrayList<Result> h = getHistory();
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        JSONArray jsonArray = new JSONArray();
        h.forEach(p->jsonArray.put(p.createJSON()));
        historyJSON = jsonArray;
        setR(2);
    }
    public String getHistoryJSON() {
        System.out.println("gettimHistory");
        try{
            return historyJSON.toString();
        }catch (NullPointerException e){
            return "[]";
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public void additionalChecking(){
        System.out.println("Additional checking");
        try {
            if (!(getY() > -5 && getY() < 5)) {
                isValid = true;
                return;
            }
        } catch (NumberFormatException e) {
            isValid = true;
            return;
        }
        isValid = false;
    }

    public void addPoint(Result point){

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        return radMap.get(sessionId) != null ? radMap.get(sessionId) : 2;
    }

    public static ArrayList<Result> getHistory() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        return historyMap.get(sessionId) != null ? historyMap.get(sessionId) :new ArrayList<Result>();
    }

    public String getMessage() {
        return message;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        System.out.println("settingR");
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        radMap.put(sessionId, r);
    }
    public void setHistory(ArrayList<Result> h) {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        String sessionId = session.getId();
        historyMap.put(sessionId, h);
    }
}
