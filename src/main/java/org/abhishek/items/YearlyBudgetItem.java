package org.abhishek.items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.abhishek.credentials.Configuration;

public class YearlyBudgetItem {
    private int year;
    private double TOTAL_YEARLY_SAVINGS;
    private double TOTAL_YEARLY_INCOME;
    private double TOTAL_YEARLY_EXPENSE;
    private int YEARLY_EXPENSE_PERCENTAGE;

    public YearlyBudgetItem() {}
    public YearlyBudgetItem(int year, double savings , double income , double expense , int percentage) {
        this.year = year;
        this.TOTAL_YEARLY_SAVINGS = savings;
        this.TOTAL_YEARLY_INCOME = income;
        this.TOTAL_YEARLY_EXPENSE = expense;
        this.YEARLY_EXPENSE_PERCENTAGE = percentage;
    }

    private void calculateYearlyIncome() {
        Connection connect = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet result = null;

        final String selectQuery = "select sum(monthly_income) from monthly_budget_item where year=?";
        final String updateQuery = "update yearly_budget_item set yearly_income = ? where year = ?";

        try {
            connect = Configuration.connectToDatabase();
            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setInt(1,this.year);
            result = selectStatement.executeQuery();
            if(result != null) {
                while(result.next()) {
                    this.TOTAL_YEARLY_INCOME = result.getDouble(1);
                }
            }else {
                throw new SQLException("Cannot get sum of monthly income ! :(");
            }

            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_YEARLY_INCOME);
            updateStatement.setInt(2, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated income for the year succcessfully! :)");
            }else {
                System.out.println("Failure: couldn't update income for the year :(");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                selectStatement.close();
                updateStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
            Configuration.disconnectToDatabase(connect);
        }
    }

    private void calculateYearlyExpense() {
        Connection connect = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet result = null;

        final String selectQuery = "select sum(monthly_expense) from monthly_budget_item where year=?";
        final String updateQuery = "update yearly_budget_item set yearly_expense = ? where year=?";

        try {
            connect = Configuration.connectToDatabase();
            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setInt(1, this.year);
            result = selectStatement.executeQuery();
            if(result != null) {
                while(result.next()) {
                    this.TOTAL_YEARLY_EXPENSE = result.getDouble(1);
                }
            }else {
                throw new SQLException("Cannot get sum of monthly expense! :(");
            }

            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_YEARLY_EXPENSE);
            updateStatement.setInt(2, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated expense for the year successfully! :)");
            }else {
                System.out.println("Failure: couldn't update expense for the year :(");
            }

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                selectStatement.close();
                updateStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
            Configuration.disconnectToDatabase(connect);
        }
    }

    private void calculateYearlySavings() {
        this.TOTAL_YEARLY_SAVINGS = this.TOTAL_YEARLY_INCOME - this.TOTAL_YEARLY_EXPENSE;

        Connection connect = null;
        PreparedStatement updateStatement = null;
        final String updateQuery = "update yearly_budget_item set yearly_savings = ? where year=?";

        try {
            connect = Configuration.connectToDatabase();
            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_YEARLY_SAVINGS);
            updateStatement.setInt(2, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated savings for the year successfully! :)");
            }else {
                System.out.println("Failure: couldn't update savings for the year :(");
            }

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                updateStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }

            Configuration.disconnectToDatabase(connect);
        }
    }


    private void calculateYearlyExpensePercentage() {
        this.YEARLY_EXPENSE_PERCENTAGE = (int)Math.round((this.TOTAL_YEARLY_EXPENSE / this.TOTAL_YEARLY_INCOME)*100);

        Connection connect = null;
        PreparedStatement updateStatement = null;
        final String updateQuery = "update yearly_budget_item set yearly_expense_percentage= ? where year=?";

        try {
            connect = Configuration.connectToDatabase();
            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.YEARLY_EXPENSE_PERCENTAGE);
            updateStatement.setInt(2, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated expense percentage for the year successfully! :)");
            }else {
                System.out.println("Failure: couldn't update expense percentage for the year :(");
            }

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                updateStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }

            Configuration.disconnectToDatabase(connect);
        }
    }

    public void updateYearlyBudget() {
        calculateYearlyIncome();
        calculateYearlyExpense();
        calculateYearlySavings();
        calculateYearlyExpensePercentage();
    }

    public void addYearlyBudgetItemToDatabase() {
        Connection connect = null;
        PreparedStatement insertStatement = null;
        PreparedStatement checkStatement = null;
        ResultSet checkResult = null;
        int alreadyExist = 0;
        final String checkQuery = "select count(year) from yearly_budget_item where year=?";
        final String insertQuery = "insert into yearly_budget_item values (?,?,?,?,?)";

        try {
            connect = Configuration.connectToDatabase();
            checkStatement = connect.prepareStatement(checkQuery);
            checkStatement.setInt(1,this.year);
            checkResult = checkStatement.executeQuery();
            if(checkResult.next()) {
                alreadyExist = checkResult.getInt(1);
            }
            if(alreadyExist == 1) {
                System.out.println("Yearly Budget Item already exist!");
                return;
            }
            insertStatement = connect.prepareStatement(insertQuery);
            insertStatement.setInt(1,this.year);
            insertStatement.setDouble(2, this.TOTAL_YEARLY_SAVINGS);
            insertStatement.setDouble(3, this.TOTAL_YEARLY_INCOME);
            insertStatement.setDouble(4, this.TOTAL_YEARLY_EXPENSE);
            insertStatement.setInt(5, this.YEARLY_EXPENSE_PERCENTAGE);


            int result = insertStatement.executeUpdate();

            if(result != 0) {
                System.out.println("Insertion of YearlyBudgetItem Successfull! :)");
            }else {
                System.out.println("Insertion of YearlyBudgetItem Failed! :(");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(alreadyExist == 0) {
                    insertStatement.close();
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            Configuration.disconnectToDatabase(connect);
        }
    }

    public static String[][] getDataFromYearlyBudget(){
        String [][]data = null ;
        Connection connect = null;
        ResultSet result = null;
        ResultSet getRows = null;
        Statement selectStatement = null;
        Statement st = null;
        int noOfRows = 0;
        final String selectQuery = "select year,yearly_income,yearly_expense,yearly_expense_percentage,yearly_savings from yearly_budget_item";
        final String rowQuery = "select count(year) from yearly_budget_item";

        connect = Configuration.connectToDatabase();
        try {
            st = connect.createStatement();
            getRows = st.executeQuery(rowQuery);
            while(getRows.next()){
                noOfRows = getRows.getInt(1);
            }
            data = new String[noOfRows][5];
            selectStatement = connect.createStatement();
            result = selectStatement.executeQuery(selectQuery);
            int i=0;
            while(result.next()) {
                String year = Integer.toString(result.getInt(1));
                String inc = Double.toString(result.getDouble(2));
                String exp = Double.toString(result.getDouble(3));
                String per = Double.toString(result.getDouble(4));
                String sav = Double.toString(result.getDouble(5));

                data[i][0] = year;
                data[i][1] = inc;
                data[i][2] = exp;
                data[i][3] = per;
                data[i][4] = sav;
                i++;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            Configuration.disconnectToDatabase(connect);
            try {
                result.close();
                st.close();
                getRows.close();
                selectStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}