package org.bitstrings.maven.plugins;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo( name = "compose" )
public class SplasherMojo
    extends AbstractMojo
{
    @Parameter( required = true )
    private Canvas canvas;

    @Parameter( required = true )
    private File outputFile;

    protected BufferedImage image;

    protected int finalWidth;

    protected int finalHeight;

    @Override
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        if ( canvas.getBackgroundImageFile() != null )
        {
            try
            {
                image = ImageIO.read( canvas.getBackgroundImageFile() );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "Unable to read background image file.", e );
            }

            if ( canvas.getWidth() == 0 )
            {
                finalWidth = image.getWidth();
            }

            if ( canvas.getHeight() == 0 )
            {
                finalWidth = image.getHeight();
            }
        }

        Graphics2D g = image.createGraphics();

        g.drawString( "PINO RULES!", 10F, 10F );

        g.dispose();

        try
        {
            ImageIO.write( image, "png", outputFile );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Unable to write output image file.", e );
        }
    }
}
