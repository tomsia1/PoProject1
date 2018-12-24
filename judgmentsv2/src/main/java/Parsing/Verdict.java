package Parsing;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Verdict {
    private CourtType courtType;
    private CourtCase[] courtCases;
    private Judge[] judges;
    private String textContent;
    private ReferencedRegulations[] referencedRegulations;
    private String judgmentDate;

    public Verdict(){};

    public Verdict (CourtCase[] signature, List<Judge> judges, String date,
                    String reason,List<ReferencedRegulations> referencedRegulations)
    {
        courtType=CourtType.ADMINISTRATIVE;
        courtCases=signature;
        this.judges=judges.toArray(new Judge[judges.size()]);
        judgmentDate=date;
        textContent=reason;
        this.referencedRegulations=referencedRegulations.
                toArray(new ReferencedRegulations[referencedRegulations.size()]);
    }

    public void normalize()
    {
        for (ReferencedRegulations reg: this.referencedRegulations)
            reg.normalize();
    }

    public static Verdict makeVerdict(String path) throws IOException
    {
        Gson gson=new Gson();

        return gson.fromJson(new FileReader(path), Verdict.class);
    }

    public List<Judge> getJudges() {
        return Collections.unmodifiableList(Arrays.asList(judges));
    }

    public String showMetric()
    {
        StringBuilder sb=new StringBuilder();

        sb.append("signature : ");
        for (CourtCase courtCase: getCourtCases())
            sb.append(courtCase.getCaseNumber() + " ");
        sb.append("\n");

        sb.append("judgment date: "+judgmentDate);
        sb.append("\n");

        sb.append("court type: "+ getCourtType());
        sb.append("\n");

        sb.append("judges: \n");
        for (Judge judge: getJudges())
            sb.append(judge.getName()+"\n");

        return sb.toString();
    }

    public CourtType getCourtType() {
        return courtType;
    }


    public List<CourtCase> getCourtCases() {
        return Collections.unmodifiableList(Arrays.asList(courtCases));
    }

    public List<ReferencedRegulations> getReferencedRegulations()
    {
        return Collections.unmodifiableList(Arrays.asList(referencedRegulations));
    }

    public String getTextContent() {
        return textContent;
    }

    public int getMonth()
    {
        if (judgmentDate.charAt(5)=='0')
            return Character.getNumericValue(judgmentDate.charAt(6));
        return 10+Character.getNumericValue(judgmentDate.charAt(6));
    }
}
