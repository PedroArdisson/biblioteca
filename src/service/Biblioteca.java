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


    public void cadastrarUsuario(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Usuario usuario = new Usuario(nome, email, telefone);
        usuarioDAO.inserirUsuario(usuario);
    }

    public void cadastrarLivro(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();

        System.out.print("Médium: ");
        String autorMedium = scanner.nextLine();

        System.out.println("Espirito(opcional): ");
        String autorEspirito = scanner.nextLine();

        Livro livro;

        if (autorEspirito.isEmpty()){
            livro = new Livro(titulo, autorMedium);
        } else {
            livro = new Livro(titulo, autorMedium, autorEspirito);
        }
    }

    public Usuario escolherUsuarioPorNomeParcial(String nome){
        List<Usuario> lista = usuarioDAO.buscarPorNomeParcial(nome);

        if (lista.isEmpty()){
            System.out.println("Nenhum usuário encontrado");
            return null;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i+1)+" - "+ lista.get(i).getNome());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Escolha: ");
        int escolha = scanner.nextInt();

        return lista.get(escolha-1);
    }

    public Livro escolherLivroPorNomeParcial (String nome){
        List<Livro> lista = livroDAO.buscarPorNome(nome);

        if (lista.isEmpty()){
            System.out.println("Nenhum livro encontrado");
            return null;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i+1)+" - "+lista.get(i).getNome());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Escolha: ");
        int escolha = scanner.nextInt();

        return lista.get(escolha-1);
    }

    public void realizaEmprestimno(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do usuario: ");
        Usuario usuario = escolherUsuarioPorNomeParcial(scanner.nextLine());

        if (usuario==null){
            return;
        }

        int emprestimosAtivos = emprestimoDAO.buscarEmprestimosAtivosPorUsuario(usuario.getId()).size();

        if (emprestimosAtivos >= MAX_LIVROS_POR_USUARIO){
            System.out.println("Usuario atingiu o limite de emprestimos simultaneos.");
            return;
        }

        System.out.print("Digite o nome do livro: ");
        Livro livro = escolherLivroPorNomeParcial(scanner.nextLine());

        if (livro == null) return;

        if (!livro.isStatus()){
            System.out.println("Livro já emprestado.");
            return;
        }

        Emprestimo emprestimo = new Emprestimo(usuario, livro);

        livro.setStatus(false);
        livroDAO.atualizarStatus(livro);

        emprestimoDAO.inserirEmprestimo(emprestimo);

        System.out.println("Emprestimo realizado com sucesso!");
    }

    public void devolverLivro() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite parte do nome do usuário: ");
        Usuario usuario = escolherUsuarioPorNomeParcial(sc.nextLine());

        if (usuario == null) return;

        var lista = emprestimoDAO.buscarEmprestimosAtivosPorUsuario(usuario.getId());

        if (lista.isEmpty()) {
            System.out.println("Esse usuário não tem livros.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i+1) + " - " + lista.get(i).getLivro().getNome());
        }

        System.out.print("Escolha: ");
        int escolha = sc.nextInt();

        Emprestimo e = lista.get(escolha - 1);

        emprestimoDAO.registrarDevolucao(e.getId());

        Livro livro = e.getLivro();
        livro.setStatus(true);
        livroDAO.atualizarStatus(livro);

        System.out.println("Devolução concluída!");
    }

}
