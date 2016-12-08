package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Windows 7 on 08/12/2016.
 */
public class Account_Control {
    Connection connection;

    public Account_Control(Connection connection) {
        this.connection = connection;
    }

    public boolean CheckLogin(String username, String password) throws Exception {
        try {
            PreparedStatement query = connection.prepareStatement("exec sp_account_login '" + username + "'");
            ResultSet rs = query.executeQuery();
            boolean b = false;
            while (rs.next()) {
                String outputPassword = rs.getString("pass");
                if (password.equals(outputPassword))
                    b = true;
                else
                    b = false;
            }
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.toString());
        }
    }
}
