package org.bitstrings.maven.plugins.splasher;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class Resource
{
    private String alias;

    public String getAlias()
    {
        return alias;
    }

    public abstract void register( GraphicsContext context )
        throws MojoExecutionException;
}
