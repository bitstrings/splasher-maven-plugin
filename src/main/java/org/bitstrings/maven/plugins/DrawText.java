package org.bitstrings.maven.plugins;

public class DrawText
{
    private String text;

    private String family;

    private String style;

    private int size = 8;

    private boolean antialias = true;

    private int x = 0;

    private int y = 0;

    private String color = "#000000";

    public String getText()
    {
        return text;
    }

    public String getFamily()
    {
        return family;
    }

    public String getStyle()
    {
        return style;
    }

    public int getSize()
    {
        return size;
    }

    public boolean isAntialias()
    {
        return antialias;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public String getColor()
    {
        return color;
    }
}
