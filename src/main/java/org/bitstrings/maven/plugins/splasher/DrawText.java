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

import static org.bitstrings.maven.plugins.splasher.DrawingUtil.decodeColor;
import static org.bitstrings.maven.plugins.splasher.DrawingUtil.decodeFontStyle;
import static org.bitstrings.maven.plugins.splasher.DrawingUtil.decodePositionAndSetBounds;
import static org.bitstrings.maven.plugins.splasher.DrawingUtil.getDrawingBounds;

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

    /**
     * The text to render.
     */
    private String text = "";

    /**
     * The text color in the form of #rrggbb or a java color constant (java.awt.Color).
     *
     * <p>
     * Example:
     *
     *     #00000000 = black
     *     BLACK = black
     *     WHITE = white
     *     #FF0000 = red
     *     red = red
     *
     * <b>Default</b>: #000000
     * </p>
     */
    private String textColor = "#000000";

    /**
     * Font name which can either be a font resource name or a font name previously loaded to the system registry.
     *
     * <p>
     * Example:
     *
     *     SansSerif
     *     Serif
     *     Monospace
     *
     * <b>Default</b>: SansSerif
     * </p>
     */
    private String fontName = Font.SANS_SERIF;

    /**
     * The font style: plain, bold, italic
     *
     * <p>
     * They can be combined using comma.
     *
     * <b>Default</b>: plain
     * </p>
     */
    private String fontStyle = "plain";

    /**
     * Font point size.
     *
     * <p>
     * <b>Default</b>: 12
     * </p>
     */
    private int fontSize = 12;

    /**
     * Font anti-aliasing.
     *
     * <p>
     * Possible values: ON, OFF, GASP, HBGR, HRGB, VGBR, VRGB
     *
     * <b>Default</b>: ON
     * </p>
     */
    private AntialiasType fontAntialias = AntialiasType.ON;

    /**
     * Use font baseline to calculate coordinates.
     *
     * <p>
     * <b>Default</b>: false
     * </p>
     */
    private boolean useBaseline = false;

    // ]--

    protected Font dwFont;

    protected Color dwTextColor;

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public String getTextColor()
    {
        return textColor;
    }

    public void setTextColor( String textColor )
    {
        this.textColor = textColor;
    }

    public String getFontName()
    {
        return fontName;
    }

    public void setFontName( String fontName )
    {
        this.fontName = fontName;
    }

    public String getFontStyle()
    {
        return fontStyle;
    }

    public void setFontStyle( String fontStyle )
    {
        this.fontStyle = fontStyle;
    }

    public int getFontSize()
    {
        return fontSize;
    }

    public void setFontSize( int fontSize )
    {
        this.fontSize = fontSize;
    }

    public AntialiasType getFontAntialias()
    {
        return fontAntialias;
    }

    public void setFontAntialias( AntialiasType fontAntialias )
    {
        this.fontAntialias = fontAntialias;
    }

    public boolean isUseBaseline()
    {
        return useBaseline;
    }

    public void setUseBaseline( boolean useBaseline )
    {
        this.useBaseline = useBaseline;
    }

    @Override
    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        int dwFontStyle;

        try
        {
            dwFontStyle = decodeFontStyle( fontStyle );
        }
        catch ( IllegalArgumentException e )
        {
            throw new MojoExecutionException( "Unknown font style " + fontStyle + ".", e );
        }

        try
        {
            dwTextColor = decodeColor( textColor );
        }
        catch ( IllegalArgumentException e )
        {
            throw new MojoExecutionException( "Unable to decode color " + textColor + ".", e );
        }

        dwFont = dwContext.getFont( fontName, dwFontStyle, fontSize );

        FontMetrics metrics = g.getFontMetrics( dwFont );

        Rectangle2D textBounds = metrics.getStringBounds( text, g );

        dwBounds.width = (int) textBounds.getWidth();

        dwBounds.height = (int) textBounds.getHeight();

        decodePositionAndSetBounds(
                getPosition(),
                dwBounds.getSize(),
                getDrawingBounds( g ).getSize(),
                0, ( isUseBaseline() ? 0 : metrics.getAscent() ),
                dwBounds );
    }

    @Override
    public void render( Graphics2D g )
    {
        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, fontAntialias.getType() );

        g.setColor( dwTextColor );

        g.setFont( dwFont );

        g.drawString( text, dwBounds.x, dwBounds.y );
    }
}
