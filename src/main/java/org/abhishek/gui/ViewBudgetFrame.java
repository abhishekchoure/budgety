package org.abhishek.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.abhishek.items.DailyBudgetItem;
import org.abhishek.items.ExpenseItem;
import org.abhishek.items.IncomeItem;
import org.abhishek.gui.GUI.MainGUI;



public class ViewBudgetFrame implements ActionListener,ItemListener{
    private JFrame viewBudgetFrame;
    private JLabel date , dayLabel ,monthLabel , yearLabel , dailyLabel , incomeLabel , expenseLabel;
    private JComboBox day , month , year;
    private JTextField dayText , monthText , yearText;
    private JButton confirmDateButton , doneButton;
    private JPanel header,datePanel,tablePanel,footer;
    private Font big , medium , small;
    private String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    private String[] years = {"2020","2021","2022"};
    private static Month selectedMonth = null;
    private static int selectedDay = 0;
    private static int selectedYear = 0;
    private LocalDate newDate;

    private String[][] dailyData , incomeData , expenseData;
    private String[] dailyCols = {"Total Income","Total Expense","Expense (%)","Savings"};
    private String[] incomeCols = {"Source","Amount"};
    private String[] expenseCols = {"Source","Amount","Expense(%)"};
    private JTable dailyTable , incomeTable , expenseTable;
    public DefaultTableModel dailyTableModel , incomeTableModel , expenseTableModel;
    private JScrollPane dailyPane , incomePane , expensePane;

    public ViewBudgetFrame() {
        viewBudgetFrame = new JFrame("YOUR BUDGET");
        viewBudgetFrame.setSize(MainGUI.WIDTH , MainGUI.HEIGHT);
        viewBudgetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewBudgetFrame.setResizable(true);
        viewBudgetFrame.setLocationRelativeTo(null);

        dailyTableModel = new DefaultTableModel(dailyData, dailyCols) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        incomeTableModel = new DefaultTableModel(incomeData, incomeCols) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        expenseTableModel = new DefaultTableModel(expenseData, expenseCols) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        big = new Font("Montserrat" , Font.BOLD , 30);
        medium = new Font("Poppins",Font.PLAIN , 25);
        small = new Font("Poppins" , Font.PLAIN , 18);

        header = new JPanel();
        date = new JLabel("SELECT YOUR DATE" , JLabel.CENTER);
        date.setFont(big);
        header.add(date);
        viewBudgetFrame.add(header,BorderLayout.NORTH);


        datePanel = new JPanel(null);
        datePanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
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

        dailyLabel = new JLabel("Daily Budget Summary");
        dailyLabel.setFont(medium);
        dailyTable = new JTable(dailyData,dailyCols);
        dailyTable.setModel(dailyTableModel);
        dailyTable.setFont(small);
        dailyTable.setRowHeight(30);
        dailyPane = new JScrollPane(dailyTable);


        incomeLabel = new JLabel("Income Summary");
        incomeLabel.setFont(medium);
        incomeTable = new JTable(incomeData,incomeCols);
        incomeTable.setModel(incomeTableModel);
        incomeTable.setFont(small);
        incomeTable.setRowHeight(30);
        incomePane = new JScrollPane(incomeTable);

        expenseLabel = new JLabel("Expense Summary");
        expenseLabel.setFont(medium);
        expenseTable = new JTable(expenseData,expenseCols);
        expenseTable.setModel(expenseTableModel);
        expenseTable.setFont(small);
        expenseTable.setRowHeight(30);
        expensePane = new JScrollPane(expenseTable);


        datePanel.add(dayLabel);
        datePanel.add(day);
        datePanel.add(monthLabel);
        datePanel.add(month);
        datePanel.add(yearLabel);
        datePanel.add(year);
        datePanel.add(confirmDateButton);
        datePanel.add(dailyLabel);
        datePanel.add(dailyPane);
        datePanel.add(incomeLabel);
        datePanel.add(incomePane);
        datePanel.add(expenseLabel);
        datePanel.add(expensePane);

        footer = new JPanel(new GridLayout(1,1));
        footer.setBorder(BorderFactory.createEmptyBorder(0,20,50,20));
        doneButton = new JButton("DONE");
        doneButton.setFont(small);
        doneButton.addActionListener(this);
        footer.add(doneButton);
        viewBudgetFrame.add(footer,BorderLayout.SOUTH);

        dayLabel.setBounds(30,30,100,25);
        day.setBounds(100,30,50,28);
        monthLabel.setBounds(160,30,100,25);
        month.setBounds(255,30,120,28);
        yearLabel.setBounds(380,30,100,25);
        year.setBounds(460,30,80,28);
        confirmDateButton.setBounds(170,80,250,35);
        dailyLabel.setBounds(160,150,300,35);
        dailyPane.setBounds(20,200,540,50);
        incomeLabel.setBounds(170,260,300,35);
        incomePane.setBounds(20,300,540,100);
        expenseLabel.setBounds(170,410,300,35);
        expensePane.setBounds(20,450,540,150);

        viewBudgetFrame.add(datePanel,BorderLayout.CENTER);
        viewBudgetFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == confirmDateButton) {
            if(selectedDay!=0 &&  selectedMonth!=null && selectedYear!=0) {
                if(confirmDateButton.getText() == "Confirm Date") {
                    if(validateDate(selectedDay,selectedMonth,selectedYear)) {
                        disableDateSelection();
                        confirmDateButton.setText("Change Date");
                        createDate();
                        showTables(newDate);
                    }else {
                        JOptionPane.showMessageDialog(viewBudgetFrame,"Date is invalid! Please check again","Invalid Date",JOptionPane.ERROR_MESSAGE);
                    }
                }else if(confirmDateButton.getText() == "Change Date") {
                    enableDateSelection();
                    confirmDateButton.setText("Confirm Date");
                }
            }else{
                JOptionPane.showMessageDialog(viewBudgetFrame,"Please select Complete Date!","DATE NOT SELECTED",JOptionPane.ERROR_MESSAGE);
            }
        }

