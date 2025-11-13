package util;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseSetup {

    public static void initializeDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Tabela de usuários
            String createUsuariosTable = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nome TEXT NOT NULL,
                    email TEXT,
                    telefone TEXT
                );
            """;

            // Tabela de livros
            String createLivrosTable = """
                CREATE TABLE IF NOT EXISTS livros (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    titulo TEXT NOT NULL,
                    autor TEXT,
                    disponivel BOOLEAN DEFAULT 1
                );
            """;

            // Tabela de empréstimos
            String createEmprestimosTable = """
                CREATE TABLE IF NOT EXISTS emprestimos (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    usuario_id INTEGER,
                    livro_id INTEGER,
                    data_emprestimo TEXT,
                    data_devolucao TEXT,
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                    FOREIGN KEY (livro_id) REFERENCES livros(id)
                );
            """;

            stmt.execute(createUsuariosTable);
            stmt.execute(createLivrosTable);
            stmt.execute(createEmprestimosTable);

            System.out.println("✅ Banco de dados inicializado com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }
}
