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

import static org.bitstrings.maven.plugins.splasher.DrawingUtil.decodePositionAndSetBounds;
import static org.bitstrings.maven.plugins.splasher.DrawingUtil.getDrawingBounds;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class Drawable
{
    // - parameters --[

    /**
     * The drawable position relative to its container.
     */
    private String position = "0,0";

    /**
     * The transparency value in %, where 0 is completely transparent and 100 is opaque.
     */
    private int alpha = 100;

    /**
     * Set this to false to skip rendering.
     */
    private boolean isVisible = true;

    // ] --

    protected Rectangle dwBounds = new Rectangle();

    protected DrawingContext dwContext;

    public String getPosition()
    {
        return position;
    }

    public void setPosition( String position )
    {
        this.position = position;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setVisible( boolean isVisible )
    {
        this.isVisible = isVisible;
    }

    public void setDrawingContext( DrawingContext context )
    {
        this.dwContext = context;
    }

    public DrawingContext getDrawingContext()
    {
        return dwContext;
    }

    public Rectangle getBounds()
    {
        return dwBounds;
    }

    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        decodePositionAndSetBounds(
                    position,
                    dwBounds.getSize(),
                    getDrawingBounds( g ).getSize(),
                    dwBounds );
    }

    public void draw( Graphics2D g )
    {
        if ( !isVisible )
        {
            return;
        }

        g.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, alpha / 100F) );

        render( g );
    }

    public abstract void render( Graphics2D g );
}
