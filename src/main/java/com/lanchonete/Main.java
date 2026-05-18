package com.lanchonete;

import com.lanchonete.repository.ConexaoDB;
import com.lanchonete.view.Menu;

public class Main {
    public static void main(String[] args) {
        // Cria as tabelas no SQLite se ainda não existirem
        ConexaoDB.inicializarBanco();

        // Inicia o menu de console
        Menu menu = new Menu();
        menu.iniciar();
    }
}
