package org.bitstrings.maven.plugins.splasher;

import java.awt.Graphics2D;

import org.apache.maven.plugin.MojoExecutionException;

public class PositionalLayout
    extends DrawableContainer
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
    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        for ( Drawable d : getDraw() )
        {
            d.setDrawingContext( dwContext );

            Graphics2D sg = (Graphics2D) g.create();

            try
            {
                d.init( sg );
            }
            finally
            {
                sg.dispose();
            }
        }

        dwBounds.width = width;

        dwBounds.height = height;

        super.init( g );
    }

    @Override
    public void draw( Graphics2D g )
    {
        super.draw( g );

        for ( Drawable d : getDraw() )
        {
            Graphics2D sg = (Graphics2D) g.create( dwBounds.x, dwBounds.y, dwBounds.width, dwBounds.height );

            try
            {
                d.draw( sg );
            }
            finally
            {
                sg.dispose();
            }
        }
    }
}
