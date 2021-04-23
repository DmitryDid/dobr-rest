package Helpers;

import java.sql.*;
import java.util.ArrayList;

public class DBHelpers {

    static String url = "jdbc:postgresql://192.168.1.232:5432/bdobrdb";
    static String user = "master";
    static String password = "zJ8LLfsuBLC8w";


    public static void executeRequest(String request) {
        try (Connection con = DriverManager.getConnection(url, user, password); Statement st = con.createStatement()) {
            st.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Выполнен запрос в БД:\n" + request);
        System.out.println();
    }


    public static void confirmEmailCompanyById(int id) {
        String request = "UPDATE company SET email_confirmed = true WHERE id = " + id + ";";
        executeRequest(request);
        System.out.println("Email компании с id = " + id + " - подтвержден.");
    }

    public static String getConfirmCode(String email) {
        String request = String.format("SELECT * FROM email_code WHERE email = '%s';", email);
        String code = null;
        try (Connection con = DriverManager.getConnection(url, user, password); Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(request);
            rs.next();
            code = rs.getString("code");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Выполнен запрос в БД:\n" + request);
        System.out.println();
        return code;
    }
}
