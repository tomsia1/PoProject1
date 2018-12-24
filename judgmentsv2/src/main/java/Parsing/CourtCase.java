package Parsing;

import java.util.Objects;

public class CourtCase
{
    private String caseNumber;

    public CourtCase(String signature)
    {
        this.caseNumber=signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourtCase)) return false;
        CourtCase courtCase = (CourtCase) o;
        return Objects.equals(caseNumber, courtCase.caseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseNumber);
    }

    @Override
    public String toString()
    {
        return caseNumber;
    }

    public String getCaseNumber() {
        return caseNumber;
    }
}
