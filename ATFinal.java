package at.pkgfinal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class ATFinal {

    private static final String ARQUIVO = "contas.txt";

    public static void main(String[] args) {
        ArrayList<Cliente> clientes = new ArrayList<>();
        int operacao;

        leArquivo(clientes);
        do {
            operacao = menu();
            switch (operacao) {
                case 0:
                    gravaArquivo(clientes);
                    System.out.println("Dados salvos com sucesso!");
                    break;
                case 1:
                    incluir(clientes);
                    break;
                case 2:
                    alterar(clientes);
                    break;
                case 3:
                    excluir(clientes);
                    break;
                case 4:
                    relatorio(clientes);
            }
        } while (operacao != 0);
    }

    private static int menu() {
        Scanner ler = new Scanner(System.in);
        int operacao;

        do {
            System.out.println("1 - Inclusão de cliente.");
            System.out.println("2 - Alteração de cliente.");
            System.out.println("3 - Exclusão de cliente.");
            System.out.println("4 - Relaório Gerencial.");
            System.out.println("0 - Sair.");
            System.out.print("Escolha uma das opções: ");
            operacao = ler.nextInt();
            if ((operacao < 0) || (operacao > 4)) {
                System.err.println("Erro: Operação inválida");
            }
        } while ((operacao < 0) || (operacao > 4));
        return operacao;
    }

    private static void leArquivo(ArrayList<Cliente> clientes) {
        FileReader file;
        int numeroConta;
        double saldoCorrente, saldoPoupanca;
        String nomeCliente;

        try {
            file = new FileReader(ARQUIVO);
            Scanner lerDados = new Scanner(file).useLocale(Locale.US);
            while (lerDados.hasNext()) {
                numeroConta = lerDados.nextInt();
                nomeCliente = lerDados.next();
                saldoCorrente = lerDados.nextDouble();
                saldoPoupanca = lerDados.nextDouble();
                clientes.add(new Cliente(numeroConta, nomeCliente, saldoCorrente, saldoPoupanca));
            }
            try {
                file.close();
            } catch (IOException ex) {
                System.err.println("Erro! Problema com o arquivo!");
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Erro! Problema com o arquivo!");
        }
    }

    private static void gravaArquivo(ArrayList<Cliente> clientes) {
        File file = new File(ARQUIVO);

        if (file.exists()) {
            file.delete();
        }
        try {
            FileWriter escritor = new FileWriter(ARQUIVO, true);
            BufferedWriter escrever = new BufferedWriter(escritor);
            for (int i = 0; i < clientes.size(); i++) {
                escrever.write(String.valueOf(clientes.get(i).getNumeroConta()));
                escrever.write(" ");
                escrever.write(String.valueOf(clientes.get(i).getNomeCliente()));
                escrever.write(" ");
                escrever.write(String.valueOf(clientes.get(i).getSaldoCorrente()));
                escrever.write(" ");
                escrever.write(String.valueOf(clientes.get(i).getSaldoPoupanca()));
                escrever.newLine();
            }
            escrever.flush();
            escrever.close();
        } catch (IOException e) {
            System.out.println("Erro ao gravar no arquivo!");
        }
    }

    private static void incluir(ArrayList<Cliente> clientes) {
        Scanner ler = new Scanner(System.in);
        int numConta, achar;
        double saldoC, saldoP;
        String nome;

        System.out.print("Entre com o número da conta: ");
        numConta = ler.nextInt();
        achar = acharConta(clientes, numConta);
        if (achar >= 0) {
            System.err.println("Conta já existente.");
            return;
        }
        System.out.print("Entre com o nome do cliente: ");
        nome = ler.next();
        System.out.print("Entre com o saldo da conta corrente: ");
        saldoC = ler.nextDouble();
        if (saldoC > 0) {
            System.out.print("Entre com o saldo da poupança: ");
            saldoP = ler.nextDouble();
            if (saldoP > 0) {
                clientes.add(new Cliente(numConta, nome, saldoC, saldoP));
            } else {
                System.err.println("Error! Saldo da Poupança não pode ser 0");
            }
        } else {
            System.err.println("Saldo da conta corrente n pode ser menor ou igual a 0");
        }
    }

    private static void alterar(ArrayList<Cliente> clientes) {
        Scanner ler = new Scanner(System.in);
        int conta, achar;

        System.out.print("Digite o número da conta que deseja alterar: ");
        conta = ler.nextInt();
        achar = acharConta(clientes, conta);
        if (achar >= 0) {
            System.out.print("Entre com o nome do cliente: ");
            clientes.get(achar).setNomeCliente(ler.next());
            System.out.print("Entre com o novo saldo da conta corrente: ");
            clientes.get(achar).setSaldoCorrente(ler.nextDouble());
            System.out.print("Entre com o novo saldo da poupança: ");
            clientes.get(achar).setSaldoPoupanca(ler.nextDouble());
        } else {
            System.err.println("Conta não existente!");
        }
    }

    private static void excluir(ArrayList<Cliente> clientes) {
        Scanner ler = new Scanner(System.in);
        int numConta, achar;
        String resposta;

        System.out.print("Digite o número da conta que deseja excluir: ");
        numConta = ler.nextInt();
        achar = acharConta(clientes, numConta);
        if (achar >= 0) {
            if ((clientes.get(achar).getSaldoCorrente() != 0) || (clientes.get(achar).getSaldoPoupanca() != 0)) {
                System.err.println("Saldo da conta corrente e poupança deve ser 0");
            } else {
                mostrarConta(clientes, achar);
                System.out.println("Deseja excluir a conta?");
                resposta = ler.next();
                if ((resposta.equals("sim")) || (resposta.equals("Sim"))) {
                    clientes.remove(achar);
                    System.out.println("Conta excluida com sucesso!");
                }
            }
        } else {
            System.err.println("Conta não existente!");
        }
    }

    private static void relatorio(ArrayList<Cliente> clientes) {
        Scanner ler = new Scanner(System.in);
        int operacao;

        do {
            System.out.println("1 - Listar clientes com saldo negativo.");
            System.out.println("2 - Listar Cliente com saldo acima de um valor.");
            System.out.println("3 - Listar todas as contas.");
            System.out.println("4 - Listar os 3 maiores saldos de conta corrente.");
            System.out.println("0 - Voltar ao menu anterior.");
            System.out.print("Digite a opção desejada: ");
            operacao = ler.nextInt();
            if ((operacao < 0) || (operacao > 4)) {
                System.err.println("Erro: Opção inválida!");
            } else {
                switch (operacao) {
                    case 1:
                        saldoNegativo(clientes);
                        break;
                    case 2:
                        saldoValorX(clientes);
                        break;
                    case 3:
                        mostrarTodos(clientes);
                        break;
                    case 4:
                        maiorValor(clientes);
                        break;
                    case 0:
                        break;
                }
            }
        } while (operacao != 0);
    }

    private static void saldoNegativo(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.err.println("Não à contas cadastradas!");
            return;
        }
        for (int i = 0; i < clientes.size(); i++) {
            if ((clientes.get(i).getSaldoCorrente() < 0) || (clientes.get(i).getSaldoPoupanca()) < 0) {
                mostrarConta(clientes, i);
            }
        }
    }

    private static void saldoValorX(ArrayList<Cliente> clientes) {
        Scanner ler = new Scanner(System.in);
        int valorComparacao;

        System.out.print("Digite o valor que deseja compara: ");
        valorComparacao = ler.nextInt();
        if (clientes.isEmpty()) {
            System.err.println("Não à contas cadastradas!");
            return;
        }
        for (int i = 0; i < clientes.size(); i++) {
            if ((clientes.get(i).getSaldoCorrente() >= valorComparacao) || (clientes.get(i).getSaldoPoupanca() >= valorComparacao)) {
                mostrarConta(clientes, i);
            }
        }
    }

    private static void mostrarTodos(ArrayList<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.err.println("Não à contas cadastradas!");
            return;
        }
        for (int i = 0; i < clientes.size(); i++) {
            mostrarConta(clientes, i);
        }
    }

    private static void maiorValor(ArrayList<Cliente> clientes) {

        if (clientes.isEmpty()) {
            System.err.println("Não à contas cadastradas!");
            return;
        }
        Collections.sort(clientes);
        int tam;
        if (clientes.size() > 3) {
            tam = 2;
        } else {
            tam = clientes.size() - 1;
        }
        for (int i = 0; i <= tam; i++) {
            mostrarConta(clientes, i);
        }
    }

    private static int acharConta(ArrayList<Cliente> clientes, int numConta) {
        int contaNaoExiste = -1;

        for (int i = 0; i < clientes.size(); i++) {
            if (numConta == clientes.get(i).getNumeroConta()) {
                return i;
            }
        }
        return contaNaoExiste;
    }

    private static void mostrarConta(ArrayList<Cliente> clientes, int mostrar) {
        System.out.println("Numero da Conta: " + clientes.get(mostrar).getNumeroConta());
        System.out.println("Nome Cliente: " + clientes.get(mostrar).getNomeCliente());
        System.out.println("Saldo conta corrente: " + clientes.get(mostrar).getSaldoCorrente());
        System.out.println("Saldo Poupança: " + clientes.get(mostrar).getSaldoPoupanca());
        System.out.println("-------------------------------------------------------------");
    }
}
