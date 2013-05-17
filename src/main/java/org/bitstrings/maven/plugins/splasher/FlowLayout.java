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

import org.apache.maven.plugin.MojoExecutionException;

public class FlowLayout
    extends DrawableContainer
{
    public enum Alignment
    {
        HORIZONTAL, VERTICAL;
    }

    // - parameters --[

    private int padding = 0;

    private Alignment alignment = Alignment.HORIZONTAL;

    // ]--

    public int getPadding()
    {
        return padding;
    }

    public void setPadding( int padding )
    {
        this.padding = padding;
    }

    public Alignment getAlignment()
    {
        return alignment;
    }

    public void setAlignment( Alignment alignment )
    {
        this.alignment = alignment;
    }

    @Override
    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        switch ( alignment )
        {
            case HORIZONTAL:
            {
                for ( Drawable d : getDraw() )
                {
                    d.setDrawingContext( dwContext );

                    Graphics2D sg = (Graphics2D) g.create();

                    try
                    {
                        d.init( sg );
                    }
                    finally
                    {
                        sg.dispose();
                    }

                    dwBounds.width += d.getBounds().width;

                    dwBounds.height = Math.max( d.getBounds().height, dwBounds.height );
                }

                if ( getDraw().size() > 1 )
                {
                    dwBounds.width += padding * ( getDraw().size() - 1 );
                }

                int offset = 0;

                for ( Drawable d : getDraw() )
                {
                    Graphics2D sg =
                            (Graphics2D) g.create(
                                    dwBounds.x + offset, dwBounds.y,
                                    d.getBounds().width, dwBounds.height );

                    try
                    {
                        d.init( sg );
                    }
                    finally
                    {
                        sg.dispose();
                    }

                    offset += d.getBounds().width + padding;
                }

                break;
            }

            case VERTICAL:
            {
                for ( Drawable d : getDraw() )
                {
                    d.setDrawingContext( dwContext );

                    Graphics2D sg = (Graphics2D) g.create();

                    try
                    {
                        d.init( sg );
                    }
                    finally
                    {
                        sg.dispose();
                    }

                    dwBounds.height += d.getBounds().height;

                    dwBounds.width = Math.max( d.getBounds().width, dwBounds.width );
                }

                if ( getDraw().size() > 1 )
                {
                    dwBounds.height += padding * ( getDraw().size() - 1 );
                }

                int offset = 0;

                for ( Drawable d : getDraw() )
                {
                    Graphics2D sg =
                            (Graphics2D) g.create(
                                    dwBounds.x, dwBounds.y + offset,
                                    dwBounds.width, d.getBounds().height );

                    try
                    {
                        d.init( sg );
                    }
                    finally
                    {
                        sg.dispose();
                    }

                    offset += d.getBounds().width + padding;
                }

                break;
            }

            default:
                throw new MojoExecutionException( "Unknown alignment " + alignment );
        }

        super.init( g );
    }

    @Override
    public void render( Graphics2D g )
    {
        int offset = 0;

        switch ( alignment )
        {
            case HORIZONTAL:
            {
                for ( Drawable d : getDraw() )
                {
                    Graphics2D sg =
                            (Graphics2D) g.create(
                                dwBounds.x + offset, dwBounds.y,
                                d.getBounds().width, dwBounds.height );

                    try
                    {
                        d.draw( sg );
                    }
                    finally
                    {
                        sg.dispose();
                    }

                    offset += d.getBounds().width + padding;
                }

                break;
            }

            case VERTICAL:
            {
                for ( Drawable d : getDraw() )
                {
                    Graphics2D sg =
                            (Graphics2D) g.create(
                                dwBounds.x, dwBounds.y + offset,
                                dwBounds.width, d.getBounds().height );

                    try
                    {
                        d.draw( sg );
                    }
                    finally
                    {
                        sg.dispose();
                    }

                    offset += d.getBounds().height + padding;
                }

                break;
            }
        }
    }
}
