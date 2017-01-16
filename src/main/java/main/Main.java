package main;

import datamanipulation.ContentFinder;
import datamanipulation.UrlFinder;
import internet.PageReader;

import java.io.BufferedReader;
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

        String s = "https://www.amazon.com/Gateway-Safety-6980-Cover2-Glasses/dp/B00FA4RB12/ref=pd_sim_469_2?_encoding=UTF8&pd_rd_i=B00FA4RB12&pd_rd_r=V7AB1C70MXEC96QQTA9P&pd_rd_w=ChCew&pd_rd_wg=Lb7s1&psc=1&refRID=V7AB1C70MXEC96QQTA9P";//"https://en.wikipedia.org/wiki/Christmas_market";
        //new UrlFinder().dumpPage(new PageReader(s).getReader());

        PageReader reader = new PageReader(s);
        List<String> RawHtml = reader.getRawHTML();
        ContentFinder contentFinder = new ContentFinder(s,RawHtml);
        UrlFinder urlFinder = new UrlFinder(s,RawHtml);

        List<String> Rawlinks = urlFinder.getRawUrls();
        List<String> rebuiltLinks = urlFinder.rebuildUrls(Rawlinks);
        List<String> amazonProductLinks = urlFinder.getAmazonProductUrls(rebuiltLinks);
        urlFinder.dumpUrls(amazonProductLinks);


        System.out.println(contentFinder.getASIN());

        String rawUPC = contentFinder.getRawUpcString();
        System.out.println(contentFinder.findUPCNumInString(rawUPC));

        System.out.println(contentFinder.findPrice());


    }


}
