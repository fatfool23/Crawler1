package internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * class creates objects that link to a web page and provides a way to read data from that page.
 *
 * Created by Sami on 12/22/16.
 */
public class PageReader
{
    private BufferedReader reader;
    private List<String> rawHTML = new ArrayList<>();

    /**
     * constructor for the PageReader object
     * @param url String of the url to be read
     */

    public PageReader(String url)
    {
        try
        {

            URL url1 = new URL(url);
            URLConnection connection = url1.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.3; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException("bad url " + url);
        }
        catch (IOException e)
        {
            System.out.println("e = " + e);
            e.printStackTrace();
            System.out.println("e.getMessage() = " + e.getMessage());
            System.out.println("e.getCause() = " + e.getCause());
            throw new RuntimeException("unable to connect to url: " + url);
        }
    }

    /**
     *
     * @return BufferedReader object to get content from page.
     */


    public BufferedReader getReader()
    {
        return reader;
    }

    /**
     * the purpose of this method is to provide the raw HTML source code to all the other classes in
     * order to minimize how many times content from the page is read
     * @return a list of strings containing all of the HTML source code of a given (in the constructor) page.
     */

    public List<String> getRawHTML()
    {
        List<String> result = new ArrayList<>();
        try
        {
            BufferedReader reader = this.getReader();
            String s = reader.readLine();
            while(s != null)
            {
                result.add(s);
                s = reader.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("unable to read from url");
        }
        rawHTML = result;
        return rawHTML;
    }

}
