package org.bitstrings.maven.plugins;

import java.io.File;

public class Canvas
{
    private File backgroundImageFile;

    private int width;

    private int height;

    private String color;

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public File getBackgroundImageFile()
    {
        return backgroundImageFile;
    }

    public String getColor()
    {
        return color;
    }
}
