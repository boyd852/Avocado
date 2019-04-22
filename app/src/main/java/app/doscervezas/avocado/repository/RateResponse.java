package app.doscervezas.avocado.repository;

public class RateResponse {

    private double rate;
    private boolean isSynced;
    private boolean isError;

    public RateResponse(double rate, boolean isSynced, boolean isError){
        this.rate = rate;
        this.isSynced = isSynced;
        this.isError = isError;
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

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }
}
