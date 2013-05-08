package org.bitstrings.maven.plugins.splasher;

import java.util.ArrayList;
import java.util.List;

import org.bitstrings.maven.plugins.splasher.FlowLayout.Alignment;

public class DrawImagesFromText
    extends Drawable
{
    // - parameters --[

    private String text;

    private String imageNamePrefix;

    private int padding;

    private Alignment alignment = Alignment.HORIZONTAL;

    // ]--

    public String getText()
    {
        return text;
    }

    public String getImageNamePrefix()
    {
        return imageNamePrefix;
    }

    public int getPadding()
    {
        return padding;
    }

    public Alignment getAlignment()
    {
        return alignment;
    }

    @Override
    public DrawableRenderer<?> createDrawableRenderer()
    {
        FlowLayout flowLayout = new FlowLayout();

        flowLayout.setPosition( getPosition() );
        flowLayout.setAlignment( alignment );
        flowLayout.setPadding( padding );

        List<Drawable> drawables = new ArrayList<Drawable>();

        for ( int i = 0; i < text.length(); i++ )
        {
            DrawImage drawImage = new DrawImage();

            drawImage.setImageName( imageNamePrefix + text.charAt( i ) );

            drawables.add( drawImage );
        }

        flowLayout.setDraw( drawables );

        return flowLayout.createDrawableRenderer();
    }
}
