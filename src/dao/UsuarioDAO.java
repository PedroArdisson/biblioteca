package dao;

import model.Usuario;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void inserirUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email, telefone) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNome());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getTelefone());
            pstmt.executeUpdate();

            System.out.println("Usuário inserido com sucesso no banco!");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("telefone")
                );
                u.setId(rs.getInt("id"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }

        return lista;
    }
}
