package model;

import java.time.LocalDate;

public class Emprestimo {

    private int id;
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataLimiteDevolucao;
    private LocalDate dataDevolucao; // para usar com o banco
    private boolean ativo;

    // ==============================================
    // 1) Construtor vazio — usado pelo DAO
    // ==============================================
    public Emprestimo() {
    }

    // ==============================================
    // 2) Construtor usado quando o sistema cria um empréstimo
    // ==============================================
    public Emprestimo(Usuario usuario, Livro livro) {
        this.usuario = usuario;
        this.livro = livro;

        this.dataEmprestimo = LocalDate.now();
        this.dataLimiteDevolucao = dataEmprestimo.plusDays(30);
        this.dataDevolucao = null;

        this.ativo = true;

        // efeitos colaterais quando cria empréstimo real
        livro.setStatus(false);
        usuario.emprestarLivros();
    }

    // ==============================================
    // Métodos
    // ==============================================
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataLimiteDevolucao() {
        return dataLimiteDevolucao;
    }

    public void setDataLimiteDevolucao(LocalDate dataLimiteDevolucao) {
        this.dataLimiteDevolucao = dataLimiteDevolucao;
    }
}
