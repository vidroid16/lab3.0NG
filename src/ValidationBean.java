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

@ManagedBean(name = "validationbean")
@SessionScoped
public class ValidationBean implements Serializable{
    private double field;
    private boolean isValid = false;

    public void setField(double field) {
        this.field = field;
    }

    public double getField() {
        return field;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isValid() {
        return isValid;
    }

    public void additionalChecking(){
        System.out.println("Additional checking");
        try {
            if (!(getField() > -5 && getField() < 5)) {
                isValid = true;
                return;
            }
        } catch (NumberFormatException e) {
            isValid = true;
            return;
        }
        isValid = false;
    }
}
