
package ConexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Fernanda
 */
public class ConnectionFactory {
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/museu?useTimezone=true&serverTimezone=UTC","root","");          
        } catch (Exception e) {
            throw new RuntimeException(e);
             
        }
     }
}