package org.bitstrings.maven.plugins.splasher;

import java.awt.Graphics2D;

import org.apache.maven.plugin.MojoExecutionException;

public interface Drawable
{
    void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException;

    void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException;
}