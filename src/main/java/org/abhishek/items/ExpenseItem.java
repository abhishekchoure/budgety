package org.abhishek.items;

import java.time.LocalDate;
import org.abhishek.credentials.Configuration;
import java.sql.*;

public class ExpenseItem {
    private double amount;
    private String source;
    private int percentage;
    private LocalDate day;

    public ExpenseItem() {}
    public ExpenseItem(double amount , String source , int percentage, LocalDate day) {

        this.amount = amount;
        this.source = source;
        this.percentage = percentage;
        this.day = day;
    }


    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    public void setSource(String newSource) {
        this.source = newSource;
    }

    public void setPercentage(int newPercentage) {
        this.percentage = newPercentage;
    }

    public void setDay(LocalDate newDay) {
        this.day = newDay;
    }


    public double getAmount() {
        return this.amount;
    }

    public String getSource() {
        return this.source;
    }

    public LocalDate getDay() {
        return this.day;
    }

    public int getPercentage() {
        return this.percentage;
    }

    public void addExpenseItemToDatabase(){
        Connection connect = null;
        PreparedStatement insertStatement = null;

        final String insertQuery = "insert into expense_item (expense_amount , expense_source , expense_percentage , day) values (?,?,?,?)";

        try {
            connect = Configuration.connectToDatabase();
            insertStatement = connect.prepareStatement(insertQuery);

            insertStatement.setDouble(1, this.amount);
            insertStatement.setString(2, this.source);
            insertStatement.setInt(3, this.percentage);
            insertStatement.setDate(4, Date.valueOf(this.day));

            int result = insertStatement.executeUpdate();
            if(result != 0) {
                System.out.println("Insertion of ExpenseItem Successfull! :)");
            }else {
                System.out.println("Insertion of ExpenseItem Failed! :(");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                insertStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
            Configuration.disconnectToDatabase(connect);
        }
    }



    public static String[][] getDataFromExpenseItem(LocalDate date) {
        String [][]data = null;
        Connection connect = null;
        ResultSet result = null;
        ResultSet rowCount = null;
        PreparedStatement selectStatement = null;
        PreparedStatement getRows = null;
        int noOfRows = 0;

        final String rowQuery = "select count(id) from expense_item where day=?";
        final String selectQuery = "select expense_source, expense_amount ,expense_percentage from expense_item where day=?";

        connect = Configuration.connectToDatabase();
        try {

            getRows = connect.prepareStatement(rowQuery);
            getRows.setDate(1,Date.valueOf(date));
            rowCount = getRows.executeQuery();
            if(rowCount.next()) {
                noOfRows = rowCount.getInt(1);
            }

            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setDate(1,Date.valueOf(date));

            result = selectStatement.executeQuery();
            data = new String[noOfRows][3];
            int i=0;

            while(result.next()) {
                String source = result.getString(1);
                String amount = Double.toString(result.getDouble(2));
                String per = Integer.toString(result.getInt(3));
                data[i][0] = source;
                data[i][1] = amount;
                data[i][2] = per;
                i++;
            }

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            Configuration.disconnectToDatabase(connect);
            try {
                result.close();
                rowCount.close();
                getRows.close();
                selectStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
