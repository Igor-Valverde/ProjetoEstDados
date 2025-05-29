public abstract class Conta {
    private static int contador = 1;
    private int codigo;
    private String nome;
    private String vencimento;
    private double valor;

    public Conta(String nome, String vencimento, double valor) {
        this.codigo = contador++;
        this.nome = nome;
        this.vencimento = vencimento;
        this.valor = valor;
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public String getVencimento() { return vencimento; }
    public double getValor() { return valor; }

    public void setNome(String nome) { this.nome = nome; }
    public void setVencimento(String vencimento) { this.vencimento = vencimento; }
    public void setValor(double valor) { this.valor = valor; }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "[" + getTipo() + "] Código: " + codigo + ", Nome: " + nome + ", Vencimento: " + vencimento + ", Valor: R$ " + valor;
    }
}