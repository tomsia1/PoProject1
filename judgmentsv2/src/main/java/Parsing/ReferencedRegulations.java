package Parsing;

import java.util.Objects;

public class ReferencedRegulations
{
    String journalTitle=null;
    int journalNo;
    int journalYear;
    int journalEntry;
    String text=null;

    public void normalize()
    {
        if (journalTitle.charAt(journalTitle.length()-1)=='.')
            journalTitle=journalTitle.substring(0,journalTitle.length()-1);

        int j=journalTitle.indexOf("r.");

        if (j!=-1 && j!=journalTitle.length()-2)
        {
            String pom=journalTitle.substring(j+2);

            if (pom.matches(" - .*"))
                journalTitle=journalTitle.substring(0,j+2)+pom.substring(2);
            else
                if (pom.matches("- .*"))
                    journalTitle=journalTitle.substring(0,j+2)+pom.substring(1);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReferencedRegulations)) return false;
        ReferencedRegulations that = (ReferencedRegulations) o;
        return journalTitle.equals(that.journalTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalTitle);
    }

    public  ReferencedRegulations(){};

    public ReferencedRegulations(String tittle)
    {
        journalTitle=tittle;
    }

    @Override
    public String toString()
    {
        return journalTitle;
    }

}