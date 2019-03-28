package banco;

import java.io.EOFException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import javax.swing.JOptionPane;

public class Banco {

    public static void main(String[] args) {
        final String nomeArquivo = "C:\\contas.obj";
        List<Conta> contas = new ArrayList<>();

        //criaContas(contas);
        leContas(nomeArquivo, contas);
        //listaContas(contas);
        escolha(contas);
        gravaContas(nomeArquivo, contas);

    }

    public static void criaContas(List<Conta> contas) {
        Conta conta1 = new Conta();
        Conta conta2 = new Conta();
        Conta conta3 = new Conta();
        Conta conta4 = new Conta();

        conta1.setConta(1);
        conta1.setNome("testaA");
        conta1.setSaldoCC(1000);
        conta1.setSaldoPoupanca(100);
        contas.add(conta1);
        conta2.setConta(2);
        conta2.setNome("testaB");
        conta2.setSaldoCC(900);
        conta2.setSaldoPoupanca(200);
        contas.add(conta2);
        conta3.setConta(3);
        conta3.setNome("testaC");
        conta3.setSaldoCC(800);
        conta3.setSaldoPoupanca(300);
        contas.add(conta3);
        conta4.setConta(4);
        conta4.setNome("testaD");
        conta4.setSaldoCC(700);
        conta4.setSaldoPoupanca(400);
        contas.add(conta4);
    }

    public static void leContas(String nome, List contas) {
        ObjectInputStream entrada = null;

        // Abre o arquivo
        try {
            entrada = new ObjectInputStream(new FileInputStream(nome));
        } catch (IOException erro) {
            System.out.println("Erro: arquivo não encontrado!!");
            return;
        }
        // Le o arquivo
        boolean fim = false;
        do {
            Conta conta;
            try {
                contas.add(entrada.readObject());
            } catch (EOFException erro) {
                fim = true;
            } catch (ClassNotFoundException erro) {
                System.out.println("Erro: criacao do objeto");
            } catch (IOException erro) {
                System.out.printf("Erro: leitura do arquivo");
            }
        } while (fim == false);
        // Fechar o arquivo
        try {
            entrada.close();
        } catch (IOException erro) {
            System.err.println("Erro: fechando arquivo");
            System.exit(1);
        }
    }

    public static void gravaContas(String nomeArquivo, List<Conta> contas) {
        ObjectOutputStream saida = null;

        try {
            saida = new ObjectOutputStream(new FileOutputStream(nomeArquivo));
        } catch (IOException erro) {
            System.out.println("Erro: abertura do arquivo");
            System.exit(1);
        }
        try {
            for (int i = 0; i < contas.size(); i++) {
                saida.writeObject(contas.get(i));
            }
        } catch (IOException erro) {
            System.out.println("Erro: gravacao no arquivo");
        }
        try {
            saida.close();
        } catch (IOException erro) {
            System.err.println("Erro: fechando o arquivo");
            System.exit(1);
        }
    }

    public static void escolha(List<Conta> contas) {
        final int FIM = 5;
        int opcao;
        do {
            opcao = menu();
            switch (opcao) {
                case 1:
                    incluirConta(contas);
                    break;
                case 2:
                    alterarConta(contas);
                    break;
                case 3:
                    excluirConta(contas);
                    break;
                case 4:
                    relatorios(contas);
                    break;
            }
        } while (opcao != FIM);

    }

    public static int menu() {
        String opcao;
        int op;

        do {
            opcao = JOptionPane.showInputDialog("1 - incluir\n2 - Alterar\n3 - Excluir\n4 - Listar\n5 - Sair");
            op = Integer.valueOf(opcao);
            if ((op < 1) || (op > 5)) {
                JOptionPane.showMessageDialog(null, "Erro: Opção inválida!");
            }
        } while ((op < 1) || (op > 5));
        return op;
    }

    public static void incluirConta(List contas) {
        int numConta, pos;

        numConta = Integer.parseInt(JOptionPane.showInputDialog("Entre com o número da conta: "));
        if (verificaConta(contas, numConta) != -1) {
            JOptionPane.showMessageDialog(null, "Erro: Conta já existe!");
            return;
        }
        Conta conta = new Conta();
        conta.setConta(numConta);
        conta.setNome(JOptionPane.showInputDialog("Entre com o nome do cliente: "));
        conta.setSaldoCC(entraSaldo("Entre com o saldo: "));
        conta.setSaldoPoupanca(entraSaldo("Entre com o saldo: "));
        contas.add(conta);
    }

