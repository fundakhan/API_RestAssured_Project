import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Locale;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;

public class ZippoTest {

    @Test
    public void test(){

        given() // preparation (token, request body, parameters, cookies...)

                .when() // (link(UTL), request method->GET<POST<PUT<DELETE)


                .then(); // response body , assertions, extract data(veri ayikla) from the response
    }

    @Test
    public void statusCodeTest(){

        given()
                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .log().body() // prints the response body
                .log().status()
                .statusCode(200);
    }

    @Test
    public void contentTypeTest(){

        given()
                .when()
                .get("https://api.zippopotam.us/TR/35520")

                .then()
                .log().body()
                .contentType(ContentType.JSON)  // checks if the response is in the JSON format
                .body("places[1].'place name'", equalTo("Örnekköy Mah."))
                .statusCode(200);
    }

    @Test
    public void checkCountryFromResponseBody(){

        given()
                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .body("country", equalTo("United States"))
                .body("'post code'", equalTo("90210"))
                .body("places[0].longitude", equalTo("-118.4065"))
                .body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    // postman                                           // Rest Assured
    // pm.response.json().'post code'                    // body("'post code'")
    // pm.response.json().places[0].'place name'         // body("places[0].'place name'", equalTo("Beverly Hills"))
    // pm.response.json().places.'place name'            // body("places.'place name'", equalTo()) // gives all place names in the list

    @Test
    public void checkStateFromResponse(){

        given()
                .when()
                .get("https://api.zippopotam.us/TR/35520")
                .then()
                .log().body()
                .statusCode(200)
                .body("places.state", hasItem("İzmir")); // hasItem
    }

    @Test
    public void bodyArrayHasItem(){

        given()
                .when()
                .get("https://api.zippopotam.us/TR/01000")
                .then()
                .log().body()
                .statusCode(200)
                .body("places.'place name'", hasItem("Hakkibeyli Köyü"));// checks if the list of place names has this value -> like contains
    }

    @Test
    public void bodyArraySizeTest(){
        given()
                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(1));

    }

    @Test
    public void bodyArraySizeTest2(){

        given()
                .when()
                .get("https://api.zippopotam.us/TR/01000")

                .then()
                .log().body()
                .statusCode(200)
                .body("places.'place name'", hasSize(71)); // check if the size of the list of place names is 71

    }

    @Test
    public void multipleTest(){

        given()
                .when()
                .get("https://api.zippopotam.us/TR/01000")
                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(71))
                .body("places.'place name'", hasItem("Hakkibeyli Köyü"))
                .body("places.state", hasItem("Adana"))
                .body("places[2].'place name'", equalTo("Dörtağaç Köyü"));
    }

    String country = "us";
    String zipCode = "90210";
    @Test
    public void pathParamTest(){

        given()
                .pathParam("country", country)
                .pathParam("zipCode",zipCode)
                .when()
                .get("https://api.zippopotam.us/{country}/{zipCode}")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void pathParamTest2(){  // pathParam() part of the URL

        given()
                .pathParam("country", "us")
                .pathParam("zipCode","90210")
                .log().uri() // prints the request url --> Request URI:	https://api.zippopotam.us/us/90210
                .when()
                .get("https://api.zippopotam.us/{country}/{zipCode}")
                .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void pathParamTest3(){
/** send get request for zipcodes between 90210 and 90213 and verify that in all responses the size
    of the places array is 1  */

        for (int i = 90210; i <= 90213 ; i++) {

            given()
                    .pathParam("country", "us")
                    .pathParam("zipCode", i)
                    .log().uri() // prints the request url --> Request URI:	https://api.zippopotam.us/us/90210 , 90211, 90212, 90213
                    .when()
                    .get("https://api.zippopotam.us/{country}/{zipCode}")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("places", hasSize(1));

        }
    }



}
