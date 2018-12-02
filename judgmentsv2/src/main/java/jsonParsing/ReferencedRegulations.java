package jsonParsing;

import java.util.Objects;

public class ReferencedRegulations
{
    String journalTitle;
    int journalNo;
    int journalYear;
    int journalEntry;
    String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferencedRegulations)) return false;
        ReferencedRegulations that = (ReferencedRegulations) o;
        return journalNo == that.journalNo &&
                journalYear == that.journalYear &&
                journalEntry == that.journalEntry;
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalNo, journalYear, journalEntry);
    }

    public  ReferencedRegulations(){};

    public ReferencedRegulations(int no,int year,int entry)
    {
        journalNo=no;
        journalYear=year;
        journalEntry=entry;
        journalTitle=text=null;
    }

    @Override
    public String toString()
    {
        return "journalNo: "+journalNo+ ", journalYear: "+journalYear+", journalEntry: "+journalEntry;
    }
}