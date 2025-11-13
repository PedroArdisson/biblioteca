package dao;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TestaConexao {
    public static void main(String[] args) {
        try {
            Connection conexao = DatabaseConnection.getConnection();
            if (conexao != null) {
                System.out.println("✅ Conexão estabelecida com sucesso!");
                conexao.close();
            } else {
                System.out.println("❌ Falha ao conectar com o banco de dados.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Erro ao conectar com o banco de dados:");
            e.printStackTrace();
        }
    }
}
