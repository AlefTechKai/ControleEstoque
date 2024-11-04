package service;

import dao.UsuarioDAO;
import model.Usuario;

// Classe que contém as regras de negócio para a entidade Usuario
public class UsuarioService {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Método para cadastrar um novo usuário
    public boolean cadastrarUsuario(String username, String senha) {
        if (usuarioDAO.buscarPorUsername(username) == null) {
            usuarioDAO.cadastrarUsuario(new Usuario(username, senha));
            return true;
        }
        return false;
    }

    // Método para autenticar um usuário
    public boolean autenticar(String username, String senha) {
        Usuario usuario = usuarioDAO.buscarPorUsername(username);
        return usuario != null && usuario.autenticar(senha);
    }
}
