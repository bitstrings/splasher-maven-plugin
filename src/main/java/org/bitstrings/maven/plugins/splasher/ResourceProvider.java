package org.bitstrings.maven.plugins.splasher;

import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class ResourceProvider
{
    public abstract Map<String, ?> resourceMap( DrawingContext context )
        throws MojoExecutionException;
}
