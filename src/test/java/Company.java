import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class Company extends TestBase {

    // /api/v{version}/Company
    @Test
    public void getCompany() {
        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("Company")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v1/company/top
    @Test
    public void getCompanyTop() {
        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("Company/top")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/Company/{id}/image
    @Test
    public void getCompanyImage() {
        String id = "5";

        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("Company/" + id + "/image")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/Company/category/{id}
    @Test
    public void getCompanyCategory() {
        String id = "5";

        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("Company/category/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/Company/{id}/offer
    @Test
    public void getCompanyOffer() {
        String id = "5";

        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("Company/" + id + "/offer")
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }

    // /api/v{version}/Company/{id}
    @Test
    public void getCompanyById() {
        String id = "5";

        Response response = given()
                .spec(baseSpec)
                .log().all()
                .when()
                .get("Company/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        toConsole(response);
    }
}
