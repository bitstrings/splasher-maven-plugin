package org.bitstrings.maven.plugins.splasher;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class Resource
{
    private String name;

    public String getName()
    {
        return name;
    }

    public abstract void register( GraphicsContext context )
        throws MojoExecutionException;
}
