package org.bitstrings.maven.plugins.splasher.condition;

import org.apache.commons.lang3.StringUtils;
import org.bitstrings.maven.plugins.splasher.DrawableCondition;

public class ContainsCondition
    implements DrawableCondition
{
    // - parameters --[

    private String source;

    private String string;

    private boolean ignoreCase;

    private boolean negate;

    // ]--

    @Override
    public boolean eval()
    {
        if ( ignoreCase )
        {
            source = source.toLowerCase();

            string = string.toLowerCase();
        }

        return negate ^ StringUtils.contains( source, string );
    }
}
