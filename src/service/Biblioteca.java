package service;

import model.Emprestimo;
import model.Livro;
import model.Usuario;

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

    public void realizarEmprestimo(int idUsuario, int idLivro){
        if(qtdEmprestimos >= emprestimos.length){
            System.out.println("O sistema atingiu o limite máximo de empréstimos simultâneos!");
        }

        Usuario usuario = buscarUsuarioPorId(idUsuario);
        Livro livro = buscarLivroPorId(idLivro);

        if (usuario==null){
            System.out.println();
        }
    }

}
