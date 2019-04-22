package app.doscervezas.avocado.db.entity;

import java.util.Date;

public class DateHeader {

    private Date date;

    public DateHeader(){};

    public DateHeader(Date date){
    this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}


