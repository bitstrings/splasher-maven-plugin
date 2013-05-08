package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.Drawable;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.FlowLayout;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

public class FlowLayoutRenderer
    extends DrawableRenderer<FlowLayout>
{
    protected final List<DrawableRenderer<?>> renderers = new ArrayList<DrawableRenderer<?>>();

    public FlowLayoutRenderer( FlowLayout layout )
    {
        super( layout );
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        switch ( drawable.getAlignment() )
        {
            case HORIZONTAL:

                for ( Drawable d : drawable.getDraw() )
                {
                    final DrawableRenderer<?> renderer = d.createDrawableRenderer();

                    renderer.init( context, g );

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
                    final DrawableRenderer<?> renderer = d.createDrawableRenderer();

                    renderer.init( context, g );

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

        super.init( context, g );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
            int offset = 0;

            switch ( drawable.getAlignment() )
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

                            offset += renderer.width + drawable.getPadding();
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

                            offset += renderer.height + drawable.getPadding();
                        }
                        finally
                        {
                            sg.dispose();
                        }
                    }

                    break;

                default:
                    throw new MojoExecutionException( "Unknown alignment " + drawable.getAlignment() );
            }
    }
}
