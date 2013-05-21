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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

public class LoadAlphaNumImages
    extends ResourceProvider
{
    // - parameters --[

    private File directory;

    private String fileNamePattern;

    private String ranges;

    private String namePrefix;

    // ]--

    public File getDirectory()
    {
        return directory;
    }

    public String getFileNamePattern()
    {
        return fileNamePattern;
    }

    public String getRanges()
    {
        return ranges;
    }

    public String getNamePrefix()
    {
        return namePrefix;
    }

    @Override
    public Map<String, ?> resourceMap( DrawingContext context )
        throws MojoExecutionException
    {
        for ( String range : StringUtils.split( ranges, ',' ) )
        {
            try
            {
                String[] members = StringUtils.split( range, '-' );

                if ( ArrayUtils.isEmpty( members ) || members.length > 2 )
                {
                    throw new MojoExecutionException( "Invalid range [" + range + "]." );
                }

                if ( StringUtils.isNumeric( members[0] ) )
                {
                    if ( members.length == 1 )
                    {
                        final int from = Integer.parseInt( members[0] );

                        return numericRangeResources( from, from, context );
                    }
                    else
                    {
                        return numericRangeResources(
                                Integer.parseInt( members[0] ),
                                Integer.parseInt( members[1] ),
                                context );
                    }
                }
                else
                {
                    if ( members.length == 1 )
                    {
                        final char from = members[0].charAt( 0 );

                        return charRangeResources( from, from, context );
                    }
                    else
                    {
                        return charRangeResources(
                                members[0].charAt( 0 ),
                                members[1].charAt( 0 ),
                                context );
                    }
                }
            }
            catch ( NumberFormatException e )
            {
                throw new MojoExecutionException( "Unable to parse range " + range + "." );
            }
        }

        return null;
    }

    public Map<String, ?> numericRangeResources( int from, int to, DrawingContext context )
        throws MojoExecutionException
    {
        Map<String, BufferedImage> resources = new HashMap<String, BufferedImage>();

        do
        {
            final File name = new File( directory, fileNamePattern.replace( "%n", String.valueOf( from ) ) );

            try
            {
                resources.put( namePrefix + from, context.loadImage( name ) );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "Unable to load image #" + from + " (" + name + ").", e );
            }
        }
        while ( ++from <= to );

        return resources;
    }

    public Map<String, ?> charRangeResources( int from, int to, DrawingContext context )
        throws MojoExecutionException
    {
        Map<String, BufferedImage> resources = new HashMap<String, BufferedImage>();

        do
        {
            final File name = new File( directory, fileNamePattern.replace( "%n", String.valueOf( from ) ) );

            try
            {
                resources.put( namePrefix + from, context.loadImage( name ) );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "Unable to load image #" + from + " (" + name + ").", e );
            }
        }
        while ( ++from <= to );

        return resources;
    }
}
