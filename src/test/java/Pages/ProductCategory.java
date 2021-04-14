package Pages;

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


    public static ProductCategoryDTO createProductCategory() {
        String name = "alcohol";
        int ageLimit = 18;
        String fileName = "alko.jpg";
        File image = new File("src/test/java/resources/" + fileName);

        Response response = given()
                .spec(MULTI_DATA_SPEC)
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
        return response.as(ProductCategoryDTO.class);
    }

    public static ProductCategoryDTO createProductCategory(Map params, File image) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", image)
                .when()
                .post("productCategory")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Создана категория продукта c параметрами " + params);
        return response.as(ProductCategoryDTO.class);
    }

    public static ProductCategoryDTO updateProductCategory(int id, Map params, File image) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .formParams(params)
                .multiPart("image", image)
                .when()
                .put("ProductCategory/" + id)
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println("Обновлена категория продукта c id = " + id);
        return response.as(ProductCategoryDTO.class);
    }

    public static ArrayList<ProductCategoryDTO> getAllProductCategory() {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("productCategory")
                .then()
                .statusCode(200)
                .extract().response();
        return getListProductCategory(response);
    }

    public static ProductCategoryDTO getRandomProdCat() {
        ArrayList<ProductCategoryDTO> list = ProductCategory.getAllProductCategory();
        int countCompany = list.size();
        int index = (int) (Math.random() * (countCompany - 1));
        if (index <= 0) index = 0;
        return list.get(index);
    }

    public static ArrayList<ProductCategoryDTO> getListProductCategory(Response response) {
        ObjectMapper mapper = new ObjectMapper();
        List<Object> list = mapper.convertValue(
                response.as(JsonNode.class),
                new TypeReference<List<ProductCategoryDTO>>() {
                });
        return (ArrayList) list;
    }

    public static int getCountProductCategory() {
        return getAllProductCategory().size();
    }

    public static ProductCategoryDTO getProductCategory(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("productCategory/" + id)
                .then()
                .statusCode(200)
                .extract().response();
        return response.as(ProductCategoryDTO.class);
    }

    public static String getProductCategoryImage(int id) {
        Response response = given()
                .spec(BASE_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .when()
                .get("productCategory/" + id + "/image")
                .then()
                .statusCode(200)
                .extract().response();
        return response.asString();
    }

    public static Response putProductCategoryImage(int id, File image) {
        Response response = given()
                .spec(MULTI_DATA_SPEC)
                .header("Authorization", "Bearer " + getAccessToken())
                .multiPart("image", image)
                .when()
                .put("ProductCategory/" + id + "/image")
                .then()
                .statusCode(200)
                .extract().response();
        return response;
    }
}