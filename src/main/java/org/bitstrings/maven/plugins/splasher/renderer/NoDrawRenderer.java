package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.Drawable;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

public class NoDrawRenderer
    extends DrawableRenderer<Drawable>
{
    public NoDrawRenderer( Drawable drawable )
    {
        super( drawable );
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException {}

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException {}
}
