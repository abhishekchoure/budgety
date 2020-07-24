package org.abhishek.items;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import org.abhishek.credentials.Configuration;

public class DailyBudgetItem {
    private LocalDate day;
    private double TOTAL_DAILY_SAVINGS;
    private double TOTAL_DAILY_INCOME;
    private double TOTAL_DAILY_EXPENSE;
    private int EXPENSE_PERCENTAGE;

    public DailyBudgetItem() {}
    public DailyBudgetItem(LocalDate day , double savings , double income , double expense , int expensePercentage , Month month, int year) {
        this.day = day;
        this.TOTAL_DAILY_SAVINGS = savings;
        this.TOTAL_DAILY_INCOME = income;
        this.TOTAL_DAILY_EXPENSE = expense;
        this.EXPENSE_PERCENTAGE = expensePercentage;
    }

    private void setDailyIncome(double income) {
        this.TOTAL_DAILY_INCOME = income;
    }
    private void setDailyExpense(double expense) {
        this.TOTAL_DAILY_EXPENSE = expense;
    }

    private void calculateDailyIncome() {
        Connection connect = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet result = null;

        final String selectQuery = "select sum(income_amount) from income_item where day=?";
        final String updateQuery = "update daily_budget_item set daily_income = ? where day = ?";

        try {
            connect = Configuration.connectToDatabase();
            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setDate(1,Date.valueOf(this.day));
            result = selectStatement.executeQuery();
            if(result != null) {
                while(result.next()) {
                    this.TOTAL_DAILY_INCOME = result.getDouble(1);
                }

            }else {
                throw new SQLException("Cannot get sum of income items! :(");
            }

            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_DAILY_INCOME);
            updateStatement.setDate(2, Date.valueOf(this.day));
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated income for the day successfully! :)");
            }else {
                System.out.println("Failure: couldn't update income for the day :(");
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                selectStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
            Configuration.disconnectToDatabase(connect);
        }
    }

    private void calculateDailyExpense() {
        Connection connect = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet result = null;

        final String selectQuery = "select sum(expense_amount) from expense_item where day=?";
        final String updateQuery = "update daily_budget_item set daily_expense = ? where day = ?";
        try {
            connect = Configuration.connectToDatabase();
            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setDate(1,Date.valueOf(this.day));
            result = selectStatement.executeQuery();
            if(result != null) {
                while(result.next()) {
                    this.TOTAL_DAILY_EXPENSE = result.getDouble(1);
                }
            }else {
                throw new SQLException("Cannot get sum of expense items! :(");
            }

            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_DAILY_EXPENSE);
            updateStatement.setDate(2, Date.valueOf(this.day));
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated expense for the day successfully! :)");
            }else {
                System.out.println("Failure: couldn't update expense for the day :(");
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

    private void calculateDailySavings() {
        Connection connect = null;
        PreparedStatement updateStatement = null;
        PreparedStatement getStatement = null;
        ResultSet getItems = null;

        final String updateQuery = "update daily_budget_item set daily_savings = ? where day = ?";
        final String getQuery = "select daily_income,daily_expense from daily_budget_item where day=?";

        try {
            connect = Configuration.connectToDatabase();

            getStatement = connect.prepareStatement(getQuery);
            getStatement.setDate(1, Date.valueOf(this.day));
            getItems = getStatement.executeQuery();
            while(getItems.next()) {
                setDailyIncome(getItems.getDouble(1));
                setDailyExpense(getItems.getDouble(2));
            }

            this.TOTAL_DAILY_SAVINGS = this.TOTAL_DAILY_INCOME - this.TOTAL_DAILY_EXPENSE;
            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.TOTAL_DAILY_SAVINGS);
            updateStatement.setDate(2, Date.valueOf(this.day));
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated savings for the day successfully! :)");
            }else {
                System.out.println("Failure: couldn't update savings for the day :(");
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

    private void calculateDailyExpensePercentage() {


        Connection connect = null;
        PreparedStatement updateStatement = null;
        PreparedStatement getStatement = null;
        ResultSet getItems = null;

        final String updateQuery = "update daily_budget_item set daily_expense_percentage = ? where day = ?";
        final String getQuery = "select daily_income , daily_expense from daily_budget_item where day = ?";

        try {
            connect = Configuration.connectToDatabase();

            getStatement = connect.prepareStatement(getQuery);
            getStatement.setDate(1, Date.valueOf(this.day));
            getItems = getStatement.executeQuery();
            while(getItems.next()) {
                setDailyIncome(getItems.getDouble(1));
                setDailyExpense(getItems.getDouble(2));
            }

            this.EXPENSE_PERCENTAGE = (int)Math.round((this.TOTAL_DAILY_EXPENSE / this.TOTAL_DAILY_INCOME)*100);

            updateStatement = connect.prepareStatement(updateQuery);
            updateStatement.setDouble(1, this.EXPENSE_PERCENTAGE);
            updateStatement.setDate(2, Date.valueOf(this.day));
            int updateResult = updateStatement.executeUpdate();
            if(updateResult != 0) {
                System.out.println("Updated expense percentage for the day successfully! :)");
            }else {
                System.out.println("Failure: couldn't update expense percentage for the day :(");
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

    private void calculateExpensePercentages() {

        Connection connect = null;
        PreparedStatement updateStatement = null;
        PreparedStatement selectStatement = null;
        PreparedStatement id = null;
        ResultSet selectResult = null;
        ResultSet idResult = null;


        final String updateQuery = "update expense_item set expense_percentage = ? where day = ? and id = ?";
        final String selectQuery = "select expense_amount from expense_item where day=? and id=?";
        final String idQuery = "select id from expense_item where day = ?";

        connect = Configuration.connectToDatabase();

        try {
            id = connect.prepareStatement(idQuery);
            id.setDate(1, Date.valueOf(this.day));
            idResult = id.executeQuery();

            while(idResult.next()) {

                double currExpenseAmount = 0;
                int currExpPer = 0;
                int currId = idResult.getInt(1);
                selectStatement = connect.prepareStatement(selectQuery);
                selectStatement.setDate(1, Date.valueOf(this.day));
                selectStatement.setInt(2,currId);

                selectResult = selectStatement.executeQuery();
                while(selectResult.next()) {
                    currExpenseAmount = selectResult.getDouble(1);
                }

                currExpPer = (int)Math.round((currExpenseAmount / this.TOTAL_DAILY_EXPENSE)*100);

                updateStatement = connect.prepareStatement(updateQuery);
                updateStatement.setInt(1,currExpPer);
                updateStatement.setDate(2, Date.valueOf(this.day));
                updateStatement.setInt(3,currId);

                int updateResult = updateStatement.executeUpdate();
                if(updateResult != 0) {
                    System.out.println("Updation of expense percentage successsfull :)");
                }else {
                    System.out.println("Updation of expense percentage failed :(");
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            try {
                idResult.close();
                id.close();
                updateStatement.close();
                selectResult.close();
                selectStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
            Configuration.disconnectToDatabase(connect);
        }
    }

    public void updateDailyBudget() {
        calculateDailyIncome();
        calculateDailyExpense();
        calculateDailySavings();
        calculateDailyExpensePercentage();
        calculateExpensePercentages();
    }

    public void updateDailyBudgetIncome() {
        calculateDailyIncome();
        calculateDailySavings();
        calculateDailyExpensePercentage();
    }

    public void updateDailyBudgetExpense() {
        calculateDailyExpense();
        calculateDailySavings();
        calculateDailyExpensePercentage();
        calculateExpensePercentages();
    }

    public void addDailyBudgetItemToDatabase() {
        Connection connect = null;
        PreparedStatement insertStatement = null;
        PreparedStatement checkStatement = null;
        ResultSet checkResult = null;
        int alreadyExist = 0;
        final String checkQuery = "select count(day) from daily_budget_item where day=?";
        final String insertQuery = "insert into daily_budget_item values (?,?,?,?,?,?,?)";

        try {
            connect = Configuration.connectToDatabase();

            checkStatement = connect.prepareStatement(checkQuery);
            checkStatement.setDate(1, Date.valueOf(this.day));
            checkResult = checkStatement.executeQuery();
            if(checkResult.next()) {
                alreadyExist = checkResult.getInt(1);
            }
            if(alreadyExist == 1) {
                System.out.println("Dailt budget item already present!");
                return;
            }

            insertStatement = connect.prepareStatement(insertQuery);
            insertStatement.setDate(1, Date.valueOf(this.day));
            insertStatement.setDouble(2, this.TOTAL_DAILY_SAVINGS);
            insertStatement.setDouble(3, this.TOTAL_DAILY_INCOME);
            insertStatement.setDouble(4, this.TOTAL_DAILY_EXPENSE);
            insertStatement.setInt(5, this.EXPENSE_PERCENTAGE);
            insertStatement.setString(6, this.day.getMonth().toString());
            insertStatement.setInt(7,this.day.getYear());

            int result = insertStatement.executeUpdate();

            if(result != 0) {
                System.out.println("Insertion of DailyBudgetItem Successfull! :)");
            }else {
                System.out.println("Insertion of DailyBudgetItem Failed! :(");
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

    public static String[][] getDataFromDailyBudget(LocalDate date) {
        String [][]data = null;
        Connection connect = null;
        ResultSet result = null;
        PreparedStatement selectStatement = null;
        int flag = 0;
        final String selectQuery = "select daily_income,daily_expense,daily_expense_percentage,daily_savings from daily_budget_item where day=?";

        connect = Configuration.connectToDatabase();
        try {


            selectStatement = connect.prepareStatement(selectQuery);
            selectStatement.setDate(1,Date.valueOf(date));
            result = selectStatement.executeQuery();

            while(result.next()) {
                data = new String[1][4];
                flag=1;
                String inc = Double.toString(result.getDouble(1));
                String exp = Double.toString(result.getDouble(2));
                String per = Integer.toString(result.getInt(3));
                String sav = Double.toString(result.getDouble(4));

                data[0][0] = inc;
                data[0][1] = exp;
                data[0][2] = per;
                data[0][3] = sav;
            }

            if(flag == 0) {
                return null;
            }

        }catch(Exception e) {
            e.printStackTrace();
        }finally {
            Configuration.disconnectToDatabase(connect);
            try {
                result.close();
                selectStatement.close();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
