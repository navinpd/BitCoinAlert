package checker.bit.com.bitcoin.pojo;
public class MyPojo
{
    private Time time;

    private String disclaimer;

    private Bpi bpi;

    public Time getTime ()
    {
        return time;
    }

    public void setTime (Time time)
    {
        this.time = time;
    }

    public String getDisclaimer ()
    {
        return disclaimer;
    }

    public void setDisclaimer (String disclaimer)
    {
        this.disclaimer = disclaimer;
    }

    public Bpi getBpi ()
    {
        return bpi;
    }

    public void setBpi (Bpi bpi)
    {
        this.bpi = bpi;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [time = "+time+", disclaimer = "+disclaimer+", bpi = "+bpi+"]";
    }
}