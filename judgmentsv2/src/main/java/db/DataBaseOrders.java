package db;

import Parsing.CourtType;
import Parsing.Judge;
import Parsing.ReferencedRegulations;
import Parsing.Verdict;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataBaseOrders {

    private DataStructure db=new DataStructure();

    public void append(List<String> paths) throws IOException
    {
        for (String path: paths)
            if (!db.load(path))
                System.out.println("nieprawidlowa sciezka: "+path);
            else
                System.out.println("wczytano: "+path);
    }

    public String listSignature()
    {
        List<String> pom=db.listSignature();

        StringBuilder sb=new StringBuilder("\n");
        for (String s: pom)
            sb.append(s+"\n");

        return sb.toString();
    }

    public String listJudges()
    {
        List<String> pom=db.listJudges().stream().map(judge ->judge.toString()+"\n").collect(Collectors.toList());

        StringBuilder sb=new StringBuilder("\n");
        for (String s:pom)
            sb.append(s);

        return sb.toString();
    }

    public String showMetric(List<String> signatures)
    {
        StringBuilder sb=new StringBuilder("\n");
        String tmp;

        for (String signature: signatures)
        {
            tmp=db.signatureMetric(signature);
            if (tmp==null)
                sb.append("no such signature\n");
            else
                sb.append(tmp+"\n");
        }

        return sb.toString();
    }

    public String reason(List<String> signatures)
    {
        StringBuilder sb=new StringBuilder("\n");
        String tmp;

        for (String signature: signatures)
        {
            sb.append(signature+":\n");
            tmp=db.tellMeWhy(signature);
            if (tmp==null)
                sb.append("no such signature\n");
            else
                sb.append(tmp+"\n");
        }

        return sb.toString();
    }

    public String judgesWithNumbers(List<String> judges_names)
    {
        StringBuilder sb=new StringBuilder("\n");
        int tmp;

        for (String name: judges_names)
        {
            sb.append("name: "+name+", cases: ");
            tmp=db.verdictNumber(name);
            if (tmp==0)
                sb.append("no such judge\n");
            else
                sb.append(tmp+"\n");
        }

        return sb.toString();
    }

    public String employeesOfTheMonth(int top)
    {
        class pair
        {
            Judge judge;
            int score;

            pair (Judge judge, int score)
            {
                this.judge=judge;
                this.score=score;
            }

            public String toString()
            {
                return "name: "+judge.toString()+", cases: "+score+ "\n";
            }
        }

        ArrayList<pair> judges=new ArrayList<>();

        for (Judge judge: db.listJudges())
            judges.add(new pair(judge,db.verdictNumber(judge.getName())));

        judges.sort((a,b) -> b.score - a.score);
        top=Math.min(top,judges.size());

        StringBuilder sb=new StringBuilder("\nmost active judges:\n");
        for (int i=0;i<top;i++)
            sb.append(judges.get(i).toString());

        return sb.toString();
    }

    public String monthlyStats()
    {
        List<Verdict> verdicts=db.listVerdicts();
        int size=verdicts.size();
        int[] count=new int[12];
        for (int i=0;i<12;i++)
            count[i]=0;

        for (Verdict verdict: verdicts)
            count[verdict.getMonth()-1]++;

        String[] months={"January","February","March","April","May","June","July","August","September","October","November","December"};

        StringBuilder sb=new StringBuilder("total case number: "+size+"\n");
        for (int i=0;i<12;i++)
            sb.append(months[i]+ ") cases: "+count[i]+", percentage: "+ 100.0*count[i]/size+"\n");

        return sb.toString();
    }

    public String courtTypeStats()
    {
        List<Verdict> verdicts=db.listVerdicts();
        int size=verdicts.size();
        int[] count=new int[5];
        for (int i=0;i<5;i++)
            count[i]=0;

        for (Verdict verdict: verdicts)
            count[verdict.getCourtType().ordinal()]++;

        StringBuilder sb=new StringBuilder("total case number: "+size+"\n");
        for (int i=0;i<5;i++)
            sb.append("court type: "+ CourtType.values()[i] + " cases: "+count[i]+", percentage: "+100.0*count[i]/size+"\n");

        return sb.toString();
    }

    public String topRegulations(int top)
    {
        class pair
        {
            ReferencedRegulations regulation;
            Integer number;

            pair (ReferencedRegulations regulation,Integer number)
            {
                this.regulation=regulation;
                this.number=number;
            }

            public String toString()
            {
                return regulation.toString()+" , references: "+number +"\n";
            }
        }

        List<Verdict> verdicts=db.listVerdicts();
        Map <ReferencedRegulations,Integer> map=new HashMap<>();

        for (Verdict verdict: verdicts)
            for (ReferencedRegulations regulation: verdict.getReferencedRegulations())
            {
                Integer number=map.get(regulation);
                if (number==null)
                    map.put(regulation,1);
                else
                    map.replace(regulation,number+1);
            }

        ArrayList<pair> entries=new ArrayList<>();
        for (Map.Entry<ReferencedRegulations,Integer> entry: map.entrySet())
            entries.add(new pair(entry.getKey(),entry.getValue()));

        entries.sort((a,b)-> b.number-a.number);
        top=Math.min(top,entries.size());

        StringBuilder sb=new StringBuilder("\nmostly reffered regulations:\n");
        for (int i=0;i<top;i++)
            sb.append(entries.get(i));

        return sb.toString();
    }

    public String avarageNumberOfAnnMarieCheerfulInCourt()
    {
        List<Verdict> verdicts=db.listVerdicts();
        int size=verdicts.size();
        int total=0;

        for (Verdict verdict: verdicts)
            total+=verdict.getJudges().size();

        return "avarage nubmer of judges: "+1.0*total/size +"\n";
    }

}
