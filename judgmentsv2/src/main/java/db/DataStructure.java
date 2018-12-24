package db;

import Parsing.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class DataStructure {

    private List<Verdict> allVerdicts;
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

        if (!file.exists())
            return  false;

        if (file.isFile())
            this.makeStructure(ItemList.makeItemList(file));
        else
            {
                    List<File> files=new LinkedList<>();
                    Path p = Paths.get(path);
                    try(Stream<Path> paths=Files.walk(p,1)) {
                            paths
                                .filter(pom -> pom.toFile().isFile())
                                .filter(pom -> pom.toFile().getName().matches(".*\\.json$")
                                || pom.toFile().getName().matches(".*\\.html$")
                                || pom.toFile().getName().matches(".*\\.htm$"))
                                .forEach(pom -> files.add(pom.toFile()));
                    }

                    for (File f: files)
                        this.makeStructure(ItemList.makeItemList(f));
            }

        return true;
    }

    private void makeStructure(ItemList itemList)
    {
        for (Verdict verdict: itemList.getVerdicts()) {

            allVerdicts.add(verdict);

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
        result.addAll(allVerdicts);
        return result;
    }

}
