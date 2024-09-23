import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.uniquindio.microservicios.taller_auto_prueba.cucumber.stepDefinitions", plugin = {"pretty"})
public class RunCucumberTest {
}