package org.bitstrings.maven.plugins.splasher;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.bitstrings.maven.plugins.splasher.renderer.NoDrawRenderer;

public class DrawOnCondition
    extends DrawableGroup
{
    // - parameters --[

    private List<DrawableCondition> conditions;

    // ]--

    public List<DrawableCondition> getConditions()
    {
        return ObjectUtils.defaultIfNull( conditions, EMPTY_LIST );
    }

    public void setConditions( List<DrawableCondition> conditions )
    {
        this.conditions = conditions;
    }

    @Override
    public DrawableRenderer<?> createDrawableRenderer()
    {
        for ( DrawableCondition c : conditions )
        {
            if (!c.eval())
            {
                return new NoDrawRenderer( this );
            }
        }

        return null;
    }
}
