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

/**
 * Compose a splash screen.
 */
@Mojo( name = "compose", threadSafe = true )
public class SplasherComposeMojo
    extends AbstractMojo
{
    @Component
    private MavenProject project;

    /**
     * Canvas.
     */
    @Parameter( required = true )
    private Canvas canvas;

    /**
     * Drawable Resources.
     */
    @Parameter
    private Resource[] resources;

    /**
     * Output image file.
     */
    @Parameter( required = true )
    private File outputImageFile;

    /**
     * Output image type.
     */
    @Parameter
    private String outputImageFormat;

    protected DrawingContext graphicsContext;

    @Override
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        graphicsContext = new DrawingContext();

        if ( resources != null )
        {
            for ( Resource resource : resources )
            {
                resource.register( graphicsContext );
            }
        }

        if ( canvas == null )
        {
            return;
        }

        BufferedImage surface = canvas.createSurface();

        Graphics2D g = surface.createGraphics();

        try
        {
            g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
            g.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );

            canvas.setDrawingContext( graphicsContext );

            canvas.init( g );

            canvas.draw( g );
        }
        finally
        {
            g.dispose();
        }

        try
        {
            File properOutputFile =
                            outputImageFile.isAbsolute()
                                    ? outputImageFile
                                    : new File(
                                            new File( project.getBuild().getOutputDirectory() ),
                                            outputImageFile.toString() );

            File outputFileDirectory = properOutputFile.getParentFile();

            if ( outputFileDirectory != null )
            {
                outputFileDirectory.mkdirs();
            }

            ImageIO.write(
                        surface,
                        outputImageFormat == null
                                ? FileUtils.extension( properOutputFile.getName() )
                                : outputImageFormat,
                        properOutputFile );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Unable to write output image file.", e );
        }
    }
}
