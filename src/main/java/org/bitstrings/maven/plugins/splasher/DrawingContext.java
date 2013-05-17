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

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.collections.map.MultiKeyMap;

public class DrawingContext
{
    protected final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

    protected final MultiKeyMap resourceMap = new MultiKeyMap();

    public GraphicsEnvironment getGraphicsEnvironment()
    {
        return graphicsEnvironment;
    }

    public Font loadFont( File fontFile )
        throws IOException, FontFormatException
    {
        return Font.createFont( Font.TRUETYPE_FONT, fontFile );
    }

    public Font getFont( String name, int style, int size )
    {
        Font font = getResource( name, Font.class );

        return
                new Font(
                    ( font == null ? name : font.getName() ),
                    style,
                    size );
    }

    public BufferedImage loadImage( File file )
        throws IOException
    {
        return ImageIO.read( file );
    }

    public BufferedImage getImage( String name )
    {
        return getResource( name, BufferedImage.class );
    }

    public <T> void registerResource( String name, T resource )
    {
        resourceMap.put( resource.getClass(), name, resource );
    }

    public <T> T getResource( String name, Class<T> resourceType )
    {
        return (T) resourceMap.get( resourceType, name );
    }
}
