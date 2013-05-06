/**
 *  Copyright (c) 2013 bitstrings.org - Pino Silvaggio
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.bitstrings.maven.plugins.splasher;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
public class SplasherComposeMojo
    extends AbstractMojo
{
    @Component
    private MavenProject project;

    @Parameter( required = true )
    private CanvasDef canvas;

    @Parameter
    private FontDef[] fonts;

    @Parameter
    private Drawable[] draw;

    @Parameter( required = true )
    private File outputFile;

    @Parameter
    private String outputFormat;

    protected BufferedImage image;

    protected int canvasWidth;

    protected int canvasHeight;

    protected GraphicsContext graphicsContext;

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
                canvasWidth = image.getWidth();
            }

            if ( canvas.getHeight() <= 0 )
            {
                canvasHeight = image.getHeight();
            }
        }

        if ( canvasWidth <= 0 )
        {
            canvasWidth = canvas.getWidth();

            if ( canvasWidth <= 0 )
            {
                throw new MojoExecutionException( "Canvas width must be greater than 0." );
            }
        }

        if ( canvasHeight <= 0 )
        {
            canvasHeight = canvas.getHeight();

            if ( canvasHeight <= 0 )
            {
                throw new MojoExecutionException( "Canvas height must be greater than 0." );
            }
        }

        if ( canvas.getBackgroundImageFile() == null )
        {
            image = new BufferedImage( canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB );
        }

        graphicsContext = new GraphicsContext( canvasWidth, canvasHeight );

        if ( fonts != null )
        {
            for ( FontDef fontDef : fonts )
            {
                try
                {
                    graphicsContext.loadFont( fontDef.getAlias(), fontDef.getFontFile() );
                }
                catch ( Exception e )
                {
                    throw new MojoExecutionException( "Unable to load font " + fontDef.getFontFile(), e );
                }
            }
        }

        final Graphics2D g = image.createGraphics();

        g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );

        try
        {
            if ( canvas.getColor() != null )
            {
                try
                {
                    g.setBackground( Color.decode( canvas.getColor() ) );
                    g.clearRect( 0, 0, canvasWidth, canvasHeight );
                }
                catch ( NumberFormatException e )
                {
                    throw new MojoExecutionException( "Unable to decode color " + canvas.getColor() + ".", e );
                }
            }

            for ( Drawable drawable : draw )
            {
                Graphics2D dg = (Graphics2D) g.create();

                try
                {
                    drawable.init( graphicsContext, g );

                    drawable.draw( graphicsContext, g );
                }
                finally
                {
                    dg.dispose();
                }
            }

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
                            outputFormat == null
                                    ? FileUtils.extension( properOutputFile.getName() )
                                    : outputFormat,
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
