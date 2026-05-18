package com.lanchonete.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe responsável pela conexão com o banco SQLite.
 * Cria automaticamente as tabelas se não existirem.
 */
public class ConexaoDB {
    private static final String URL = "jdbc:sqlite:lanchonete.db";

    public static Connection conectar() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);
        // Habilita foreign keys no SQLite (vem desabilitado por padrão)
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }
        return conn;
    }

    public static void inicializarBanco() {
        String sqlProduto = """
            CREATE TABLE IF NOT EXISTS produto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                preco REAL NOT NULL,
                tipo TEXT NOT NULL CHECK(tipo IN ('LANCHE', 'BEBIDA', 'SOBREMESA'))
            );
        """;

        String sqlLanche = """
            CREATE TABLE IF NOT EXISTS lanche (
                produto_id INTEGER PRIMARY KEY,
                ingredientes TEXT,
                tem_bacon INTEGER NOT NULL DEFAULT 0,
                tipo_carne TEXT,
                FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
            );
        """;

        String sqlBebida = """
            CREATE TABLE IF NOT EXISTS bebida (
                produto_id INTEGER PRIMARY KEY,
                volume_ml INTEGER NOT NULL,
                gelada INTEGER NOT NULL DEFAULT 0,
                com_gas INTEGER NOT NULL DEFAULT 0,
                FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
            );
        """;

        String sqlSobremesa = """
            CREATE TABLE IF NOT EXISTS sobremesa (
                produto_id INTEGER PRIMARY KEY,
                tem_lactose INTEGER NOT NULL DEFAULT 0,
                calorias INTEGER NOT NULL,
                FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
            );
        """;

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlProduto);
            stmt.execute(sqlLanche);
            stmt.execute(sqlBebida);
            stmt.execute(sqlSobremesa);
            System.out.println("✓ Banco de dados inicializado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }
}
