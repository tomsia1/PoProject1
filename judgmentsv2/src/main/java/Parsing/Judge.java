package Parsing;

import java.util.Objects;

public class Judge
{

    private String name;
    private String function;
    private SpecialRole[] specialRoles;

    public Judge(String name, String function)
    {
        this.name=name;
        this.function=function;
        specialRoles=null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Judge)) return false;
        Judge judge = (Judge) o;
        return name.equals(judge.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Judge(){};

    public Judge(String name)
    {
        this.name=name;
        function=null;
        specialRoles=null;
    }

    public String toString()
    {
        return name;
    }

    public String getName()
    {
        return name;
    }

}