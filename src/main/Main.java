package main;

import datamanipulation.UrlFinder;
import internet.PageReader;

import java.net.URL;

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
        PageReader pageReader = new PageReader(s);
        //new UrlFinder().dumpPage(pageReader.getReader());
        new UrlFinder().getUrls(pageReader.getReader());


    }


}
