package com.lanchonete.repository;

import com.lanchonete.models.Lanche;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LancheRepository {

    public void inserir(Lanche lanche) {
        String sqlProduto = "INSERT INTO produto (nome, preco, tipo) VALUES (?, ?, 'LANCHE')";
        String sqlLanche = "INSERT INTO lanche (produto_id, ingredientes, tem_bacon, tipo_carne) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, lanche.getNome());
                ps1.setDouble(2, lanche.getPreco());
                ps1.executeUpdate();

                ResultSet keys = ps1.getGeneratedKeys();
                if (keys.next()) {
                    int idGerado = keys.getInt(1);
                    lanche.setId(idGerado);

                    try (PreparedStatement ps2 = conn.prepareStatement(sqlLanche)) {
                        ps2.setInt(1, idGerado);
                        ps2.setString(2, lanche.getIngredientes());
                        ps2.setInt(3, lanche.isTemBacon() ? 1 : 0);
                        ps2.setString(4, lanche.getTipoCarne());
                        ps2.executeUpdate();
                    }
                }
                conn.commit();
                System.out.println("✓ Lanche inserido com ID " + lanche.getId());
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir lanche: " + e.getMessage());
        }
    }

    public List<Lanche> listarTodos() {
        List<Lanche> lanches = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nome, p.preco, l.ingredientes, l.tem_bacon, l.tipo_carne
            FROM produto p
            INNER JOIN lanche l ON p.id = l.produto_id
            WHERE p.tipo = 'LANCHE'
        """;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lanche l = new Lanche(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("ingredientes"),
                    rs.getInt("tem_bacon") == 1,
                    rs.getString("tipo_carne")
                );
                lanches.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar lanches: " + e.getMessage());
        }
        return lanches;
    }

    public Lanche buscarPorId(int id) {
        String sql = """
            SELECT p.id, p.nome, p.preco, l.ingredientes, l.tem_bacon, l.tipo_carne
            FROM produto p
            INNER JOIN lanche l ON p.id = l.produto_id
            WHERE p.id = ?
        """;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Lanche(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getString("ingredientes"),
                        rs.getInt("tem_bacon") == 1,
                        rs.getString("tipo_carne")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar lanche: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Lanche lanche) {
        String sqlProduto = "UPDATE produto SET nome = ?, preco = ? WHERE id = ?";
        String sqlLanche = "UPDATE lanche SET ingredientes = ?, tem_bacon = ?, tipo_carne = ? WHERE produto_id = ?";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlProduto);
                 PreparedStatement ps2 = conn.prepareStatement(sqlLanche)) {

                ps1.setString(1, lanche.getNome());
                ps1.setDouble(2, lanche.getPreco());
                ps1.setInt(3, lanche.getId());
                ps1.executeUpdate();

                ps2.setString(1, lanche.getIngredientes());
                ps2.setInt(2, lanche.isTemBacon() ? 1 : 0);
                ps2.setString(3, lanche.getTipoCarne());
                ps2.setInt(4, lanche.getId());
                int linhas = ps2.executeUpdate();

                conn.commit();
                return linhas > 0;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar lanche: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        // Por causa do ON DELETE CASCADE, apagar da tabela produto já apaga da tabela lanche
        String sql = "DELETE FROM produto WHERE id = ? AND tipo = 'LANCHE'";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar lanche: " + e.getMessage());
            return false;
        }
    }
}
