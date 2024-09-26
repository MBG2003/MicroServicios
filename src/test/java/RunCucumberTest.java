import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Ruta a tus archivos .feature
        glue = "com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions", // Paquete donde están tus Step Definitions
        plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"} // Integración con Allure
)
public class RunCucumberTest {
}