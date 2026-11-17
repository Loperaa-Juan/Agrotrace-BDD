package userInterface;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("http://localhost:5173/login")
public class LoginUI {
    public static final Target INPUT_EMAIL = Target.the("Input Email").located(By.id("identifier-input"));
    public static final Target INPUT_CONTRASENA = Target.the("Input contraseña").located(By.id("password-input"));
    public static final Target LOGIN_BUTTON = Target.the("Botón de Login").located(By.id("login-button"));
}
