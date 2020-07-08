package org.abhishek.app;

import java.time.LocalDate;
import java.time.Month;
import org.abhishek.items.DailyBudgetItem;
import org.abhishek.items.ExpenseItem;
import org.abhishek.items.IncomeItem;
import org.abhishek.items.MonthlyBudgetItem;
import org.abhishek.items.YearlyBudgetItem;
import org.abhishek.gui.GUI;

public class Main {

    public static void main(String[] args){
        //test();
        new GUI();
    }

    public static void test() {

        YearlyBudgetItem yearlyItems[] = {
                new YearlyBudgetItem(2020,0,0,0,0),
                new YearlyBudgetItem(2021,0,0,0,0),
                new YearlyBudgetItem(2022,0,0,0,0)
        };

        for(YearlyBudgetItem item : yearlyItems) {
            item.addYearlyBudgetItemToDatabase();
        }


        MonthlyBudgetItem months[] = {
                new MonthlyBudgetItem(Month.JANUARY,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.FEBRUARY,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.MARCH,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.APRIL,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.MAY,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.JUNE,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.JULY,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.AUGUST,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.SEPTEMBER,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.OCTOBER,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.NOVEMBER,0,0,0,0,2020),
                new MonthlyBudgetItem(Month.DECEMBER,0,0,0,0,2020),
        };

        for(MonthlyBudgetItem item : months) {
            item.addMonthlyBudgetItemToDatabase();
        }

        DailyBudgetItem dailyItem1 = new DailyBudgetItem(LocalDate.now(),0,0,0,0,LocalDate.now().getMonth(),LocalDate.now().getYear());
        dailyItem1.addDailyBudgetItemToDatabase();

        IncomeItem incomeItem1 = new IncomeItem(45000,"Salary",LocalDate.now());
        incomeItem1.addIncomeItemToDatabase();
        ExpenseItem expenseItem1= new ExpenseItem(10000,"Shopping",0,LocalDate.now());
        ExpenseItem expenseItem2 = new ExpenseItem(5000,"Food",0,LocalDate.now());
        expenseItem1.addExpenseItemToDatabase();
        expenseItem2.addExpenseItemToDatabase();

        dailyItem1.updateDailyBudget();
		/*may.updateMonthlyBudget();
		yearlyItem.updateYearlyBudget();*/

		/*LocalDate date = LocalDate.of(2020, Month.MAY, 13);

		/*DailyBudgetItem dailyItem2 = new DailyBudgetItem(date,0,0,0,0,date.getMonth(),date.getYear());
		dailyItem2.addDailyBudgetItemToDatabase();

		IncomeItem incomeItem2 = new IncomeItem(25000,"Salary",date);
		incomeItem2.addIncomeItemToDatabase();
		ExpenseItem expenseItem3= new ExpenseItem(1000,"Electricity bill",0,date);
		ExpenseItem expenseItem4 = new ExpenseItem(200,"Food",0,date);
		expenseItem3.addExpenseItemToDatabase();
		expenseItem4.addExpenseItemToDatabase();

		dailyItem2.updateDailyBudget();*/
		/*may.updateMonthlyBudget();
		yearlyItem.updateYearlyBudget();*/

		/*ExpenseItem expenseItem6= new ExpenseItem(5000,"Trip",0,LocalDate.now());
		expenseItem6.addExpenseItemToDatabase();
		dailyItem1.updateDailyBudget();*/
		/*may.updateMonthlyBudget();
		yearlyItem.updateYearlyBudget();*/

        for(MonthlyBudgetItem item : months) {
            item.updateMonthlyBudget();
        }
        for(YearlyBudgetItem item : yearlyItems) {
            item.updateYearlyBudget();
        }
    }
}
