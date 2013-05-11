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

import static org.bitstrings.maven.plugins.splasher.DrawingUtil.decodeSize;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.maven.plugin.MojoExecutionException;


public class Canvas
    extends PositionalLayout
{
    // - parameters --[

    private String backgroundImageName;

    private String backgroundColor;

    // ]--

    protected BufferedImage dwSurface;

    protected Color dwBackgroundColor;

    public String getBackgroundImageName()
    {
        return backgroundImageName;
    }

    public void setBackgroundImageName( String backgroundImageName )
    {
        this.backgroundImageName = backgroundImageName;
    }

    public String getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setBackgroundColor( String backgroundColor )
    {
        this.backgroundColor = backgroundColor;
    }

    public BufferedImage createSurface()
    {
        int[] dwSize = decodeSize( getSize() );

        dwBounds.setSize( dwSize[0], dwSize[1] );

        if ( backgroundImageName != null )
        {
            dwSurface = dwContext.getImage( backgroundImageName );

            if ( getSize().equals( "0x0" ) )
            {
                dwBounds.width = dwSurface.getWidth();

                dwBounds.height = dwSurface.getHeight();
            }
        }

        if ( dwSurface == null )
        {
            dwSurface = new BufferedImage( dwBounds.width, dwBounds.height, BufferedImage.TYPE_INT_ARGB );
        }

        return dwSurface;
    }

    @Override
    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        super.init( g );

        if ( backgroundImageName != null )
        {
            dwSurface = dwContext.getImage( backgroundImageName );
        }

        if ( ( dwBounds.width < 0 ) || ( dwBounds.height < 0 ) )
        {
            throw new MojoExecutionException(
                                "Canvas size is weird -- width: " + dwBounds.width + ", height: " + dwBounds.height );
        }

        if ( backgroundColor != null )
        {
            try
            {
                dwBackgroundColor = Color.decode( backgroundColor );
            }
            catch ( Exception e )
            {
                throw new MojoExecutionException( "Illegal canvas color " + backgroundColor, e );
            }
        }
    }

    @Override
    public void draw( Graphics2D g )
    {
        if ( dwBackgroundColor != null )
        {
            g.setBackground( dwBackgroundColor );

            g.clearRect( dwBounds.x, dwBounds.y, dwBounds.width, dwBounds.height );
        }

        if ( dwSurface != null )
        {
            g.drawImage( dwSurface, dwBounds.x, dwBounds.y, null );
        }

        super.draw( g );
    }
}
