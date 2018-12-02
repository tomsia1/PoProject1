package db;

import jsonParsing.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class DataStructure {

    private List<ItemList> allVerdicts;
    private Map<String ,Verdict> signatures;
    private Map<Judge, ArrayList<Verdict>> arbiters;

    public DataStructure()
    {
        allVerdicts=new LinkedList<>();
        signatures=new HashMap<>();
        arbiters=new HashMap<>();
    }


    public boolean load(String path) throws IOException
    {
        File file=new File(path);

        if (!file.exists() || !file.isFile())
            return  false;

        this.makeStructure(ItemList.makeItemList(path));
        return true;
    }

    private void makeStructure(ItemList itemList)
    {
        allVerdicts.add(itemList);

        for (Verdict verdict: itemList.getVerdicts()) {

            for (CourtCase courtCase: verdict.getCourtCases())
                signatures.put(courtCase.getCaseNumber(),verdict);

            for (Judge judge: verdict.getJudges())
            {
                ArrayList<Verdict> tmp=arbiters.get(judge);

                if (tmp==null)
                {
                    tmp=new ArrayList<>();
                    tmp.add(verdict);
                    arbiters.put(judge,tmp);
                }
                else
                {
                    tmp.add(verdict);
                    arbiters.replace(judge,tmp);
                }
            }
        }
    }

    public List<String> listSignature()
    {
        List<String> result=new LinkedList<>();
        result.addAll(signatures.keySet());
        return result;
    }

    public List<Judge> listJudges()
    {
        List<Judge> result=new LinkedList<>();
        result.addAll(arbiters.keySet());
        return result;
    }

    public  String signatureMetric(String signature)
    {
        Verdict verdict=signatures.get(signature);
        if (verdict==null)
            return null;
        return verdict.showMetric();
    }

    public String tellMeWhy(String signature)
    {
        Verdict verdict=signatures.get(signature);
        if (verdict==null)
            return null;
        return verdict.getTextContent();
    }

    public int verdictNumber(String judge_name)
    {
        ArrayList<Verdict> verdicts= arbiters.get(new Judge(judge_name));
        if (verdicts==null)
            return 0;
        else
            return verdicts.size();
    }

    public List<Verdict> listVerdicts()
    {
        List<Verdict> result=new LinkedList<>();
        for (ItemList list: allVerdicts)
            result.addAll(list.getVerdicts());
        return result;

    }
}
