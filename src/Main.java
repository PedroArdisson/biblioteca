import dao.UsuarioDAO;
import model.Usuario;

public class Main {
    public static void main(String[] args) {

        UsuarioDAO dao = new UsuarioDAO();

        // Inserindo usuário de teste
        Usuario u = new Usuario("Pedro", "pedro@example.com", "99999-9999");
        dao.inserirUsuario(u);

        // Listando usuários
        System.out.println("\n=== Usuários cadastrados no banco ===");
        for (Usuario usuario : dao.listarUsuarios()) {
            System.out.println(usuario.getId() + ": " + usuario.getNome() + " - " + usuario.getEmail());
        }
    }
}
