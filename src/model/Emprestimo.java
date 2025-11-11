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
}
