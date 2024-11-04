package view;

import service.UsuarioService;
import service.ProdutoService;
import model.Produto;

import java.awt.*;
import javax.swing.*;

public class ControleEstoquev2 {

    private JFrame frame;
    private UsuarioService usuarioService = new UsuarioService();
    private ProdutoService produtoService = new ProdutoService();
    private boolean usuarioLogado = false;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ControleEstoquev2 window = new ControleEstoquev2();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ControleEstoquev2() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> realizarLogin());

        JButton cadastrarUsuarioButton = new JButton("Cadastrar Usuário");
        cadastrarUsuarioButton.addActionListener(e -> cadastrarUsuario());
        cadastrarUsuarioButton.setVisible(false);

        JButton cadastrarProdutoButton = new JButton("Cadastrar Produto");
        cadastrarProdutoButton.addActionListener(e -> cadastrarProduto());
        cadastrarProdutoButton.setVisible(false);

        JButton consultarProdutosButton = new JButton("Consultar Produtos");
        consultarProdutosButton.addActionListener(e -> consultarProdutos());
        consultarProdutosButton.setVisible(false);

        JButton consultarProdutoPorIdButton = new JButton("Consultar Produto por ID");
        consultarProdutoPorIdButton.addActionListener(e -> consultarProdutoPorId());
        consultarProdutoPorIdButton.setVisible(false);

        JButton excluirProdutoButton = new JButton("Excluir Produto");
        excluirProdutoButton.addActionListener(e -> excluirProduto());
        excluirProdutoButton.setVisible(false);

        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> System.exit(0));

        // Adicionando os componentes ao frame
        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("Senha:"));
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(cadastrarUsuarioButton);
        frame.add(cadastrarProdutoButton);
        frame.add(consultarProdutosButton);
        frame.add(consultarProdutoPorIdButton);
        frame.add(excluirProdutoButton);
        frame.add(sairButton);
    }

    private void atualizarBotoes() {
        Component[] components = frame.getContentPane().getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getText().equals("Login") || button.getText().equals("Sair")) {
                    button.setVisible(true);
                } else {
                    button.setVisible(usuarioLogado);
                }
            } else if (component instanceof JTextField || component instanceof JPasswordField) {
                component.setVisible(!usuarioLogado); // Ocultar campos de login após login
            }
        }
        frame.revalidate();
        frame.repaint();
    }

    private void realizarLogin() {
        String username = usernameField.getText();
        String senha = new String(passwordField.getPassword());

        if (usuarioService.autenticar(username, senha)) {
            usuarioLogado = true;
            JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
            atualizarBotoes();
            usernameField.setText(""); // Limpar campo de usuário
            passwordField.setText(""); // Limpar campo de senha
        } else {
            JOptionPane.showMessageDialog(frame, "Falha no login.");
        }
    }

    private void cadastrarUsuario() {
        String username = JOptionPane.showInputDialog(frame, "Digite o nome de usuário:");
        String senha = JOptionPane.showInputDialog(frame, "Digite a senha:");

        if (usuarioService.cadastrarUsuario(username, senha)) {
            JOptionPane.showMessageDialog(frame, "Usuário cadastrado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(frame, "Nome de usuário já existente.");
        }
    }

    private void cadastrarProduto() {
        String nome = JOptionPane.showInputDialog(frame, "Nome do produto:");
        double preco = Double.parseDouble(JOptionPane.showInputDialog(frame, "Preço do produto:"));
        int quantidade = Integer.parseInt(JOptionPane.showInputDialog(frame, "Quantidade:"));

        produtoService.cadastrarProduto(nome, preco, quantidade);
        JOptionPane.showMessageDialog(frame, "Produto cadastrado com sucesso!");
    }

    private void consultarProdutos() {
        String produtos = produtoService.consultarProdutos().toString();
        JOptionPane.showMessageDialog(frame, "Produtos:\n" + produtos);
    }

    private void consultarProdutoPorId() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID do produto a ser consultado:"));
        Produto produto = produtoService.consultarProdutoPorId(id);
        if (produto != null) {
            JOptionPane.showMessageDialog(frame, produto.toString());
        } else {
            JOptionPane.showMessageDialog(frame, "Produto não encontrado!");
        }
    }

    private void excluirProduto() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "ID do produto a ser excluído:"));
        produtoService.excluirProduto(id);
        JOptionPane.showMessageDialog(frame, "Produto excluído com sucesso!");
    }
}
