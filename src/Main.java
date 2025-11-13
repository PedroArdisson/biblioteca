import dao.LivroDAO;
import model.Livro;

public class Main {
    public static void main(String[] args) {

        LivroDAO livroDAO = new LivroDAO();

        Livro l1 = new Livro("O Livro dos Espíritos", "Allan Kardec");
        livroDAO.inserirLivro(l1);

        System.out.println("\n=== LIVROS CADASTRADOS ===");
        for (Livro livro : livroDAO.listarLivros()) {
            System.out.println(livro.getId() + " - " + livro.getNome() + " | Disponível: " + livro.isStatus());
        }

    }
}
