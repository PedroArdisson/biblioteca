import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.UsuarioDAO;
import model.Emprestimo;
import model.Livro;
import model.Usuario;
import service.Biblioteca;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Biblioteca biblioteca = new Biblioteca();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LivroDAO livroDAO = new LivroDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
        Scanner sc = new Scanner(System.in);

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== MENU BIBLIOTECA CEAL ===");
            System.out.println("1 - Cadastrar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Cadastrar livro");
            System.out.println("4 - Listar livros");
            System.out.println("5 - Realizar empréstimo");
            System.out.println("6 - Devolver livro");
            System.out.println("7 - Listar empréstimos ativos de um usuário");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Opção inválida.");
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1 -> biblioteca.cadastrarUsuario();

                case 2 -> {
                    List<Usuario> usuarios = usuarioDAO.listarUsuarios();
                    if (usuarios.isEmpty()) {
                        System.out.println("Nenhum usuário cadastrado.");
                    } else {
                        System.out.println("\n=== USUÁRIOS ===");
                        for (Usuario u : usuarios) {
                            System.out.println(u.getId() + " - " + u.getNome() +
                                    " (" + u.getEmail() + ", " + u.getTelefone() + ")");
                        }
                    }
                }

                case 3 -> biblioteca.cadastrarLivro();

                case 4 -> {
                    List<Livro> livros = livroDAO.listarLivros();
                    if (livros.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado.");
                    } else {
                        System.out.println("\n=== LIVROS ===");
                        for (Livro l : livros) {
                            System.out.println(l.getId() + " - " + l.getNome() +
                                    " | Médium: " + l.getAutorMedium() +
                                    " | Espírito: " + l.getAutorEspirito() +
                                    " | Disponível: " + (l.isStatus() ? "Sim" : "Não"));
                        }
                    }
                }

                case 5 -> biblioteca.realizarEmprestimo();

                case 6 -> biblioteca.devolverLivro();2

                case 7 -> {
                    System.out.print("Digite parte do nome do usuário: ");
                    String busca = sc.nextLine();
                    Usuario u = biblioteca.escolherUsuarioPorNomeParcial(busca);
                    if (u == null) break;

                    List<Emprestimo> emps = emprestimoDAO.buscarEmprestimosAtivosPorUsuario(u.getId());
                    if (emps.isEmpty()) {
                        System.out.println("Nenhum empréstimo ativo para esse usuário.");
                    } else {
                        System.out.println("\n=== EMPRÉSTIMOS ATIVOS DE " + u.getNome() + " ===");
                        for (Emprestimo e : emps) {
                            System.out.println("Empréstimo ID: " + e.getId()
                                    + " | Livro: " + e.getLivro().getNome()
                                    + " | Data empréstimo: " + e.getDataEmprestimo()
                            );
                        }
                    }
                }

                case 0 -> System.out.println("Encerrando...");

                default -> System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }
}
