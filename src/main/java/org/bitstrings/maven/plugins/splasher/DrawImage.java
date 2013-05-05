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

import static org.bitstrings.maven.plugins.splasher.GraphicsUtil.*;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;

public class DrawImage
    extends Drawable
{
    // - parameters --[

    private File imageFile;

    private String position = "0,0";

    // ]--

    protected BufferedImage awtImage;

    public File getImageFile()
    {
        return imageFile;
    }

    public String getPosition()
    {
        return position;
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        try
        {
            awtImage = context.loadImage( imageFile );
        }
        catch ( IOException e )
        {
            throw new MojoExecutionException( "Unable to read image file " + imageFile + ".", e );
        }

        bounds = new Rectangle( awtImage.getWidth(), awtImage.getHeight() );

        decodeAndSetXY( position, this, g.getDeviceConfiguration().getBounds() );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        g.drawImage( awtImage, x, y, null );
    }
}
