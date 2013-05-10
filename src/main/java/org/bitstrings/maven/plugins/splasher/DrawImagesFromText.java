package org.bitstrings.maven.plugins.splasher;

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
}
