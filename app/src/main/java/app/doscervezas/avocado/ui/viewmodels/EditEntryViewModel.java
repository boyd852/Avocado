package app.doscervezas.avocado.ui.viewmodels;


import android.util.Log;

import java.util.Date;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import app.doscervezas.avocado.db.dao.SpendDao;
import app.doscervezas.avocado.db.entity.SpendEntry;
import app.doscervezas.avocado.repository.DataRepository;
import app.doscervezas.avocado.utils.AvoDateUtils;

public class EditEntryViewModel extends ViewModel {

    public SpendDao spendDao;
    public DataRepository dataRepository;

    @Inject
    public EditEntryViewModel(SpendDao spendDao, DataRepository dataRepository){
        this.spendDao = spendDao;
        this.dataRepository = dataRepository;
    }

    public SpendEntry getSpendEntry(long spendEntryId){
        return spendDao.getSpendEntryFromId(spendEntryId);
    }

    public void deleteOldSpendEntry(long spendEntryId){
        SpendEntry oldSpendEntry = spendDao.getSpendEntryFromId(spendEntryId);
        if(oldSpendEntry.datesAreSplit()){
            long spendEntryGroupId = oldSpendEntry.getSpendEntryGroupId();
            spendDao.deleteSpendEntryGroup(spendEntryGroupId);

        } else {
            spendDao.deleteEntry(oldSpendEntry);
        }
    }

    public void updateSpendEntry(double spend, String dateInString, String description, String currency, long budgetId, String category, boolean datesAreSplit, String dateSecondaryInString){

        Log.d("TAG", "updateSpendEntry: datesaresplit = " + datesAreSplit);

        Date date = AvoDateUtils.convertStringToDate(dateInString, "dd/MM/yyyy");
        Date dateWithoutTime = AvoDateUtils.removeTimeFromDate(date);
        long daysBetween = 0;

        if(datesAreSplit){
            Date secondaryDate = AvoDateUtils.convertStringToDate(dateSecondaryInString, "dd/MM/yyyy");
            Date dateSecondaryWithoutTime = AvoDateUtils.removeTimeFromDate(secondaryDate);
            daysBetween = AvoDateUtils.getDaysBetweenDates(dateWithoutTime, dateSecondaryWithoutTime) + 1;

            double spendPerDay = spend/daysBetween;

            long lastspendEntryGroupId = spendDao.getLastSpendEntryGroupId();
            long currentSpendEntryGroupId = lastspendEntryGroupId + 1;

            for(int i=0; i<daysBetween; i++){
                Date spendEntryDate = AvoDateUtils.addDaysToDate(dateWithoutTime,i);

                //Declare SpendEntry
                SpendEntry spendEntry = new SpendEntry(spendPerDay, spendEntryDate, description, currency, budgetId, category, currentSpendEntryGroupId, datesAreSplit, dateWithoutTime, dateSecondaryWithoutTime, spend);

                //Pass to repository for processing
                dataRepository.updateSpendDb(spendEntry);
            }
        } else {

            long spendEntryGroupId = 0;

            //Declare SpendEntry
            SpendEntry spendEntry = new SpendEntry(spend, dateWithoutTime, description, currency, budgetId, category, spendEntryGroupId, datesAreSplit);

            //Pass to repository for processing
            dataRepository.updateSpendDb(spendEntry);
        }
    }


    public void deleteEntry(SpendEntry spendEntry){
        if(spendEntry.datesAreSplit()){
            spendDao.deleteSpendEntryGroup(spendEntry.getSpendEntryGroupId());
        }else{
            spendDao.deleteEntry(spendEntry);
        }
    }
}
