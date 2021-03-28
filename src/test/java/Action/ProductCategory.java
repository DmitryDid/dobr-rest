package Action;

import Constants.CONST;
import Tests.TestBase;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class ProductCategory extends TestBase {

    static private String name = "alcohol";
    static private int ageLimit = 18;
    static private String fileName = "alko.jpg";
    static private File image = new File("src/test/java/Resources/" + fileName);

    public static Response createProductCategory() {

        ArrayList allPG = getListDTO(getAllProductCategory());
        assertTrue(allPG.size() > 0);

        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
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

    // GET /api/v{version}/ProductCategory
    public static Response getAllProductCategory() {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + Auth.getAccessToken())
                .when()
                .get("productCategory")
                .then()
                .extract().response();
        return response;
    }
}
