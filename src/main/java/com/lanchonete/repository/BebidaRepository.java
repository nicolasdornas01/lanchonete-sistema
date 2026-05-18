package com.lanchonete.repository;

import com.lanchonete.models.Bebida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BebidaRepository {

    public void inserir(Bebida bebida) {
        String sqlProduto = "INSERT INTO produto (nome, preco, tipo) VALUES (?, ?, 'BEBIDA')";
        String sqlBebida = "INSERT INTO bebida (produto_id, volume_ml, gelada, com_gas) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlProduto, Statement.RETURN_GENERATED_KEYS)) {
                ps1.setString(1, bebida.getNome());
                ps1.setDouble(2, bebida.getPreco());
                ps1.executeUpdate();

                ResultSet keys = ps1.getGeneratedKeys();
                if (keys.next()) {
                    int idGerado = keys.getInt(1);
                    bebida.setId(idGerado);

                    try (PreparedStatement ps2 = conn.prepareStatement(sqlBebida)) {
                        ps2.setInt(1, idGerado);
                        ps2.setInt(2, bebida.getVolumeMl());
                        ps2.setInt(3, bebida.isGelada() ? 1 : 0);
                        ps2.setInt(4, bebida.isComGas() ? 1 : 0);
                        ps2.executeUpdate();
                    }
                }
                conn.commit();
                System.out.println("✓ Bebida inserida com ID " + bebida.getId());
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir bebida: " + e.getMessage());
        }
    }

    public List<Bebida> listarTodos() {
        List<Bebida> bebidas = new ArrayList<>();
        String sql = """
            SELECT p.id, p.nome, p.preco, b.volume_ml, b.gelada, b.com_gas
            FROM produto p
            INNER JOIN bebida b ON p.id = b.produto_id
            WHERE p.tipo = 'BEBIDA'
        """;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Bebida b = new Bebida(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getInt("volume_ml"),
                    rs.getInt("gelada") == 1,
                    rs.getInt("com_gas") == 1
                );
                bebidas.add(b);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar bebidas: " + e.getMessage());
        }
        return bebidas;
    }

    public Bebida buscarPorId(int id) {
        String sql = """
            SELECT p.id, p.nome, p.preco, b.volume_ml, b.gelada, b.com_gas
            FROM produto p
            INNER JOIN bebida b ON p.id = b.produto_id
            WHERE p.id = ?
        """;

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Bebida(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("volume_ml"),
                        rs.getInt("gelada") == 1,
                        rs.getInt("com_gas") == 1
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar bebida: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Bebida bebida) {
        String sqlProduto = "UPDATE produto SET nome = ?, preco = ? WHERE id = ?";
        String sqlBebida = "UPDATE bebida SET volume_ml = ?, gelada = ?, com_gas = ? WHERE produto_id = ?";

        try (Connection conn = ConexaoDB.conectar()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement(sqlProduto);
                 PreparedStatement ps2 = conn.prepareStatement(sqlBebida)) {

                ps1.setString(1, bebida.getNome());
                ps1.setDouble(2, bebida.getPreco());
                ps1.setInt(3, bebida.getId());
                ps1.executeUpdate();

                ps2.setInt(1, bebida.getVolumeMl());
                ps2.setInt(2, bebida.isGelada() ? 1 : 0);
                ps2.setInt(3, bebida.isComGas() ? 1 : 0);
                ps2.setInt(4, bebida.getId());
                int linhas = ps2.executeUpdate();

                conn.commit();
                return linhas > 0;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar bebida: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        String sql = "DELETE FROM produto WHERE id = ? AND tipo = 'BEBIDA'";
        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao deletar bebida: " + e.getMessage());
            return false;
        }
    }
}
