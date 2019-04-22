package app.doscervezas.avocado.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class RateData {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String currency;
    private Double rate;
    private Date date;

    public RateData(){
    }

    @Ignore
    public RateData(Date date, String currency, Double rate){
        this.date = date;
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

    public Double getRate(){
        return rate;
    }

    public void setRate(Double rate){
        this.rate = rate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
