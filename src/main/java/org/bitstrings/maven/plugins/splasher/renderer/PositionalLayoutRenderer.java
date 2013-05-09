package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.Drawable;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;
import org.bitstrings.maven.plugins.splasher.PositionalLayout;

public class PositionalLayoutRenderer
    extends DrawableRenderer<PositionalLayout>
{
    protected final List<DrawableRenderer<?>> renderers = new ArrayList<DrawableRenderer<?>>();

    protected static final Class<PositionalLayout>[] DEFAULT_MAPPED_DRAWABLES =
                                (Class<PositionalLayout>[]) ClassUtils.toClass( PositionalLayout.class );

    @Override
    public Class<? extends PositionalLayout>[] getDefaultMappedDrawables()
    {
        return DEFAULT_MAPPED_DRAWABLES;
    }

    @Override
    public void init( PositionalLayout drawable, GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        for ( Drawable d : drawable.getDraw() )
        {
            final DrawableRenderer<Drawable> renderer = (DrawableRenderer<Drawable>) context.getDrawableRenderer( d );

            renderer.init( d, context, g );

            renderers.add( renderer );
        }

        width = drawable.getWidth();

        height = drawable.getHeight();

        super.init( drawable, context, g );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
    {
        for ( DrawableRenderer<?> renderer : renderers )
        {
            Graphics2D sg = (Graphics2D) g.create(x, y, width, height );
            try
            {
                renderer.draw( context, sg );
            }
            finally
            {
                sg.dispose();
            }
        }
    }
}
