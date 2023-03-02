package stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ApiService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class contains step definitions for the API feature files. It uses the Cucumber framework to define the
 * test scenarios.
 */
public class ApiSteps {

    private Response response;
    private final ApiService apiService = new ApiService();

    private static final Logger logger = LogManager.getLogger(ApiSteps.class);

    /**
     * This method performs a GET operation for a given path parameter and catalogue parameter value. It fetches the
     * details for a category with a given ID and catalogue parameter value.
     *
     * @param categoryId           ID of the category
     * @param catalogueParamValue catalogue parameter value for the category
     */
    @Given("^I fetch details for category with id \"([^\"]*)\" and catalogue parameter \"([^\"]*)\"$")
    public void i_perform_GET_operation_for_with_catalogue_parameter(String categoryId, String catalogueParamValue) {
        boolean catalogue = Boolean.parseBoolean(catalogueParamValue);
        response = apiService.getDetails(categoryId, catalogue);
    }

    /**
     This method verifies that the response status code matches the expected status code.
     @param statusCode expected status code
     */
    @Then("^I should see the status code as (\\d+)$")
    public void i_should_see_the_status_code_as(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    /**
     This method verifies that the response body contains the expected properties.
     @param table expected properties as a DataTable
     */
    @Then("the response should contain the following properties:")
    public void verifyResponseBody(DataTable table) {
        List<Map<String, String>> expectedProperties = table.asMaps(String.class, String.class);

        for (Map<String, String> expectedProperty : expectedProperties) {
            String propertyName = expectedProperty.get("Property");
            String expectedValue = expectedProperty.get("Value");

            JsonPath jsonPath = response.jsonPath();
            assertThat(jsonPath.get(propertyName).toString(), is(expectedValue));
            logger.debug("For property %s expected value was %s but got %s", propertyName, expectedValue, jsonPath.get(propertyName).toString());
        }
    }

    /**
     This method verifies that the response body contains the expected promotion.
     @param expectedName expected name of the promotion
     @param expectedDescription expected description of the promotion
     */
    @Then("the response has promotion with {string} name and {string} description")
    public void verifyPromotion(String expectedName, String expectedDescription) {
        String arrayProperty = "Promotions";
        List<Map<String, Object>> responseList = response.jsonPath().getList(arrayProperty);

        Optional<Map<String, Object>> promotion = responseList.stream()
                .filter(p -> p.get("Name").equals(expectedName))
                .findFirst();

        assertThat(promotion.isPresent(), equalTo(true));
        assertThat(promotion.get(), allOf(
                hasEntry("Name", expectedName),
                hasEntry("Description", expectedDescription)));
    }

}

