package datamanipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Sami on 12/22/16.
 * methods of this class read from the PageReader class the main purpose of this class is to find all URLS on
 * a given webpage
 *
 */
public class UrlFinder
{

    public static final String HTMLURLKey = "href=";

    public UrlFinder()
    {

    }

    /**
     * This method looks through the code of a web page and finds the links within it
     *
     * @param reader buffered reader for a web page typically passed from PageReader class
     * @return a Linked list of all of the links in a page
     */

    public LinkedList getUrls(BufferedReader reader)
    {
        LinkedList<String> result = new LinkedList();
        try
        {
            String in = reader.readLine();
            while (in != null)
            {
                String url;
                url = in.trim();

                if (url.contains(HTMLURLKey))
                {
                    url = this.findlink(in); // call assumes HTMLURL key is contained in the string "in"

                    //if (url.startsWith("http"))
                    {
                        result.add(url);
                    }
                }
                in = reader.readLine();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("unable to read file");
        }
        for (String s : result)
        {
            System.out.println(s);
        }
        return result;
    }

    /**
     * This method dumps all of the code for a given web page to the console
     *
     * @param reader buffered reader for a web page typically passed from PageReader class
     */

    public void dumpPage(BufferedReader reader)
    {
        try
        {
            String out = reader.readLine();
            while(out != null)
            {
                System.out.println(out);
                out = reader.readLine();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException("unable to read data");
        }
    }


    /**
     * this is a helper method for the getUrls method it is responsible for reading raw data in the form of one line
     * from a webpage and returns just the link as a string
     * @param in raw data in the form of one line from a webpage (assumes only one link per line)
     * @return a string containing only the link
     */

    private String findlink(String in)
    {

        String value = in.substring(in.indexOf(HTMLURLKey)+HTMLURLKey.length()+1);

        if(value.indexOf('\'') == -1)
        {
            value = value.substring(0,value.indexOf('"'));
        }
        else
        {
            value = value.substring(0, value.indexOf('\''));
        }

        return value;

    }




}
