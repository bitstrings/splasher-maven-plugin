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
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.FlowLayout.Alignment;

public class DrawImagesFromText
    extends Drawable
{
    // - parameters --[

    private String text;

    private String imageNamePrefix;

    private int padding;

    private Alignment alignment = Alignment.HORIZONTAL;

    private String imagePosition = "left,bottom";

    // ]--

    protected FlowLayout drawImagesFromText;

    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    public String getImageNamePrefix()
    {
        return imageNamePrefix;
    }

    public void setImageNamePrefix( String imageNamePrefix )
    {
        this.imageNamePrefix = imageNamePrefix;
    }

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

    public String getImagePosition()
    {
        return imagePosition;
    }

    public void setImagePosition( String imagePosition )
    {
        this.imagePosition = imagePosition;
    }

    @Override
    public void init( Graphics2D g )
        throws MojoExecutionException
    {
        drawImagesFromText = new FlowLayout();

        drawImagesFromText.setDrawingContext( dwContext );
        drawImagesFromText.setPosition( getPosition() );
        drawImagesFromText.setAlignment( alignment );
        drawImagesFromText.setPadding( padding );

        final List<Drawable> drawables = new ArrayList<Drawable>();

        for ( int i = 0; i < text.length(); i++ )
        {
            DrawImage drawImage = new DrawImage();

            drawImage.setImageName( imageNamePrefix + text.charAt( i ) );

            drawImage.setPosition( imagePosition );

            drawables.add( drawImage );
        }

        drawImagesFromText.setDraw( drawables );

        Graphics2D sg = (Graphics2D) g.create();

        try
        {
            drawImagesFromText.init( sg );
        }
        finally
        {
            sg.dispose();
        }

        dwBounds.setBounds( drawImagesFromText.getBounds() );
    }

    @Override
    public void render( Graphics2D g )
    {
        Graphics2D sg = (Graphics2D) g.create();

        try
        {
            drawImagesFromText.draw( sg );
        }
        finally
        {
            sg.dispose();
        }
    }
}
