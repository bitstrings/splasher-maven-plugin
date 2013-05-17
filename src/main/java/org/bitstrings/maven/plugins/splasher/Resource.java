package org.bitstrings.maven.plugins.splasher;

import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class Resource
{
    public abstract Map<String, ?> resources( DrawingContext context )
        throws MojoExecutionException;
}
