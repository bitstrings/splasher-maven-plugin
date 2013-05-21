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

import static org.bitstrings.maven.plugins.splasher.DrawingUtil.decodeSize;

import java.awt.Graphics2D;

import org.apache.maven.plugin.MojoExecutionException;

public class PositionalLayout
    extends DrawableContainer
{
    // - parameters --[

    private String size = "0x0";

    // ]--

    public String getSize()
    {
        return size;
    }

    public void setSize( String size )
    {
        this.size = size;
    }

    @Override
    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        dwBounds.setSize( decodeSize( size ) );

        super.init( g );

        for ( Drawable d : getDraw() )
        {
            d.setDrawingContext( dwContext );

            Graphics2D sg = (Graphics2D) g.create( dwBounds.x, dwBounds.y, dwBounds.width, dwBounds.height );

            try
            {
                d.init( sg );
            }
            finally
            {
                sg.dispose();
            }
        }
    }

    @Override
    public void render( Graphics2D g )
    {
        for ( Drawable d : getDraw() )
        {
            Graphics2D sg = (Graphics2D) g.create( dwBounds.x, dwBounds.y, dwBounds.width, dwBounds.height );

            try
            {
                d.draw( sg );
            }
            finally
            {
                sg.dispose();
            }
        }
    }
}
