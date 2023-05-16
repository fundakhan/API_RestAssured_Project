package PaymentSystem;

import POJO.Payment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.lang.model.element.Name;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;


 public class ListOfCard {

    Cookies cookies;

    @BeforeClass
    public void setup(){
        RestAssured.baseURI = "https://connect.squareupsandbox.com/v2";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", "user-id-1");
        requestBody.put("amount", "100.00");
        requestBody.put("currency", "USD");
        requestBody.put("card_number", "4111111111111111");
        requestBody.put("exp_month", "11");
        requestBody.put("exp_year", "2022");
        requestBody.put("cvc", "123");


        cookies = given()
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post("/payments")
                .then()
                .statusCode(401)
                .extract().response().getDetailedCookies(); // extract cookies from the response

    }
    Payment payment;
    Response response;

    @Test
    public void testPaymentSystemAPI(){

        payment = new Payment();

        response = given()
                .body(payment)
                .contentType(ContentType.JSON)
                .cookies(cookies)

                .when()
                .post("/payments")

                .then()
                .statusCode(401)
                .log().body()
                .extract().response();

    }

//    @Test
//    public void testPaymentSystemAPI(){
//
//        RequestSpecification request = RestAssured.given();
//        request.header("Content-Type","application/json");
//        request.header("Authorization", "Bearer access_token_here");
//
//        // Set up request body
//        String requestBody = "{ \"user_id\" : \"user-id-1\" , \"amount\": 100.00, \"currency\": \"USD\", \"card_number\": \"4111111111111111\", \"exp_month\": 11, \"exp_year\": 2022, \"cvc\": \"123\" }";
//
//        // Send POST request to payment system API endpoint
//        Response response = request.body(requestBody).post("/payments");
//
//        // Verify status code and response body
//        int statusCode = response.getStatusCode();
//        String responseBody = response.getBody().asString();
//        Assert.assertEquals(statusCode, 200, "API call failed with status code" + statusCode);
//        Assert.assertEquals(responseBody.contains("\"card_brand\": \"VISA\""), true, "Response body doesn't contain expected card brand");
//
//        System.out.println("Response body: " + responseBody);
//
//        /** In this example, we're testing a hypothetical API endpoint /payments on the https://api.paymentsystem.com domain.
//            We're sending a POST request with a JSON payload containing information about the payment to be processed.
//            The API returns a JSON response similar to the one you provided, and we're verifying that the response contains the expected card_brand field
//            with a value of VISA.
//         */
//    }
}
