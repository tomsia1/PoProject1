package Parsing;

import com.google.gson.Gson;
import htmlParsing.HtmlParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemList {

    private Verdict[] items;

    private ItemList(){};

    public List<Verdict> getVerdicts() {
        return Collections.unmodifiableList(Arrays.asList(items));
    }

    public static ItemList makeItemList(String path) throws IOException
    {
        File file=new File(path);
        return  makeItemList(file);
    }

    public static ItemList makeItemList(File file) throws IOException
    {
        ItemList result;
        if (file.getAbsolutePath().endsWith(".html") || file.getAbsolutePath().endsWith(".htm"))
            result=parseHtml(file);
        else {
            Gson gson = new Gson();
            result=gson.fromJson(new FileReader(file), ItemList.class);
        }

        for (Verdict verdict: result.items)
            verdict.normalize();

        return result;
    }

    private static ItemList parseHtml(String path) throws IOException
    {
        File file= new File(path);
        return parseHtml(file);
    }

    private static ItemList parseHtml(File file) throws IOException
    {
        ItemList result=new ItemList();

        HtmlParser parser=new HtmlParser(file);

        Verdict [] tmp={parser.parse()};
        result.items=tmp;

        return result;
    }

}
