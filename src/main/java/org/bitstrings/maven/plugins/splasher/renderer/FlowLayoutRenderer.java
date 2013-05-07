package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.Drawable;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.FlowLayout;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

public class FlowLayoutRenderer
    extends DrawableRenderer<FlowLayout>
{
    public FlowLayoutRenderer( FlowLayout flowLayout )
    {
        super( flowLayout );
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        super.init( context, g );

        bounds = new Rectangle();

        switch ( drawable.getAlignment() )
        {
            case HORIZONTAL:

                for ( Drawable d : drawable.getDraw() )
                {
                    final DrawableRenderer<?> renderer = d.createDrawableRenderer();

                    renderer.init( context, g );

                    bounds.width += renderer.getBounds().width;

                    bounds.height = Math.max( renderer.getBounds().height, bounds.height );
                }

                if ( drawable.getDraw().size() > 1 )
                {
                    bounds.width += drawable.getPadding() * ( drawable.getDraw().size() - 1 );
                }

                break;

            case VERTICAL:

                for ( Drawable d : drawable.getDraw() )
                {
                    final DrawableRenderer<?> renderer = d.createDrawableRenderer();

                    renderer.init( context, g );

                    bounds.height += renderer.getBounds().height;

                    bounds.width = Math.max( renderer.getBounds().width, bounds.width );
                }

                if ( drawable.getDraw().size() > 1 )
                {
                    bounds.height += drawable.getPadding() * ( drawable.getDraw().size() - 1 );
                }

                break;

            default:
                throw new MojoExecutionException( "Unknown alignment " + drawable.getAlignment() );
        }
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
            int offset = 0;

            switch ( drawable.getAlignment() )
            {
                case HORIZONTAL:

                    for ( Drawable d : drawable.getDraw() )
                    {
                        final DrawableRenderer<?> renderer = d.createDrawableRenderer();

                        Graphics2D sg =
                                (Graphics2D) g.create(
                                    renderer.x + x + offset, renderer.y + y,
                                    g.getDeviceConfiguration().getBounds().width,
                                    g.getDeviceConfiguration().getBounds().height );
                        try
                        {
                            renderer.draw( context, sg );

                            offset += renderer.getBounds().width + drawable.getPadding();
                        }
                        finally
                        {
                            sg.dispose();
                        }
                    }

                    break;

                case VERTICAL:

                    for ( Drawable d : drawable.getDraw() )
                    {
                        final DrawableRenderer<?> renderer = d.createDrawableRenderer();

                        Graphics2D sg =
                                (Graphics2D) g.create(
                                    renderer.x + x, renderer.y + y + offset,
                                    g.getDeviceConfiguration().getBounds().width,
                                    g.getDeviceConfiguration().getBounds().height );
                        try
                        {
                            renderer.draw( context, sg );

                            offset += renderer.getBounds().height + drawable.getPadding();
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
