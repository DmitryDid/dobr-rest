package Constants;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.MultiPartConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class CONST {

   /* protected static String BASE_URL = "https://bdobr.ru/api/v1/";*/
    public static final String BASE_URL = "http://192.168.1.232/api/v1/";
    public static final String PLAYER_ID = "aee5c185-29b8-4d4c-a4bf-d7641aefc556";
    public static final String EMAIL = "nsk_dem@mail.ru";
    public static final String LATITUDE = "55.0415000";
    public static final String LONGITUDE = "82.9346000";

    public static final RequestSpecification BASE_SPEC = new RequestSpecBuilder()
            .setBaseUri(CONST.BASE_URL)
            .setContentType(ContentType.JSON)
            .build();

    public static final RequestSpecification MULTi_DATA_SPEC = new RequestSpecBuilder()
            .setBaseUri(CONST.BASE_URL)
            .setContentType("multipart/form-data")
            .addHeader("Accept-Charset", "UTF-8")
            .setConfig(RestAssured.config = RestAssured.config()
                    .encoderConfig(encoderConfig().defaultCharsetForContentType("UTF-8", "multipart/form-data"))
                    .encoderConfig(encoderConfig().defaultContentCharset("UTF-8"))
                    .multiPartConfig(MultiPartConfig.multiPartConfig().defaultCharset("UTF-8")))
            .build();

}
