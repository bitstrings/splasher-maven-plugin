package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.Canvas;
import org.bitstrings.maven.plugins.splasher.DrawableMapped;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;
import org.bitstrings.maven.plugins.splasher.PositionalLayout;

@DrawableMapped( Canvas.class )
public class CanvasRenderer
    extends PositionalLayoutRenderer
{
    protected BufferedImage backgroundImage;

    protected Color backgroundColor;

    public BufferedImage getBackgroundImage()
    {
        return backgroundImage;
    }

    public void setBackgroundImage( BufferedImage backgroundImage )
    {
        this.backgroundImage = backgroundImage;
    }

    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor( Color backgroundColor )
    {
        this.backgroundColor = backgroundColor;
    }

    public void initRoot( Canvas canvas, GraphicsContext context )
        throws MojoExecutionException
    {
        init( canvas, context, null );
    }

    @Override
    public void init( PositionalLayout layout, GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        Canvas canvas = (Canvas) layout;

        x = 0;
        y = 0;

        if ( canvas.getBackgroundImageName() != null )
        {
            backgroundImage = context.getImage( canvas.getBackgroundImageName() );

            width = backgroundImage.getWidth();

            height = backgroundImage.getHeight();
        }

        if ( ( width < 0 ) || ( height < 0 ) )
        {
            throw new MojoExecutionException( "Canvas size is weird -- width: " + width + ", height: " + height );
        }

        if ( canvas.getBackgroundColor() != null )
        {
            try
            {
                backgroundColor = Color.decode( canvas.getBackgroundColor() );
            }
            catch ( Exception e )
            {
                throw new MojoExecutionException( "Illegal canvas color " + canvas.getBackgroundColor(), e );
            }
        }
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
    {
        if ( backgroundColor != null )
        {
            g.setBackground( backgroundColor );

            g.clearRect( 0, 0, width, height );
        }

        if ( backgroundImage != null )
        {
            g.drawImage( backgroundImage, 0, 0, null );
        }

        super.draw( context, g );
    }
}
