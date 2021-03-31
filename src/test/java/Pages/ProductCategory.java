package Pages;

import Constants.CONST;
import DTO.ProductCategoryDTO;
import Tests.TestBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static Pages.Auth.getAccessToken;
import static io.restassured.RestAssured.given;

public class ProductCategory extends TestBase {


    public static Response createProductCategory() {
        String name = "alcohol";
        int ageLimit = 18;
        String fileName = "alko.jpg";
        File image = new File("src/test/java/Resources/" + fileName);

        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParam("name", name)
                .formParam("ageLimit", ageLimit)
                .multiPart("image", image)
                .when()
                .post("productCategory")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создана категория продукта " + name);
        return response;
    }

    public static Response createProductCategory(Map params, File image) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", image)
                .when()
                .post("productCategory")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создана категория продукта c параметрами " + params);
        return response;
    }

    public static Response updateProductCategory(String id, Map params, File image) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", image)
                .when()
                .put("ProductCategory/" + id)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Обновлена категория продукта c id = " + id);
        return response;
    }

    // GET /api/v{version}/ProductCategory
    public static Response getAllProductCategory() {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("productCategory")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static ArrayList getListProductCategory(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<ProductCategoryDTO>>() {
                });
        return (ArrayList) list;
    }

    public static Response getProductCategoryById(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("productCategory/" + id)
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }

    public static Response getProductCategoryImageById(String id) {
        Response response = given()
                .spec(CONST.BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("productCategory/" + id + "/image")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.asString());
        return response;
    }

    public static Response putProductCategoryImageById(String id, File image) {
        Response response = given()
                .spec(CONST.MULTi_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .multiPart("image", image)
                .when()
                .put("ProductCategory/" + id + "/image")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(response.asString());
        return response;
    }
}