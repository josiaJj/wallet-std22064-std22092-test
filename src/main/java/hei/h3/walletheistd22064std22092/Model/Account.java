package hei.h3.walletheistd22064std22092.Model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Account {
    private int id;
    private String name;
    private double balance;
    private List<Transaction> listTransactions = new ArrayList<>();
    private Currency currency;
    private String accountType;

    public Account(int i, String checkingAccount, int i1, Currency euro, String bank) {
    }

    public Account performTransaction(Transaction transaction) {
        // Checking balance sufficiency for non-"Bank" accounts on debit transactions
        if (!accountType.equals("Bank") && transaction.getTransactionType().equals("Debit")) {
            if (balance < transaction.getAmount()) {
                System.out.println("Insufficient balance");
                return null;
            }
        }

        // Adding the transaction to the account's transaction list
        getListTransactions().add(transaction);

        // Updating the account balance based on the transaction type
        if (transaction.getTransactionType().equals("Credit")) {
            balance += transaction.getAmount();
        } else if (transaction.getTransactionType().equals("Debit")) {
            balance -= transaction.getAmount();
        }

        // Return the state of the account updated
        return this;
    }

    public double getBalanceAtDateTime(String dateTimeString) {
        // Converting Strind Date into Object Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Date dateTime;
        try {
            dateTime = (Date) dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            System.out.println("Date format error");
            return -1;
        }

        // Calculate balance at specified date and time
        double balance = 0;
        for (Transaction transaction : getListTransactions()) {
            if (transaction.getDateTime().before(dateTime) || transaction.getDateTime().equals(dateTime)) {
                if (transaction.getTransactionType().equals("Credit")) {
                    balance += transaction.getAmount();
                } else if (transaction.getTransactionType().equals("Debit")) {
                    balance -= transaction.getAmount();
                }
            }
        }

        return balance;
    }
    public double getCurrentBalance() {
        // Get Date and Time now
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCurrentDateTime = currentDateTime.format(formatter);

        return getBalanceAtDateTime(formattedCurrentDateTime);
    }
    public List<Transaction> getBalanceListBetween(String startDateTime, String endDateTime) {
        // Converting String Date into LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTimeInDate;
        LocalDateTime endDateTimeInDate;

        try {
            startDateTimeInDate = LocalDateTime.parse(startDateTime, formatter);
            endDateTimeInDate = LocalDateTime.parse(endDateTime, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Date format error");
            return new ArrayList<>(); // Return an empty list or handle the error appropriately
        }

        List<Transaction> transactionListBetween = new ArrayList<>();
        for (Transaction transaction : getListTransactions()) {
            LocalDateTime transactionDateTime =
                    LocalDateTime.ofInstant(transaction.getDateTime().toInstant(), ZoneId.systemDefault());

            if (transactionDateTime.isAfter(startDateTimeInDate) && transactionDateTime.isBefore(endDateTimeInDate)) {
                transactionListBetween.add(transaction);
            }
        }

        return transactionListBetween;
    }
}
