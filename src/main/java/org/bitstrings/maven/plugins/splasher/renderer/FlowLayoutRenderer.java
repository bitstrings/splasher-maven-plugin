package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.Drawable;
import org.bitstrings.maven.plugins.splasher.DrawableMapped;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.FlowLayout;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

@DrawableMapped( FlowLayout.class )
public class FlowLayoutRenderer
    extends DrawableRenderer<FlowLayout>
{
    protected final List<DrawableRenderer<?>> renderers = new ArrayList<DrawableRenderer<?>>();

    protected FlowLayout.Alignment alignment;

    protected int padding;

    public FlowLayout.Alignment getAlignment()
    {
        return alignment;
    }

    public void setAlignment( FlowLayout.Alignment alignment )
    {
        this.alignment = alignment;
    }

    public int getPadding()
    {
        return padding;
    }

    public void setPadding( int padding )
    {
        this.padding = padding;
    }

    @Override
    public void init( FlowLayout drawable, GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        alignment = drawable.getAlignment();

        switch ( alignment )
        {
            case HORIZONTAL:

                for ( Drawable d : drawable.getDraw() )
                {
                    final DrawableRenderer<Drawable> renderer =
                                    (DrawableRenderer<Drawable>) context.createDrawableRenderer( d );

                    renderer.init( d, context, g );

                    width += renderer.width;

                    height = Math.max( renderer.height, height );

                    renderers.add( renderer );
                }

                if ( drawable.getDraw().size() > 1 )
                {
                    width += drawable.getPadding() * ( drawable.getDraw().size() - 1 );
                }

                break;

            case VERTICAL:

                for ( Drawable d : drawable.getDraw() )
                {
                    final DrawableRenderer<Drawable> renderer =
                                    (DrawableRenderer<Drawable>) context.createDrawableRenderer( d );

                    renderer.init( d, context, g );

                    height += renderer.height;

                    width = Math.max( renderer.width, width );

                    renderers.add( renderer );
                }

                if ( drawable.getDraw().size() > 1 )
                {
                    height += drawable.getPadding() * ( drawable.getDraw().size() - 1 );
                }

                break;

            default:
                throw new MojoExecutionException( "Unknown alignment " + drawable.getAlignment() );
        }

        super.init( drawable, context, g );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
    {
        super.draw( context, g );

        int offset = 0;

        switch ( alignment )
        {
            case HORIZONTAL:

                for ( DrawableRenderer<?> renderer : renderers )
                {
                    Graphics2D sg =
                            (Graphics2D) g.create(
                                renderer.x + x + offset, renderer.y + y,
                                g.getDeviceConfiguration().getBounds().width,
                                g.getDeviceConfiguration().getBounds().height );
                    try
                    {
                        renderer.draw( context, sg );

                        offset += renderer.width + padding;
                    }
                    finally
                    {
                        sg.dispose();
                    }
                }

                break;

            case VERTICAL:

                for ( DrawableRenderer<?> renderer : renderers )
                {
                    Graphics2D sg =
                            (Graphics2D) g.create(
                                renderer.x + x, renderer.y + y + offset,
                                g.getDeviceConfiguration().getBounds().width,
                                g.getDeviceConfiguration().getBounds().height );
                    try
                    {
                        renderer.draw( context, sg );

                        offset += renderer.height + padding;
                    }
                    finally
                    {
                        sg.dispose();
                    }
                }

                break;
        }
    }
}
