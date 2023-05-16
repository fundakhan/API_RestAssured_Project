import io.restassured.builder.*;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.testng.Assert;
import org.testng.annotations.*;


import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class GoRest {

    /**  {
     "meta": {    OBJECT
     "pagination": {  Inside Another OBJECT
     "total": 2949,
     "pages": 295,
     "page": 200,
     "limit": 10,
     "links": {   Inside Another OBJECT belogns to pegination Object
     "previous": "https://gorest.co.in/public/v1/users?page=199",
     "current": "https://gorest.co.in/public/v1/users?page=200",
     "next": "https://gorest.co.in/public/v1/users?page=201"
     }
     }
     },
     "data": [
     {
     "id": 2447,
     "name": "Dinesh Embranthiri",
     "email": "dinesh_embranthiri@hahn-roob.co",
     "gender": "female",
     "status": "active"
     },
     {
     "id": 2446,
     "name": "Himadri Chopra",
     "email": "himadri_chopra@nitzsche-ward.name",
     "gender": "male",
     "status": "active"
     }, */


    @Test
    public void queryParamTest(){ // param() is the part of the body

       given()
               .param("page" , "200") // if we want to see specific page=200 it shows me 200. page --> https://gorest.co.in/public/v1/users?page=200
               .when()
               .get("https://gorest.co.in/public/v1/users") // this url shows only page 1
               .then()
               .log().body()
               .statusCode(200)
               .body("meta.pagination.page", equalTo(200));
    }

    @Test
    public void queryParamTest1(){
/** send the same request for the pages between 1-10 and check if
 *  the page number we send from request and page number we get from response are the same
  */

        for (int i = 1; i <=10 ; i++) {

            given()
                    .param("page", i)
                    .when()
                    .get("https://gorest.co.in/public/v1/users") // this url shows only page 1
                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("meta.pagination.page", equalTo(i));
        }
    }

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
    public void baseURITest(){
        given()
                .param("page" , "2")
                .spec(requestSpec)
                .when()
                .get("/users") // we just put endpoint
                .then()
                .body("meta.pagination.page", equalTo(2))
                .spec(responseSpec);

    }

    /** JSON data Extract */
    @Test
    public void extractData(){

       String placeName = given()
                .pathParam("country", "us")
                .pathParam("zipCode","90210")
                .spec(requestSpec)
                .when()
                .get("https://api.zippopotam.us/{country}/{zipCode}")
                .then()
//                .spec(responseSpec)
                .extract().path("places[0].'place name'"); // with extract method all request now returns a value
                                                             // we can assign it to a variable like String, int, Array ...

        System.out.println("place name: " + placeName); // Beverly Hills
    }

    @Test
    public void extractData1(){

        int limit =  given()
                .param("page", 2)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .extract().path("meta.pagination.limit");

        System.out.println("limit: " + limit); // 10
        Assert.assertEquals(limit,10, "Test is failed");
    }

    @Test
    public void extractData2(){
/** get all IDs from the response and verify that 1359553 is among them separately */


        List<Integer> listOfIds =  given()
                .param("page", 2)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .extract().path("data.id");

        System.out.println("listOfIds: " + listOfIds);
        Assert.assertTrue(listOfIds.contains(1359553));
    }

    @Test
    public void extractData3(){
/** send get request to "https://gorest.co.in/public/v1/users"
 *  extract all names from data to a list
  */

    List<String> nameList = given()
             .when()
             .get("/users")
             .then()
             .spec(responseSpec)
             .extract().path("data.name");

        System.out.println("nameList: " + nameList.get(5));
        Assert.assertEquals(nameList.get(5), "Amogh Varrier");
    }

    @Test
    public void extractData4(){
/** Response object will hold everything. give us everything */

      Response response =  given()
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .extract().response();

      List<Integer> listOfIds = response.path("data.id");
      List<Integer> listOfNames = response.path("data.name");
      int limit = response.path("meta.pagination.limit");
      String currentLink = response.path("meta.pagination.links.current");

        System.out.println("listOfIds: "+ listOfIds);
        System.out.println("listOfNames: "+ listOfNames);
        System.out.println("limit: "+ limit);
        System.out.println("currentLink: "+ currentLink);

        Assert.assertTrue(listOfNames.contains("Dhanpati Guha"));
        Assert.assertTrue(listOfIds.contains(1359568));
        Assert.assertEquals(limit, 10);
        Assert.assertEquals(currentLink, "https://gorest.co.in/public/v1/users?page=1");

    }











}
