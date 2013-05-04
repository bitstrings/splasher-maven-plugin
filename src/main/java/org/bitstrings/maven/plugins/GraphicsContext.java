package org.bitstrings.maven.plugins;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphicsContext
{
    protected final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

    protected final Map<String, String> fontAliasMap = new HashMap<String, String>();

    public GraphicsEnvironment getGraphicsEnvironment()
    {
        return graphicsEnvironment;
    }

    public void loadFont( String alias, File fontFile )
        throws IOException, FontFormatException
    {
        Font font = Font.createFont( Font.TRUETYPE_FONT, fontFile );

        graphicsEnvironment.registerFont( font );

        if ( alias != null )
        {
            fontAliasMap.put( alias, font.getName() );
        }
    }

    public Font getFont( String name, int style, int size )
    {
        final String fontName = fontAliasMap.get( name );

        return new Font( fontName == null ? name : fontName, style, size );
    }
}
