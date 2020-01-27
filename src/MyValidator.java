import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@FacesValidator("myValidator")
public class MyValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
//        String val = String.valueOf(value);
//        val.replaceAll(",","." );
//        value = val;
        try {
            double parsedValue = Double.parseDouble(value.toString());
            if (!(parsedValue > -5 && parsedValue < 5)) {
                FacesMessage message = new FacesMessage("Введено некорректное значение Y", "Y должен входить в диапазон (-5 ... 5)");
                throw new ValidatorException(message);
            }
        } catch (NumberFormatException e) {
            FacesMessage message = new FacesMessage("Введено некорректное значение Y", "Y должен быть числом");
            System.out.println("AAAAAAAAAAAAAAAA");
            throw new ValidatorException(message);
        }
    }
}