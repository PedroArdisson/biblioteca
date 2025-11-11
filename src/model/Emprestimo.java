package model;

import java.time.LocalDate;

public class Emprestimo {
    private int id;
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataLimiteDevolucao;
    private boolean ativo;

    private static int proximoId = 1;

    public void devolverLivros(){
        if(ativo){
            ativo = false;
            livro.setStatus(true);
            usuario.devolverLivros();
            System.out.println("Livro: "+livro.getNome()+" devolvido com sucesso por "+ usuario.getNome()+"!");
        }
        else{
            System.out.println("Esse livro já foi devolvido anteriormente!");
        }
    }

    public Emprestimo(Usuario usuario, Livro livro) {
        this.id = proximoId;
        proximoId++;
        this.usuario = usuario;
        this.livro = livro;
        dataEmprestimo = LocalDate.now();
        dataLimiteDevolucao = dataEmprestimo.plusDays(30);
        ativo = true;
        livro.setStatus(false);
        usuario.emprestarLivros();
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

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public static int getProximoId() {
        return proximoId;
    }

    public static void setProximoId(int proximoId) {
        Emprestimo.proximoId = proximoId;
    }
}
