package org.bitstrings.maven.plugins.splasher.renderer;

import static org.bitstrings.maven.plugins.splasher.GraphicsUtil.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.DrawText;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

public class DrawTextRenderer
    extends DrawableRenderer<DrawText>
{
    protected CharSequence text;

    protected Font font;

    protected int style;

    protected int size;

    public DrawTextRenderer( DrawText drawText )
    {
        super( drawText );
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        if ( drawable.getFontStyle() != null )
        {
            try
            {
                style = decodeFontStyle( drawable.getFontStyle() );
            }
            catch ( IllegalArgumentException e )
            {
                throw new MojoExecutionException( "Illegal font style " + drawable.getFontStyle() + ".", e );
            }
        }

        font = context.getFont( drawable.getFontName(), style, drawable.getFontSize() );

        FontMetrics metrics = g.getFontMetrics( font );

        Rectangle2D textBounds = metrics.getStringBounds( drawable.getText(), g );

        width = (int) textBounds.getWidth();

        width = (int) textBounds.getHeight();

        decodePositionAndSetXY(
            drawable.getPosition(),
            this,
            g.getDeviceConfiguration().getBounds(),
            0, ( drawable.isUseBaseline() ? 0 : metrics.getAscent() ) );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        try
        {
            g.setColor( Color.decode( drawable.getTextColor() ) );
        }
        catch ( NumberFormatException e )
        {
            throw new MojoExecutionException( "Unable to decode color " + drawable.getTextColor() + ".", e );
        }

        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, drawable.getFontAntialias().getType() );

        g.setFont( font );

        g.drawString( drawable.getText(), x, y );
    }
}
