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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.apache.maven.plugin.MojoExecutionException;


public class DrawImage
    extends Drawable
{
    // - parameters --[

    /**
     * The image resource name.
     */
    private String imageName;

    // ]--

    protected BufferedImage dwImage;

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName( String imageName )
    {
        this.imageName = imageName;
    }

    public void set( String imageName )
    {
        this.imageName = imageName;
    }

    @Override
    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        dwImage = dwContext.getImage( imageName );

        dwBounds.setSize( dwImage.getWidth(), dwImage.getHeight() );

        super.init( g );
    }

    @Override
    public void render( Graphics2D g )
    {
        g.drawImage( dwImage, dwBounds.x, dwBounds.y, null );
    }
}
