/**
 *  Copyright (c) 2013 bitstrings.org - Pino Silvaggio
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.bitstrings.maven.plugins.splasher;

import java.io.File;
import java.util.Collections;
import java.util.Map;

import org.apache.maven.plugin.MojoExecutionException;

public class LoadFont
    extends ResourceProvider
{
    // - parameters --[

    /**
     * Font resource name.
     */
    private String name;

    /*
     * The font file.
     *
     * <p>
     * Java 6 supports ttf and type1.
     * Java 7 supports ttf, otf and type1.
     * </p>
     */
    private File fontFile;

    // ]--

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public File getFontFile()
    {
        return fontFile;
    }

    public void set( File fontFile )
    {
        this.fontFile = fontFile;
    }

    @Override
    public Map<String, ?> resourceMap( DrawingContext context )
        throws MojoExecutionException
    {
        try
        {
            return Collections.singletonMap( name, context.loadFont( fontFile ) );
        }
        catch ( Exception e )
        {
            throw new MojoExecutionException( "Unable to load font " + fontFile + ".", e );
        }
    }
}
