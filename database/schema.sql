-- Tabela base de produtos
CREATE TABLE IF NOT EXISTS produto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    preco REAL NOT NULL,
    tipo TEXT NOT NULL CHECK(tipo IN ('LANCHE', 'BEBIDA', 'SOBREMESA'))
);

-- Atributos específicos de Lanche
CREATE TABLE IF NOT EXISTS lanche (
    produto_id INTEGER PRIMARY KEY,
    ingredientes TEXT,
    tem_bacon INTEGER NOT NULL DEFAULT 0,
    tipo_carne TEXT,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
);

-- Atributos específicos de Bebida
CREATE TABLE IF NOT EXISTS bebida (
    produto_id INTEGER PRIMARY KEY,
    volume_ml INTEGER NOT NULL,
    gelada INTEGER NOT NULL DEFAULT 0,
    com_gas INTEGER NOT NULL DEFAULT 0,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
);

-- Atributos específicos de Sobremesa
CREATE TABLE IF NOT EXISTS sobremesa (
    produto_id INTEGER PRIMARY KEY,
    tem_lactose INTEGER NOT NULL DEFAULT 0,
    calorias INTEGER NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produto(id) ON DELETE CASCADE
);
