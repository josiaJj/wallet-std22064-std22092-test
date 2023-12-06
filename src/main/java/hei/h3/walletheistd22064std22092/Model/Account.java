package hei.h3.walletheistd22064std22092.Model;

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
}
