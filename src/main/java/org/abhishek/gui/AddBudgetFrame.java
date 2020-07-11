package org.abhishek.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.abhishek.items.DailyBudgetItem;
import org.abhishek.items.ExpenseItem;
import org.abhishek.items.IncomeItem;
import org.abhishek.items.MonthlyBudgetItem;
import org.abhishek.items.YearlyBudgetItem;


public class AddBudgetFrame implements ActionListener , ItemListener{

    JFrame addBudgetFrame;
    private final static int HEIGHT = 800;
    private final static int WIDTH = HEIGHT/12 * 9;
    private JLabel date , dayLabel ,monthLabel , yearLabel;
    private JComboBox day , month , year;
    private JTextField dayText , monthText , yearText;
    private JButton confirmDateButton;
    private JPanel datePanel , header  , footer;
    private Font big , medium , small;
    private String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    private String[] years = {"2020","2021","2022"};
    private JComboBox selectAnItem;
    private JLabel incomeLabel , expenseLabel , incAmountLabel , incSourceLabel, expAmountLabel , expSourceLabel , selectLabel;
    private JTextField incAmountField , incSourceField , expAmountField , expSourceField;
    private JButton addIncomeButton , addExpenseButton;
    private JButton doneButton , viewYourBudget;
    private YearlyBudgetItem yearObject;
    private MonthlyBudgetItem monthObject;
    private DailyBudgetItem dayObject;
    private LocalDate newDate;
    private static Month selectedMonth = null;
    private static int selectedDay = 0;
    private static int selectedYear = 0;
    private static String selectedItem = "";

