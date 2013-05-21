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

import static org.apache.maven.plugins.annotations.LifecyclePhase.GENERATE_RESOURCES;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

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
@Mojo( name = "compose", defaultPhase=GENERATE_RESOURCES, threadSafe = true )
public class SplasherComposeMojo
    extends AbstractMojo
{
    @Component
    private MavenProject project;

    /**
     * The canvas.
     * <br>
     * <br>
     * See <b>Reference: Drawables</b>.
     */
    @Parameter( required = true )
    private Canvas canvas;

    /**
     * Resources declaration used for drawing on canvas.
     *
     * <pre>
     * Example:
     *     &lt;resources&gt;
     *       &lt;loadImage&gt;
     *         &lt;imageFile&gt;logo.png&lt;/imageFile&gt;
     *         &lt;name&gt;logo&lt;/name&gt;
     *       &lt;/loadImage&gt;
     *     &lt;/resources&gt;
     * </pre>
     * <br>
     * See <b>Reference: Resources</b>.
     */
    @Parameter
    private ResourceProvider[] resources;

    /**
     * Output image file. If the image format is not explicitly specified then the file extension is used.
     */
    @Parameter( required = true )
    private File outputImageFile;

    /**
     * Output image type. The supported formats are those of the JDK in use.
     *
     * i.e.: Java 7 supports GIF, PNG, JPG
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
            for ( ResourceProvider resource : resources )
            {
                Map<String, ?> mappedResources = resource.resourceMap( graphicsContext );

                for ( Map.Entry<String, ?> entry : mappedResources.entrySet() )
                {
                    if ( getLog().isDebugEnabled() )
                    {
                        getLog().debug( "Resource type: " + resource.getClass().getName() );
                        getLog().debug(
                            "  Registering resource name: " + entry.getKey()
                                + " :: " + entry.getValue().getClass().getName() );
                    }

                    graphicsContext.registerResource( entry.getKey(), entry.getValue() );
                }
            }
        }

        if ( canvas == null )
        {
            if ( getLog().isWarnEnabled() )
            {
                getLog().warn( "No canvas." );
            }

            return;
        }

        canvas.setDrawingContext( graphicsContext );

        BufferedImage surface = canvas.init();

        Graphics2D g = surface.createGraphics();

        g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
        g.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );

        g.dispose();

        canvas.draw();

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

            if ( outputImageFormat == null )
            {
                outputImageFormat = FileUtils.extension( properOutputFile.getName() );
            }

            ImageIO.write( surface, outputImageFormat, properOutputFile );

            if ( getLog().isInfoEnabled() )
            {
                getLog().info(
                    "Writing image to " + properOutputFile
                        + " (format: " + outputImageFormat.toUpperCase() + " )" );
            }
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Unable to write output image file.", e );
        }
    }
}
