package checker.bit.com.bitcoin.pojo;

/**
 * Created by Navin on 03/07/17.
 */

public class Bpi
{
    private SGD SGD;

    private USD USD;

    public SGD getSGD ()
    {
        return SGD;
    }

    public void setSGD (SGD SGD)
    {
        this.SGD = SGD;
    }

    public USD getUSD ()
    {
        return USD;
    }

    public void setUSD (USD USD)
    {
        this.USD = USD;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SGD = "+SGD+", USD = "+USD+"]";
    }
}
