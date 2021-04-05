package Pages;

import io.restassured.response.Response;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Base {

    protected static void toConsole(Object object) {
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
    protected static String getUniqueNumber(int length) {
        String number = String.valueOf(System.currentTimeMillis());
        int end = number.length();
        int start = end - length;
        return number.substring(start, end);
    }

    // "2021-03-22T12:59:18.9914025Z" для примера разбора строки в дату
    protected static Date getDate(String stringDate) {
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
