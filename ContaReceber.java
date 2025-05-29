public class ContaReceber extends Conta {
    public ContaReceber(String nome, String vencimento, double valor) {
        super(nome, vencimento, valor);
    }

    @Override
    public String getTipo() {
        return "A RECEBER";
    }
}
