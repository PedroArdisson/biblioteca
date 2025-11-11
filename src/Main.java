import model.Livro;
import model.Usuario;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Livro l1 = new Livro("Nosso Lar", "Chico Xavier", "André Luiz");
        Livro l2 = new Livro("Contos desta e doutra vida", "Chico Xavier", "Humberto de Campos");
        Livro l3 = new Livro("Boa Nova", "Chico Xavier", "Humberto de Campos");

        Usuario u1 = new Usuario("Pedro", "pedroardisson2003@gmail.com", "21996164335");

        System.out.println(l3.getNome());
        System.out.println(u1);
    }
}