package org.abhishek.items;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;

import org.abhishek.credentials.Configuration;

public class MonthlyBudgetItem {
    private Month month;
    private double TOTAL_MONTHLY_SAVINGS;
    private double TOTAL_MONTHLY_INCOME;
    private double TOTAL_MONTHLY_EXPENSE;
    private int MONTHLY_EXPENSE_PERCENTAGE;
    private int year;

    public MonthlyBudgetItem() {}
    public MonthlyBudgetItem(Month month , double savings, double income , double expense , int expense_percentage , int year) {
        this.month = month;
        this.TOTAL_MONTHLY_SAVINGS = savings;
        this.TOTAL_MONTHLY_INCOME = income;
        this.TOTAL_MONTHLY_EXPENSE = expense;
        this.MONTHLY_EXPENSE_PERCENTAGE = expense_percentage;
        this.year = year;
    }

    private void calculateMonthlyIncome() {
        Connection connect = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet result = null;

        final String selectQuery = "select sum(daily_income) from daily_budget_item where month=? and year=?";
        final String updateQuery = "update monthly_budget_item set monthly_income = ? where month = ? and year = ?";

        try {
            connect = Configuration.connectToDatabase();
            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setString(1,this.month.toString());
            selectStatement.setInt(2, this.year);
            result = selectStatement.executeQuery();
            if(result != null) {
                while(result.next()) {
                    this.TOTAL_MONTHLY_INCOME = result.getDouble(1);
                }
            }else {
                throw new SQLException("Cannot get sum of daily income ! :(");
            }

            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_MONTHLY_INCOME);
            updateStatement.setString(2,this.month.toString());
            updateStatement.setInt(3, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated income for the month successfully! :)");
            }else {
                System.out.println("Failure: couldn't update income for the month :(");
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

    private void calculateMonthlyExpense() {
        Connection connect = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet result = null;

        final String selectQuery = "select sum(daily_expense) from daily_budget_item where month=? and year=?";
        final String updateQuery = "update monthly_budget_item set monthly_expense = ? where  month=? and year=?";

        try {
            connect = Configuration.connectToDatabase();
            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setString(1,this.month.toString());
            selectStatement.setInt(2, this.year);
            result = selectStatement.executeQuery();
            if(result != null) {
                while(result.next()) {
                    this.TOTAL_MONTHLY_EXPENSE = result.getDouble(1);
                }
            }else {
                throw new SQLException("Cannot get sum of daily expense! :(");
            }

            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_MONTHLY_EXPENSE);
            updateStatement.setString(2,this.month.toString());
            updateStatement.setInt(3, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated expense for the month successfully! :)");
            }else {
                System.out.println("Failure: couldn't update expense for the month :(");
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

    private void calculateMonthlySavings() {
        this.TOTAL_MONTHLY_SAVINGS = this.TOTAL_MONTHLY_INCOME - this.TOTAL_MONTHLY_EXPENSE;

        Connection connect = null;
        PreparedStatement updateStatement = null;
        final String updateQuery = "update monthly_budget_item set monthly_savings = ? where month=? and year=?";

        try {
            connect = Configuration.connectToDatabase();
            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_MONTHLY_SAVINGS);
            updateStatement.setString(2,this.month.toString());
            updateStatement.setInt(3, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated savings for the month successfully! :)");
            }else {
                System.out.println("Failure: couldn't update savings for the month :(");
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

    private void calculateMonthlyExpensePercentage() {
        this.MONTHLY_EXPENSE_PERCENTAGE = (int)Math.round((this.TOTAL_MONTHLY_EXPENSE / this.TOTAL_MONTHLY_INCOME)*100);

        Connection connect = null;
        PreparedStatement updateStatement = null;
        final String updateQuery = "update monthly_budget_item set monthly_expense_percentage= ? where month=? and year=?";

        try {
            connect = Configuration.connectToDatabase();
            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.MONTHLY_EXPENSE_PERCENTAGE);
            updateStatement.setString(2,this.month.toString());
            updateStatement.setInt(3, this.year);
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated expense percentage for the month successfully! :)");
            }else {
                System.out.println("Failure: couldn't update expense percentage for the month :(");
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

    public void updateMonthlyBudget() {
        calculateMonthlyIncome();
        calculateMonthlyExpense();
        calculateMonthlySavings();
        calculateMonthlyExpensePercentage();
    }


    public void addMonthlyBudgetItemToDatabase() {
        Connection connect = null;
        ResultSet checkResult = null;
        PreparedStatement insertStatement = null;
        PreparedStatement checkStatement = null;
        int alreadyExist = 0;
        final String insertQuery = "insert into monthly_budget_item values (?,?,?,?,?,?)";
        final String checkQuery = "select count(month) from monthly_budget_item where month=? and year=?";

        try {
            connect = Configuration.connectToDatabase();

            checkStatement = connect.prepareStatement(checkQuery);
            checkStatement.setString(1, this.month.toString());
            checkStatement.setInt(2,this.year);
            checkResult = checkStatement.executeQuery();
            if(checkResult.next()) {
                alreadyExist = checkResult.getInt(1);
            }
            if(alreadyExist == 1) {
                System.out.println("Monthly Budget Item already Exist!");
                return;
            }

            insertStatement = connect.prepareStatement(insertQuery);
            insertStatement.setString(1,this.month.toString());
            insertStatement.setDouble(2, this.TOTAL_MONTHLY_SAVINGS);
            insertStatement.setDouble(3, this.TOTAL_MONTHLY_INCOME);
            insertStatement.setDouble(4, this.TOTAL_MONTHLY_EXPENSE);
            insertStatement.setInt(5, this.MONTHLY_EXPENSE_PERCENTAGE);
            insertStatement.setInt(6,this.year);


            int result = insertStatement.executeUpdate();

            if(result != 0) {
                System.out.println("Insertion of MonthlyBudgetItem Successfull! :)");
            }else {
                System.out.println("Insertion of MonthlyBudgetItem Failed! :(");
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

    public static String[][] getDataFromMonthlyBudget(int year) {
        String [][]data = null;
        Connection connect = null;
        ResultSet result = null;
        ResultSet rowCount = null;
        PreparedStatement selectStatement = null;
        PreparedStatement getRows = null;
        int noOfRows = 0;

        final String rowQuery = "select count(month) from monthly_budget_item where year=?";
        final String selectQuery = "select month,monthly_income,monthly_expense,monthly_expense_percentage,monthly_savings from monthly_budget_item where year = ?";

        connect = Configuration.connectToDatabase();
        try {

            getRows = connect.prepareStatement(rowQuery);
            getRows.setInt(1,year);
            rowCount = getRows.executeQuery();
            if(rowCount.next()) {
                noOfRows = rowCount.getInt(1);
            }

            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setInt(1, year);

            result = selectStatement.executeQuery();
            data = new String[noOfRows][5];
            int i=0;

            while(result.next()) {
                String month = result.getString(1);
                String inc = Double.toString(result.getDouble(2));
                String exp = Double.toString(result.getDouble(3));
                String per = Double.toString(result.getDouble(4));
                String sav = Double.toString(result.getDouble(5));

                data[i][0] = month;
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