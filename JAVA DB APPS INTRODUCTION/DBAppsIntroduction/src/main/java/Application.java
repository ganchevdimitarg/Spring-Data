import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static constantes.Constants.*;

public class Application {

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", USERNAME);
        properties.setProperty("password", PASSWORD);

        Connection connection = DriverManager.getConnection(URL, properties);

        Engine engine = new Engine(connection);
        engine.run();
    }
}
