package org.bitstrings.maven.plugins.splasher;

import static org.bitstrings.maven.plugins.splasher.DrawingUtil.decodeSize;

import java.awt.Graphics2D;

import org.apache.maven.plugin.MojoExecutionException;

public class PositionalLayout
    extends DrawableContainer
{
    // - parameters --[

    private String size = "0x0";

    // ]--

    public String getSize()
    {
        return size;
    }

    public void setSize( String size )
    {
        this.size = size;
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

        int[] dwSize = decodeSize( size );

        dwBounds.setSize( dwSize[0], dwSize[1] );

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
