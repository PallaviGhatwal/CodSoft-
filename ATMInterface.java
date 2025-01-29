// package JavaProject;

import java.util.Scanner;

class BankAccount {
    private String accountHolderName;
    private String accountNumber;
    private float balance;

    public BankAccount(String accountHolderName, String accountNumber, float initialBalance) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public float getBalance() {
        return balance;
    }

    public boolean withdraw(float amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean deposit(float amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }
}

class ATM {
    private BankAccount linkedAccount;

    public ATM(BankAccount account) {
        this.linkedAccount = account;
    }

    public void showMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;

            while (isRunning) {
                System.out.println("\nATM MENU:");
                System.out.println("1. Withdraw");
                System.out.println("2. Deposit");
                System.out.println("3. Check Balance");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to withdraw: ");
                        float withdrawAmount = scanner.nextFloat();
                        if (linkedAccount.withdraw(withdrawAmount)) {
                            System.out.println("Withdrawal successful. New balance: " + linkedAccount.getBalance() + " Rs");
                        } else {
                            System.out.println("Withdrawal failed. Insufficient balance or invalid amount.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        float depositAmount = scanner.nextFloat();
                        if (linkedAccount.deposit(depositAmount)) {
                            System.out.println("Deposit successful. New balance: " + linkedAccount.getBalance() + " Rs");
                        } else {
                            System.out.println("Deposit failed. Invalid amount.");
                        }
                        break;
                    case 3:
                        System.out.println("Current balance: " + linkedAccount.getBalance() + " Rs");
                        break;
                    case 4:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nWelcome to the ATM Interface!");
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your account number: ");
            String accountNumber = scanner.nextLine();

            BankAccount userAccount = new BankAccount(name, accountNumber, 10000f); // Initial balance of 10000 Rs
            ATM atm = new ATM(userAccount);

            atm.showMenu();
        }
    }
}
