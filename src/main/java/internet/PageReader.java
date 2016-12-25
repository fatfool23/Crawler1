package internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * class creates objects that link to a web page and provides a way to read data from that page.
 *
 * Created by Sami on 12/22/16.
 */
public class PageReader
{
    private BufferedReader reader;

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
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");

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

}
