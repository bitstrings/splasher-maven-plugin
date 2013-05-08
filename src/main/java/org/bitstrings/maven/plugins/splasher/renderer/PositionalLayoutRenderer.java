package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.Drawable;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;
import org.bitstrings.maven.plugins.splasher.PositionalLayout;

public class PositionalLayoutRenderer
    extends DrawableRenderer<PositionalLayout>
{
    protected final List<DrawableRenderer<?>> renderers = new ArrayList<DrawableRenderer<?>>();

    public PositionalLayoutRenderer( PositionalLayout layout )
    {
        super( layout );
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        for ( Drawable d : drawable.getDraw() )
        {
            final DrawableRenderer<?> renderer = d.createDrawableRenderer();

            renderer.init( context, g );

            renderers.add( renderer );
        }

        width = drawable.getWidth();

        height = drawable.getHeight();

        super.init( context, g );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
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
