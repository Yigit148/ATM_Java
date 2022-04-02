/* Program explanation: first you have to create an account and deposit money into it. Create method runs and writes your id and password in separate txt files
* and calls deposit_first which writes your balance in another txt file. Then when you run it again you can choose access and it will take your id and password from you
* and check if they match using another method. Which checks if the given id's index and given PIN's indexs match. If they do it gives you 3 choices
* withdraw,deposit or balance. There is a deposit() method and both withdraw and deposit calls it, withdraw gives a negative int while deposit gives a positive */

import java.io.*;
import java.util.Scanner;

public class ATM {
    static Scanner scanner = new Scanner(System.in);
    static String ID = "";
    static int index_no;
    static int balance_account;
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Type 'access' to access your bank account, Type 'create' to create a bank account");
        String access_create = scanner.nextLine();

        if ((access_create.equals("access"))){
            if (check()){
                access(ID);
            }else{
                System.out.println("An error occurred");
            }
        }else if (access_create.equals("create")){
            create();
        }

    }

     static void access(String id) throws FileNotFoundException {
        System.out.println("Type 'withdraw' to withdraw from your bank account, Type 'deposit' to deposit to your account, Type 'balance' to see your balance");
        String withdraw_deposit = scanner.nextLine();

        switch (withdraw_deposit) {
            case ("withdraw") -> {
                balance("New_Balance.txt");
                withdraw();}
            case ("deposit") -> {
                balance("New_Balance.txt");
                deposit_money();}
            case ("balance") -> {
                balance("New_Balance.txt");
                access(ID);}
        }
    }

    static void create() {
        System.out.println("Please Type in Your ID Number ");
        String client_ID = scanner.nextLine();

        System.out.println("Please Type in PIN ");
        String  client_password = scanner.nextLine();

        try {
            FileWriter ID = new FileWriter("ID_CHECK.txt",true);
            FileWriter passwords = new FileWriter("PIN_CHECK.txt",true);
            ID.write(client_ID + "\n");
            passwords.write(client_password + "\n");

            deposit_first();

            ID.close();
            passwords.close();

            System.out.println("Successfull.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    static void withdraw() throws FileNotFoundException {
        System.out.println("Please Type in the amount you want to withdraw");
        int new_balance_amount = scanner.nextInt();
        deposit("", -new_balance_amount);

    }

    static void deposit_first() {
        System.out.println("Please Type in the amount you want to deposit, if you wont deposit Type '0' ");
        String balance_amount = scanner.nextLine();

        try {
            FileWriter balance = new FileWriter("New_Balance.txt",true);
            balance.write(balance_amount + "\n");
            balance.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void deposit_money() throws FileNotFoundException {
        System.out.println("Please Type in the amount you want to deposit");
        int new_balance_amount = scanner.nextInt();
        deposit("",new_balance_amount);
    }

    static void deposit(String id, int nw) throws FileNotFoundException {

        balance_account += nw;
        System.out.println("New Account Balance: " + balance_account);

        try {
            FileWriter balance = new FileWriter("New_Balance.txt");
            File balance1 = new File("Balance.txt");
            Scanner myReader = new Scanner(balance1);
            int i = 0;
            while (myReader.hasNextLine()) {
                i++;
                String data = myReader.nextLine();
                if (i == index_no){
                    balance.write(balance_account + "\n");
                }else{
                    balance.write(data + "\n");
                }
            }

            balance.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static void balance(String a) throws FileNotFoundException {
        int i = 0;
        File balance = new File(a);
        Scanner myReader = new Scanner(balance);
        while (myReader.hasNextLine()) {
            i++;
            String data = myReader.nextLine();
            if (i == index_no){
                System.out.println("Your current balance: " + data);
                balance_account = Integer.parseInt(data);
            }
        }
    }

    static boolean check() throws FileNotFoundException {
        System.out.println("Please Type in Your ID Number");
        String client_ID = scanner.nextLine();

        ID = client_ID;

        System.out.println("Please Type in PIN");
        String  client_password = scanner.nextLine();

        File ID = new File("ID_CHECK.txt");
        File PIN = new File("PIN_CHECK.txt");

        return check_Reader(client_ID, ID) == check_Reader(client_password, PIN);

    }

    static int check_Reader(String checker, File files) throws FileNotFoundException {
        int i = 0;
        Scanner myReader = new Scanner(files);
        while (myReader.hasNextLine()) {
            i++;
            String data = myReader.nextLine();
            if (checker.equals(data)){
                index_no = i;
                return i;
            }
        }
        System.out.println("No Account Found");
        main(null);
        myReader.close();
        return 0;
    }
}