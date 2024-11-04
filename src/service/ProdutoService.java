package service;

import dao.ProdutoDAO;
import model.Produto;
import java.util.List;

// Classe que contém as regras de negócio para a entidade Produto
public class ProdutoService {
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    // Método para cadastrar um novo produto
    public void cadastrarProduto(int identificacao, String nome, double preco, int quantidade) {
        produtoDAO.cadastrarProduto(new Produto(identificacao, nome, preco, quantidade));
    }

    // Método para consultar todos os produtos
    public List<Produto> consultarProdutos() {
        return produtoDAO.consultarProdutos();
    }

    // Método para consultar um produto por ID
    public Produto consultarProdutoPorId(int id) {
        return produtoDAO.consultarProdutoPorId(id);
    }

    // Método para excluir um produto
    public void excluirProduto(int id) {
        produtoDAO.excluirProduto(id);
    }
}
