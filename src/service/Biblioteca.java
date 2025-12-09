package service;

import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.UsuarioDAO;
import model.Emprestimo;
import model.Livro;
import model.Usuario;

import java.util.List;
import java.util.Scanner;

public class Biblioteca {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private LivroDAO livroDAO = new LivroDAO();
    private EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    private final int MAX_LIVROS_POR_USUARIO = 5;

    private final Scanner sc = new Scanner(System.in);

    // ===============================================================
    // CADASTRO DE USUÁRIO
    // ===============================================================
    public void cadastrarUsuario() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        Usuario usuario = new Usuario(nome, email, telefone);
        usuarioDAO.inserirUsuario(usuario);

        System.out.println("Usuário cadastrado!");
    }

    // ===============================================================
    // CADASTRO DE LIVRO
    // ===============================================================
    public void cadastrarLivro() {
        System.out.print("Título: ");
        String titulo = sc.nextLine();

        System.out.print("Médium: ");
        String autorMedium = sc.nextLine();

        System.out.print("Espírito (opcional): ");
        String autorEspirito = sc.nextLine();

        Livro livro;

        if (autorEspirito.isEmpty()) {
            livro = new Livro(titulo, autorMedium);
        } else {
            livro = new Livro(titulo, autorMedium, autorEspirito);
        }

        livroDAO.inserirLivro(livro);
        System.out.println("Livro cadastrado!");
    }

    // ===============================================================
    // ESCOLHER USUÁRIO POR NOME PARCIAL
    // ===============================================================
    public Usuario escolherUsuarioPorNomeParcial(String nome) {
        List<Usuario> lista = usuarioDAO.buscarPorNomeParcial(nome);

        if (lista.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
            return null;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getNome());
        }

        System.out.print("Escolha: ");
        int escolha = lerNumero(lista.size());

        return lista.get(escolha - 1);
    }

    // ===============================================================
    // ESCOLHER LIVRO POR NOME PARCIAL
    // ===============================================================
    public Livro escolherLivroPorNomeParcial(String nome) {
        List<Livro> lista = livroDAO.buscarPorNome(nome);

        if (lista.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return null;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getNome());
        }

        System.out.print("Escolha: ");
        int escolha = lerNumero(lista.size());

        return lista.get(escolha - 1);
    }

    // ===============================================================
    // REALIZAR EMPRÉSTIMO
    // ===============================================================
    public void realizarEmprestimo() {

        System.out.print("Digite o nome do usuário: ");
        Usuario usuario = escolherUsuarioPorNomeParcial(sc.nextLine());

        if (usuario == null) return;

        int emprestimosAtivos = emprestimoDAO.buscarEmprestimosAtivosPorUsuario(usuario.getId()).size();

        if (emprestimosAtivos >= MAX_LIVROS_POR_USUARIO) {
            System.out.println("Usuário atingiu o limite de empréstimos.");
            return;
        }

        System.out.print("Digite o nome do livro: ");
        Livro livro = escolherLivroPorNomeParcial(sc.nextLine());

        if (livro == null) return;

        if (!livro.isStatus()) {
            System.out.println("Livro já está emprestado.");
            return;
        }

        // cria empréstimo
        Emprestimo emprestimo = new Emprestimo(usuario, livro);

        // atualiza banco
        emprestimoDAO.inserirEmprestimo(emprestimo);
        livroDAO.atualizarStatus(livro);

        System.out.println("Empréstimo realizado!");
    }

    // ===============================================================
    // DEVOLVER LIVRO
    // ===============================================================
    public void devolverLivro() {
        System.out.print("Digite parte do nome do usuário: ");
        Usuario usuario = escolherUsuarioPorNomeParcial(sc.nextLine());

        if (usuario == null) return;

        var lista = emprestimoDAO.buscarEmprestimosAtivosPorUsuario(usuario.getId());

        if (lista.isEmpty()) {
            System.out.println("Esse usuário não tem livros emprestados.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i).getLivro().getNome());
        }

        System.out.print("Escolha: ");
        int escolha = lerNumero(lista.size());

        Emprestimo e = lista.get(escolha - 1);

        emprestimoDAO.registrarDevolucao(e.getId());

        Livro livro = e.getLivro();
        livro.setStatus(true);
        livroDAO.atualizarStatus(livro);

        System.out.println("Devolução concluída!");
    }

    // ===============================================================
    // MÉTODO AUXILIAR — SEGURANÇA NAS ESCOLHAS
    // ===============================================================
    private int lerNumero(int max) {
        int numero;

        while (true) {
            try {
                numero = Integer.parseInt(sc.nextLine());

                if (numero < 1 || numero > max) {
                    System.out.print("Opção inválida. Digite entre 1 e " + max + ": ");
                } else {
                    return numero;
                }

            } catch (Exception e) {
                System.out.print("Entrada inválida. Digite um número: ");
            }
        }
    }
}
