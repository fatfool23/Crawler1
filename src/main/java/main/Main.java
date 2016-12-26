package main;

import datamanipulation.UrlFinder;
import internet.PageReader;

import java.util.ArrayList;
import java.util.List;

/**
 *  driver class
 *
 * Created by Sami on 12/22/16.
 */
public class Main
{

    public static void main(String[] args)
    {
        String s = "https://en.wikipedia.org/wiki/Christmas_market";
        //new UrlFinder().dumpPage(new PageReader(s).getReader());
        UrlFinder urlFinder = new UrlFinder(s);
        List<String> links = urlFinder.getUrls(new PageReader(s).getReader());
        urlFinder.dumpRebuiltUrls(urlFinder.rebuildUrls(links));

    }


}
