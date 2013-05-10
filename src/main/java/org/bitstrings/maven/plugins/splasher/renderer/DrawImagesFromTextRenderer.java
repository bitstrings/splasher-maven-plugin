package org.bitstrings.maven.plugins.splasher.renderer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.bitstrings.maven.plugins.splasher.DrawImage;
import org.bitstrings.maven.plugins.splasher.DrawImagesFromText;
import org.bitstrings.maven.plugins.splasher.Drawable;
import org.bitstrings.maven.plugins.splasher.DrawableMapped;
import org.bitstrings.maven.plugins.splasher.DrawableRenderer;
import org.bitstrings.maven.plugins.splasher.FlowLayout;
import org.bitstrings.maven.plugins.splasher.GraphicsContext;

@DrawableMapped( DrawImagesFromText.class )
public class DrawImagesFromTextRenderer
    extends DrawableRenderer<DrawImagesFromText>
{
    protected FlowLayoutRenderer flowLayoutRenderer;

    @Override
    public void init( DrawImagesFromText drawable, GraphicsContext context, Graphics2D g )
        throws MojoExecutionException
    {
        final FlowLayout flowLayout = new FlowLayout();

        flowLayout.setPosition( drawable.getPosition() );
        flowLayout.setAlignment( drawable.getAlignment() );
        flowLayout.setPadding( drawable.getPadding() );

        final List<Drawable> drawables = new ArrayList<Drawable>();

        final String text = drawable.getText();

        for ( int i = 0; i < text.length(); i++ )
        {
            DrawImage drawImage = new DrawImage();

            drawImage.setImageName( drawable.getImageNamePrefix() + text.charAt( i ) );

            drawables.add( drawImage );
        }

        flowLayout.setDraw( drawables );

        super.init( drawable, context, g );

        flowLayoutRenderer = (FlowLayoutRenderer) context.createDrawableRenderer( flowLayout );

        flowLayoutRenderer.init( flowLayout, context, g );
    }

    @Override
    public void draw( GraphicsContext context, Graphics2D g )
    {
        super.draw( context, g );

        flowLayoutRenderer.draw( context, g );
    }
}
