package hei.h3.walletheistd22064std22092;

import hei.h3.walletheistd22064std22092.Model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class WalletHeiStd22064Std22092Application {

    public static void main(String[] args) {
        SpringApplication.run(WalletHeiStd22064Std22092Application.class, args);
        Currency euro = new Currency(1, "Euro", "EUR");
        Account account = new Account(1, "Checking Account", 100000, euro, "Bank");

        // Ajouter des transactions au compte (utilisez votre propre logique pour ajouter les transactions)
        Transaction transaction1 = new Transaction(1, "Salary", 100000, "Credit");
        account.performTransaction(transaction1);

        Transaction transaction2 = new Transaction(2, "Christmas Gift", 50000, "Debit");
        account.performTransaction(transaction2);

        Transaction transaction3 = new Transaction(3, "New Shoes", 20000, "Debit");
        account.performTransaction(transaction3);

        // Get Date and Time now
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // Obtenir le solde actuel
        double currentBalance = account.getBalanceAtDateTime(formattedDateTime);
        System.out.println("Current Balance: " + currentBalance);
    }

}
