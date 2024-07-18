import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

class UserAccount {
    protected Map<String, Map<String, String>> userData = new LinkedHashMap<>();

    protected UserAccount() {
        Map<String, String> user1 = new LinkedHashMap<>();
        user1.put("Name", "Sundarapandi T");
        user1.put("AccountNo", "2136010075");
        user1.put("Balance", "25000");
        user1.put("Password", "sundar0075");

        Map<String, String> user2 = new LinkedHashMap<>();
        user2.put("Name", "Sasi");
        user2.put("AccountNo", "2136010014");
        user2.put("Balance", "20000");
        user2.put("Password", "sasi0014");

        userData.put("2136010075", user1);
        userData.put("2136010014", user2);
    }

    public boolean verifyAccount(String accNo, String pw) {
        Map<String, String> user = userData.get(accNo);
        return user != null && user.get("Password").equals(pw);
    }

    public boolean withdraw(String accNo, int amount) {
        Map<String, String> user = userData.get(accNo);
        if (user != null) {
            int currentBalance = Integer.parseInt(user.get("Balance"));
            if (currentBalance >= amount) {
                user.put("Balance", String.valueOf(currentBalance - amount));
                return true;
            }
        }
        return false;
    }

    public boolean deposit(String accNo, int amount) {
        Map<String, String> user = userData.get(accNo);
        if (user != null) {
            int currentBalance = Integer.parseInt(user.get("Balance"));
            if (amount > 100) {
                user.put("Balance", String.valueOf(currentBalance + amount));
                return true;
            }
        }
        return false;
    }
}

public class ATM_Interface extends UserAccount {
    static UserAccount data = new UserAccount();
    static String accountNumber;

    public static void performOperation() {
        JFrame frame2 = new JFrame("ATM Operation");
        frame2.setSize(500, 600);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("OUR ATM Services", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(title, gbc);

        gbc.gridwidth = 1;

        JButton checkBalance = new JButton("Check Balance");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(checkBalance, gbc);
        checkBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame(frame2, resultPanel(true, false, false));
            }
        });

        JButton withdraw = new JButton("Withdraw");
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(withdraw, gbc);
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame(frame2, resultPanel(false, true, false));
            }
        });

        JButton deposit = new JButton("Deposit");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(deposit, gbc);
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFrame(frame2, resultPanel(false, false, true));
            }
        });

        JButton exit = new JButton("Exit");
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(exit, gbc);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        frame2.getContentPane().add(mainPanel);
        frame2.setVisible(true);
    }

    public static JPanel resultPanel(boolean isBalance, boolean isWithdraw, boolean isDeposit) {
        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("TSP ATM Services", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        viewPanel.add(title, gbc);

        if (isBalance) {
            Map<String, String> user = data.userData.get(accountNumber);
            if (user != null) {
                JLabel balanceLabel = new JLabel("Your Balance: Rs." + user.get("Balance"), JLabel.CENTER);
                balanceLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                viewPanel.add(balanceLabel, gbc);
            }
        } else {
            JLabel amountLabel = new JLabel("Enter Your Amount: ");
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            viewPanel.add(amountLabel, gbc);

            JTextField amountField = new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 1;
            viewPanel.add(amountField, gbc);

            JButton actionButton = new JButton(isWithdraw ? "Withdraw" : "Deposit");
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            viewPanel.add(actionButton, gbc);

            JLabel messageLabel = new JLabel();
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            viewPanel.add(messageLabel, gbc);

            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String amountStr = amountField.getText();
                    try {
                        int amount = Integer.parseInt(amountStr);
                        boolean success = isWithdraw ? data.withdraw(accountNumber, amount) : data.deposit(accountNumber, amount);
                        messageLabel.setText(success
                                ? (isWithdraw ? "Withdraw" : "Deposit") + " Successful! New Balance: Rs." + data.userData.get(accountNumber).get("Balance")
                                : isWithdraw ? "Insufficient Balance!" : "Enter Amount at least Rs.100!");
                    } catch (NumberFormatException ex) {
                        messageLabel.setText("Invalid Amount!");
                    }
                }
            });
        }

        return viewPanel;
    }

    private static void updateFrame(JFrame frame, JPanel newPanel) {
        frame.getContentPane().removeAll();
        JButton backbtn = new JButton("Back");
        backbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performOperation();
            }
        });
        newPanel.add(backbtn);
        frame.getContentPane().add(newPanel);
        frame.revalidate();
        frame.repaint();
    }
   public static void login()
   {
       JFrame frame = new JFrame("ATM-System");
       frame.setSize(500, 600);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       JPanel mainPanel = new JPanel();
       mainPanel.setLayout(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.insets = new Insets(10, 10, 10, 10);

       JLabel title = new JLabel("Welcome to TSP ATM Service", JLabel.CENTER);
       title.setFont(new Font("Arial", Font.BOLD, 24));
       gbc.gridx = 0;
       gbc.gridy = 0;
       gbc.gridwidth = 2;
       mainPanel.add(title, gbc);

       gbc.gridwidth = 1;

       JLabel accountNo = new JLabel("Enter Your Account No: ");
       gbc.gridx = 0;
       gbc.gridy = 1;
       mainPanel.add(accountNo, gbc);

       JTextField accountField = new JTextField(15);
       gbc.gridx = 1;
       gbc.gridy = 1;
       mainPanel.add(accountField, gbc);

       JLabel password = new JLabel("Enter Your Password: ");
       gbc.gridx = 0;
       gbc.gridy = 2;
       mainPanel.add(password, gbc);

       JPasswordField pinField = new JPasswordField(15);
       gbc.gridx = 1;
       gbc.gridy = 2;
       mainPanel.add(pinField, gbc);

       JButton enter = new JButton("Enter");
       gbc.gridx = 1;
       gbc.gridy = 3;
       mainPanel.add(enter, gbc);

       JLabel valid = new JLabel();
       gbc.gridx = 0;
       gbc.gridy = 4;
       gbc.gridwidth = 2;
       mainPanel.add(valid, gbc);

       enter.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               accountNumber = accountField.getText();
               String password = new String(pinField.getPassword());
               if (data.verifyAccount(accountNumber, password)) {
                   performOperation();
               } else {
                   valid.setText("Invalid AccountNo or Password!");
               }
           }
       });

       frame.getContentPane().add(mainPanel);
       frame.setVisible(true);
   }
    public static void main(String[] args) {
       login();
}
}