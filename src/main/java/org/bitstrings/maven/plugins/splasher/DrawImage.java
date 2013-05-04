package org.bitstrings.maven.plugins.splasher;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.maven.plugin.MojoExecutionException;

public class DrawImage
    implements Drawable
{
    private File imageFile;

    private String position;

    protected int x;

    protected int y;

    public File getImageFile()
    {
        return imageFile;
    }

    public String getPosition()
    {
        return position;
    }

    protected BufferedImage awtImage;

    @Override
    public void init(GraphicsContext context, Graphics2D g)
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

        int[] xy =
            GraphicsUtil.decodeXY(
                    position,
                    awtImage.getWidth(), awtImage.getHeight(),
                    context.getCanvasBounds() );

        this.x = xy[0];
        this.y = xy[1];
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        g.drawImage( awtImage, x, y, null );
    }
}
