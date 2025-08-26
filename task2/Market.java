import java.util.*;

public class Market {
    private Map<String, Stock> stocks;
    private Random random;

    public Market() {
        stocks = new HashMap<>();
        random = new Random();

        // Sample stocks
        stocks.put("AAPL", new Stock("AAPL", "Apple Inc.", 150.0));
        stocks.put("GOOG", new Stock("GOOG", "Alphabet Inc.", 2800.0));
        stocks.put("TSLA", new Stock("TSLA", "Tesla Inc.", 700.0));
        stocks.put("AMZN", new Stock("AMZN", "Amazon.com Inc.", 3300.0));
    }

    public Stock getStock(String ticker) {
        return stocks.get(ticker);
    }

    public Collection<Stock> getAllStocks() {
        return stocks.values();
    }

    // Simulate price update randomly within +-5%
    public void updatePrices() {
        for (Stock stock : stocks.values()) {
            double changePercent = (random.nextDouble() * 10) - 5; // -5% to +5%
            double newPrice = stock.getPrice() * (1 + changePercent / 100);
            newPrice = Math.max(newPrice, 1); // minimum price $1
            stock.setPrice(newPrice);
        }
    }
}