    public AddBudgetFrame() {
        addBudgetFrame = new JFrame("ADD A BUDGET ITEM");
        addBudgetFrame.setSize(WIDTH,HEIGHT);
        addBudgetFrame.setLocationRelativeTo(null);
        addBudgetFrame.setResizable(false);
        addBudgetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        big = new Font("Poppins" , Font.BOLD , 30);
        medium = new Font("Poppins",Font.PLAIN , 25);
        small = new Font("Poppins" , Font.PLAIN , 18);

        header = new JPanel();
        date = new JLabel("SELECT YOUR DATE" , JLabel.CENTER);
        date.setFont(big);
        header.add(date);
        addBudgetFrame.add(header,BorderLayout.NORTH);


        datePanel = new JPanel(null);
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 400, 100));
        dayLabel = new JLabel("Day :");
        dayLabel.setFont(medium);
        day = new JComboBox<String>(days);
        day.setFont(small);
        day.setSelectedIndex(-1);
        day.addItemListener(this);


        monthLabel = new JLabel("Month :");
        monthLabel.setFont(medium);
        month = new JComboBox<Month>(Month.values());
        month.setFont(small);
        month.setSelectedIndex(-1);
        month.addItemListener(this);

        yearLabel = new JLabel("Year :");
        yearLabel.setFont(medium);
        year = new JComboBox<String>(years);
        year.setFont(small);
        year.setSelectedIndex(-1);
        year.addItemListener(this);

        confirmDateButton = new JButton("Confirm Date");
        confirmDateButton.setFont(small);
        confirmDateButton.addActionListener(this);

        selectLabel = new JLabel("Select what to add :");
        selectLabel.setFont(medium);
        selectAnItem = new JComboBox<String>(new String[] {"Income" , "Expense"});
        selectAnItem.setFont(small);
        selectAnItem.setSelectedIndex(-1);
        selectAnItem.setEnabled(false);
        selectAnItem.addItemListener(this);

        incomeLabel = new JLabel("INCOME :");
        incomeLabel.setFont(medium);
        incAmountLabel = new JLabel("Amount :");
        incAmountLabel.setFont(small);
        incAmountField = new JTextField();
        incAmountField.setFont(small);
        incSourceLabel = new JLabel("Source :");
        incSourceLabel.setFont(small);
        incSourceField = new JTextField();
        incSourceField.setFont(small);
        addIncomeButton = new JButton("ADD INCOME ITEM");
        addIncomeButton.setFont(small);
        addIncomeButton.addActionListener(this);

        expenseLabel = new JLabel("EXPENSE :");
        expenseLabel.setFont(medium);
        expAmountLabel = new JLabel("Amount :");
        expAmountLabel.setFont(small);
        expAmountField = new JTextField();
        expAmountField.setFont(small);
        expSourceLabel = new JLabel("Source :");
        expSourceLabel.setFont(small);
        expSourceField = new JTextField();
        expSourceField.setFont(small);
        addExpenseButton = new JButton("ADD EXPENSE ITEM");
        addExpenseButton.setFont(small);
        addExpenseButton.addActionListener(this);

        dayLabel.setBounds(30,30,100,25);
        day.setBounds(100,30,50,28);
        monthLabel.setBounds(160,30,100,25);
        month.setBounds(255,30,120,28);
        yearLabel.setBounds(380,30,100,25);
        year.setBounds(460,30,80,28);
        confirmDateButton.setBounds(170,80,250,35);
        selectLabel.setBounds(30,180,250,35);
        selectAnItem.setBounds(300,180,150,35);

        datePanel.add(dayLabel);
        datePanel.add(day);
        datePanel.add(monthLabel);
        datePanel.add(month);
        datePanel.add(yearLabel);
        datePanel.add(year);
        datePanel.add(confirmDateButton);
        datePanel.add(selectLabel);
        datePanel.add(selectAnItem);
        datePanel.add(incomeLabel);
        datePanel.add(incSourceLabel);
        datePanel.add(incSourceField);
        datePanel.add(incAmountLabel);
        datePanel.add(incAmountField);
        datePanel.add(addIncomeButton);
        datePanel.add(expenseLabel);
        datePanel.add(expSourceLabel);
        datePanel.add(expSourceField);
        datePanel.add(expAmountLabel);
        datePanel.add(expAmountField);
        datePanel.add(addExpenseButton);
        addBudgetFrame.add(datePanel , BorderLayout.CENTER);

        footer = new JPanel(new GridLayout(2,1));
        footer.setBorder(BorderFactory.createEmptyBorder(20,20,50,20));
        viewYourBudget = new JButton("VIEW YOUR BUDGET OF THE SPECIFIED DATE");
        viewYourBudget.setEnabled(false);
        viewYourBudget.setFont(small);
        viewYourBudget.addActionListener(this);
        doneButton = new JButton("DONE");
        doneButton.setFont(small);
        doneButton.addActionListener(this);

        footer.add(viewYourBudget);
        footer.add(doneButton);
        addBudgetFrame.add(footer,BorderLayout.SOUTH);

        addBudgetFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirmDateButton) {
            if(selectedDay!=0 &&  selectedMonth!=null && selectedYear!=0) {
                if(confirmDateButton.getText().equals("Confirm Date")) {
                    disableDateSelection();
                    JOptionPane.showMessageDialog(addBudgetFrame,"Your Confirmed Date is : " +  selectedDay + " " + selectedMonth + ", " + selectedYear,"DATE SELECTED SUCCESSFULLY",JOptionPane.INFORMATION_MESSAGE);
                    confirmDateButton.setText("Change Date");
                    selectAnItem.setEnabled(true);
                    viewYourBudget.setEnabled(true);
                    selectAnItem.setSelectedIndex(0);
                    createDate();
                    yearObject = new YearlyBudgetItem(selectedYear,0,0,0,0);
                    yearObject.addYearlyBudgetItemToDatabase();
                    monthObject = new MonthlyBudgetItem(selectedMonth,0,0,0,0,selectedYear);
                    monthObject.addMonthlyBudgetItemToDatabase();
                    dayObject = new DailyBudgetItem(newDate,0,0,0,0,selectedMonth,selectedYear);
                    dayObject.addDailyBudgetItemToDatabase();
                }else if(confirmDateButton.getText().equals("Change Date")) {
                    enableDateSelection();
                    confirmDateButton.setText("Confirm Date");
                    hideIncomeItem();
                    hideExpenseItem();
                    selectAnItem.setEnabled(false);
                    viewYourBudget.setEnabled(false);
                }
            }else{
                JOptionPane.showMessageDialog(addBudgetFrame,"Please select Complete Date!","DATE NOT SELECTED",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource() == doneButton){
            addBudgetFrame.dispose();
            selectedDay=0;
            selectedMonth = null;
            selectedYear = 0;
        }

        if(e.getSource() == addIncomeButton) {
            if(!incAmountField.getText().isEmpty() && !incSourceField.getText().isEmpty()) {
                createDate();
                double incAmount = Double.parseDouble(incAmountField.getText());
                String incSource = incSourceField.getText();
                IncomeItem income = new IncomeItem(incAmount , incSource , newDate);
                income.addIncomeItemToDatabase();
                JOptionPane.showMessageDialog(addBudgetFrame,"INCOME ADDED SUCCESSFULLY!","INCOME",JOptionPane.INFORMATION_MESSAGE);
                incAmountField.setText("");incSourceField.setText("");
                dayObject.updateDailyBudgetIncome();
                monthObject.updateMonthlyBudget();
                yearObject.updateYearlyBudget();
            }else {
                JOptionPane.showMessageDialog(addBudgetFrame,"Please fill all the Income fields!","INCOME!",JOptionPane.ERROR_MESSAGE);
            }
        }else if(e.getSource() == addExpenseButton) {
            if(!expAmountField.getText().isEmpty() && !expSourceField.getText().isEmpty()) {
                createDate();
                double expAmount = Double.parseDouble(expAmountField.getText());
                String expSource = expSourceField.getText();
                ExpenseItem expense = new ExpenseItem(expAmount,expSource,0,newDate);
                expense.addExpenseItemToDatabase();
                JOptionPane.showMessageDialog(addBudgetFrame,"EXPENSE ADDED SUCCESSFULLY!","EXPENSE",JOptionPane.INFORMATION_MESSAGE);
                expAmountField.setText(" ");expSourceField.setText(" ");
                dayObject.updateDailyBudgetExpense();
                monthObject.updateMonthlyBudget();
                yearObject.updateYearlyBudget();
            }else {
                JOptionPane.showMessageDialog(addBudgetFrame,"Please fill all the Income fields!","EXPENSE",JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == day) {
            selectedDay = Integer.parseInt((String)day.getSelectedItem());
        }
        else if(e.getSource() == month) {
            selectedMonth = (Month)month.getSelectedItem();
            if(selectedMonth.equals(Month.FEBRUARY)){
                validateDate(28);
            }else if(selectedMonth.equals(Month.APRIL) || selectedMonth.equals(Month.JUNE) || selectedMonth.equals(Month.SEPTEMBER) || selectedMonth.equals(Month.NOVEMBER)){
               validateDate(30);
            }else{
               validateDate(31);
            }
        }else if(e.getSource() == year) {
            selectedYear = Integer.parseInt((String)year.getSelectedItem());
            if(selectedYear%4 == 0){
                if(selectedMonth == Month.FEBRUARY){
                    validateDate(29);
                }
            }else{
                if(selectedMonth == Month.FEBRUARY){
                    validateDate(28);
                }
            }
        }

        if(e.getSource() == selectAnItem) {
            selectedItem = selectAnItem.getSelectedItem().toString();
            if(selectedItem == "Income") {
                hideExpenseItem();
                showIncomeItem();
            }
            if(selectedItem == "Expense") {
                hideIncomeItem();
                showExpenseItem();
            }
        }
    }

    private void showIncomeItem() {
        incomeLabel.setBounds(50,250,150,35);
        incSourceLabel.setBounds(50,300,120,30);
        incSourceField.setBounds(180,300,200,30);
        incAmountLabel.setBounds(50,350,120,30);
        incAmountField.setBounds(180,350,200,30);
        addIncomeButton.setBounds(50,430,250,35);
    }

    private void hideIncomeItem() {
        incomeLabel.setBounds(0,0,0,0);
        incSourceLabel.setBounds(0,0,0,0);
        incSourceField.setBounds(0,0,0,0);
        incAmountLabel.setBounds(0,0,0,0);
        incAmountField.setBounds(0,0,0,0);
        addIncomeButton.setBounds(0,0,0,0);
    }

    private void showExpenseItem() {
        expenseLabel.setBounds(50,250,150,35);
        expSourceLabel.setBounds(50,300,120,30);
        expSourceField.setBounds(180,300,200,30);
        expAmountLabel.setBounds(50,350,120,30);
        expAmountField.setBounds(180,350,200,30);
        addExpenseButton.setBounds(50,430,250,35);
    }

    private void hideExpenseItem() {
        expenseLabel.setBounds(0,0,0,0);
        expSourceLabel.setBounds(0,0,0,0);
        expSourceField.setBounds(0,0,0,0);
        expAmountLabel.setBounds(0,0,0,0);
        expAmountField.setBounds(0,0,0,0);
        addExpenseButton.setBounds(0,0,0,5);
    }

    private void disableDateSelection() {
        day.setEnabled(false);
        month.setEnabled(false);
        year.setEnabled(false);
    }

    private void enableDateSelection() {
        day.setEnabled(true);
        month.setEnabled(true);
        year.setEnabled(true);
    }

    private void validateDate(int noOfDays){
        datePanel.remove(day);
        day.setEnabled(false);
        days = new String[noOfDays];
        for(int i=0;i<noOfDays;i++){
            days[i] = Integer.valueOf(i+1).toString();
        }
        day = new JComboBox<String>(days);
        day.setBounds(100,30,55,28);
        day.addItemListener(this);
        day.setFont(small);
        datePanel.add(day);
    }

    private void createDate() {
        newDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
    }

    public Insets getInsets() {
        return new Insets(10,10,10,10);
    }
}