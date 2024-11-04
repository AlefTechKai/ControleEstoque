package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Classe responsável por gerenciar a conexão com o banco de dados MySQL.
public class ConexaoMySQL {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/controle_estoque"; //conexao com o DB
        String user = "admin"; // seu usuário
        String password = "Velocidade123"; // sua senha

        return DriverManager.getConnection(url, user, password);
    }
}
