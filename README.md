# 🍔 Sistema de Gerenciamento de Lanchonete

Sistema CRUD em Java + SQLite para gerenciar produtos de uma lanchonete, desenvolvido como atividade prática da disciplina de Desenvolvimento de Sistemas.

## 👥 Integrantes
- Nicolas 
- Mainã
- Caio

## 📋 Sobre o projeto

O sistema permite cadastrar, listar, atualizar e excluir três tipos de produtos:
- **Lanches** (com ingredientes, tipo de carne, opção bacon)
- **Bebidas** (com volume, gelada/natural, com/sem gás)
- **Sobremesas** (com calorias e indicação de lactose)

## 🧠 Conceitos de POO aplicados

### Herança
Todas as classes de produto herdam da classe abstrata `Produto`:

```
Produto (abstract)
├── Lanche
├── Bebida
└── Sobremesa
```

### Polimorfismo
Cada subclasse sobrescreve dois métodos:
- `calcularPrecoFinal()` — cada tipo aplica sua própria regra de preço:
  - **Lanche**: + R$ 3,00 se tem bacon
  - **Bebida**: + 10% se gelada
  - **Sobremesa**: - 20% se calorias > 500
- `exibirInfo()` — cada tipo formata sua descrição com os atributos próprios

A função "Listar TODOS os produtos" do menu demonstra polimorfismo na prática: itera uma única `List<Produto>` e cada objeto responde com seu próprio comportamento.

### Encapsulamento
Todos os atributos são `private`/`protected`, acessados via getters e setters, com validações nos setters (ex: preço não pode ser negativo).

## 🗂️ Estrutura do projeto

```
lanchonete-sistema/
├── pom.xml                          # Configuração Maven
├── README.md
├── .gitignore
├── database/
│   └── schema.sql                   # Script SQL de criação das tabelas
└── src/main/java/com/lanchonete/
    ├── Main.java                    # Ponto de entrada
    ├── models/                      # Classes de domínio (OO pura)
    │   ├── Produto.java             # Classe abstrata base
    │   ├── Lanche.java
    │   ├── Bebida.java
    │   └── Sobremesa.java
    ├── repository/                  # Camada de acesso a dados (DAO)
    │   ├── ConexaoDB.java
    │   ├── LancheRepository.java
    │   ├── BebidaRepository.java
    │   └── SobremesaRepository.java
    └── view/
        └── Menu.java                # Interface de console
```

A separação entre `models` (regras de negócio) e `repository` (acesso ao banco) cumpre o princípio de **separação de responsabilidades** — as classes de domínio não conhecem SQL.

## 🚀 Como rodar

### Pré-requisitos
- Java 17 ou superior
- Maven
- IntelliJ IDEA (recomendado) ou outra IDE

### Passos
1. Clone o repositório:
   ```bash
   git clone https://github.com/SEU_USUARIO/lanchonete-sistema.git
   cd lanchonete-sistema
   ```

2. Abra o projeto no IntelliJ (File → Open → selecione a pasta).

3. Aguarde o Maven baixar as dependências (SQLite JDBC).

4. Execute a classe `Main.java`.

O banco `lanchonete.db` é criado automaticamente na primeira execução.

## 🗄️ Banco de Dados

Usamos **SQLite** por ser leve e não exigir instalação. As tabelas são criadas automaticamente ao iniciar o sistema (ver `ConexaoDB.inicializarBanco()`).

- Todas as queries usam **PreparedStatement** (proteção contra SQL Injection).
- Conexões são fechadas com **try-with-resources**.
- Inserções e atualizações em múltiplas tabelas usam **transações** (commit/rollback).
- `ON DELETE CASCADE` garante consistência ao excluir produtos.

## 📊 Diagrama de Classes

```
              ┌──────────────────┐
              │   <<abstract>>   │
              │     Produto      │
              ├──────────────────┤
              │ - id: int        │
              │ - nome: String   │
              │ - preco: double  │
              ├──────────────────┤
              │ + calcularPrecoFinal() : double   {abstract}
              │ + exibirInfo() : String           {abstract}
              └────────▲─────────┘
                       │
       ┌───────────────┼───────────────┐
       │               │               │
┌──────┴──────┐ ┌──────┴──────┐ ┌──────┴───────┐
│   Lanche    │ │   Bebida    │ │  Sobremesa   │
├─────────────┤ ├─────────────┤ ├──────────────┤
│-ingredientes│ │ - volumeMl  │ │ - temLactose │
│ - temBacon  │ │ - gelada    │ │ - calorias   │
│ - tipoCarne │ │ - comGas    │ │              │
└─────────────┘ └─────────────┘ └──────────────┘
```

## ✅ Funcionalidades

- [x] CRUD completo de Lanches
- [x] CRUD completo de Bebidas
- [x] CRUD completo de Sobremesas
- [x] Listagem unificada com polimorfismo
- [x] Cálculo automático de preço final por tipo
- [x] Persistência em SQLite

## 🛠️ Tecnologias

- Java 17
- Maven
- SQLite (driver xerial/sqlite-jdbc)
