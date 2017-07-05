package checker.bit.com.bitcoin.pojo;

/**
 * Created by Navin on 03/07/17.
 */

public class ZebPay {

    private String market;

    private String sell;

    private String buy;

    private String volume;

    private String currency;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "ClassPojo [market = " + market + ", sell = " + sell + ", buy = " + buy + ", volume = " + volume + ", currency = " + currency + "]";
    }


}
