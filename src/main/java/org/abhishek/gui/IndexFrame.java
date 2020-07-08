package org.abhishek.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.abhishek.items.MonthlyBudgetItem;
import org.abhishek.items.YearlyBudgetItem;

public class IndexFrame implements ActionListener{

    JFrame frame;
    JLabel title , description, getStarted , year , month ;
    JButton addBudget , viewBudget ,refreshButton,closeButton;
    JTable yearTable , monthTable ;
    JScrollPane monthPane , yearPane;
    JPanel headerPanel , contentPanel , navigationPanel;
    Font mainFont , descriptionFont ,buttonFont , tableFont;

    private String[][] monthlyData;
    private String[] monthlyCols = {"Month","Total Income","Total Expense","Expense(%)","Total Savings"};
    private String[] yearlyCols = {"Year" ,"Total Income","Total Expense","Expense(%)","Total Savings"};
    private String[][] yearlyData;
    public DefaultTableModel monthTableModel;
    public DefaultTableModel yearTableModel;

    public IndexFrame(JFrame frame) {
        this.frame = frame;
        this.frame.setLayout(new BorderLayout(5,5));
        this.frame.setResizable(false);

        updateTables();

        monthTableModel = new DefaultTableModel(monthlyData, monthlyCols) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        yearTableModel = new DefaultTableModel(yearlyData, yearlyCols) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        mainFont = new Font("Poppins",Font.BOLD,30);
        descriptionFont = new Font("Poppins",Font.PLAIN,18);
        buttonFont = new Font("Poppins",Font.PLAIN,20);
        tableFont = new Font("Poppins",Font.PLAIN,15);

        headerPanel = new JPanel(new GridLayout(3,1));
        title = new JLabel("BUDGETY",JLabel.CENTER);
        title.setFont(mainFont);
        description = new JLabel("Budgety helps you to be financially organized!",JLabel.CENTER);
        description.setFont(descriptionFont);
        getStarted = new JLabel("Get Started!",JLabel.CENTER);
        getStarted.setFont(descriptionFont);
        headerPanel.add(title);
        headerPanel.add(description);
        headerPanel.add(getStarted);
        this.frame.add(headerPanel,BorderLayout.NORTH);

        contentPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,10));
        month = new JLabel("Your Monthly Budget",JLabel.CENTER);
        mainFont = new Font("Poppins",Font.BOLD,25);
        month.setFont(mainFont);
        monthTable = new JTable(monthlyData,monthlyCols);
        monthTable.setModel(monthTableModel);
        monthTable.setFont(tableFont);
        monthTable.setRowHeight(30);
        monthPane = new JScrollPane(monthTable);
        monthPane.setPreferredSize(new Dimension(500, 170));

        year = new JLabel("Your Yearly Budget",JLabel.CENTER);
        mainFont = new Font("Poppins",Font.BOLD,25);
        year.setFont(mainFont);
        yearTable = new JTable(yearlyData,yearlyCols);
        yearTable.setModel(yearTableModel);
        yearTable.setFont(tableFont);
        yearTable.setRowHeight(30);
        yearPane = new JScrollPane(yearTable);
        yearPane.setPreferredSize(new Dimension(500, 114));

        contentPanel.add(year);
        contentPanel.add(yearPane);
        contentPanel.add(month);
        contentPanel.add(monthPane);
        this.frame.add(contentPanel,BorderLayout.CENTER);


        navigationPanel = new JPanel(new GridLayout(3,1));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(0,20,20,20));
        addBudget = new JButton("ADD A BUDGET");
        addBudget.setFont(buttonFont);
        viewBudget = new JButton("VIEW MY BUDGET");
        viewBudget.setFont(buttonFont);
        refreshButton = new JButton("REFRESH");
        refreshButton.setFont(buttonFont);
        closeButton = new JButton("CLOSE");
        closeButton.setFont(buttonFont);

        navigationPanel.add(addBudget);
        navigationPanel.add(viewBudget);
        navigationPanel.add(refreshButton);
        navigationPanel.add(closeButton);
        this.frame.add(navigationPanel, BorderLayout.SOUTH);

        addBudget.addActionListener(this);
        viewBudget.addActionListener(this);
        refreshButton.addActionListener(this);
        closeButton.addActionListener(this);
    }

    private void updateMonthlyData() {
        monthlyData = MonthlyBudgetItem.getDataFromMonthlyBudget(LocalDate.now().getYear());
    }

    private void updateYearlyData() {
        yearlyData = YearlyBudgetItem.getDataFromYearlyBudget();
    }

    private void refreshTables() {
        DefaultTableModel monthModel = (DefaultTableModel)monthTable.getModel();
        monthModel.setRowCount(0);
        DefaultTableModel yearModel = (DefaultTableModel)yearTable.getModel();
        yearModel.setRowCount(0);
        updateTables();
        Object[] newMonthData = new Object[5];
        for(int i=0 ; i< monthlyData.length ; i++) {
            newMonthData[0]=monthlyData[i][0];
            newMonthData[1]=monthlyData[i][1];
            newMonthData[2]=monthlyData[i][2];
            newMonthData[3]=monthlyData[i][3];
            newMonthData[4]=monthlyData[i][4];
            monthModel.addRow(newMonthData);
        }
        Object[] newYearData = new Object[5];
        for(int i=0;i<yearlyData.length;i++) {
            newYearData[0] = yearlyData[i][0];
            newYearData[1] = yearlyData[i][1];
            newYearData[2] = yearlyData[i][2];
            newYearData[3] = yearlyData[i][3];
            newYearData[4] = yearlyData[i][4];
            yearModel.addRow(newYearData);
        }
    }

    public void updateTables() {
        updateMonthlyData();
        updateYearlyData();
    }

    public String[][] getMonthlyData(){
        return monthlyData;
    }

    public String[][] getYearlyData(){
        return yearlyData;
    }

    public String[] getMonthlyCols() {
        return monthlyCols;
    }

    public String[] getYearlyCols() {
        return yearlyCols;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == addBudget) {
            new AddBudgetFrame();
        }else if(ae.getSource() == viewBudget) {
            new ViewBudgetFrame();
        }else if(ae.getSource() == refreshButton) {
            refreshTables();
        }
        else if(ae.getSource() == closeButton) {
            System.exit(0);
        }
    }

}
