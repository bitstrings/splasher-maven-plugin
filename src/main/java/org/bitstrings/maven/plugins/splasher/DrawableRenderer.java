package org.bitstrings.maven.plugins.splasher;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class DrawableRenderer<T extends Drawable>
{
    protected final T drawable;

    public int x;

    public int y;

    protected Rectangle bounds;

    public DrawableRenderer( T drawable )
    {
        this.drawable = drawable;
    }

    public T getDrawable()
    {
        return drawable;
    }

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

    public void setBounds( Rectangle bounds )
    {
        this.bounds = bounds;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        GraphicsUtil.decodeAndSetXY( drawable.getPosition(), this, g.getDeviceConfiguration().getBounds() );
    }

    public abstract void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException;
}
