package Helpers;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class DBHelpers {

    static String url = "jdbc:postgresql://192.168.1.232:5432/bdobrdb";
    static String user = "master";
    static String password = "zJ8LLfsuBLC8w";

    String confirmEmailCompanyById1 = "UPDATE company SET email_confirmed = true WHERE id = 1;";

    public static void executeRequest(String request) {
        try (Connection con = DriverManager.getConnection(url, user, password); Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(request);
            rs.next();
        } catch (SQLException e) {
            e.getMessage();
        }
        System.out.println("Выполнен запрос в БД:\n" + request);
        System.out.println();
    }

    public static void confirmEmailCompanyById(String id) {
        String request = "UPDATE company SET email_confirmed = true WHERE id = " + id + ";";
        executeRequest(request);
    }

    public static ArrayList<String> getConfirmCode() {
        ArrayList list = new ArrayList();
        try (Connection con = DriverManager.getConnection(url, user, password); Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM email_code;");
            while (rs.next()) {
                list.add(rs.getString("code"));
            }
        } catch (SQLException ignored) {
        }
        return list;
    }

    public static String getId(String request) {
        try (Connection con = DriverManager.getConnection(url, user, password); Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(request);
            rs.next();
            return rs.getString("id");
        } catch (SQLException e) {
            e.getMessage();
        }
        System.out.println("Выполнен запрос в БД:\n" + request);
        System.out.println();
        return request;
    }

    @Test
    public void name() {
        getId("SELECT * FROM company WHERE email = nsk_dem@mail.ru;");
    }
}
