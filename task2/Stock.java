import java.io.Serializable;

public class Stock implements Serializable {
    private String ticker;
    private String name;
    private double price; // current price per share

    public Stock(String ticker, String name, double price) {
        this.ticker = ticker;
        this.name = name;
        this.price = price;
    }

    public String getTicker() { return ticker; }
    public String getName() { return name; }
    public double getPrice() { return price; }

    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return ticker + " (" + name + ") - $" + String.format("%.2f", price);
    }
}
