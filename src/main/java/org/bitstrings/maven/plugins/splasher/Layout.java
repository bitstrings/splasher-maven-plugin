package org.bitstrings.maven.plugins.splasher;

import java.util.List;


public abstract class Layout
    extends Drawable
{
    // - parameters --[

    private List<Drawable> draw;

    // ]--

    public List<Drawable> getDraw()
    {
        return draw;
    }

    public void setDraw( List<Drawable> draw )
    {
        this.draw = draw;
    }
}
