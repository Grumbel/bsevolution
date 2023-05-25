import java.awt.*;
import java.awt.event.*;

/**
   A DoubleBuffer class. Maintains an offscreen
   image to allow for flicker-free animation.

 @author Stuart Reynolds ... 1999
 */
public class DoubleBuffer
    extends Canvas
{
    private Image offscreen;

    public DoubleBuffer()
    {
	super();
    }


    private void makeOffscreenImage()
    {
	//Get rid of old image now.
	//Don't wait for garbage collection
	if (offscreen!=null)
	{
            // offscreen.getGraphics().finalize();
	    offscreen=null;
	    System.gc();
	}
	if (getSize().width>0 && getSize().height>0)
	    offscreen = createImage(getSize().width,
				    getSize().height);
    }


    /**
       Get the graphics for the offscreen buffer image.
       @return The Graphics of the offscreen image or
       null if the image hasn't yet been created.
     */
    public Graphics getOffscreenGraphics()
    {
	if (offscreen==null)
	    makeOffscreenImage();

	if (offscreen!=null)
	    return offscreen.getGraphics();
	else
	    return null;
    }

    /**
       Get the offscreen buffer image.
       @return The offscreen image or
       null if the image hasn't yet been created.
     */
    public Image getOffscreenImage()
    {
	if (offscreen==null)
	    makeOffscreenImage();

	return offscreen;
    }

    /** Display the offscreen buffer on the Canvas right now. */
    public void copyToForeground()
    {
	if (offscreen==null)
	    return;

	Graphics mygraphics = getGraphics();
	if (mygraphics==null)
	    return;

	mygraphics.drawImage(offscreen, 0,0, this);
    }
}


