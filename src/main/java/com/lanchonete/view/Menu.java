package com.lanchonete.view;

import com.lanchonete.models.Bebida;
import com.lanchonete.models.Lanche;
import com.lanchonete.models.Produto;
import com.lanchonete.models.Sobremesa;
import com.lanchonete.repository.BebidaRepository;
import com.lanchonete.repository.LancheRepository;
import com.lanchonete.repository.SobremesaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner sc = new Scanner(System.in);
    private final LancheRepository lancheRepo = new LancheRepository();
    private final BebidaRepository bebidaRepo = new BebidaRepository();
    private final SobremesaRepository sobremesaRepo = new SobremesaRepository();

    public void iniciar() {
        int op;
        do {
            System.out.println("\n========= LANCHONETE =========");
            System.out.println("1 - Gerenciar Lanches");
            System.out.println("2 - Gerenciar Bebidas");
            System.out.println("3 - Gerenciar Sobremesas");
            System.out.println("4 - Listar TODOS os produtos (polimorfismo)");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");
            op = lerInt();
            switch (op) {
                case 1 -> menuLanche();
                case 2 -> menuBebida();
                case 3 -> menuSobremesa();
                case 4 -> listarTodosPolimorfico();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    // ====== LANCHE ======
    private void menuLanche() {
        int op;
        do {
            System.out.println("\n--- LANCHES ---");
            System.out.println("1 - Cadastrar  2 - Listar  3 - Atualizar  4 - Deletar  0 - Voltar");
            System.out.print("Opção: ");
            op = lerInt();
            switch (op) {
                case 1 -> cadastrarLanche();
                case 2 -> listarLanches();
                case 3 -> atualizarLanche();
                case 4 -> deletarLanche();
            }
        } while (op != 0);
    }

    private void cadastrarLanche() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Preço base: ");
        double preco = lerDouble();
        System.out.print("Ingredientes: ");
        String ing = sc.nextLine();
        System.out.print("Tem bacon? (s/n): ");
        boolean bacon = sc.nextLine().equalsIgnoreCase("s");
        System.out.print("Tipo de carne: ");
        String carne = sc.nextLine();

        Lanche l = new Lanche(nome, preco, ing, bacon, carne);
        lancheRepo.inserir(l);
    }

    private void listarLanches() {
        List<Lanche> lista = lancheRepo.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum lanche cadastrado.");
        } else {
            lista.forEach(l -> System.out.println(l.exibirInfo()));
        }
    }

    private void atualizarLanche() {
        System.out.print("ID do lanche: ");
        int id = lerInt();
        Lanche l = lancheRepo.buscarPorId(id);
        if (l == null) {
            System.out.println("Não encontrado.");
            return;
        }
        System.out.print("Novo nome (" + l.getNome() + "): ");
        l.setNome(sc.nextLine());
        System.out.print("Novo preço (" + l.getPreco() + "): ");
        l.setPreco(lerDouble());
        System.out.print("Novos ingredientes: ");
        l.setIngredientes(sc.nextLine());
        System.out.print("Tem bacon? (s/n): ");
        l.setTemBacon(sc.nextLine().equalsIgnoreCase("s"));
        System.out.print("Tipo de carne: ");
        l.setTipoCarne(sc.nextLine());

        System.out.println(lancheRepo.atualizar(l) ? "✓ Atualizado!" : "Falhou.");
    }

    private void deletarLanche() {
        System.out.print("ID do lanche: ");
        int id = lerInt();
        System.out.println(lancheRepo.deletar(id) ? "✓ Deletado!" : "Não encontrado.");
    }

    // ====== BEBIDA ======
    private void menuBebida() {
        int op;
        do {
            System.out.println("\n--- BEBIDAS ---");
            System.out.println("1 - Cadastrar  2 - Listar  3 - Atualizar  4 - Deletar  0 - Voltar");
            System.out.print("Opção: ");
            op = lerInt();
            switch (op) {
                case 1 -> cadastrarBebida();
                case 2 -> listarBebidas();
                case 3 -> atualizarBebida();
                case 4 -> deletarBebida();
            }
        } while (op != 0);
    }

    private void cadastrarBebida() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Preço base: ");
        double preco = lerDouble();
        System.out.print("Volume (ml): ");
        int vol = lerInt();
        System.out.print("Gelada? (s/n): ");
        boolean gelada = sc.nextLine().equalsIgnoreCase("s");
        System.out.print("Com gás? (s/n): ");
        boolean gas = sc.nextLine().equalsIgnoreCase("s");

        Bebida b = new Bebida(nome, preco, vol, gelada, gas);
        bebidaRepo.inserir(b);
    }

    private void listarBebidas() {
        List<Bebida> lista = bebidaRepo.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma bebida cadastrada.");
        } else {
            lista.forEach(b -> System.out.println(b.exibirInfo()));
        }
    }

    private void atualizarBebida() {
        System.out.print("ID da bebida: ");
        int id = lerInt();
        Bebida b = bebidaRepo.buscarPorId(id);
        if (b == null) {
            System.out.println("Não encontrada.");
            return;
        }
        System.out.print("Novo nome (" + b.getNome() + "): ");
        b.setNome(sc.nextLine());
        System.out.print("Novo preço (" + b.getPreco() + "): ");
        b.setPreco(lerDouble());
        System.out.print("Volume ml: ");
        b.setVolumeMl(lerInt());
        System.out.print("Gelada? (s/n): ");
        b.setGelada(sc.nextLine().equalsIgnoreCase("s"));
        System.out.print("Com gás? (s/n): ");
        b.setComGas(sc.nextLine().equalsIgnoreCase("s"));

        System.out.println(bebidaRepo.atualizar(b) ? "✓ Atualizado!" : "Falhou.");
    }

    private void deletarBebida() {
        System.out.print("ID da bebida: ");
        int id = lerInt();
        System.out.println(bebidaRepo.deletar(id) ? "✓ Deletado!" : "Não encontrada.");
    }

    // ====== SOBREMESA ======
    private void menuSobremesa() {
        int op;
        do {
            System.out.println("\n--- SOBREMESAS ---");
            System.out.println("1 - Cadastrar  2 - Listar  3 - Atualizar  4 - Deletar  0 - Voltar");
            System.out.print("Opção: ");
            op = lerInt();
            switch (op) {
                case 1 -> cadastrarSobremesa();
                case 2 -> listarSobremesas();
                case 3 -> atualizarSobremesa();
                case 4 -> deletarSobremesa();
            }
        } while (op != 0);
    }

    private void cadastrarSobremesa() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Preço base: ");
        double preco = lerDouble();
        System.out.print("Tem lactose? (s/n): ");
        boolean lac = sc.nextLine().equalsIgnoreCase("s");
        System.out.print("Calorias: ");
        int cal = lerInt();

        Sobremesa s = new Sobremesa(nome, preco, lac, cal);
        sobremesaRepo.inserir(s);
    }

    private void listarSobremesas() {
        List<Sobremesa> lista = sobremesaRepo.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhuma sobremesa cadastrada.");
        } else {
            lista.forEach(s -> System.out.println(s.exibirInfo()));
        }
    }

    private void atualizarSobremesa() {
        System.out.print("ID da sobremesa: ");
        int id = lerInt();
        Sobremesa s = sobremesaRepo.buscarPorId(id);
        if (s == null) {
            System.out.println("Não encontrada.");
            return;
        }
        System.out.print("Novo nome (" + s.getNome() + "): ");
        s.setNome(sc.nextLine());
        System.out.print("Novo preço (" + s.getPreco() + "): ");
        s.setPreco(lerDouble());
        System.out.print("Tem lactose? (s/n): ");
        s.setTemLactose(sc.nextLine().equalsIgnoreCase("s"));
        System.out.print("Calorias: ");
        s.setCalorias(lerInt());

        System.out.println(sobremesaRepo.atualizar(s) ? "✓ Atualizado!" : "Falhou.");
    }

    private void deletarSobremesa() {
        System.out.print("ID da sobremesa: ");
        int id = lerInt();
        System.out.println(sobremesaRepo.deletar(id) ? "✓ Deletado!" : "Não encontrada.");
    }

    // ====== POLIMORFISMO EM AÇÃO ======
    private void listarTodosPolimorfico() {
        System.out.println("\n--- TODOS OS PRODUTOS (mesma lista, comportamentos diferentes) ---");
        List<Produto> todos = new ArrayList<>();
        todos.addAll(lancheRepo.listarTodos());
        todos.addAll(bebidaRepo.listarTodos());
        todos.addAll(sobremesaRepo.listarTodos());

        if (todos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        // Polimorfismo: cada produto chama seu próprio exibirInfo() e calcularPrecoFinal()
        for (Produto p : todos) {
            System.out.println(p.exibirInfo());
        }

        double total = todos.stream().mapToDouble(Produto::calcularPrecoFinal).sum();
        System.out.printf("\nValor total do cardápio: R$ %.2f%n", total);
    }

    // ====== HELPERS ======
    private int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Número inválido, tente de novo: ");
            }
        }
    }

    private double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Número inválido, tente de novo: ");
            }
        }
    }
}
