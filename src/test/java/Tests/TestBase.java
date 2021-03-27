package Tests;

import Entity.Auth;
import Entity.Company;
import Entity.ProductCategory;
import Entity.User;
import Helpers.DBHelpers;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.MultiPartConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Before;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class TestBase {

    /*protected String baseURL = "https://bdobr.ru/api/v1/";*/

    private String baseURL = "http://192.168.1.232/api/v1/";


    protected DBHelpers dbHelpers = new DBHelpers();
    protected Company company;
    protected Auth auth;
    protected User user;
    protected ProductCategory productCategory;


    protected RequestSpecification baseSpec = new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setContentType(ContentType.JSON)
            .build();

    protected RequestSpecification multiDataSpec = new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setContentType("multipart/form-data")
            .addHeader("Accept-Charset", "UTF-8")
            .setConfig(RestAssured.config = RestAssured.config()
                    .encoderConfig(encoderConfig().defaultCharsetForContentType("UTF-8", "multipart/form-data"))
                    .encoderConfig(encoderConfig().defaultContentCharset("UTF-8"))
                    .multiPartConfig(MultiPartConfig.multiPartConfig().defaultCharset("UTF-8")))
            .build();


    @Before
    public void init() {
        new TestBase();
        company = new Company();
        auth = new Auth();
        user = new User();
        if (auth.accessToken == null) {
            auth.accessToken = auth.getToken();
        }
        productCategory = new ProductCategory();


        productCategory.createProductCategory();
        company.createCompany();
    }

    private void createRow() {
        user.createUser();
        productCategory.createProductCategory();
        company.createCompany();
    }

    protected void toConsole(Object object) {
        String response = "не удалось получить тело ответа";
        if (object instanceof Response) {
            response = ((Response) object).body().asString();
        }
        if (object instanceof String) {
            response = (String) object;
        }
        if (object instanceof JSONObject) {
            response = object.toString();
            System.out.println("json:\n" + response);
            return;
        }
        System.out.println("\ntoConsole:\n" + response);
        System.out.println();
    }

    /*
     * length не может быть больше 13
     * */
    protected String getUniqueNumber(int length) {
        String number = String.valueOf(System.currentTimeMillis());
        int end = number.length();
        int start = end - length;
        return number.substring(start, end);
    }

    // "2021-03-22T12:59:18.9914025Z" для примера разбора строки в дату
    protected Date getDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        Date date;
        try {
            String s = stringDate.substring(0, 19);
            date = format.parse(s);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
