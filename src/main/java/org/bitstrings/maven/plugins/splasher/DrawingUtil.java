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

import static org.apache.commons.lang3.StringUtils.remove;
import static org.codehaus.plexus.util.StringUtils.split;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.codehaus.plexus.util.StringUtils;

import de.congrace.exp4j.ExpressionBuilder;

public final class DrawingUtil
{
    public static final Dimension DIMENSION_1X1 = new Dimension( 1, 1 );

    public static final String POSITION_CENTER_STR = "center";

    public static final String POSITION_LEFT_STR = "left";

    public static final String POSITION_RIGHT_STR = "right";

    public static final String POSITION_TOP_STR = "top";

    public static final String POSITION_BOTTOM_STR = "bottom";

    private static final Map<String, Color> COLOR_MAP = new HashMap<String, Color>();

    static
    {
        Field[] fields = Color.class.getFields();

        for ( Field field : fields )
        {
            if ( Color.class.isAssignableFrom( field.getType() )
                            && Modifier.isStatic( field.getModifiers() )
                            && Modifier.isPublic( field.getModifiers() ) )
            {
                try
                {
                    COLOR_MAP.put( field.getName(), (Color) field.get( Color.class ) );
                }
                catch ( Exception e )
                {
                    // what? this shouldn't happen -> let it go
                }
            }
        }
    }

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

    public static Rectangle getDrawingBounds( Graphics2D g )
    {
        return ObjectUtils.defaultIfNull( g.getClipBounds(), g.getDeviceConfiguration().getBounds() );
    }

    public static void decodePositionAndSetBounds(
                                String position,
                                Dimension dimension,
                                Dimension containerDimension,
                                Rectangle targetBounds )
    {
        decodePositionAndSetBounds( position, dimension, containerDimension, 0, 0, targetBounds );
    }

    public static void decodePositionAndSetBounds(
                                String position,
                                Dimension dimension,
                                Dimension containerDimension,
                                int xOffset, int yOffset,
                                Rectangle targetBounds )
    {
        int[] xy = decodePosition( position, dimension, containerDimension );

        targetBounds.x = ( xy[0] + xOffset );
        targetBounds.y = ( xy[1] + yOffset );
    }

    public static int[] decodePair( String pair )
        throws IllegalArgumentException
    {
        return decodeExpressions( pair, 2 );
    }

    public static Dimension decodeSize( String pair )
                    throws IllegalArgumentException
    {
        final int[] size = decodeExpressions( pair, "xX", 2 );

        return new Dimension( size[0], size[1] );
    }

    public static int[] decodeExpressions( String series, int n )
        throws IllegalArgumentException
    {
        return decodeExpressions( series, ",", n );
    }

    public static int[] decodeExpressions( String expressions, String separator, int n )
        throws IllegalArgumentException
    {
        final int[] parsedSeries = new int[n];

        if ( expressions == null )
        {
            throw new IllegalArgumentException( "Unable to parse expressions " + expressions );
        }

        int i = 0;

        for ( String expression : split( expressions, separator ) )
        {
            expression = expression.trim();

            try
            {
                parsedSeries[i++] = evaluateExpression( expression );
            }
            catch ( NumberFormatException e )
            {
                throw new IllegalArgumentException( "Unable to parse expression " + expression, e );
            }
        }

        return parsedSeries;
    }

    public static int[] decodePosition( String position, Dimension containerDimension )
        throws IllegalArgumentException
    {
        return decodePosition( position, DIMENSION_1X1, containerDimension );
    }

    public static int[] decodePosition( String position, Dimension dimension, Dimension containerDimension )
        throws IllegalArgumentException
    {
        final int[] coordinates = new int[2];

        if ( position == null )
        {
            throw new IllegalArgumentException( "Unable to parse coordinates " + position );
        }

        String expression;

        String[] xy = split( position, "," );

        xy[0] = xy[0].trim().toLowerCase();

        if ( xy[0].startsWith( POSITION_CENTER_STR ) )
        {
            coordinates[0] = ( containerDimension.width - dimension.width ) >> 1;

            expression = remove( xy[0], POSITION_CENTER_STR );
        }
        else if ( xy[0].startsWith( POSITION_LEFT_STR ) )
        {
            coordinates[0] = 0;

            expression = remove( xy[0], POSITION_LEFT_STR );
        }
        else if ( xy[0].startsWith( POSITION_RIGHT_STR ) )
        {
            coordinates[0] = containerDimension.width - dimension.width;

            expression = remove( xy[0], POSITION_RIGHT_STR );
        }
        else
        {
            try
            {
                coordinates[0] = 0;

                expression = xy[0];
            }
            catch ( NumberFormatException e )
            {
                throw new IllegalArgumentException( "Unable to parse x coordinate " + xy[0], e );
            }
        }

        coordinates[0] += evaluateExpression( expression );

        xy[1] = xy[1].trim().toLowerCase();

        if ( xy[1].startsWith( POSITION_CENTER_STR ) )
        {
            coordinates[1] = ( ( containerDimension.height - dimension.height ) >> 1 );

            expression = remove( xy[1], POSITION_CENTER_STR );
        }
        else if ( xy[1].startsWith( POSITION_TOP_STR ) )
        {
            coordinates[1] = 0;

            expression = remove( xy[1], POSITION_TOP_STR );
        }
        else if ( xy[1].startsWith( POSITION_BOTTOM_STR ) )
        {
            coordinates[1] = containerDimension.height - dimension.height;

            expression = remove( xy[1], POSITION_BOTTOM_STR );
        }
        else
        {
            try
            {
                coordinates[1] = 0;

                expression = xy[1];
            }
            catch ( NumberFormatException e )
            {
                throw new IllegalArgumentException( "Unable to parse y coordinate " + xy[1], e );
            }
        }

        coordinates[1] += evaluateExpression( expression );

        return coordinates;
    }

    public static int evaluateExpression( String expression )
        throws IllegalArgumentException
    {
        try
        {
            return
                    StringUtils.isBlank( expression )
                            ? 0
                            : (int) new ExpressionBuilder( expression ).build().calculate();
        }
        catch ( Exception e )
        {
            throw new IllegalArgumentException( "Unable to evaluate expression " + expression );
        }
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

    public static Color decodeColor( String colorStr )
        throws IllegalArgumentException
    {
        Color color = COLOR_MAP.get( colorStr );

        if ( color == null )
        {
            try
            {
                color = Color.decode( colorStr );
            }
            catch ( NumberFormatException e )
            {
                try
                {
                    int[] rgb = decodeExpressions( colorStr, 3 );

                    color = new Color( rgb[0], rgb[1], rgb[2] );
                }
                catch ( IllegalArgumentException e2 )
                {
                    throw new IllegalArgumentException( "Unable to decode color " + colorStr, e );
                }
            }
        }

        return color;
    }
}