        if(e.getSource() == doneButton){
            viewBudgetFrame.dispose();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getSource() == day) {
            selectedDay = Integer.parseInt((String)day.getSelectedItem());
        }
        else if(e.getSource() == month) {
            selectedMonth = (Month)month.getSelectedItem();
        }else if(e.getSource() == year){
            selectedYear = Integer.parseInt((String) year.getSelectedItem());
        }
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

    private void createDate() {
        newDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
    }

    private void updateDailyBudgetFromDatabase(LocalDate newDate) {
        dailyData = DailyBudgetItem.getDataFromDailyBudget(newDate);
    }

    private void updateExpenseItemFromDatabase(LocalDate newDate) {
        expenseData = ExpenseItem.getDataFromExpenseItem(newDate);
    }

    private void updateIncomeItemFromDatabase(LocalDate newDate) {
        incomeData = IncomeItem.getDataFromIncomeItem(newDate);
    }

    public void updateTables(LocalDate newDate) {
        updateIncomeItemFromDatabase(newDate);
        updateExpenseItemFromDatabase(newDate);
        updateDailyBudgetFromDatabase(newDate);
    }

    private boolean validateDate(int selectedDay,Month selectedMonth,int selectedYear){
        if(selectedYear % 4  == 0){
            if(selectedMonth.equals(Month.FEBRUARY)) {
                if (selectedDay > 29) {
                    return false;
                }
            }else if(selectedMonth.equals(Month.APRIL) || selectedMonth.equals(Month.JUNE) || selectedMonth.equals(Month.SEPTEMBER) || selectedMonth.equals(Month.NOVEMBER)){
                if(selectedDay > 30){
                    return false;
                }
            }
        }else{
            if(selectedMonth.equals(Month.FEBRUARY)) {
                if (selectedDay > 28) {
                    return false;
                }
            }else if(selectedMonth.equals(Month.APRIL) || selectedMonth.equals(Month.JUNE) || selectedMonth.equals(Month.SEPTEMBER) || selectedMonth.equals(Month.NOVEMBER)){
                if(selectedDay > 30){
                    return false;
                }
            }
        }
        return true;
    }

    private void showTables(LocalDate newDate) {
        DefaultTableModel dailyModel = (DefaultTableModel)dailyTable.getModel();
        dailyModel.setRowCount(0);
        DefaultTableModel incomeModel = (DefaultTableModel)incomeTable.getModel();
        incomeModel.setRowCount(0);
        DefaultTableModel expenseModel = (DefaultTableModel)expenseTable.getModel();
        expenseModel.setRowCount(0);

        updateTables(newDate);

        if(dailyData == null){
            JOptionPane.showMessageDialog(viewBudgetFrame,"No record found","Empty Record",JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[] newDayData = new Object[4];
        for(int i=0 ; i< dailyData.length ; i++) {
            newDayData[0]=dailyData[i][0];
            newDayData[1]=dailyData[i][1];
            newDayData[2]=dailyData[i][2];
            newDayData[3]=dailyData[i][3];
            dailyModel.addRow(newDayData);
        }

        Object[] newIncomeData = new Object[2];
        for(int i=0;i<incomeData.length;i++) {
            newIncomeData[0] = incomeData[i][0];
            newIncomeData[1] = incomeData[i][1];
            incomeModel.addRow(newIncomeData);
        }

        Object[] newExpenseData = new Object[3];
        for(int i=0;i<expenseData.length;i++) {
            newExpenseData[0] = expenseData[i][0];
            newExpenseData[1] = expenseData[i][1];
            newExpenseData[2] = expenseData[i][2];
            expenseModel.addRow(newExpenseData);
        }
    }
}