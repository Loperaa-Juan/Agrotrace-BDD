package userInterface;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PerfilUI {
    // Elemento que solo existe en la página de perfil
    public static final Target CONTENEDOR_PERFIL = Target.the("Página de perfil").located(By.id("root"));

    // Texto con el rol del usuario en el perfil
    public static final Target TXT_ROL = Target.the("Rol del usuario").located(By.id("role-label"));

    // Menú usuario
    public static final Target BTN_MENU = Target.the("Menú desplegable").located(By.id("menu-usuario"));

    // Botón de cerrar sesión
    public static final Target BTN_CERRAR_SESION = Target.the("Botón de cerrar sesión").located(By.id("btn-cerrar-sesion"));

    // Botón para publicar cosechas
    public static final Target BTN_PUBLICAR_COSECHA = Target.the("Botón para publicar cosecha").located(By.id("btn-publicar-cosecha"));

    // Opción del menú de usuario que lleva a la página de filtros y búsqueda
    public static final Target BTN_FILTROS_BUSQUEDA = Target.the("Opción para ir a la página de filtros").located(By.id("btn-filtros-busqueda"));


}
