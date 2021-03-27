package Entity;

import Tests.TestBase;
import io.restassured.response.Response;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ProductCategory extends TestBase {

    String name = "alcohol";
    int ageLimit = 18;
    String fileName = "alko.jpg";
    File image = new File("src/test/java/Resources/" + fileName);

    public Response createProductCategory() {
        Response response = given()
                .spec(multiDataSpec)
                .header("Authorization", "Bearer " + Auth.accessToken)
                .formParam("name", name)
                .formParam("ageLimit", ageLimit)
                .multiPart("image", image)
                .when()
                .post("productCategory")
                .then()
                .statusCode(200).extract().response();
        System.out.println("Создана категория продукта " + name);
        return response;
    }
}
