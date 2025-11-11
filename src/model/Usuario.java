package model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private int livrosEmprestados;

    private static int proximoId = 1;

    public void emprestarLivros(){
        if (livrosEmprestados<5){
            livrosEmprestados++;
        }
        else{
            System.out.println("Limite de livros emprestados atingido para "+nome);
        }
    }

    public void devolverLivros(){
        if (livrosEmprestados>0){
            livrosEmprestados--;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", livrosEmprestados=" + livrosEmprestados +
                '}';
    }

    public Usuario(String nome, String email, String telefone) {
        this.id = proximoId;
        proximoId++;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.livrosEmprestados = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void setLivrosEmprestados(int livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }

    public static int getProximoId() {
        return proximoId;
    }

    public static void setProximoId(int proximoId) {
        Usuario.proximoId = proximoId;
    }
}
