package datamanipulation;

import java.util.*;
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
    private ArrayList<String> rawIgnoredLinks;
    private List<String> rawHTML = new ArrayList<>();

    /**
     * this is the constructor for the URl finder class it sets up necessary information to the class
     * such as the current url being searched and the Raw Html source code from that page
     *
     * @param currentUrl the current working url
     * @param rawHTML Raw HTMl passed in as a list of strings typically from page reader class
     *
     */
    public UrlFinder(String currentUrl,List<String> rawHTML)
    {
        this.currentUrl = currentUrl;
        currentDomain = this.currentUrl.substring(0,this.currentUrl.indexOf("/","https://".length()+1));
        this.rawHTML = rawHTML;
    }

    /**
     * This method looks through the code of a web page and finds the links within it
     *
     * @return a Linked list of all of the raw links in a page
     */

    public List<String> getRawUrls()
    {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> rawHtml = new ArrayList<>();
        rawHtml.addAll(this.rawHTML);
            String in = rawHtml.get(0);
            for (int i = 1; i < rawHtml.size(); i++)
            {
                String url;
                url = in.trim();
                List<String> urls = new ArrayList<>();

                if (url.contains(HTMLURLKey))
                {

                  urls = this.findLinks(in); // call assumes HTMLURL key is contained in the string "in"
                    for (String s : urls)
                    {
                        result.add(s);
                    }
                }
                in = rawHtml.get(i);
            }

        for (String s : result)
        {
           // System.out.println(s);
        }
        return result;
    }


    /**
     * this method is used to dump all the raw html source to the console
     */
    public void dumpPage()
    {
        ArrayList<String> rawHtml = new ArrayList<>();
                rawHtml.addAll(this.rawHTML);
        for (String s : rawHtml)
        {
            System.out.println(s);
        }
    }


    /**
     * This is a helper method for findUrls takes in a string under the assumption that it contains the the
     * Amazonurl key "href=" at least once. This method then returns an array of strings that are links following the
     * Amazonurl key
     * @param in a string to be searched for a link or partial link
     * @return a List of links and or partial links.
     */

    private List<String> findLinks(String in)
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

    public void dumpUrls(List<String> in)
    {
        for (String s : in)
        {
            System.out.println(s);
        }
    }

    /**
     * Takes a list of partial complete and internal links and rebuilds them prints all ignored links to the console
     *
     * @param in a list of strings that represent links (typically pass in putout of the getRawUrls method)
     * @return a list of stings of all the rebuilt Urls
     */

    public List<String> rebuildUrls(List<String> in)
    {

        Predicate<String> completeUrlPredicate = s -> s.startsWith("http");
        Predicate<String> partialLinkPredicate = s -> s.startsWith("/");
        Stream<String> completeUrls = in.stream().filter(completeUrlPredicate);
        Stream<String> toBeFixedResult = in.stream().filter(partialLinkPredicate);
        Stream<String> fixedResult = toBeFixedResult.map(s -> currentDomain+s);
        Stream<String> ignore = in.stream().filter(completeUrlPredicate.negate().and(partialLinkPredicate.negate()));

        List<String> completeUrlList = completeUrls.collect(Collectors.toList());
        List<String> fixedUrlList = fixedResult.collect(Collectors.toList());
        List<String> ignoreList = ignore.collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        result.addAll(completeUrlList);
        result.addAll(fixedUrlList);


            //rawIgnoredLinks.addAll(ignoreList);

        return result;

    }

    /**
     * This method takes in a list of complete url links and finds all of those that correspond to an item for sale
     * on amazon
     *
     * @param followableUrl A list of urls (assumes that the list contains compleate urls and some of them are linked to
     *  products)
     * @return A list of amazon urls that are linked to products
     *
     */
    public List<String> getAmazonProductUrls(List<String> followableUrl)
    {
        Set<String> screwRepeats = new HashSet<>();
        ArrayList<String> result = new ArrayList<>();
        for (String s : followableUrl)
        {
            if(s.contains("/dp/B"))
            {
                screwRepeats.add(s);
            }
        }
        result.addAll(screwRepeats);
        return result;
    }

    /**
     * This method will dump all the RAW links that were ignored
     */

    public void dumpIgnoredRawLinks()
    {
        for (String rawIgnoredLink : rawIgnoredLinks)
        {
            System.out.println("rawIgnoredLink = " + rawIgnoredLink);
        }
    }
}
