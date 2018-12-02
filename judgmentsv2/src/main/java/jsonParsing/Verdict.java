package jsonParsing;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Verdict {
    private int id;
    private CourtType courtType;
    private CourtCase[] courtCases;
    private JudgementType judgementType;
    private Judge[] judges;
    private Source source;
    private String[] courtReporters;
    private String decision;
    private String summary;
    private String textContent;
    private String[] legalBases;
    private ReferencedRegulations[] referencedRegulations;
    private String[] keywords;
    private ReferencedCourtCases[] referencedCourtCases;
    private String receiptDate;
    private String meansOfAppeal;
    private String judgementResult;
    private String[] lowerCourtJudgments;
    private String judgmentDate;

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
