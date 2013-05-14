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
        dwBounds.setSize( decodeSize( size ) );

        super.init( g );

        for ( Drawable d : getDraw() )
        {
            d.setDrawingContext( dwContext );

            Graphics2D sg = (Graphics2D) g.create( dwBounds.x, dwBounds.y, dwBounds.width, dwBounds.height );

            try
            {
                d.init( sg );
            }
            finally
            {
                sg.dispose();
            }
        }
    }

    @Override
    public void render( Graphics2D g )
    {
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
