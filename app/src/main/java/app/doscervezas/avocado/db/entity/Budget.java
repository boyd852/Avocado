package app.doscervezas.avocado.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;

@Entity
public class Budget {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String budgetName;
    private String budgetCurrency;
    private Double dailyBudget;
    private Date budgetDate;

    public Budget(){
    }

    @Ignore
    public Budget(String budgetName, String budgetCurrency, Double dailyBudget, Date budgetDate){
        this.budgetName = budgetName;
        this.budgetCurrency = budgetCurrency;
        this.dailyBudget = dailyBudget;
        this.budgetDate = budgetDate;
    }

    @Ignore
    public Budget(String budgetName, String budgetCurrency, Double dailyBudget, Date budgetDate, long id){
        this.budgetName = budgetName;
        this.budgetCurrency = budgetCurrency;
        this.dailyBudget = dailyBudget;
        this.budgetDate = budgetDate;
        this.id=id;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public String getBudgetCurrency() {
        return budgetCurrency;
    }

    public void setBudgetCurrency(String budgetCurrency) {
        this.budgetCurrency = budgetCurrency;
    }

    public Double getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(Double dailyBudget) {
        this.dailyBudget = dailyBudget;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBudgetDate() {
        return budgetDate;
    }

    public void setBudgetDate(Date budgetDate) {
        this.budgetDate = budgetDate;
    }
}
