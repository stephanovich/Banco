package banco;

import java.io.Serializable;

public class Conta implements Comparable<Conta>, Serializable{

    private int conta;
    private String nome;
    private double saldoCC;
    private double saldoPoupanca;

    public int getConta() {
        return conta;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSaldoCC() {
        return saldoCC;
    }

    public void setSaldoCC(double saldoCC) {
        this.saldoCC = saldoCC;
    }

    public double getSaldoPoupanca() {
        return saldoPoupanca;
    }

    public void setSaldoPoupanca(double saldoPoupanca) {
        this.saldoPoupanca = saldoPoupanca;
    }

    @Override
    public String toString() {
        String msg;

        msg = conta + " " + nome + " " + saldoCC + " " + saldoPoupanca;
        return msg;
    }
    
    public boolean saldoZerado() {
        
        return (this.saldoCC == 0);
    }
    
    public boolean saldoPoupancaZerado() {
        
        return (this.saldoPoupanca == 0);
    }
    
    @Override
    public int compareTo(Conta conta) {
        if (this.getSaldoCC() > conta.getSaldoCC()) {
            return -1;
        }
        if (this.getSaldoCC() < conta.getSaldoCC()) {
            return 1;
        }
        return 0;    
    }
}
