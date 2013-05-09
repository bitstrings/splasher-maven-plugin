package org.bitstrings.maven.plugins.splasher.renderer;

import static org.bitstrings.maven.plugins.splasher.GraphicsUtil.decodeFontStyle;
import static org.bitstrings.maven.plugins.splasher.GraphicsUtil.decodePositionAndSetXY;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import org.apache.commons.lang3.ClassUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.DrawText;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

public class DrawTextRenderer
    extends DrawableRenderer<DrawText>
{
    protected String text;

    protected Color textColor;

    protected Font font;

    protected int fontStyle;

    protected int fontSize;

    protected Object fontAntialias;

    protected static final Class<DrawText>[] DEFAULT_MAPPED_DRAWABLES =
                                (Class<DrawText>[]) ClassUtils.toClass( DrawText.class );

    @Override
    public Class<? extends DrawText>[] getDefaultMappedDrawables()
    {
        return DEFAULT_MAPPED_DRAWABLES;
    }

    @Override
    public void init( DrawText drawable, GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        text = drawable.getText();

        if ( drawable.getFontStyle() != null )
        {
            try
            {
                fontStyle = decodeFontStyle( drawable.getFontStyle() );
            }
            catch ( IllegalArgumentException e )
            {
                throw new MojoExecutionException( "Illegal font style " + drawable.getFontStyle() + ".", e );
            }
        }

        font = context.getFont( drawable.getFontName(), fontStyle, drawable.getFontSize() );

        FontMetrics metrics = g.getFontMetrics( font );

        Rectangle2D textBounds = metrics.getStringBounds( drawable.getText(), g );

        width = (int) textBounds.getWidth();

        height = (int) textBounds.getHeight();

        decodePositionAndSetXY(
            drawable.getPosition(),
            this,
            g.getDeviceConfiguration().getBounds(),
            0, ( drawable.isUseBaseline() ? 0 : metrics.getAscent() ) );

        try
        {
            textColor = Color.decode( drawable.getTextColor() );
        }
        catch ( NumberFormatException e )
        {
            throw new MojoExecutionException( "Unable to decode color " + drawable.getTextColor() + ".", e );
        }

        fontAntialias = drawable.getFontAntialias().getType();
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
    {
        g.setColor( textColor );

        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, fontAntialias );

        g.setFont( font );

        g.drawString( text, x, y );
    }
}
