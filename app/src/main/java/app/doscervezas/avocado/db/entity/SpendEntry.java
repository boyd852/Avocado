package app.doscervezas.avocado.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(
                entity = Budget.class,
                parentColumns = "id",
                childColumns = "budgetId",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
        ),
                indices={@Index("budgetId")})

public class SpendEntry {

    @PrimaryKey(autoGenerate = true)
    public long id;
    private long budgetId;
    private Date date;
    private double cost;
    private String description;
    private String currency;
    private double homeSpend;
    private double rate;
    private double homeRate;
    private boolean isSynced;
    private String category;
    private long spendEntryGroupId;
    private boolean datesAreSplit;
    private Date startDate;
    private Date endDate;
    private double totalSpend;

    public SpendEntry(){
    }

    @Ignore
    public SpendEntry(double cost, Date date, String description, String currency, long budgetId, String category, long spendEntryGroupId, boolean datesAreSplit){
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.currency = currency;
        this.budgetId = budgetId;
        this.category = category;
        this.spendEntryGroupId = spendEntryGroupId;
        this.datesAreSplit = datesAreSplit;
    }

    @Ignore
    public SpendEntry(double cost, Date date, String description, String currency, long budgetId, String category, long spendEntryGroupId, boolean datesAreSplit, Date startDate, Date endDate, double totalSpend){
        this.date = date;
        this.cost = cost;
        this.description = description;
        this.currency = currency;
        this.budgetId = budgetId;
        this.category = category;
        this.spendEntryGroupId = spendEntryGroupId;
        this.datesAreSplit = datesAreSplit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalSpend = totalSpend;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public void setTotalSpend(double totalSpend) {
        this.totalSpend = totalSpend;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean datesAreSplit() {
        return datesAreSplit;
    }

    public void setDatesAreSplit(boolean datesAreSplit) {
        this.datesAreSplit = datesAreSplit;
    }

    public long getSpendEntryGroupId() {
        return spendEntryGroupId;
    }

    public void setSpendEntryGroupId(long spendEntryGroupId) {
        this.spendEntryGroupId = spendEntryGroupId;
    }

    public long getId(){
        return id;
    }

    public Date getDate() {
        return date;
    }

    public double getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id){
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getHomeSpend() {
        return homeSpend;
    }

    public void setHomeSpend(double homeSpend) {
        this.homeSpend = homeSpend;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public double getHomeRate() {
        return homeRate;
    }

    public void setHomeRate(double homeRate) {
        this.homeRate = homeRate;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public String getCategory(){
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }
}
