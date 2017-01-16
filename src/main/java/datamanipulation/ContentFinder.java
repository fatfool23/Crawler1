package datamanipulation;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sami on 12/29/16.
 *
 * the purpose of this class is to find desired content on a page such as product numbers and prices
 */
public class ContentFinder
{
    private ArrayList<String> rawHTMl = new ArrayList<>();
    private String Amazonurl;
    public static final String AmazonPriceKey = "priceblock_ourprice";

    /**
     * This is the constructor for the ContentFinder Class its purpose is to provide the methods in this class with
     * the information they need. It provides a copy of the raw html code from the web page as well as the
     * current working url
     *
     * @param url the url that is providing the source HTML
     * @param rawHtml Raw HTMl passed in as a list of strings typically from page reader class
     */

    public ContentFinder(String url,List<String> rawHtml)
    {

        if (url.contains("ref="))
        {
            int end = url.lastIndexOf("/");
            this.Amazonurl = url.substring(0, end);
            System.out.println("Amazonurl = " + this.Amazonurl);
        } else
        {
            this.Amazonurl = url;
        }
        this.rawHTMl.addAll(rawHtml);
    }

    /**
     * this method gets the ASIN # (amazon specific ID number) it uses the url in order to do this (the last part of the
     * url is the ASIN #)
     * @return ASIN # amazon specific ID #
     */

    public String getASIN()
    {
        return Amazonurl.substring(Amazonurl.lastIndexOf("/") + 1);
    }


    /**
     * This method searches the raw html code in order to find a String with the upc code in it. Because there is a large
     * variety both in display styles and in amazon's code it is necessary to run past "UPC" meaning the string returned
     * by this method typically varies wildly in size.
     *
     * @return A string of raw html code that contains the upc Code/#
     */

    public String getRawUpcString()
    {
        String result = "";
        List<String> RawHtlm = new ArrayList<>(rawHTMl);
        int index = 0;
        for (String s : RawHtlm)
        {
            if (s.contains("UPC"))
            {
                result = s.trim();
                index++;
                for (int i = 0; i < 20; i++)//got to find upc on subsequent lines
                {
                    result += RawHtlm.get(index + i).trim();
                }

            }
            index++;
        }
        //System.out.println(result);
        return result;
    }

    /**
     * this is a helper method typically called to sift through the raw upc string returned by the getRawUpc
     * function
     *
     *
     * @param in a string that contains the upc code
     * @return the upc code
     */

    public String findUPCNumInString(String in)
    {
        Pattern p = Pattern.compile("[0-9]{12}");
        Matcher m = p.matcher(in);
        if (m.find())
        {
            return m.group();
        }
        else
        {
            return "not found";
        }
    }

    /**
     * this method is used to find the price of an item on amazon
     *
     * @return the price of an item on amazon
     */

    public String findPrice()
    {
        List<String> RawHTML = new ArrayList<>(rawHTMl);
        String result = "";
        for (String s : RawHTML)
        {
            if(s.contains(AmazonPriceKey))
            {
                result = s;
            }
        }
        Pattern p = Pattern.compile("\\$\\d*\\.\\d\\d");
        Matcher m = p.matcher(result);
        if(m.find())
        {
            result = m.group();
        }
        else
        {
            throw new RuntimeException("unable to find price");
        }

        return result;
    }

}