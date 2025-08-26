import java.util.Scanner;

public class TradingApp {
    public static void main(String[] args) {
        Market market = new Market();
        User user = new User("TraderJoe", 10000.0);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Stock Trading Simulator!");

        while (true) {
            market.updatePrices();

            System.out.println("\nCurrent Market Prices:");
            for (Stock stock : market.getAllStocks()) {
                System.out.println(stock);
            }

            System.out.println("\nYour Portfolio:");
            System.out.println(user);

            System.out.println("\nChoose an action: [buy, sell, exit]");
            String action = scanner.nextLine().trim().toLowerCase();

            if (action.equals("exit")) break;

            System.out.println("Enter stock ticker:");
            String ticker = scanner.nextLine().trim().toUpperCase();

            Stock stock = market.getStock(ticker);
            if (stock == null) {
                System.out.println("Stock not found!");
                continue;
            }

            System.out.println("Enter quantity:");
            int qty;
            try {
                qty = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity!");
                continue;
            }

            switch (action) {
                case "buy":
                    user.buyStock(ticker, qty, stock.getPrice());
                    break;
                case "sell":
                    user.sellStock(ticker, qty, stock.getPrice());
                    break;
                default:
                    System.out.println("Unknown action.");
            }
        }

        scanner.close();
        System.out.println("Thanks for trading! Final portfolio:");
        System.out.println(user);
    }
}
