package org.bitstrings.maven.plugins.splasher;

import org.bitstrings.maven.plugins.splasher.renderer.PositionalLayoutRenderer;

public class PositionalLayout
    extends DrawableGroup
{
    // - parameters --[

    private int width;

    private int height;

    // ]--

    public int getWidth()
    {
        return width;
    }

    public void setWidth( int width )
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight( int height )
    {
        this.height = height;
    }

    @Override
    public DrawableRenderer<?> createDrawableRenderer()
    {
        return new PositionalLayoutRenderer( this );
    }
}
