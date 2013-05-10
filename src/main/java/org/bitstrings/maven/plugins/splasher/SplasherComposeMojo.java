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
import org.bitstrings.maven.plugins.splasher.renderer.CanvasRenderer;
import org.codehaus.plexus.util.FileUtils;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

@Mojo( name = "compose" )
public class SplasherComposeMojo
    extends AbstractMojo
{
    @Component
    private MavenProject project;

    @Parameter( required = true )
    private Canvas canvas;

    @Parameter
    private Resource[] resources;

    @Parameter( required = true )
    private File outputFile;

    @Parameter
    private String outputFormat;

    protected DrawingContext graphicsContext;

    @Override
    public void execute()
        throws MojoExecutionException, MojoFailureException
    {
        if ( canvas == null )
        {
            return;
        }

        graphicsContext = new DrawingContext();

        try
        {
            final ClassPath classPath = ClassPath.from( Thread.currentThread().getContextClassLoader() );

            ImmutableSet<ClassPath.ClassInfo> renderersClassInfo =
                            classPath.getTopLevelClasses( this.getClass().getPackage().getName() + ".renderer" );

            for ( ClassPath.ClassInfo classInfo : renderersClassInfo )
            {
                if ( classInfo.getName().endsWith( "Renderer" ) )
                {
                    Class<?> c = classInfo.load();

                    if ( DrawableRenderer.class.isAssignableFrom( c ) )
                    {
                        graphicsContext.registerDrawableRenderer( (Class<DrawableRenderer<? extends Drawable>>) c );
                    }
                }
            }
        }
        catch ( Exception e )
        {
            throw new MojoFailureException( "Error initializing renderers.", e );
        }

        if ( resources != null )
        {
            for ( Resource resource : resources )
            {
                resource.register( graphicsContext );
            }
        }

        CanvasRenderer canvasRenderer = (CanvasRenderer) graphicsContext.createDrawableRenderer( canvas );

        canvasRenderer.initRoot( canvas, graphicsContext );

        BufferedImage image =
                        new BufferedImage( canvasRenderer.width, canvasRenderer.height, BufferedImage.TYPE_INT_RGB );

        final Graphics2D g = image.createGraphics();

        try
        {
            g.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
            g.setRenderingHint( RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY );

            canvasRenderer.draw( graphicsContext, g );

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
