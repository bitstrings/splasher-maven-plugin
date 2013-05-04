package org.bitstrings.maven.plugins;

import org.apache.maven.plugin.MojoExecutionException;

public interface LateInitComponent
{
    void init()
        throws MojoExecutionException;
}
