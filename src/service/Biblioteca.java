package service;

import model.Emprestimo;
import model.Livro;
import model.Usuario;

import java.util.Scanner;

public class Biblioteca {
    Usuario[] usuarios;
    Livro[] livros;
    Emprestimo[] emprestimos;

    private int qtdUsuarios;
    private int qtdLivros;
    private int qtdEmprestimos;

    private final int MAX_LIVROS_POR_USUARIO = 5;

    public Biblioteca(int maxUsuarios, int maxLivros, int maxEmprestimos){
        usuarios = new Usuario[maxUsuarios];
        livros = new Livro[maxLivros];
        emprestimos = new Emprestimo[maxEmprestimos];
        qtdUsuarios = 0;
        qtdLivros = 0;
        qtdEmprestimos = 0;
    }

    public void cadastrarLivro(String nome, String autorMedium, String autorEspirito){
        if(qtdLivros<livros.length){
            Livro novoLivro;

            if (autorEspirito == null || autorEspirito.isEmpty()){
                novoLivro = new Livro(nome, autorMedium);
            } else {
                novoLivro = new Livro(nome, autorMedium, autorEspirito);
            }

            livros[qtdLivros] = novoLivro;
            qtdLivros++;

            System.out.println("Livro cadastrado com sucesso: "+nome);
        } else {
            System.out.println("Limite máximo de livros atingidos!");
        }
    }

    public void cadastrarUsuarios(String nome, String email, String telefone){
        if (qtdUsuarios<usuarios.length){
            Usuario novoUsuario;
            novoUsuario = new Usuario(nome, email, telefone);
            usuarios[qtdUsuarios] = novoUsuario;
            qtdUsuarios ++;
            System.out.println("Usuário cadastrado com sucesso: "+nome);
        } else {
            System.out.println("Limite máximo de usuários atingido!");
        }
    }
    
    public Usuario buscarUsuarioPorId(int id){
        for (int i = 0; i < qtdUsuarios; i++) {
            if (usuarios[i].getId()==id){
                return usuarios[i];
            }
        }
        return null;
    }

    public Usuario buscarUsuarioPorNome(String nome){
        for (int i = 0; i < qtdUsuarios; i++) {
            if (usuarios[i].getNome().equalsIgnoreCase(nome)){
                return usuarios[i];
            }
        }
        return null;
    }

    public Livro buscarLivroPorId(int id){
        for (int i = 0; i < qtdLivros; i++) {
            if (livros[i].getId()==id){
                return livros[i];
            }
        }
        return null;
    }

    public Livro buscarLivroPorNome(String nome){
        for (int i = 0; i < qtdLivros; i++) {
            if (livros[i].getNome().equalsIgnoreCase(nome)){
                return livros[i];
            }
        }
        return null;
    }

    public void realizarEmprestimo(){
        Scanner sc = new Scanner(System.in);

        System.out.println("=== REALIZAR EMPRÉSTIMO ===");
        System.out.print("Deseja buscar o usuário por [1] ID ou [2] Nome? ");
        int opcao = sc.nextInt();
        sc.nextLine();

        Usuario usuario = null;

        if (opcao==1){
            System.out.print("Digite o ID do Usuário: ");
            int idUsuario = sc.nextInt();
            sc.nextLine();
            usuario = buscarUsuarioPorId(idUsuario);
        }

        else if (opcao == 2){
            System.out.print("Digite o nome do usuário: ");
            String nomeUsuario = sc.nextLine();
            usuario = buscarUsuarioPorNome(nomeUsuario);
        }

        else{
            System.out.println("Opção Inválida");
            return;
        }

        System.out.println("Digite o nome do livro: ");
        String nomeLivro = sc.nextLine();
        Livro livro = buscarLivroPorNome(nomeLivro);

        if (livro == null){
            System.out.println("Livro não encontrado!");
            return;
        }

        if (usuario.getLivrosEmprestados()>= MAX_LIVROS_POR_USUARIO){
            System.out.println("⚠️ O usuário " + usuario.getNome() +
                    " já atingiu o limite de " + MAX_LIVROS_POR_USUARIO + " livros emprestados.");
            return;
        }

        if (!livro.isStatus()){
            System.out.println("⚠️ O livro \"" + livro.getNome() + "\" já está emprestado!");
            return;
        }

        Emprestimo novoEmprestimo = new Emprestimo(usuario, livro);
        emprestimos[qtdEmprestimos] = novoEmprestimo;
        qtdEmprestimos++;

        System.out.println("\nEmpréstimo realizado com sucesso!");
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Livro: " + livro.getNome());
        System.out.println("Devolução até: " + novoEmprestimo.getDataLimiteDevolucao());

    }

}
