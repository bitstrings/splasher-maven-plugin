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

import static org.bitstrings.maven.plugins.splasher.GraphicsUtil.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import org.apache.maven.plugin.MojoExecutionException;

public class DrawText
    extends Drawable
{
    public enum AntialiasType
    {
        ON( RenderingHints.VALUE_TEXT_ANTIALIAS_ON ),
        OFF( RenderingHints.VALUE_TEXT_ANTIALIAS_OFF ),
        GASP( RenderingHints.VALUE_TEXT_ANTIALIAS_GASP ),
        HBGR( RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HBGR ),
        HRGB( RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB ),
        VBGR( RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VBGR ),
        VRGB( RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_VRGB );

        private Object type;

        private AntialiasType( Object type )
        {
            this.type = type;
        }

        public Object getType()
        {
            return type;
        }
    }

    // - parameters --[

    private String text;

    private String fontName;

    private String fontStyle = "plain";

    private int fontSize = 8;

    private AntialiasType fontAntialias = AntialiasType.ON;

    private String position = "0,0";

    private String color = "#000000";

    // ]--

    protected int awtFontStyle;

    protected Font awtFont;

    public String getText()
    {
        return text;
    }

    public String getFontName()
    {
        return fontName;
    }

    public String getFontStyle()
    {
        return fontStyle;
    }

    public int getFontSize()
    {
        return fontSize;
    }

    public AntialiasType getFontAntialias()
    {
        return fontAntialias;
    }

    public String getPosition()
    {
        return position;
    }

    public String getColor()
    {
        return color;
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        if ( fontStyle != null )
        {
            try
            {
                awtFontStyle = decodeFontStyle( fontStyle );
            }
            catch ( IllegalArgumentException e )
            {
                throw new MojoExecutionException( "Illegal font style " + fontStyle + ".", e );
            }
        }

        awtFont = context.getFont( getFontName(),awtFontStyle, getFontSize() );

        FontMetrics metrics = g.getFontMetrics( awtFont );

        Rectangle2D textBounds = metrics.getStringBounds( getText(), g );

        bounds = textBounds.getBounds();

        decodeAndSetXY( position, this, g.getDeviceConfiguration().getBounds(), 0, metrics.getAscent() );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
            try
            {
                g.setColor( Color.decode( getColor() ) );
            }
            catch ( NumberFormatException e )
            {
                throw new MojoExecutionException( "Unable to decode color " + getColor() + ".", e );
            }

            g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, getFontAntialias().getType() );

            g.setFont( awtFont );

            g.drawString( getText(), x, y );
    }
}
