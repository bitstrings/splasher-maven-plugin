package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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
        super.init( context, g );

        image = context.getImage( drawable.getImageName() );

        this.bounds = new Rectangle( x, y, image.getWidth(), image.getHeight() );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        g.drawImage( image, x, y, null );
    }
}
