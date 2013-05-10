package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.DrawImage;
import org.bitstrings.maven.plugins.splasher.DrawableMapped;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

@DrawableMapped( DrawImage.class )
public class DrawImageRenderer
    extends DrawableRenderer<DrawImage>
{
    protected BufferedImage image;

    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage( BufferedImage image )
    {
        this.image = image;
    }

    @Override
    public void init( DrawImage drawable, GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        image = context.getImage( drawable.getImageName() );

        width = image.getWidth();

        height = image.getHeight();

        super.init( drawable, context, g );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
    {
        super.draw( context, g );

        g.drawImage( image, x, y, null );
    }
}
