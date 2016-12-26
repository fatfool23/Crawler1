package datamanipulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Sami on 12/22/16.
 * methods of this class read from the PageReader class the main purpose of this class is to find all URLS on
 * a given webpage
 *
 */

public class UrlFinder
{



    public static final String HTMLURLKey = "href=";
    private String currentUrl;
    private String currentDomain;

    public UrlFinder(String currentUrl)
    {
        this.currentUrl = currentUrl;
        currentDomain = this.currentUrl.substring(0,this.currentUrl.indexOf("/","https://".length()+1));
    }

    /**
     * This method looks through the code of a web page and finds the links within it
     *
     * @param reader buffered reader for a web page typically passed from PageReader class
     * @return a Linked list of all of the links in a page
     */

    public List<String> getUrls(BufferedReader reader)
    {
        LinkedList<String> result = new LinkedList();
        try
        {
            String in = reader.readLine();
            while (in != null)
            {
                String url;
                url = in.trim();
                List<String> urls = new ArrayList<>();

                if (url.contains(HTMLURLKey))
                {

                  urls = this.findLink(in); // call assumes HTMLURL key is contained in the string "in"
                    for (String s : urls)
                    {
                        result.add(s);
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
           // System.out.println(s);
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
     * This is a helper method for findUrls takes in a string under the assumption that it contains the the
     * url key "href=" at least once. This method then returns an array of strings that are links following the
     * url key
     * @param in a string to be searched for a link or partial link
     * @return a List of links and or partial links.
     */

    List<String> findLink(String in)
    {
        ArrayList<String> input = new ArrayList<>(Arrays.asList(in.split(HTMLURLKey)));
        ArrayList<String> result = new ArrayList<>();
        for (String s : input)
        {
            if(s.startsWith("'"))
            {
                s = s.substring(1);
                s = s.substring(0,s.indexOf("'"));
                result.add(s);
            }
            else if(s.startsWith("\""))
            {
                s = s.substring(1);
                s = s.substring(0,s.indexOf("\""));
                result.add(s);
            }
        }
        return result;
    }

    /**
     * a method to dump all rebuilt links to the console
     * @param in a list of the rebuilt links pass in output from rebuildUrls method
     */

    public void dumpRebuiltUrls(List<String> in)
    {
        for (String s : in)
        {
            System.out.println(s);
        }
    }

    /**
     * Takes a list of partial complete and internal links and rebuilds them prints all ignored links to the console
     *
     * @param in a list of strings that represent links (typically pass in putout of the getUrls method)
     * @return a list of stings of all the rebuilt Urls
     */

    public List<String> rebuildUrls(List<String> in)
    {

        Predicate<String> completeUrlPredicate = s -> s.startsWith("http");
        Predicate<String> partialLinkPredicate = s -> s.startsWith("/");
        Stream<String> compleateUrls = in.stream().filter(completeUrlPredicate);
        Stream<String> toBeFixedResult = in.stream().filter(partialLinkPredicate);
        Stream<String> fixedResult = toBeFixedResult.map(s -> currentDomain+s);
        Stream<String> ignore = in.stream().filter(completeUrlPredicate.negate().and(partialLinkPredicate.negate()));

        List<String> completeUrlList = compleateUrls.collect(Collectors.toList());
        List<String> fixedUrlList = fixedResult.collect(Collectors.toList());
        List<String> ignoreList = ignore.collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        result.addAll(completeUrlList);
        result.addAll(fixedUrlList);

        for (String s : ignoreList)
        {
            System.out.println("Ignored: = " + s);
        }
        return result;

    }


}
