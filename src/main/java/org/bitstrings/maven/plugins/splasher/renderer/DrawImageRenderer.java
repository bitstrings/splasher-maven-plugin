package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.DrawImage;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

public class DrawImageRenderer
    extends DrawableRenderer<DrawImage>
{
    protected BufferedImage image;

    public DrawImageRenderer( DrawImage drawImage )
    {
        super( drawImage );
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        image = context.getImage( drawable.getImageName() );

        width = image.getWidth();

        height = image.getHeight();

        super.init( context, g );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        g.drawImage( image, x, y, null );
    }
}
