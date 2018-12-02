package jsonParsing;

import com.google.gson.Gson;

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
        Gson gson=new Gson();

        return gson.fromJson(new FileReader(path), ItemList.class);
    }

}
