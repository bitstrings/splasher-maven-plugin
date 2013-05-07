package org.bitstrings.maven.plugins.splasher;

import java.util.ArrayList;
import java.util.List;


public abstract class Layout
    extends Drawable
{
    // - parameters --[

    private final List<Drawable> draw = new ArrayList<Drawable>();

    // ]--

    public List<Drawable> getDraw()
    {
        return draw;
    }
}
