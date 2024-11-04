package view;

import service.UsuarioService;
import service.ProdutoService;
import model.Produto;
import java.util.Scanner;

// Classe principal que interage com o usuário
public class Main {
    private static UsuarioService usuarioService = new UsuarioService();
    private static ProdutoService produtoService = new ProdutoService();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean usuarioLogado = false; // Controle de login

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Login");
            if (usuarioLogado) { // Exibir opções apenas se o usuário estiver logado
                System.out.println("2. Cadastrar Usuário");
                System.out.println("3. Cadastrar Produto");
                System.out.println("4. Consultar Produtos");
                System.out.println("5. Consultar Produto por ID");
                System.out.println("6. Excluir Produto");
            }
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");

            int escolha = scanner.nextInt();
            scanner.nextLine();  // Limpar buffer

            switch (escolha) {
                case 1:
                    realizarLogin();
                    break;
                case 2:
                	if (usuarioLogado) {
                		cadastrarUsuario();
                    } else {
                        System.out.println("Você precisa estar logado para cadastrar usuario.");
                    }
                    
                    break;
                case 3:
                    if (usuarioLogado) {
                        cadastrarProduto();
                    } else {
                        System.out.println("Você precisa estar logado para cadastrar um produto.");
                    }
                    break;
                case 4:
                    if (usuarioLogado) {
                        consultarProdutos();
                    } else {
                        System.out.println("Você precisa estar logado para consultar produtos.");
                    }
                    break;
                case 5:
                    if (usuarioLogado) {
                        consultarProdutoPorId();
                    } else {
                        System.out.println("Você precisa estar logado para consultar um produto por ID.");
                    }
                    break;
                case 6:
                    if (usuarioLogado) {
                        excluirProduto();
                    } else {
                        System.out.println("Você precisa estar logado para excluir um produto.");
                    }
                    break;
                case 7:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    // Função para login
    private static void realizarLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        if (usuarioService.autenticar(username, senha)) {
            usuarioLogado = true; // Usuário agora está logado
            System.out.println("Login bem-sucedido!");
        } else {
            System.out.println("Falha no login.");
        }
    }

    // Função para cadastrar um novo usuário
    private static void cadastrarUsuario() {
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();

        if (usuarioService.cadastrarUsuario(username, senha)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Nome de usuário já existente.");
        }
    }

    // Função para cadastrar um novo produto
    private static void cadastrarProduto() {
        System.out.print("ID do produto: ");
        int identificacao = scanner.nextInt();
        scanner.nextLine();  // Limpar buffer
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço do produto: ");
        double preco = scanner.nextDouble();
        System.out.print("Quantidade: ");
        int quantidade = scanner.nextInt();

        produtoService.cadastrarProduto(identificacao, nome, preco, quantidade);
    }

    // Função para consultar todos os produtos
    private static void consultarProdutos() {
        produtoService.consultarProdutos().forEach(System.out::println);
    }

    // Função para consultar um produto por ID
    private static void consultarProdutoPorId() {
        System.out.print("ID do produto a ser consultado: ");
        int id = scanner.nextInt();
        Produto produto = produtoService.consultarProdutoPorId(id);
        if (produto != null) {
            System.out.println(produto);
        } else {
            System.out.println("Produto não encontrado!");
        }
    }

    // Função para excluir um produto
    private static void excluirProduto() {
        System.out.print("ID do produto a ser excluído: ");
        int id = scanner.nextInt();
        produtoService.excluirProduto(id);
        System.out.println("Produto excluído com sucesso!");
    }
}
