package jsonParsing;

public enum CourtType
{
    CONSTITUTIONAL_TRIBUNAL, COMMON, SUPREME, ADMINISTRATIVE, NATIONAL_APPEAL_CHAMBER;

    @Override
    public String toString() {
        switch (this)
        {
            case COMMON: return "sad powszechny";
            case SUPREME: return "sad najwyzszy";
            case ADMINISTRATIVE: return "sad administracyjny";
            case CONSTITUTIONAL_TRIBUNAL: return "trybunal konstytucyjny";
            case NATIONAL_APPEAL_CHAMBER: return "krajowa izba odwolawcza";
            default: return "";
        }
    }
}