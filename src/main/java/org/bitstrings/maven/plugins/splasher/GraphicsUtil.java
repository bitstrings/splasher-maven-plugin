package org.bitstrings.maven.plugins.splasher;

import java.awt.Font;
import java.awt.Rectangle;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

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
    {
        final int[] coordinate = new int[2];

        str = str.trim();

        if ( str.equalsIgnoreCase( "center" ) )
        {
            coordinate[0] = ( ( bounds.width - width ) >> 1 ) + bounds.x;
            coordinate[1] = ( ( bounds.height - height ) >> 1 ) + bounds.y;
        }
        else
        {
            String[] xy = Iterables.toArray( Splitter.on( ',' ).split( str ), String.class );

            if ( xy.length != 2 )
            {
                throw new IllegalArgumentException( "Invalid position " + str + "." );
            }

            coordinate[0] = Integer.parseInt( xy[0] );
            coordinate[1] = Integer.parseInt( xy[1] );
        }


        return coordinate;
    }

    public static int decodeFontStyle( String str )
        throws IllegalArgumentException
    {
        int awtFontStyle = 0;

        for ( String token : Splitter.on( '|' ).split( str ) )
        {
            awtFontStyle |= FontStyle.valueOf( token.toUpperCase() ).getStyle();
        }

        return awtFontStyle;
    }
}
