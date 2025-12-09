package dao;

import model.Livro;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public void inserirLivro(Livro livro) {
        String sql = "INSERT INTO livros (titulo, autor_medium, autor_espirito, disponivel) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, livro.getNome());
            pstmt.setString(2, livro.getAutorMedium());
            pstmt.setString(3, livro.getAutorEspirito());
            pstmt.setBoolean(4, livro.isStatus());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    livro.setId(rs.getInt(1));
                }
            }

            System.out.println("Livro inserido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir livro: " + e.getMessage());
        }
    }

    public List<Livro> listarLivros() {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT * FROM livros";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor_medium"),
                        rs.getString("autor_espirito")
                );

                livro.setId(rs.getInt("id"));
                livro.setStatus(rs.getBoolean("disponivel"));

                lista.add(livro);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar livros: " + e.getMessage());
        }

        return lista;
    }

    public List<Livro> buscarPorNome(String nomeParcial) {
        List<Livro> resultados = new ArrayList<>();
        String sql = "SELECT * FROM livros WHERE titulo LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + nomeParcial + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor_medium"),
                        rs.getString("autor_espirito")
                );

                livro.setId(rs.getInt("id"));
                livro.setStatus(rs.getBoolean("disponivel"));

                resultados.add(livro);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }

        return resultados;
    }

    public Livro buscarPorId(int id) {
        String sql = "SELECT * FROM livros WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor_medium"),
                        rs.getString("autor_espirito")
                );

                livro.setId(rs.getInt("id"));
                livro.setStatus(rs.getBoolean("disponivel"));

                return livro;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar livro por ID: " + e.getMessage());
        }

        return null;
    }

    public boolean deletarLivro(int id) {
        String sqlVerificar = """
                SELECT COUNT(*) AS total
                FROM emprestimos
                WHERE livro_id = ? AND data_devolucao IS NULL
                """;

        String sqlDelete = "DELETE FROM livros WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            try (PreparedStatement pstmt = conn.prepareStatement(sqlVerificar)) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next() && rs.getInt("total") > 0) {
                    System.out.println("⚠️ NÃO é possível deletar: o livro está emprestado!");
                    return false;
                }
            }

            try (PreparedStatement pstmt = conn.prepareStatement(sqlDelete)) {
                pstmt.setInt(1, id);
                int linhasAfetadas = pstmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("📕 Livro deletado com sucesso!");
                    return true;
                } else {
                    System.out.println("⚠️ Nenhum livro encontrado com esse ID.");
                    return false;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar livro: " + e.getMessage());
            return false;
        }
    }

    public void atualizarStatus(Livro livro) {
        String sql = "UPDATE livros SET disponivel = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, livro.isStatus());
            pstmt.setInt(2, livro.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status do livro: " + e.getMessage());
        }
    }
}
