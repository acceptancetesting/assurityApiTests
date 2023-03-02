package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**

 This class is used to execute the API tests using Cucumber.
 It defines the location of the feature files, step definitions, and the output report formats.
 It also loads the test properties file based on the environment specified.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefs",
        plugin = {"pretty", "json:target/cucumber-report/cucumber.json",
                "html:target/cucumber-report/cucumber.html"},
        monochrome = true
)
public class ApiTest {
    public static Properties testProperties;

    /**
     This static block loads the test properties file based on the environment specified.
     It uses the environment variable 'env' to determine the appropriate file name.
     If 'env' is not set, it defaults to 'dev'.
     The file is then loaded into the testProperties object.
     If an IOException occurs, the stack trace is printed.
     */
    static {
        try {
            testProperties = new Properties();
            String env = System.getProperty("env") == null ? "dev" : System.getProperty("env");
            String fileName = "test-" + env + ".properties";
            InputStream inputStream = ApiTest.class.getClassLoader().getResourceAsStream(fileName);
            testProperties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
