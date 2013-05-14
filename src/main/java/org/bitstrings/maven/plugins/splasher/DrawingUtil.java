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

import static org.codehaus.plexus.util.StringUtils.split;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

public final class DrawingUtil
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

    private DrawingUtil() {}

    public static void decodePositionAndSetBounds(
                                String position,
                                int width, int height,
                                Rectangle containerBounds,
                                Rectangle targetBounds )
    {
        decodePositionAndSetBounds( position, width, height, containerBounds, 0, 0, targetBounds );
    }

    public static void decodePositionAndSetBounds(
                                String position,
                                int width, int height,
                                Rectangle containerBounds,
                                int xOffset, int yOffset,
                                Rectangle targetBounds )
    {
        int[] xy = decodePair( position, width, height, containerBounds );

        targetBounds.x = ( xy[0] + xOffset );
        targetBounds.y = ( xy[1] + yOffset );
    }

    public static int[] decodePair( String pair )
        throws IllegalArgumentException
    {
        return decodeSeries( pair, 2 );
    }

    public static Dimension decodeSize( String pair )
                    throws IllegalArgumentException
    {
        final int[] size = decodeSeries( pair, "xX", 2 );

        return new Dimension( size[0], size[1] );
    }

    public static int[] decodeSeries( String series, int n )
        throws IllegalArgumentException
    {
        return decodeSeries( series, ",", n );
    }

    public static int[] decodeSeries( String series, String separator, int n )
        throws IllegalArgumentException
    {
        final int[] parsedSeries = new int[n];

        if ( series == null )
        {
            throw new IllegalArgumentException( "Unable to parse series " + series );
        }

        int i = 0;

        for ( String elem : split( series, separator ) )
        {
            elem = elem.trim();

            try
            {
                parsedSeries[i++] = Integer.parseInt( elem );
            }
            catch ( NumberFormatException e )
            {
                throw new IllegalArgumentException( "Unable to parse number " + elem, e );
            }
        }

        return parsedSeries;
    }

    public static int[] decodePair( String pair, int width, int height, Rectangle containerBounds )
        throws IllegalArgumentException
    {
        final int[] coordinates = new int[2];

        if ( pair == null )
        {
            throw new IllegalArgumentException( "Unable to parse coordinates " + pair );
        }

        String[] xy = split( pair, "," );

        xy[0] = xy[0].trim().toLowerCase();

        if ( xy[0].equals( POSITION_CENTER_STR ) )
        {
            coordinates[0] = ( ( containerBounds.width - width ) >> 1 ) + containerBounds.x;
        }
        else if ( xy[0].equals( POSITION_LEFT_STR ) )
        {
            coordinates[0] = 0;
        }
        else if ( xy[0].equals( POSITION_RIGHT_STR ) )
        {
            coordinates[0] = containerBounds.width - width;
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
            coordinates[1] = ( ( containerBounds.height - height ) >> 1 ) + containerBounds.y;
        }
        else if ( xy[1].equals( POSITION_TOP_STR ) )
        {
            coordinates[1] = 0;
        }
        else if ( xy[1].equals( POSITION_BOTTOM_STR ) )
        {
            coordinates[1] = containerBounds.height - height;
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

        for ( String token : split( str, "," ) )
        {
            awtFontStyle |= FontStyle.valueOf( token.toUpperCase() ).getStyle();
        }

        return awtFontStyle;
    }
}
