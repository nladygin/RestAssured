
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


public class MyTest {

    @Test
    public void complex() throws JsonProcessingException, JSONException {

        //Add user
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://localhost:8090/user/addUser";
        Account user = new Account("Pheophan", "password");
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(user));
        Response response = requestSpecification.post(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();

        //Add book
        url = "http://localhost:8090/"+user.username+"/books";
        Book book = new Book(user, "War and peace", "Leo Tolstoy", "description");
        requestSpecification = given().contentType(ContentType.JSON).body(mapper.writeValueAsBytes(book));
        response = requestSpecification.post(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_CREATED)
                .log();

        //Remove book
        url = "http://localhost:8090/"+user.username+"/books/removeBook/" + Helper.getLastBookId(user.username);
        requestSpecification = given().contentType(ContentType.JSON);
        response = requestSpecification.delete(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_OK)
                .log();

        //Remove user
        url = "http://localhost:8090/user/removeUser/"+user.username;
        requestSpecification = given().contentType(ContentType.JSON);
        response = requestSpecification.delete(url);
        response.prettyPrint();
        response.then()
                .statusCode(SC_OK)
                .log();
    }
}
