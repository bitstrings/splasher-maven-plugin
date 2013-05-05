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
import java.awt.Rectangle;

import org.apache.maven.plugin.MojoExecutionException;

public abstract class Drawable
{
    protected int x;

    protected int y;

    protected Rectangle bounds;

    public void setX( int x )
    {
        this.x = x;
    }

    public int getX()
    {
        return x;
    }

    public void setY( int y )
    {
        this.y = y;
    }

    public int getY()
    {
        return y;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public abstract void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException;

    public abstract void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException;
}
