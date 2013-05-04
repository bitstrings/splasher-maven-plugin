package org.bitstrings.maven.plugins.splasher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.apache.maven.plugin.MojoExecutionException;

public class DrawText
    implements Drawable, ComponentInitLate
{
    public enum FontStyle
    {
        PLAIN( Font.PLAIN ),
        BOLD( Font.BOLD ),
        ITALIC( Font.ITALIC );

        private int style;

        private FontStyle( int style )
        {
            this.style = style;
        }

        public int getStyle()
        {
            return style;
        }
    }

    private String text;

    private String fontName;

    private String fontStyle = "plain";

    private int fontSize = 8;

    private boolean antialias = true;

    private String position;

    private String color = "#000000";

    protected int x = 0;

    protected int y = 0;

    protected int awtFontStyle;

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

    public boolean isAntialias()
    {
        return antialias;
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
    public void init()
        throws MojoExecutionException
    {
        if ( fontStyle != null )
        {
            try
            {
                awtFontStyle = GraphicsUtil.decodeFontStyle( fontStyle );
            }
            catch ( IllegalArgumentException e )
            {
                throw new MojoExecutionException( "Illegal font style " + fontStyle + ".", e );
            }
        }
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

            g.setRenderingHint(
                    RenderingHints.KEY_TEXT_ANTIALIASING,
                    isAntialias()
                            ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                            : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF );

            g.setFont( context.getFont( getFontName(),awtFontStyle, getFontSize() ) );

            g.drawString( getText(), getX(), getY() );
    }
}
