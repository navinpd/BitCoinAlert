package checker.bit.com.bitcoin.pojo;

/**
 * Created by Navin on 03/07/17.
 */

public class SGD
{
    private String rate;

    private String description;

    private String rate_float;

    private String code;

    public String getRate ()
    {
        return rate;
    }

    public void setRate (String rate)
    {
        this.rate = rate;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getRate_float ()
    {
        return rate_float;
    }

    public void setRate_float (String rate_float)
    {
        this.rate_float = rate_float;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rate = "+rate+", description = "+description+", rate_float = "+rate_float+", code = "+code+"]";
    }
}