package Tests;

import Constants.CONST;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FiasTest extends TestBase {

    @Test
    public void getFiasAddressText() {
        String text = "город Новосибирск, улица Фрунзе, дом 5";
        String[] words = text.split(" ");

        String response = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .get("Fias/address/" + text)
                .then()
                .statusCode(200)
                .extract()
                .response().asString();

        for (String word : words) {
            assertTrue(response.contains(word));
        }
    }

    @Test
    public void getFiasInn() {
        String response = given()
                .spec(CONST.BASE_SPEC)
                .when()
                .get("Fias/inn/" + "5401309211")
                .then()
                .statusCode(200)
                .extract()
                .response().asString();

        assertEquals(
                "[{\"name\":\"ООО \\\"ЭЛИТА-ВОСТОК\\\"\",\"inn\":\"5401309211\",\"management\":\"Леонов Александр Владимирович\"}]",
                response);
    }
}
