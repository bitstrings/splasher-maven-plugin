package org.bitstrings.maven.plugins.splasher.condition;

import org.apache.commons.lang3.StringUtils;
import org.bitstrings.maven.plugins.splasher.DrawableCondition;

public class EqualsCondition
    implements DrawableCondition
{
    // - parameters --[

    private String string1;

    private String string2;

    private boolean ignoreCase;

    private boolean negate;

    // ]--

    public String getString1()
    {
        return string1;
    }

    public void setString1( String string1 )
    {
        this.string1 = string1;
    }

    public String getString2()
    {
        return string2;
    }

    public void setString2( String string2 )
    {
        this.string2 = string2;
    }

    public boolean isIgnoreCase()
    {
        return ignoreCase;
    }

    public void setIgnoreCase( boolean ignoreCase )
    {
        this.ignoreCase = ignoreCase;
    }

    public boolean isNegate()
    {
        return negate;
    }

    public void setNegate( boolean negate )
    {
        this.negate = negate;
    }

    @Override
    public boolean eval()
    {
        return
                negate ^
                    ( ignoreCase
                        ? StringUtils.equalsIgnoreCase( string1, string2 )
                        : StringUtils.equals( string1, string2 ) );
    }
}
