import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BankAccount {
    private String accountHolder;
    private double balance;

    public BankAccount(String accountHolder, double balance) {
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0)
            balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

public class BankAccountGUI extends JFrame implements ActionListener {
    private BankAccount account;
    private JLabel nameLabel, balanceLabel, messageLabel;
    private JTextField amountField;
    private JButton depositBtn, withdrawBtn, checkBalanceBtn;

    public BankAccountGUI() {
        // Initialize account
        account = new BankAccount("Anuj kumar", 1000.00);

        // Frame setup
        setTitle("🏦 Bank Account Management");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Gradient background panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(25, 25, 35);
                Color color2 = new Color(45, 45, 65);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Components
        nameLabel = new JLabel("👤 " + account.getAccountHolder(), JLabel.CENTER);
        nameLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);

        balanceLabel = new JLabel("💰 Balance: ₹" + account.getBalance(), JLabel.CENTER);
        balanceLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        balanceLabel.setForeground(new Color(180, 220, 255));

        amountField = new JTextField();
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setFont(new Font("Poppins", Font.PLAIN, 14));
        amountField.setForeground(Color.WHITE);
        amountField.setBackground(new Color(40, 40, 60));
        amountField.setCaretColor(Color.WHITE);
        amountField.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150)), 
                "Enter Amount", 0, 0, new Font("Poppins", Font.PLAIN, 12), new Color(160, 160, 255)));

        depositBtn = createStyledButton("Deposit");
        withdrawBtn = createStyledButton("Withdraw");
        checkBalanceBtn = createStyledButton("Check Balance");

        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        checkBalanceBtn.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(checkBalanceBtn);

        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Poppins", Font.ITALIC, 13));
        messageLabel.setForeground(new Color(160, 255, 160));

        // Add components
        mainPanel.add(nameLabel);
        mainPanel.add(balanceLabel);
        mainPanel.add(amountField);
        mainPanel.add(buttonPanel);
        mainPanel.add(messageLabel);

        add(mainPanel, BorderLayout.CENTER);

        // Apply custom look
        UIManager.put("Button.select", new Color(60, 60, 80));
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 60, 90));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 180), 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(90, 90, 140));
                button.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 255), 2, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 60, 90));
                button.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 180), 1, true));
            }
        });
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (e.getSource() == depositBtn) {
                account.deposit(amount);
                messageLabel.setText("✅ Deposited ₹" + amount + " successfully!");
            } else if (e.getSource() == withdrawBtn) {
                if (account.withdraw(amount))
                    messageLabel.setText("💸 Withdrawn ₹" + amount + " successfully!");
                else
                    messageLabel.setText("⚠️ Insufficient balance!");
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("❗ Please enter a valid amount!");
        }

        if (e.getSource() == checkBalanceBtn) {
            messageLabel.setText("💰 Current Balance: ₹" + account.getBalance());
        }

        balanceLabel.setText("💰 Balance: ₹" + account.getBalance());
        amountField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankAccountGUI());
    }
}
