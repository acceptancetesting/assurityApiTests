package services;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import stepdefs.ApiSteps;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

/**
 * This class provides methods to interact with the API service
 */
public class ApiService {

    private static final String DEFAULT_ENVIRONMENT = "dev";
    private static final String BASE_URL_PROPERTY_NAME = "baseUrl";
    private static final String BASE_PATH_PROPERTY_NAME = "categories.basePath";
    private static final String CATALOGUE_PARAM_NAME = "catalogue";
    private final String baseUrl;

    private static final Logger logger = LogManager.getLogger(ApiSteps.class);

    /**
     * Constructor to load the properties file and initialize the base URL
     */
    public ApiService() {
        String environment = System.getProperty("env", DEFAULT_ENVIRONMENT);
        String propertiesFileName = "test-" + environment + ".properties";
        Properties properties = new Properties();

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFileName);
            properties.load(inputStream);
            baseUrl = properties.getProperty(BASE_URL_PROPERTY_NAME);
            basePath = properties.getProperty(BASE_PATH_PROPERTY_NAME);
            logger.debug("Got properties baseUrl %s and %basePath ", baseUrl, basePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file: " + propertiesFileName, e);
        }

        if (baseUrl == null) {

            throw new RuntimeException("Missing required property: " + BASE_URL_PROPERTY_NAME);
        }
    }

    /**
     * Sends a GET request to the API to retrieve the details for the category with the given ID and catalogue parameter value
     *
     * @param pathParam           The ID of the category to retrieve details for
     * @param catalogueParamValue The value of the catalogue parameter to include in the request
     * @return A Response object containing the response from the API
     */
    public Response getDetails(String pathParam, boolean catalogueParamValue) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("id", pathParam)
                .queryParam(CATALOGUE_PARAM_NAME, catalogueParamValue)
                .when()
                .baseUri(baseUrl)
                .basePath(basePath)
                .get("{id}/Details.json")
                .then()
                .extract()
                .response();
    }
}



