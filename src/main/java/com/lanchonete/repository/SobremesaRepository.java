package com.lanchonete.repository;

import com.lanchonete.models.Sobremesa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SobremesaRepository {

    public void inserir(Sobremesa sobremesa) {
        String sqlProduto = "INSERT INTO produto (nome, preco, tipo) VALUES (?, ?, 'SOBREMESA')";
        String sqlSobremesa = "INSERT INTO sobremesa (produto_id, tem_lactose, calorias) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, sobremesa.getNome());
                ps1.setDouble(2, sobremesa.getPreco());
                ps1.executeUpdate();

                ResultSet keys = ps1.getGeneratedKeys();
                if (keys.next()) {
                    int idGerado = keys.getInt(1);
                    sobremesa.setId(idGerado);

                    try (PreparedStatement ps2 = conn.prepareStatement(sqlSobremesa)) {
                        ps2.setInt(1, idGerado);
                        ps2.setInt(2, sobremesa.isTemLactose() ? 1 : 0);
                        ps2.setInt(3, sobremesa.getCalorias());
                        ps2.executeUpdate();
                    }
                }
                conn.commit();
                System.out.println("✓ Sobremesa inserida com ID " + sobremesa.getId());
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir sobremesa: " + e.getMessage());
        }
    }

    public List<Sobremesa> listarTodos() {
        List<Sobremesa> sobremesas = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nome, p.preco, s.tem_lactose, s.calorias
            FROM produto p
            INNER JOIN sobremesa s ON p.id = s.produto_id
            WHERE p.tipo = 'SOBREMESA'
        """;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sobremesa s = new Sobremesa(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("tem_lactose") == 1,
                    rs.getInt("calorias")
                );
                sobremesas.add(s);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar sobremesas: " + e.getMessage());
        }
        return sobremesas;
    }

    public Sobremesa buscarPorId(int id) {
        String sql = """
            SELECT p.id, p.nome, p.preco, s.tem_lactose, s.calorias
            FROM produto p
            INNER JOIN sobremesa s ON p.id = s.produto_id
            WHERE p.id = ?
        """;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sobremesa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("tem_lactose") == 1,
                        rs.getInt("calorias")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar sobremesa: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Sobremesa sobremesa) {
        String sqlProduto = "UPDATE produto SET nome = ?, preco = ? WHERE id = ?";
        String sqlSobremesa = "UPDATE sobremesa SET tem_lactose = ?, calorias = ? WHERE produto_id = ?";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlProduto);
                 PreparedStatement ps2 = conn.prepareStatement(sqlSobremesa)) {

                ps1.setString(1, sobremesa.getNome());
                ps1.setDouble(2, sobremesa.getPreco());
                ps1.setInt(3, sobremesa.getId());
                ps1.executeUpdate();

                ps2.setInt(1, sobremesa.isTemLactose() ? 1 : 0);
                ps2.setInt(2, sobremesa.getCalorias());
                ps2.setInt(3, sobremesa.getId());
                int linhas = ps2.executeUpdate();

                conn.commit();
                return linhas > 0;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar sobremesa: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ? AND tipo = 'SOBREMESA'";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar sobremesa: " + e.getMessage());
            return false;
        }
    }
}
