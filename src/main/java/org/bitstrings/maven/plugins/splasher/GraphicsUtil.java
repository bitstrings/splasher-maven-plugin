package org.bitstrings.maven.plugins.splasher;

import java.awt.Font;
import java.awt.Rectangle;

import org.codehaus.plexus.util.StringUtils;

public final class GraphicsUtil
{
    private static final String POSITION_CENTER_STR = "center";

    private static final String POSITION_LEFT_STR = "left";

    private static final String POSITION_RIGHT_STR = "right";

    private static final String POSITION_TOP_STR = "top";

    private static final String POSITION_BOTTOM_STR = "bottom";

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

        if ( xy[0].equals( POSITION_CENTER_STR ) )
        {
            coordinates[0] = ( ( bounds.width - width ) >> 1 ) + bounds.x;
        }
        else if ( xy[0].equals( POSITION_LEFT_STR ) )
        {
            coordinates[0] = 0;
        }
        else if ( xy[0].equals( POSITION_RIGHT_STR ) )
        {
            coordinates[0] = bounds.width - width;
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

        if ( xy[1].equals( POSITION_CENTER_STR ) )
        {
            coordinates[1] = ( ( bounds.height - height ) >> 1 ) + bounds.y;
        }
        else if ( xy[1].equals( POSITION_TOP_STR ) )
        {
            coordinates[1] = 0;
        }
        else if ( xy[1].equals( POSITION_BOTTOM_STR ) )
        {
            coordinates[1] = bounds.height - height;
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
