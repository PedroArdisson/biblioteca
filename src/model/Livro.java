package model;

public class Livro {
    private int id;
    private String nome;
    private String autorMedium;
    private String autorEspirito;
    private boolean status;

    public Livro() {
    }

    public Livro(String nome, String autorMedium) {
        this.nome = nome;
        this.autorMedium = autorMedium;
        this.status = true;
    }

    public Livro(String nome, String autorMedium, String autorEspirito) {
        this.nome = nome;
        this.autorMedium = autorMedium;
        this.autorEspirito = autorEspirito;
        this.status = true;
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

    public String getAutorMedium() {
        return autorMedium;
    }

    public void setAutorMedium(String autorMedium) {
        this.autorMedium = autorMedium;
    }

    public String getAutorEspirito() {
        return autorEspirito;
    }

    public void setAutorEspirito(String autorEspirito) {
        this.autorEspirito = autorEspirito;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
