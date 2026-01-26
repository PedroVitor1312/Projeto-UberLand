package main;

import java.util.Scanner;

import controller.SistemaUberLand;

public class Programa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaUberLand sistema = new SistemaUberLand();

        System.out.println("\n=====================");
        System.out.println("BEM VINDO A UBERLAND");
        System.out.println("=====================\n");

        boolean rodando = true;
        while (rodando) {
            System.out.println("1 - Cadastrar Passageiro");
            System.out.println("2 - Cadastrar Motorista");
            System.out.println("3 - Solicitar Corrida");
            System.out.println("4 - Listar Passageiros");
            System.out.println("5 - Listar Motoristas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro(sc);
            System.out.println();

            switch (opcao) {
                case 1 -> sistema.cadastrarPassageiro(sc);
                case 2 -> sistema.cadastrarMotorista(sc);
                case 3 -> sistema.solicitarCorrida(sc);
                case 4 -> sistema.listarPassageiros();
                case 5 -> sistema.listarMotoristas();
                case 0 -> {
                    System.out.println("Encerrando o sistema...");
                    rodando = false;
                }
                default -> System.out.println("Opção inválida!");
            }

            System.out.println();
        }

        sc.close();
    }

    private static int lerInteiro(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Digite um número válido:");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
}