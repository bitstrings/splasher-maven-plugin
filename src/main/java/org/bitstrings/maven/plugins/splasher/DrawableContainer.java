package org.bitstrings.maven.plugins.splasher;

import static java.util.Collections.EMPTY_LIST;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;


public abstract class DrawableContainer
    extends Drawable
{
    // - parameters --[

    /**
     * The drawables for this container.
     */
    private List<Drawable> draw;

    // ]--

    public List<Drawable> getDraw()
    {
        return ObjectUtils.defaultIfNull( draw, EMPTY_LIST );
    }

    public void setDraw( List<Drawable> draw )
    {
        this.draw = draw;
    }
}
