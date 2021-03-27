package Tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class ProductCategoryTest extends TestBase {

    // POST /api/v{version}/ProductCategory
    @Test
    public void postProductCategory() {
        String name = "alcohol";
        int ageLimit = 18;
        String fileName = "alko.jpg";
        File image = new File("src/test/java/Resources/" + fileName);

        // POST /api/v{version}/Company
        Response postResponse = given()
                .spec(multiDataSpec)
                .log().all()
                .header("Authorization", "Bearer " + auth.getToken())
                .formParam("name", name)
                .formParam("ageLimit", ageLimit)
                .multiPart("image", image)
                .when()
                .post("productCategory")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(postResponse);
    }

    // GET /api/v{version}/ProductCategory
    @Test
    public void getProductCategoryAll() {
        Response postResponse = given()
                .spec(baseSpec)
                .header("Authorization", "Bearer " + auth.getToken())
                .log().all()
                .when()
                .get("productCategory")
                .then()
                .statusCode(200)
                .extract()
                .response();
        toConsole(postResponse);
    }
}
