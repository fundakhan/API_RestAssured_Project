package PaymentSystem;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class PaymentSystemTest {

    String apiKey;
    String paymentUrl;

    @BeforeClass
    public void setup(){
        // Set your Payment System API key and API URL here
        apiKey = "your_api_key";
        paymentUrl = "https://api.payment.com/payment";

    }

    @Test
    public void testPaymentSuccess(){
        // Submit the information required for a successful payment transaction here
        String paymentData = "{\n" +
                "    \"amount\": 100,\n" +
                "    \"currency\": \"USD\",\n" +
                "    \"card_number\": \"1234567812345678\",\n" +
                "    \"expiry_date\": \"12/25\",\n" +
                "    \"cvc\": \"123\"\n" +
                "}";

        Response response = given()
                .header("Authorization", apiKey)
                .contentType(ContentType.JSON)
                .body(paymentData)
                .post(paymentUrl);

        // Check response code - Waiting for code 200 for successful operation
        Assert.assertEquals(response.getStatusCode(), 200);

        // Check response content - We expect "success" for a successful operation
        String responseBody = response.getBody().asString();
        Assert.assertEquals(responseBody.contains("success"),true);

    }

    @Test
    public void testPaymentFailure(){
        // Submit information required for a failed payment transaction here
        String paymentData = "{\n" +
                "    \"amount\": 1000,\n" +
                "    \"currency\": \"USD\",\n" +
                "    \"card_number\": \"1234567812345678\",\n" +
                "    \"expiry_date\": \"12/25\",\n" +
                "    \"cvc\": \"123\"\n" +
                "}";

        Response response = given()
                .header("Authorization", apiKey)
                .contentType(ContentType.JSON)
                .body(paymentData)
                .post(paymentUrl);

        // Check response frame - Waiting for window 400 for failed operation
        Assert.assertEquals(response.getStatusCode(), 400);

        // Check response content - We expect "error" for a failed operation
        String responseBody = response.getBody().asString();
        Assert.assertEquals(responseBody.contains("error"), true);
    }

}
