import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class AccountFrame extends JFrame {
    JLabel accountNoLBL, ownerLBL, balanceLBL, cityLBL, genderLBL, amountLBL;
    JTextField accountNoTXT, ownerTXT, balanceTXT, amountTXT;
    JComboBox<City> citiesCMB;

    JButton newBTN, saveBTN, showBTN, depositBTN, withdrawBTN, quitBTN;
    JRadioButton maleRDB, femaleRDB;
    ButtonGroup genderBTNGRP;

    JList<Account> accountsLST;
    JPanel p1, p2, p3, p4, p5;

    Set<Account> accountSet = new TreeSet<>();
    Account acc, x;
    boolean newRec = true;

    // combobox data
    DefaultComboBoxModel<City> citiesCMBMDL;
    DefaultListModel<Account> accountsLSTMDL;

    // Table Data

    JTable table;
    DefaultTableModel tableModel;
    ArrayList<Transaction> translist = new ArrayList<>();

    public AccountFrame() throws HeadlessException {
        super("Account Operations");
        setLayout(null);
        setSize(600, 400);


        //Adding Components to the frame
        accountNoLBL = new JLabel("Account Number:");
        ownerLBL = new JLabel("Owner Name:");
        balanceLBL = new JLabel("Balance:");
        cityLBL = new JLabel("City");
        genderLBL = new JLabel("Gender");
        amountLBL = new JLabel("Amount");

        // 2 -  TextField
        accountNoTXT = new JTextField();
        accountNoTXT.setEnabled(false);
        accountNoTXT.setOpaque(false);

        ownerTXT = new JTextField();
        //ownerTXT.setEnabled(false);
        ownerTXT.setOpaque(false);

        balanceTXT = new JTextField();
        balanceTXT.setEnabled(false);
        balanceTXT.setOpaque(false);

        amountTXT = new JTextField();
        amountTXT.setPreferredSize(new Dimension(150, 25));

        // ComboBox
        citiesCMBMDL = new DefaultComboBoxModel<>();
        citiesCMBMDL.addElement(null);
        citiesCMBMDL.addElement(new City("Patna", "Bihar"));
        citiesCMBMDL.addElement(new City("Gaya", "Bihar"));
        citiesCMBMDL.addElement(new City("Bairiya", "Bihar"));
        citiesCMBMDL.addElement(new City("Danapur", "Bihar"));
        citiesCMBMDL.addElement(new City("Jahanabaad", "Bihar"));

        //Adding data to JCOMBOBOX
        citiesCMB = new JComboBox<City>(citiesCMBMDL);

        // 4- Radio Button
        maleRDB = new JRadioButton("Male", true);
        femaleRDB = new JRadioButton("Female");
        genderBTNGRP = new ButtonGroup();
        genderBTNGRP.add(maleRDB);
        genderBTNGRP.add(femaleRDB);


        // 5- Buttons

        newBTN = new JButton("New");
        saveBTN = new JButton("Save");
        showBTN = new JButton("Show");
        quitBTN = new JButton("Quit");
        depositBTN = new JButton("Deposit");
        withdrawBTN = new JButton("Withdraw");


        // 6 - Table

        accountsLSTMDL = new DefaultListModel<>();
        accountsLST = new JList<>(accountsLSTMDL);

        //7- Panels
        p1 = new JPanel();
        p1.setBounds(5, 5, 300, 150);
        p1.setLayout(new GridLayout(5, 2));

        p2 = new JPanel();
        p2.setBounds(5, 155, 300, 40);
        p2.setLayout(new FlowLayout());

        p3 = new JPanel();
        p3.setBounds(5, 195, 600, 40);
        p3.setLayout(new FlowLayout());

        p4 = new JPanel();
        p4.setBounds(305, 5, 300, 190);
        p4.setLayout(new BorderLayout());

        p5 = new JPanel();
        p5.setBounds(5, 240, 580, 120);
        p5.setLayout(new BorderLayout());


        // Adding Components to the Pannels
        p1.add(accountNoLBL);
        p1.add(accountNoTXT);
        p1.add(ownerLBL);
        p1.add(ownerTXT);
        p1.add(balanceLBL);
        p1.add(balanceTXT);
        p1.add(cityLBL);
        p1.add(citiesCMB);
        p1.add(maleRDB);
        p1.add(femaleRDB);


        p2.add(newBTN);
        p2.add(saveBTN);
        p2.add(showBTN);
        p2.add(quitBTN);


        p3.add(amountLBL);
        p3.add(amountTXT);
        p3.add(depositBTN);
        p3.add(withdrawBTN);

        p4.add(accountsLST);


        //Adding Panel to Frame

        add(p1);
        add(p2);
        add(p3);
        add(p4);
        add(p5);


        // Table Creation

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("TrsNo");
        tableModel.addColumn("TrsDate");
        tableModel.addColumn("TrsType");
        tableModel.addColumn("TrsAmount");


        JScrollPane scrollPane = new JScrollPane(table);
        p5.add(scrollPane);


        // *************************************************** Function *****************************

        newBTN.addActionListener(e -> {
            accountNoTXT.setText(" ");
            ownerTXT.setText(" ");
            citiesCMB.setSelectedItem(0);
            maleRDB.setSelected(true);
            balanceTXT.setText(" ");
            amountTXT.setText(" ");
            newRec = true;
        });
        saveBTN.addActionListener(e -> {
            if (newRec) {
                //Insertion
                if (ownerTXT.getText().length() != 0) {
                    acc = new Account(
                            ownerTXT.getText(), (City) citiesCMB.getSelectedItem(),
                            maleRDB.isSelected() ? 'M' : 'F'
                    );
                    accountNoTXT.setText(String.valueOf(acc.accNo));
                    accountSet.add(acc);
                    accountsLSTMDL.addElement(acc);
                    newRec = false;
                } else {
                    JOptionPane.showMessageDialog(null, "Please full all fields");
                }
            } else {
                // Updating
                accountSet.remove(acc);
                int a = Integer.parseInt(accountNoTXT.getText());
                String o = ownerTXT.getText();
                City c = (City) citiesCMB.getSelectedItem();

                char g = maleRDB.isSelected() ? 'M' : 'F';
                double b = Double.parseDouble(balanceTXT.getText());
                acc = new Account(a, o, c, g, b);
                accountsLSTMDL.setElementAt(acc, accountsLST.getSelectedIndex());
                newRec = false;
            }
        });


        showBTN.addActionListener(e -> {
            String s = "";
            Iterator<Account> it = accountSet.iterator();
            while (it.hasNext()) {
                s += it.toString() + "\n";
                JOptionPane.showMessageDialog(null, s);
            }
        });
        depositBTN.addActionListener(e -> {
            if (!newRec && amountTXT.getText().length() != 0) {
                // Adding Transaction to table
                Transaction t = new Transaction(acc, LocalDate.now(),
                        'D', Double.parseDouble(amountTXT.getText()));


                DisplayTransactionsInTable(t);
                // Perform deposit from account
                acc.deposit(Double.parseDouble(amountTXT.getText()));
                balanceTXT.setText(String.valueOf(acc.balance));
            }
        });
        withdrawBTN.addActionListener(e -> {
            if (!newRec && amountTXT.getText().length()!=0)
            {
                //Adding Transaction to table
                Transaction t = new Transaction(
                        acc ,LocalDate.now(),
                        'W',
                        Double.parseDouble(amountTXT.getText()));


                DisplayTransactionsInTable(t);

                // Perform withdrawal on account

                acc.withdraw(Double.parseDouble(amountTXT.getText()));
                balanceTXT.setText(String.valueOf(acc.balance));
            }
        });

        accountsLST.addListSelectionListener(e -> {
            acc = x= accountsLST.getSelectedValue();

            accountNoTXT.setText(String.valueOf(acc.accNo));
            ownerTXT.setText(acc.owner);
            citiesCMB.setSelectedItem(acc.city);

            if (acc.gender == 'M')
                maleRDB.setSelected(true);
            else
                femaleRDB.setSelected(true);

            balanceTXT.setText(String.valueOf(acc.balance));
            amountTXT.setEnabled(true);
            depositBTN.setEnabled(true);
            newRec = false;

            // Clear Table:
            for (int i = tableModel.getRowCount() -1;i>=0;i++)
            {
                tableModel.removeRow(i);
            }

            // Get Transactions to selected Account
            SearchTransactionList(acc.accNo);
        });
    }

    private void SearchTransactionList(int accNo) {
        List<Transaction> filterList = new ArrayList<>();

        //iterate through the list

        for (Transaction t: translist)
        {
            // filter value that contains trsNo
            if (t.getAcc().accNo == accNo)
            {
                filterList.add(t);
            }
        }

        // Display the filtered List

        for (int i= 0 ; i< filterList.size();i++)
        {
            //Display Data into table
            tableModel.addRow(new Object[]{
                    filterList.get(i).getTrsNo(),
                    filterList.get(i).getDate(),
                    filterList.get(i).getOperation(),
                    filterList.get(i).getOperation(),
                    filterList.get(i).getAmount()
            });
        }

    }

    private void DisplayTransactionsInTable(Transaction t) {
        // Display data into table
        tableModel.addRow(new Object[]{
                t.getTrsNo(),
                t.getDate(),
                t.getOperation(),
                t.getAmount()
        });

        // Adding object  to array list
        translist.add(t);

    }

    public static void main(String[] args) {
        AccountFrame af = new AccountFrame();
        af.setVisible(true);
        af.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
