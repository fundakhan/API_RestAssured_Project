import POJO.Location;
import POJO.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.*;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;

public class ZippoTest_POJO {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    @BeforeClass
    public void setup(){

        baseURI = "https://gorest.co.in/public/v1"; // if the request url in the request method doesn't have http part
        // rest assured adds baseURI in front of it

        requestSpec = new RequestSpecBuilder()
                .log(LogDetail.URI)                     // prints request body
                .setContentType(ContentType.JSON)       // ser data format as JSON
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)     // checks if the status code is 200 from all response
                .expectContentType(ContentType.JSON)      // checks if the response type is in JSON format
                .log(LogDetail.BODY)                      // prints the body of all response
                .build();

    }

    @Test
    public void extractJsonPOJO(){

        // Location                                     // PLace
        // String post code;                            String place name;
        // String country;                              String longitude;
        // String country abbreviation;                 String state;
        // List<Place> places;                          String state abbreviation;
        //String latitude;

        Location location = given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .spec(responseSpec)
                .extract().as(Location.class);

        System.out.println("location.getCountry() = " + location.getCountry()); // United States
        System.out.println("location.getPostCode() = " + location.getPostCode()); // 90210
        System.out.println("location.getPlaces().get(0).getPlaceName() = " + location.getPlaces().get(0).getPlaceName()); // Beverly Hills
        System.out.println("location.getPlaces().get(0).getState() = " + location.getPlaces().get(0).getState()); // California
    }
/**
    // extract.path()   => We can get only one value. Doesn't allow us to assign an int to a String variable and extract classes.
    // extract.as(Location.class) => Allows us to get the entire response body as an object. Doesn't let us to separate any part of the body
    // extract.jsonPath. => Lets us to set an int to a String, extract the entire body and extract any part of the body we want. So we don't have to
    // create classes for the entire body
 */

    @Test
    public void extractWithJsonPath(){



    }
}
