package Tests;

import Pages.Base;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.MultiPartConfig;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;

import static Pages.Auth.accessToken;
import static Pages.Auth.getToken;
import static Pages.Company.createCompanyWithEmail;
import static Pages.Company.getCountCompany;
import static Pages.Offer.*;
import static Pages.ProductCategory.createProductCategory;
import static Pages.ProductCategory.getCountProductCategory;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class TestBase extends Base {

    protected static String REMOTE_SERVER_URL = "https://bdobr.ru/api/v1/";
    public static final String LOCAL_SERVER_URL = "http://192.168.1.232/api/v1/";
    /*public static final String BASE_URL = "http://localhost:5003/api/v1/";*/
    public static final String PLAYER_ID = "aee5c185-29b8-4d4c-a4bf-d7641aefc556";
    public static final String EMAIL = "nsk_dem@mail.ru";


    @Before
    public void init() {
        RestAssured.defaultParser = Parser.JSON;
        accessToken = getToken();
        createRows();
    }

    private void createRows() {
        if (getCountProductCategory() < 3) {
            createProductCategory();
        }
        if (getCountCompany() < 3) {
            createCompanyWithEmail(getUniqueNumber(9) + "@init.ru");
            createActiveOffer();
            createInactiveOffer();
            createPreOffer();
        }
    }

    public static final RequestSpecification SERVER_SPEC = new RequestSpecBuilder()
            .setBaseUri(REMOTE_SERVER_URL)
            .setContentType(ContentType.JSON)
            .build();

    public static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
            .setBaseUri(LOCAL_SERVER_URL)
            .setContentType(ContentType.JSON)
            .build();

    public static final RequestSpecification MULTI_DATA_SPEC = new RequestSpecBuilder()
            .setBaseUri(LOCAL_SERVER_URL)
            .setContentType("multipart/form-data")
            .addHeader("Accept-Charset", "UTF-8")
            .setConfig(RestAssured.config = RestAssured.config()
                    .encoderConfig(encoderConfig().defaultCharsetForContentType("UTF-8", "multipart/form-data"))
                    .encoderConfig(encoderConfig().defaultContentCharset("UTF-8"))
                    .multiPartConfig(MultiPartConfig.multiPartConfig().defaultCharset("UTF-8")))
            .build();
}
