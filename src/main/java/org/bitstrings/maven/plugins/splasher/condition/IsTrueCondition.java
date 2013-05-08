package org.bitstrings.maven.plugins.splasher.condition;

import org.bitstrings.maven.plugins.splasher.DrawableCondition;

public class IsTrueCondition
    implements DrawableCondition
{
    // - parameters --[

    private String value;

    // ]--

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    @Override
    public boolean eval()
    {
        return Boolean.parseBoolean( value );
    }
}
