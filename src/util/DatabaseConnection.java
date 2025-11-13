package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:sqlite:C:/Users/Informática 01/Desktop/PROG/biblioteca-ceal/database/biblioteca.db?journal_mode=WAL";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("✅ Conexão com o banco SQLite estabelecida com sucesso!");

            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA journal_mode=WAL;");
            }

            return conn;

        } catch (SQLException e) {
            System.err.println("❌ Erro ao conectar com o banco de dados: " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("🔒 Conexão encerrada.");
            } catch (SQLException e) {
                System.err.println("⚠️ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
