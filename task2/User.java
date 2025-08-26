import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private double cashBalance;
    private Portfolio portfolio;

    public User(String username, double initialCash) {
        this.username = username;
        this.cashBalance = initialCash;
        this.portfolio = new Portfolio();
    }

    public String getUsername() { return username; }
    public double getCashBalance() { return cashBalance; }
    public Portfolio getPortfolio() { return portfolio; }

    public void deposit(double amount) {
        cashBalance += amount;
    }

    public boolean withdraw(double amount) {
        if (cashBalance >= amount) {
            cashBalance -= amount;
            return true;
        }
        return false;
    }

    public void buyStock(String ticker, int qty, double pricePerShare) {
        double cost = qty * pricePerShare;
        if (withdraw(cost)) {
            portfolio.addStock(ticker, qty);
            System.out.println("Bought " + qty + " shares of " + ticker);
        } else {
            System.out.println("Insufficient funds to buy " + ticker);
        }
    }

    public void sellStock(String ticker, int qty, double pricePerShare) {
        if (portfolio.removeStock(ticker, qty)) {
            double proceeds = qty * pricePerShare;
            deposit(proceeds);
            System.out.println("Sold " + qty + " shares of " + ticker);
        } else {
            System.out.println("Insufficient shares to sell " + ticker);
        }
    }

    @Override
    public String toString() {
        return "User: " + username + "\nCash Balance: $" + String.format("%.2f", cashBalance) + "\nPortfolio:\n" + portfolio.toString();
    }
}
