package org.bitstrings.maven.plugins.splasher;

import org.apache.maven.plugin.MojoExecutionException;

public interface ComponentInitLate
{
    void init()
        throws MojoExecutionException;
}
