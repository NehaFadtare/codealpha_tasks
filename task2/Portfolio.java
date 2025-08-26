import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Portfolio implements Serializable {
    private Map<String, Integer> holdings; // ticker -> quantity

    public Portfolio() {
        holdings = new HashMap<>();
    }

    public void addStock(String ticker, int qty) {
        holdings.put(ticker, holdings.getOrDefault(ticker, 0) + qty);
    }

    public boolean removeStock(String ticker, int qty) {
        int currentQty = holdings.getOrDefault(ticker, 0);
        if (currentQty >= qty) {
            if (currentQty == qty) holdings.remove(ticker);
            else holdings.put(ticker, currentQty - qty);
            return true;
        }
        return false;
    }

    public int getQuantity(String ticker) {
        return holdings.getOrDefault(ticker, 0);
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        holdings.forEach((ticker, qty) -> sb.append(ticker).append(": ").append(qty).append("\n"));
        return sb.toString();
    }
}
