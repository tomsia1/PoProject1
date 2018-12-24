package htmlParsing;

import Parsing.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HtmlParser {
    private Document doc;

    public HtmlParser(File file) throws IOException
    {
        doc= Jsoup.parse(file,"UTF-8");
    }

    public Elements tableSplit()
    {
        return doc.select("table").get(3).select("td");
    }

    private CourtCase[] findCourtCases()
    {
        Element elem=doc.select("TITLE").first();
        String s=elem.toString();
        int p=s.indexOf(">");
        int q=s.indexOf("-");
        CourtCase [] result ={new CourtCase(s.substring(p+1,q).trim())};
        return result;
    }

    private String findDate()
    {
        Element elem=tableSplit().get(3);
        String s=elem.toString();
        s=s.substring(s.indexOf('>')+1);
        s=s.substring(0,s.indexOf('<'));
        return s.trim();
    }

    private List<Judge> findJudges()
    {
        Element elem=tableSplit().get(13);
        String s=elem.toString();
        s=s.substring(s.indexOf('>')+1);
        s=s.substring(0,s.lastIndexOf('<'));
        String [] judges=s.split("<br />");
        List<Judge> result=new LinkedList<>();
        for (String info: judges)
        {
            String name=info.trim();
            String function=null;
            int p=name.indexOf('/');

            if (p!=-1) {
                function = name.substring(p + 1);
                function = function.substring(0, function.indexOf('/'));

                name=name.substring(0,p);
                name=name.trim();
            }

            result.add(new Judge(name,function));
        }

        return result;
    }

    private String findReason()
    {
        String s=doc.getElementsByClass("info-list-value-uzasadnienie").get(1).toString();
        s=s.replaceAll("<p>","");
        s=s.replaceAll("</p>"," ");

        int p=s.indexOf('>');
        int q=s.lastIndexOf('<');
        s=s.substring(p+1,q);

        return s.trim();
    }

    private List<ReferencedRegulations> findReferencedRegulations()
    {
        Elements tmps=doc.getElementsByClass("nakt");
        List<String> result=new LinkedList<>();
        List<ReferencedRegulations> regs=new LinkedList<>();

        for (Element tmp: tmps)
        {
            String title=tmp.toString();
            int i= title.indexOf('>');
            int j= title.lastIndexOf('<');
            result.add(title.substring(i+1,j).trim());
        }

        for (String title: result)
        {
            int i=title.indexOf("tekst jedn")+title.indexOf("t. jedn")+title.indexOf("t.j.")+2;

            if (i!=-1)
                title=title.substring(0,i);

            for (int j=title.length()-1; j>=0; j--)
            {
                char c=title.charAt(j);
                if (c!=' '){
                    if (c=='-')
                        title=title.substring(0,j);
                    break;
                }
            }

            regs.add(new ReferencedRegulations(title.trim()));

        }

        return regs;
    }

    public Verdict parse()
    {
       return new Verdict(findCourtCases(),findJudges(),findDate(),
               findReason(),findReferencedRegulations());
    }

}
