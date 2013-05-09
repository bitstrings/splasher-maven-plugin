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
import java.awt.RenderingHints;

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

    private String textColor = "#000000";

    private String fontName = Font.SANS_SERIF;

    private String fontStyle = "plain";

    private int fontSize = 12;

    private AntialiasType fontAntialias = AntialiasType.ON;

    public boolean useBaseline = false;

    // ]--

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
}
