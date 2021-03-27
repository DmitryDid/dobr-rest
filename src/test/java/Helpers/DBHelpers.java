package Helpers;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;

public class DBHelpers {

    String url = "jdbc:postgresql://192.168.1.232:5432/bdobrdb";
    String user = "master";
    String password = "zJ8LLfsuBLC8w";

    String confirmEmailCompanyById1 = "UPDATE company SET email_confirmed = true WHERE id = 1;";

    public void executeRequest(String request) {
        try (Connection con = DriverManager.getConnection(url, user, password); Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(request);
            rs.next();
        } catch (SQLException ignored) {
        }
        System.out.println("Выполнен запрос в БД:\n" + request);
        System.out.println();
    }

    public void confirmEmailCompanyById(int id) {
        String request = "UPDATE company SET email_confirmed = true WHERE id = " + id + ";";
        executeRequest(request);
    }

    @Test
    public ArrayList<String> getConfirmCode() {
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
}
