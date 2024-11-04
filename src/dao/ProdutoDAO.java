package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.ConexaoMySQL;
import model.Produto;

// Classe responsável pela interação com o banco de dados para a entidade Produto
public class ProdutoDAO {

	public void cadastrarProduto(Produto produto) {
	    String createTableSQL = "CREATE TABLE IF NOT EXISTS produto (" +
	                             "id INT PRIMARY KEY, " +
	                             "nome VARCHAR(100) NOT NULL, " +
	                             "preco DECIMAL(10, 2) NOT NULL, " +
	                             "quantidade INT NOT NULL)";
	    
	    String insertSQL = "INSERT INTO produto (id, nome, preco, quantidade) VALUES (?, ?, ?, ?)";
	    String checkSQL = "SELECT COUNT(*) FROM produto WHERE id = ? OR nome = ?";
	    
	    try (Connection conn = ConexaoMySQL.getConnection();
	         Statement stmt = conn.createStatement()) {
	        
	        // Criar a tabela se não existir
	        stmt.executeUpdate(createTableSQL);

	        // Verificar se o produto já existe
	        try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
	            checkStmt.setInt(1, produto.getIdentificacao());
	            checkStmt.setString(2, produto.getNome());
	            
	            ResultSet rs = checkStmt.executeQuery();
	            if (rs.next() && rs.getInt(1) > 0) {
	                System.out.println("Produto já consta em cadastro!!");
	                return; // Sai do método se o produto já existir
	            }
	        }

	        // Preparar a inserção do produto
	        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
	            pstmt.setInt(1, produto.getIdentificacao());
	            pstmt.setString(2, produto.getNome());
	            pstmt.setDouble(3, produto.getPreco());
	            pstmt.setInt(4, produto.getQuantidade());
	            pstmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException("Erro ao cadastrar produto: " + e.getMessage(), e);
	    }
	}


    // Método para consultar todos os produtos cadastrados
    public List<Produto> consultarProdutos() {
        String sql = "SELECT * FROM produto";
        List<Produto> produtos = new ArrayList<>();
        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                );
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar produtos: " + e.getMessage(), e);
        }
        return produtos;
    }

    // Método para consultar um produto por ID
    public Produto consultarProdutoPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar produto por ID: " + e.getMessage(), e);
        }
        return null;
    }

    // Método para excluir um produto do banco de dados
    public void excluirProduto(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";
        try (Connection conn = ConexaoMySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir produto: " + e.getMessage(), e);
        }
    }
}
