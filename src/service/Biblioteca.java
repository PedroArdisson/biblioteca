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
            usuario = buscarUsuarioPorNomeParcial(nomeUsuario);
        }

        else{
            System.out.println("Opção Inválida");
            return;
        }

        System.out.println("Digite o nome do livro: ");
        String nomeLivro = sc.nextLine();
        Livro livro = buscarLivroPorNomeParcial(nomeLivro);

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
        if (novoEmprestimo != null) {
            System.out.println("Devolução até: " + novoEmprestimo.getDataLimiteDevolucao());
        }

    }

    public void exibirEmprestimosAtivos(){
        System.out.println("=== RELATÓRIO DE EMPRÉSTIMOS ATIVOS ===");

        boolean encontrou = false;

        for (int i = 0; i < qtdEmprestimos; i++) {
            Emprestimo e = emprestimos[i];

            if(e != null && e.isAtivo()){
                System.out.println("Empréstimo ID: " + (i + 1));
                System.out.println("Usuário: " + e.getUsuario().getNome());
                System.out.println("Livro: " + e.getLivro().getNome());
                System.out.println("Data do Empréstimo: " + e.getDataEmprestimo());
                System.out.println("Data Limite: " + e.getDataLimiteDevolucao());
                System.out.println("-----------------------------------");
                encontrou = true;
            }
        }
        if (!encontrou){
            System.out.println("Nenhum empréstimo ativo no momento");
        }
    }

    public void devolverLivro(){
        Scanner sc = new Scanner(System.in);

        System.out.println("=== DEVOLUÇÃO DE LIVROS ===");
        System.out.print("Deseja buscar o usuário por [1] ID ou [2] nome? ");
        int opcao = sc.nextInt();
        sc.nextLine();

        Usuario usuario = null;

        if (opcao==1){
            System.out.print("Digite o ID do Usuário: ");
            int idUsuario = sc.nextInt();
            sc.nextLine();
            usuario = buscarUsuarioPorId(idUsuario);
        } else if (opcao==2) {
            System.out.print("Digite o nome do usuário: ");
            String nomeUsuario = sc.nextLine();
            usuario = buscarUsuarioPorNome(nomeUsuario);
        } else {
            System.out.println("Opção Inválida!");
        }

        if (usuario==null){
            System.out.println("Usuário não encontrado!");
            return;
        }

        Emprestimo[] emprestimosUsuario = new Emprestimo[qtdEmprestimos];
        int count = 0;

        for (int i = 0; i < qtdEmprestimos; i++) {
            if (emprestimos[i].isAtivo() && emprestimos[i].getUsuario().equals(usuario)){
                emprestimosUsuario[count] = emprestimos [i];
                count++;
            }
        }

        if (count==0){
            System.out.println("O usuário "+usuario.getNome()+" não possui livros emprestados.");
            return;
        }

        System.out.println("\nLivros emprestados por "+usuario.getNome()+":");
        for (int i = 0; i < count; i++) {
            System.out.println("["+(i+1)+"]"+emprestimosUsuario[i].getLivro().getNome());
        }

        System.out.println("\nEscolha o número do livro a devolver: ");
        int escolha = sc.nextInt();
        sc.nextLine();

        if (escolha < 1 || escolha > count){
            System.out.println("Opção inválida!");
            return;
        }

        Emprestimo emprestimoSelecionado = emprestimosUsuario[escolha-1];
        emprestimoSelecionado.devolverLivros();
    }

    public Usuario buscarUsuarioPorNomeParcial(String nomeParcial){
        Usuario[] resultados = new Usuario[qtdUsuarios];
        int qtdResultados = 0;

        for (int i = 0; i < qtdUsuarios; i++) {
            if (usuarios[i].getNome().toLowerCase().contains(nomeParcial.toLowerCase())){
                resultados[qtdResultados] = usuarios[i];
                qtdResultados++;
            }
        }

        if(qtdResultados == 0){
            System.out.println("Nenhum usuário encontrado com esse nome.");
            return null;
        }

        System.out.println("\nUsuários encontrados:");
        for (int i = 0; i < qtdResultados; i++) {
            System.out.println((i+1)+" - "+ resultados[i].getNome()+" (ID: "+ resultados[i].getId()+")");
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o número do usuário desejado: ");
        int escolha = sc.nextInt();

        if (escolha<1 || escolha> qtdResultados){
            System.out.println("opção inválida!");
            return null;
        }

        return resultados[escolha-1];
    }

    public Livro buscarLivroPorNomeParcial(String nomeParcial){
        Livro[] resultados = new Livro[qtdLivros];
        int qtdResultados = 0;

        for (int i = 0; i < qtdLivros; i++) {
            if (livros[i].getNome().toLowerCase().contains(nomeParcial.toLowerCase())){
                resultados[qtdResultados] = livros[i];
                qtdResultados++;
            }
        }

        if (qtdResultados==0){
            System.out.println("Nenhum livro encontrado com esse nome.");
            return null;
        }

        System.out.println("\nLivros encontrados:");
        for (int i = 0; i < qtdResultados; i++) {
            System.out.println((i+1)+" - "+ resultados[i].getNome()+" (ID: "+resultados[i].getId()+")");
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o número do livro desejado: ");
        int escolha = sc.nextInt();

        if (escolha < 1 || escolha > qtdResultados){
            System.out.println("Opção Inválida!");
            return null;
        }

        return resultados[escolha-1];
    }

}
