package org.bitstrings.maven.plugins.splasher;

import java.awt.Font;
import java.awt.Rectangle;

import org.codehaus.plexus.util.StringUtils;

public final class GraphicsUtil
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

    private GraphicsUtil() {}

    public static int[] decodeXY( String str, int width, int height, Rectangle bounds )
        throws IllegalArgumentException
    {
        final int[] coordinates = new int[2];

        String[] xy = StringUtils.split( str, "," );

        xy[0] = xy[0].trim().toLowerCase();

        if ( xy[0].equals( "center" ) )
        {
            coordinates[0] = ( ( bounds.width - width ) >> 1 ) + bounds.x;
        }
        else
        {
            try
            {
                coordinates[0] = Integer.parseInt( xy[0] );
            }
            catch ( NumberFormatException e )
            {
                throw new IllegalArgumentException( "Unable to parse x coordinate " + xy[0], e );
            }
        }

        xy[1] = xy[1].trim().toLowerCase();

        if ( xy[1].equals( "center" ) )
        {
            coordinates[1] = ( ( bounds.height - height ) >> 1 ) + bounds.y;
        }
        else
        {
            try
            {
                coordinates[1] = Integer.parseInt( xy[1] );
            }
            catch ( NumberFormatException e )
            {
                throw new IllegalArgumentException( "Unable to parse y coordinate " + xy[1], e );
            }
        }

        return coordinates;
    }

    public static int decodeFontStyle( String str )
        throws IllegalArgumentException
    {
        int awtFontStyle = 0;

        for ( String token : StringUtils.split( str, "," ) )
        {
            awtFontStyle |= FontStyle.valueOf( token.toUpperCase() ).getStyle();
        }

        return awtFontStyle;
    }
}
