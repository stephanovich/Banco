package at.pkgfinal;

public class Cliente implements  Comparable<Cliente>{
    private int numeroConta;
    private String nomeCliente;
    private double saldoCorrente;
    private double saldoPoupanca;
    
    Cliente(){
        
    }
    Cliente (int numeroConta, String nomeCliente, double saldoCorrente, double saldoPoupanca) {
        this.numeroConta = numeroConta;
        this.nomeCliente = nomeCliente;
        this.saldoCorrente = saldoCorrente;
        this.saldoPoupanca = saldoPoupanca;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public double getSaldoCorrente() {
        return saldoCorrente;
    }

    public void setSaldoCorrente(double saldoCorrente) {
        this.saldoCorrente = saldoCorrente;
    }

    public double getSaldoPoupanca() {
        return saldoPoupanca;
    }

    public void setSaldoPoupanca(double saldoPoupanca) {
        this.saldoPoupanca = saldoPoupanca;
    }
    
    @Override
	public int compareTo(Cliente outraConta) {
		 if (this.getSaldoCorrente() > outraConta.getSaldoCorrente()) {
	          return -1;
	     }
	     if (this.getSaldoCorrente() < outraConta.getSaldoCorrente()) {
	          return 1;
	     }
	     return 0;
	}
}
