package org.bitstrings.maven.plugins;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.FileUtils;

@Mojo( name = "compose" )
public class SplasherMojo
    extends AbstractMojo
{
    @Component
    private MavenProject project;

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

            if ( canvas.getWidth() <= 0 )
            {
                finalWidth = image.getWidth();
            }

            if ( canvas.getHeight() <= 0 )
            {
                finalHeight = image.getHeight();
            }
        }

        if ( finalWidth <= 0 )
        {
            finalWidth = canvas.getWidth();

            if ( finalWidth <= 0 )
            {
                throw new MojoExecutionException( "Canvas width must be greater than 0." );
            }
        }

        if ( finalHeight <= 0 )
        {
            finalHeight = canvas.getHeight();

            if ( finalHeight <= 0 )
            {
                throw new MojoExecutionException( "Canvas height must be greater than 0." );
            }
        }

        if ( canvas.getBackgroundImageFile() == null )
        {
            image = new BufferedImage( finalWidth, finalHeight, BufferedImage.TYPE_INT_RGB );
        }

        final Graphics2D g = image.createGraphics();

        try
        {
            if ( canvas.getColor() != null )
            {
                try
                {
                    g.setBackground( Color.decode( canvas.getColor() ) );
                }
                catch ( NumberFormatException e )
                {
                    throw new MojoExecutionException( "Unable to decode color " + canvas.getColor() + ".", e );
                }
            }

            g.drawString( "PINO RULES!", 16F, 32F );

            try
            {
                File properOutputFile =
                                outputFile.isAbsolute()
                                        ? outputFile
                                        : new File(
                                                new File( project.getBuild().getOutputDirectory() ),
                                                outputFile.toString() );

                File outputFileDirectory = properOutputFile.getParentFile();

                if ( outputFileDirectory != null )
                {
                    outputFileDirectory.mkdirs();
                }

                ImageIO.write(
                            image,
                            FileUtils.extension( properOutputFile.getName() ),
                            properOutputFile );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "Unable to write output image file.", e );
            }
        }
        finally
        {
            g.dispose();
        }
    }
}
