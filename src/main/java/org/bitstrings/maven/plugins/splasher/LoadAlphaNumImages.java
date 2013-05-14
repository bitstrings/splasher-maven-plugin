package org.bitstrings.maven.plugins.splasher;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;

public class LoadAlphaNumImages
    extends Resource
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
    public void register( DrawingContext context )
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

                        registerNumericRange( from, from, context );
                    }
                    else
                    {
                        registerNumericRange(
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

                        registerCharRange( from, from, context );
                    }
                    else
                    {
                        registerCharRange(
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
    }

    protected void registerNumericRange( int from, int to, DrawingContext context )
        throws MojoExecutionException
    {
        do
        {
            final File name = new File( directory, fileNamePattern.replace( "%n", String.valueOf( from ) ) );

            try
            {
                context.loadImage( namePrefix + from, name );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "Unable to load image #" + from + " (" + name + ").", e );
            }
        }
        while ( ++from <= to );
    }

    protected void registerCharRange( char from, char to, DrawingContext context )
        throws MojoExecutionException
    {
        do
        {
            final File name = new File( directory, fileNamePattern.replace( "%n", String.valueOf( from ) ) );

            try
            {
                context.loadImage( namePrefix + from, name );
            }
            catch ( IOException e )
            {
                throw new MojoExecutionException( "Unable to load image for char " + from + " (" + name + ").", e );
            }
        }
        while ( ++from <= to );
    }
}
