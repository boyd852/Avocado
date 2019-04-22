package app.doscervezas.avocado.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DefaultRatesData {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String currency;
    private double rate;

    public DefaultRatesData(){
    }

    @Ignore
    public DefaultRatesData(String currency, double rate){
        this.currency = currency;
        this.rate = rate;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public double getRate(){
        return rate;
    }

    public void setRate(double rate){
        this.rate = rate;
    }

    public static DefaultRatesData populateDataTest(){
        return new DefaultRatesData("AUD", 1.5);
    }
}
