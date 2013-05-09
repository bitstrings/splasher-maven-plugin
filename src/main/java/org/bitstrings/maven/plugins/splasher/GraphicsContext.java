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
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class GraphicsContext
{
    protected final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

    protected final Map<String, String> fontNameMap = new HashMap<String, String>();

    protected final Map<String, BufferedImage> imageNameMap = new HashMap<String, BufferedImage>();

    protected final Map<Class<? extends Drawable>, DrawableRenderer<? extends Drawable>> drawableToRendererMap
            = new HashMap<Class<? extends Drawable>, DrawableRenderer<? extends Drawable>>();

    protected final Rectangle canvasBounds;

    public GraphicsContext( int canvasWidth, int canvasHeight )
    {
        this.canvasBounds = new Rectangle( canvasWidth, canvasHeight );
    }

    public Rectangle getCanvasBounds()
    {
        return canvasBounds;
    }

    public GraphicsEnvironment getGraphicsEnvironment()
    {
        return graphicsEnvironment;
    }

    public void loadFont( String name, File fontFile )
        throws IOException, FontFormatException
    {
        Font font = Font.createFont( Font.TRUETYPE_FONT, fontFile );

        graphicsEnvironment.registerFont( font );

        if ( name != null )
        {
            fontNameMap.put( name, font.getName() );
        }
    }

    public Font getFont( String name, int style, int size )
    {
        final String fontName = fontNameMap.get( name );

        return new Font( fontName == null ? name : fontName, style, size );
    }

    public void loadImage( String name, File file )
        throws IOException
    {
        BufferedImage image = ImageIO.read( file );

        if ( name != null )
        {
            imageNameMap.put( name, image );
        }
    }

    public BufferedImage getImage( String name )
    {
        return imageNameMap.get( name );
    }

    public <T extends Drawable> void registerDrawableRenderer(
                                              Class<T> drawableClass, DrawableRenderer<? super T> renderer )
    {
        drawableToRendererMap.put( drawableClass, renderer );
    }

    public <T extends Drawable> void registerDrawableRenderer( DrawableRenderer<T> renderer )
    {
        for ( Class<? extends T> drawableClass : renderer.getDefaultMappedDrawables() )
        {
            registerDrawableRenderer( drawableClass, renderer );
        }
    }

    public <T extends Drawable> DrawableRenderer<? super T> getDrawableRenderer( Class<T> drawableClass )
    {
        return (DrawableRenderer<? super T>) drawableToRendererMap.get( drawableClass );
    }

    public <T extends Drawable> DrawableRenderer<? extends Drawable> getDrawableRenderer( T drawable )
    {
        return getDrawableRenderer( drawable.getClass() );
    }
}
