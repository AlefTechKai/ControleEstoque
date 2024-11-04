package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.ConexaoMySQL;
import model.Usuario;

// Classe responsável pela interação com o banco de dados para a entidade Usuario
public class UsuarioDAO {

    // Método para cadastrar um novo usuário no banco de dados
    public void cadastrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuario (username, senha) VALUES (?, ?)";
        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage(), e);
        }
    }

    // Método para buscar um usuário por nome de usuário
    public Usuario buscarPorUsername(String username) {
        String sql = "SELECT * FROM usuario WHERE username = ?";
        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(rs.getString("username"), rs.getString("senha"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage(), e);
        }
        return null;
    }
}
