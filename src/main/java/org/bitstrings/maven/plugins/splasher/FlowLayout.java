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
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;

public class FlowLayout
    extends Drawable
{
    public enum Alignment
    {
        HORIZONTAL, VERTICAL;
    }

    private final List<Drawable> draw = new ArrayList<Drawable>();

    private String position = "0,0";

    private int padding;

    private Alignment alignment = Alignment.HORIZONTAL;

    public String getPosition()
    {
        return position;
    }

    public int getPadding()
    {
        return padding;
    }

    @Override
    public void init( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        bounds = new Rectangle();

        for ( Drawable d : draw )
        {
            d.init( context, g );

            bounds.width += d.getBounds().getWidth();

            bounds.height = Math.max( d.getBounds().height, bounds.height );
        }

        if ( draw.size() > 1 )
        {
            bounds.width += padding * ( draw.size() - 1 );
        }

        decodeAndSetXY( position, this, g.getDeviceConfiguration().getBounds() );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        int offset = 0;

        for ( Drawable d : draw )
        {
            Graphics2D sg =
                    (Graphics2D) g.create(
                        d.getX() + x + offset, d.getY() + y,
                        g.getDeviceConfiguration().getBounds().width, g.getDeviceConfiguration().getBounds().height );
            try
            {
                d.draw( context, sg );

                offset += d.getBounds().getWidth() + padding;
            }
            finally
            {
                sg.dispose();
            }
        }
    }
}
