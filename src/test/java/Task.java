import POJO.ToDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class Task {

    /** Task 1
     *  create a request to https://jsonplaceholder.typicode.com/todos/2
     *  expect status 200
     *  convert into POJO
     */

    @Test
    public void task1(){

      ToDo toDo = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .extract().as(ToDo.class);

        System.out.println("toDo: " + toDo); // ToDo{userId=1, id=2, title='quis ut nam facilis et officia qui', completed=false}
    }

    /** Task 2
     *  create a request to https://httpstat.us/203
     *  expect status 203
     *  expect content type TEXT
     */

    @Test
    public void task2(){

        given()
                .when()
                .get("https://httpstat.us/203")
                .then()
                .log().body()
                .statusCode(203)
                .contentType(ContentType.TEXT);

        String expectedResult = "203 Non-Authoritative Information";

    }

    /**
     * Task 3
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */

    @Test
    public void task3(){

     ToDo toDo = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().as(ToDo.class);

        System.out.println("title: " + toDo.getTitle() );

        Assert.assertTrue(toDo.getTitle().contains("quis ut nam facilis et officia qui"));

        /** OR */
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title",equalTo("quis ut nam facilis et officia qui"));

    }

    /**
     * Task 4
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect response completed status to be false
     */

    @Test
    public void task4(){

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false));
    }
}
