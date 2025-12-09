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
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, e.getUsuario().getId());
            pstmt.setInt(2, e.getLivro().getId());
            pstmt.setString(3, e.getDataEmprestimo().toString());
            pstmt.setNull(4, Types.VARCHAR);

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getInt(1));
                }
            }

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
                SELECT e.id, e.data_emprestimo,
                       l.id AS livro_id, l.titulo, l.autor_medium, l.autor_espirito, l.disponivel
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

                Emprestimo e = new Emprestimo();
                e.setId(rs.getInt("id"));
                e.setLivro(livro);

                LocalDate dataEmp = LocalDate.parse(rs.getString("data_emprestimo"));
                e.setDataEmprestimo(dataEmp);
                e.setDataLimiteDevolucao(dataEmp.plusDays(30));

                e.setDataDevolucao(null);
                e.setAtivo(true);

                lista.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar empréstimos: " + e.getMessage());
        }

        return lista;
    }

    public Emprestimo buscarPorId(int id) {
        String sql = """
                SELECT e.id, e.data_emprestimo, e.data_devolucao,
                       l.id AS livro_id, l.titulo, l.autor_medium, l.autor_espirito, l.disponivel
                FROM emprestimos e
                JOIN livros l ON l.id = e.livro_id
                WHERE e.id = ?
                """;

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
                livro.setId(rs.getInt("livro_id"));
                livro.setStatus(rs.getBoolean("disponivel"));

                Emprestimo e = new Emprestimo();
                e.setId(id);
                e.setLivro(livro);

                LocalDate dataEmp = LocalDate.parse(rs.getString("data_emprestimo"));
                e.setDataEmprestimo(dataEmp);
                e.setDataLimiteDevolucao(dataEmp.plusDays(30));

                String dataDev = rs.getString("data_devolucao");
                if (dataDev != null) {
                    e.setDataDevolucao(LocalDate.parse(dataDev));
                    e.setAtivo(false);
                } else {
                    e.setDataDevolucao(null);
                    e.setAtivo(true);
                }

                return e;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar empréstimo por ID: " + e.getMessage());
        }

        return null;
    }
}
