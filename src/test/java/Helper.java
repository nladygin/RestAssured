import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;

import static io.restassured.RestAssured.given;

public class Helper {


    public static String getLastBookId(String user) throws JSONException {
        String url = "http://localhost:8090/"+user+"/books";
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON);
        Response response = requestSpecification.get(url);
        response.prettyPrint();
        JSONArray jsonResponse = new JSONArray(response.asString());
        return jsonResponse.getJSONObject(jsonResponse.length() - 1).getString("id");
    }


    public static String getLastId() throws JSONException {
        String url = "http://localhost:8090/user/allUsers";
        RequestSpecification requestSpecification = given().contentType(ContentType.JSON);
        Response response = requestSpecification.get(url);
        response.prettyPrint();
        JSONArray jsonResponse = new JSONArray(response.asString());
        return jsonResponse.getJSONObject(0).getString("id");
    }
}
