import book.Account;
import book.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;


public class RestAssuredTest {


    @Test
    public void postUser() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://localhost:8090/user/addUser";
        Account account = new Account("Bob", "password");
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(account));
        Response response = requestSpecification.post(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
    }


    @Test
    public void removeUser() throws JsonProcessingException, JSONException {
        String user = "Sidor";
        String url = "http://localhost:8090/"+user+"/books/removeBook/" + Helper.getLastBookId(user);
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON);
        Response response = requestSpecification.delete(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_OK)
                .log();
        url = "http://localhost:8090/user/removeUser/"+user;
        requestSpecification = given().contentType(ContentType.JSON);
        response = requestSpecification.delete(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_OK)
                .log();
    }


    @Test
    public void postAndRemoveBook() throws JsonProcessingException, JSONException {
        String user = "Ivan";
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://localhost:8090/"+user+"/books";
        Account account = new Account(user, "password");
        Book book = new Book(account, "Anna Karenina", "Leo Tolstoy", "description");
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(book));
        Response response = requestSpecification.post(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();
        url = "http://localhost:8090/"+user+"/books/removeBook/" + Helper.getLastBookId(user);
        requestSpecification = given().contentType(ContentType.JSON);
        response = requestSpecification.delete(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_OK)
                .log();
    }


    @Test
    public void changeBook() throws JSONException, JsonProcessingException {
        String user = "Petya";
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://localhost:8090/"+user+"/books/editBook/" + Helper.getLastBookId(user);
        Account account = new Account(user, "password");
        Book book = new Book(account, "Tom Sawyer", "Mark Twain", "description");
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(book));
        Response response = requestSpecification.put(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_OK)
                .log();
    }


    @Test
    public void changeUser() throws JsonProcessingException {
        String user = "Vasya";
        String url = "http://localhost:8090/user/editUser/" + user;
        Account account = new Account("Nikodim", "password");
        ObjectMapper mapper = new ObjectMapper();
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(account));
        Response response = requestSpecification.put(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_OK)
                .log();
    }


}
