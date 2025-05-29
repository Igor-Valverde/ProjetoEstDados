import java.io.*;
import java.util.*;
// import java.lang.Thread.sleep;

public class Main {
    static ArrayList<Conta> contas = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void limparTerminal() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Não foi possível limpar o terminal.");
        }
    }

    
    public static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("A pausa foi interrompida.");
        }
    }

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int opcao;
        do {
            limparTerminal();
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Cadastrar Conta");
            System.out.println("2 - Consultar Conta por Código");
            System.out.println("3 - Consultar Conta por Nome");
            System.out.println("4 - Listar Todas as Contas");
            System.out.println("5 - Atualizar Conta");
            System.out.println("6 - Excluir Conta");
            System.out.println("7 - Exportar Relatório para .txt");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                
                case 1 -> {
                    cadastrarConta();
                    pause(3000);
                    limparTerminal();
                }
                case 2 -> {
                    consultarPorCodigo();
                    pause(3000);
                    limparTerminal();
                }
                case 3 -> {
                    consultarPorNome();
                    pause(3000);
                    limparTerminal();
                }
                case 4 -> {
                    listarContasOrdenadas();
                    pause(3000);
                    limparTerminal();
                }
                case 5 -> {
                    atualizarConta();
                    pause(3000);
                    limparTerminal();
                }
                case 6 -> {
                    excluirConta();
                    pause(3000);
                    limparTerminal();
                }
                case 7 -> {
                    exportarParaTxt();
                    pause(3000);
                    limparTerminal();
                }
                case 0 -> {
                    System.out.println("Encerrando...");
                    pause(3000);
                    limparTerminal();
                }
                default -> {
                    System.out.println("Opção inválida.");
                    pause(3000);
                    limparTerminal();
                }
            }
        } while (opcao != 0);
    }

    public static void cadastrarConta() {
        System.out.print("Nome da conta: ");
        String nome = scanner.nextLine();

        String vencimento;
        do {
            System.out.print("Data vencimento (YYYY-MM-DD): ");
            vencimento = scanner.nextLine();
        } while (!vencimento.matches("\\d{4}-\\d{2}-\\d{2}"));

        double valor;
        do {
            System.out.print("Valor (positivo): ");
            valor = scanner.nextDouble();
            scanner.nextLine();
        } while (valor <= 0);

        System.out.print("1 - Conta a Pagar | 2 - Conta a Receber: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        Conta conta = (tipo == 1) ? new ContaPagar(nome, vencimento, valor) : new ContaReceber(nome, vencimento, valor);
        contas.add(conta);
        System.out.println("Conta cadastrada com sucesso!");
    }

    public static void consultarPorCodigo() {
        System.out.print("Código da conta: ");
        int cod = scanner.nextInt();
        scanner.nextLine();
        for (Conta c : contas) {
            if (c.getCodigo() == cod) {
                System.out.println(c);
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    public static void consultarPorNome() {
        System.out.print("Nome da conta: ");
        String nome = scanner.nextLine();
        for (Conta c : contas) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                System.out.println(c);
            }
        }
    }

    public static void listarContasOrdenadas() {
        contas.sort(Comparator.comparing(Conta::getVencimento));
        double totalPagar = 0, totalReceber = 0;
        for (Conta c : contas) {
            System.out.println(c);
            if (c instanceof ContaPagar) totalPagar += c.getValor();
            else totalReceber += c.getValor();
        }
        System.out.println("\nTotal a Pagar: R$ " + totalPagar);
        System.out.println("Total a Receber: R$ " + totalReceber);
    }

    public static void atualizarConta() {
        System.out.print("Código da conta para atualizar: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        for (Conta c : contas) {
            if (c.getCodigo() == cod) {
                System.out.print("Novo nome (atual: " + c.getNome() + "): ");
                String nome = scanner.nextLine();
                if (!nome.isEmpty()) c.setNome(nome);

                System.out.print("Nova data (atual: " + c.getVencimento() + "): ");
                String venc = scanner.nextLine();
                if (venc.matches("\\d{4}-\\d{2}-\\d{2}")) c.setVencimento(venc);

                System.out.print("Novo valor (atual: R$ " + c.getValor() + "): ");
                String val = scanner.nextLine();
                if (!val.isEmpty()) {
                    double v = Double.parseDouble(val);
                    if (v > 0) c.setValor(v);
                }

                System.out.println("Conta atualizada.");
                return;
            }
        }
        System.out.println("Conta não encontrada.");
    }

    public static void excluirConta() {
        System.out.print("Código da conta para excluir: ");
        int cod = scanner.nextInt();
        scanner.nextLine();

        contas.removeIf(c -> c.getCodigo() == cod);
        System.out.println("Conta excluída (se existente).\n");
    }

    public static void exportarParaTxt() {
        try {
            PrintWriter writer = new PrintWriter("relatorio_contas.txt");
            contas.sort(Comparator.comparing(Conta::getVencimento));
            double totalPagar = 0, totalReceber = 0;

            for (Conta c : contas) {
                writer.println(c);
                if (c instanceof ContaPagar) totalPagar += c.getValor();
                else totalReceber += c.getValor();
            }

            writer.println("\nTotal a Pagar: R$ " + totalPagar);
            writer.println("Total a Receber: R$ " + totalReceber);
            writer.close();
            System.out.println("Exportado para relatorio_contas.txt");
        } catch (Exception e) {
            System.out.println("Erro ao exportar: " + e.getMessage());
        }
    }
}
