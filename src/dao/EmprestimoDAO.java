package dao;

import model.Emprestimo;
import model.Livro;
import util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public void inserirEmprestimo(Emprestimo e) {
        String sql = "INSERT INTO emprestimos (usuario_id, livro_id, data_emprestimo, data_devolucao) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, e.getUsuario().getId());
            pstmt.setInt(2, e.getLivro().getId());
            pstmt.setString(3, e.getDataEmprestimo().toString());
            pstmt.setString(4, e.getDataLimiteDevolucao().toString());

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Erro ao inserir empréstimo: " + ex.getMessage());
        }
    }

    public boolean registrarDevolucao(int idEmprestimo) {
        String sql = "UPDATE emprestimos SET data_devolucao = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, LocalDate.now().toString());
            pstmt.setInt(2, idEmprestimo);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao registrar devolução: " + e.getMessage());
            return false;
        }
    }

    public List<Emprestimo> buscarEmprestimosAtivosPorUsuario(int usuarioId) {
        List<Emprestimo> lista = new ArrayList<>();

        String sql = """
            SELECT e.id, e.data_emprestimo, l.id AS livro_id, l.titulo, l.autor_medium, l.autor_espirito, l.disponivel
            FROM emprestimos e
            JOIN livros l ON l.id = e.livro_id
            WHERE e.usuario_id = ? AND e.data_devolucao IS NULL
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, usuarioId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor_medium"),
                        rs.getString("autor_espirito")
                );
                livro.setId(rs.getInt("livro_id"));
                livro.setStatus(rs.getBoolean("disponivel"));

                Emprestimo e = new Emprestimo(null, null);
                e.setId(rs.getInt("id"));
                e.setLivro(livro);
                e.setDataEmprestimo(LocalDate.parse(rs.getString("data_emprestimo")));

                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar empréstimos: " + e.getMessage());
        }

        return lista;
    }

    public Emprestimo buscarPorId(int id) {
        String sql = """
            SELECT id, data_emprestimo, data_devolucao
            FROM emprestimos WHERE id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Emprestimo e = new Emprestimo(null, null);
                e.setId(id);
                e.setDataEmprestimo(LocalDate.parse(rs.getString("data_emprestimo")));

                String devolucao = rs.getString("data_devolucao");
                if (devolucao != null)
                    e.setDataLimiteDevolucao(LocalDate.parse(devolucao));

                return e;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar empréstimo: " + e.getMessage());
        }

        return null;
    }
}