    public static void alterarConta(List<Conta> contas) {
        int numConta, pos;

        numConta = Integer.parseInt(JOptionPane.showInputDialog("Entre com o número da conta: "));
        pos = verificaConta(contas, numConta);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Erro: Conta não existente!");
            return;
        }
        contas.get(pos).setNome(JOptionPane.showInputDialog("Entre com o nome do cliente: "));
        contas.get(pos).setSaldoCC(Double.valueOf(JOptionPane.showInputDialog("Entre com o saldo da conta corrente: ")));
        contas.get(pos).setSaldoPoupanca(Double.valueOf(JOptionPane.showInputDialog("Entre com o saldo da poupança: ")));
    }

    public static void excluirConta(List<Conta> contas) {
        int numConta, pos;

        numConta = Integer.parseInt(JOptionPane.showInputDialog("Entre com o número da conta: "));
        pos = verificaConta(contas, numConta);
        if (pos == -1) {
            JOptionPane.showMessageDialog(null, "Erro: Conta não existente!");
            return;
        }
        if ((!contas.get(pos).saldoZerado())&&(!contas.get(pos).saldoPoupancaZerado())) {
            JOptionPane.showMessageDialog(null, "Erro: Conta com saldo positivo!");
            return;
        }
        int resp = Integer.valueOf(JOptionPane.showInputDialog(contas.get(pos) + "\n" + "Deseja excluir a conta? \n"
                + " 1 - Sim \n 2 - Não\n"));
        if (resp == 1) {
            contas.remove(pos);
        }
    }

    public static void relatorios(List contas) {
        final int FIM = 5;
        int opcao;

        do {
            opcao = menuRelatorios();
            switch (opcao) {
                case 1:
                    listarContas(contas);
                    break;
                case 2:
                    listarNegativos(contas);
                    break;
                case 3:
                    listarAcimadoValor(contas);
                    break;
                case 4: listarTresMaiores(contas);
                    break;
            }
        } while (opcao != FIM);
    }

    public static int menuRelatorios() {
        String opcao;
        int op;

        do {
            opcao = JOptionPane.showInputDialog("1 - Listas todas as contas\n2 - Listas contas negativas\n3 - "
                    + "Contas acima de \n4 - Listar as três maiores \n5 - Sair");
            op = Integer.valueOf(opcao);
            if ((op < 1) || (op > 5)) {
                JOptionPane.showMessageDialog(null, "Erro: Opção inválida!");
            }
        } while ((op < 1) || (op > 5));
        return op;
    }

    public static void listarContas(List<Conta> contas) {

        if (contas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro: não contas há serem listadas!");
            return;
        }
        for (int i = 0; i < contas.size(); i++) {
            System.out.println("Numero da Conta: " + contas.get(i).getConta());
            System.out.println("Nome Cliente: " + contas.get(i).getNome());
            System.out.println("Saldo conta corrente: " + contas.get(i).getSaldoCC());
            System.out.println("Saldo Poupança: " + contas.get(i).getSaldoPoupanca());
            System.out.println("-------------------------------------------------------------");
        }
    }

    public static void listarNegativos(List<Conta> contas) {
        boolean naoTemConta = false;
        
        if (contas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro: não contas há serem listadas!");
            return;
        }
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getSaldoCC() < 0) {
                naoTemConta = true;
                System.out.println("Numero da Conta: " + contas.get(i).getConta());
                System.out.println("Nome Cliente: " + contas.get(i).getNome());
                System.out.println("Saldo conta corrente: " + contas.get(i).getSaldoCC());
                System.out.println("Saldo Poupança: " + contas.get(i).getSaldoPoupanca());
                System.out.println("-------------------------------------------------------------");
            }
        }
        if (!naoTemConta) {
            System.out.println("-------------------------------------------------------------");
            System.out.println("Não existem contas negativadas!");
            System.out.println("-------------------------------------------------------------");
        }
    }

    public static void listarAcimadoValor(List<Conta> contas) {
        double valor;
        boolean naoTemSaldo = false;

        valor = (Double.parseDouble(JOptionPane.showInputDialog("Entre com o valor para fazer a comparação: ")));
        if (contas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro: não contas há serem listadas!");
            return;
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Contas com saldo acima de " + valor + ": ");
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getSaldoCC() > valor) {
                naoTemSaldo = true;
                System.out.println("Numero da Conta: " + contas.get(i).getConta());
                System.out.println("Nome Cliente: " + contas.get(i).getNome());
                System.out.println("Saldo conta corrente: " + contas.get(i).getSaldoCC());
                System.out.println("Saldo Poupança: " + contas.get(i).getSaldoPoupanca());
                System.out.println("-------------------------------------------------------------");
            }
        }
        if (!naoTemSaldo){
            System.out.println("Não existem saldo acima desse valor ");
            System.out.println("-------------------------------------------------------------");

        }
    }

    public static void listarTresMaiores(List<Conta> contas) {
        if (contas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Erro: não contas há serem listadas!");
            return;
        }
        Collections.sort(contas);
        int tam;
        if (contas.size() > 3) {
            tam = 3;
        }
        else {
            tam = contas.size();
        }
        for (int i = 0; i < tam; i++) {
            System.out.println("Numero da Conta: " + contas.get(i).getConta());
            System.out.println("Nome Cliente: " + contas.get(i).getNome());
            System.out.println("Saldo conta corrente: " + contas.get(i).getSaldoCC());
            System.out.println("Saldo Poupança: " + contas.get(i).getSaldoPoupanca());
            System.out.println("-------------------------------------------------------------");
        }
    }
    
    public static double entraSaldo(String msg) {
        double saldo;

        do {
            saldo = Double.parseDouble(JOptionPane.showInputDialog("Entre com o saldo: "));
            if (saldo <= 0) {
                JOptionPane.showMessageDialog(null, "Erro: Saldo deve ser maior que ZERO.");
            }
        } while (saldo <= 0);

        return saldo;
    }

    public static int verificaConta(List<Conta> contas, int conta) {
        int pos = -1;

        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getConta() == conta) {
                pos = i;
                break;
            }
        }
        return pos;
    }
}
