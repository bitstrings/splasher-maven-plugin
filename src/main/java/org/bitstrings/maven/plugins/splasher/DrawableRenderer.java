package org.bitstrings.maven.plugins.splasher;

import java.awt.Graphics2D;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class DrawableRenderer<T extends Drawable>
{
    public int x;

    public int y;

    public int width;

    public int height;

    protected boolean isVisible = true;

    public int getX()
    {
        return x;
    }

    public void setX( int x )
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY( int y )
    {
        this.y = y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public void init( T drawable, GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        GraphicsUtil.decodePositionAndSetXY( drawable.getPosition(), this, g.getDeviceConfiguration().getBounds() );
    }

    public void draw( GraphicsContext context, Graphics2D g )
    {
        if ( !isVisible )
        {
            return;
        }
    }
}
