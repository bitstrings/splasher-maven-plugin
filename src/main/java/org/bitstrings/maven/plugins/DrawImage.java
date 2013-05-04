package org.bitstrings.maven.plugins;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.maven.plugin.MojoExecutionException;

public class DrawImage
    implements Drawable, ComponentInitLate
{
    private File imageFile;

    private int x;

    private int y;

    public File getImageFile()
    {
        return imageFile;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    protected BufferedImage awtImage;

    @Override
    public void init()
        throws MojoExecutionException
    {
        try
        {
            awtImage = ImageIO.read( imageFile );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Unable to read image file " + imageFile + ".", e );
        }
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        g.drawImage( awtImage, getX(), getY(), null );
    }
}
