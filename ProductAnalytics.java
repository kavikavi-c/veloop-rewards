import java.util.*;

// Product Sales Analytics - Java
public class ProductAnalytics {
    record Product(String name, String category, int price, int units) {
        int revenue() { return price * units; }
    }

    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(List.of(
            new Product("Smart Watch", "Electronics", 2499, 120),
            new Product("Headphones", "Electronics", 1799, 210),
            new Product("Running Shoes", "Fashion", 2199, 95),
            new Product("Cotton T-Shirt", "Fashion", 499, 340),
            new Product("Table Lamp", "Home", 899, 150),
            new Product("Basmati Rice 5kg", "Grocery", 649, 410)
        ));

        products.sort((a, b) -> b.revenue() - a.revenue());

        int totalRevenue = 0, totalUnits = 0;
        for (Product p : products) {
            totalRevenue += p.revenue();
            totalUnits += p.units();
        }

        System.out.println("=== PRODUCT SALES ANALYTICS ===");
        System.out.printf("%-18s%-13s%7s%7s%11s%n", "Product", "Category", "Price", "Units", "Revenue");
        System.out.println("-".repeat(56));
        for (Product p : products) {
            System.out.printf("%-18s%-13s%7d%7d%11d%n", p.name(), p.category(), p.price(), p.units(), p.revenue());
        }
        System.out.println("-".repeat(56));

        Product top = products.get(0);
        System.out.printf("Total revenue : Rs.%,d%n", totalRevenue);
        System.out.println("Units sold    : " + totalUnits);
        System.out.printf("Average price : Rs.%.2f%n", (double) totalRevenue / totalUnits);
        System.out.printf("Top product   : %s (Rs.%,d)%n", top.name(), top.revenue());

        Map<String, Integer> cats = new HashMap<>();
        for (Product p : products) cats.merge(p.category(), p.revenue(), Integer::sum);

        System.out.println("\nRevenue by category:");
        cats.entrySet().stream()
            .sorted((a, b) -> b.getValue() - a.getValue())
            .forEach(e -> {
                double share = e.getValue() * 100.0 / totalRevenue;
                System.out.printf("  %-12s Rs.%,9d  %5.1f%%  %s%n", e.getKey(), e.getValue(), share, "#".repeat((int) (share / 100 * 40)));
            });
    }
}
