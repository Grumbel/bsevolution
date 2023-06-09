//     BSEvolution - A simple toy to simulate the evolution of the
//                   biegosaurus and the stachelophyte
//     Copyright (C) 2000  Ingo Ruhnke <grumbel@gmx.de>
//
//     This program is free software; you can redistribute it and/or modify
//     it under the terms of the GNU General Public License as published by
//     the Free Software Foundation; either version 2 of the License, or
//     (at your option) any later version.
//
//     This program is distributed in the hope that it will be useful,
//     but WITHOUT ANY WARRANTY; without even the implied warranty of
//     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//     GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

import java.awt.*;
import java.awt.image.BufferedImage;

/** This class provides a double buffered canvas, so you get more
    flicker free animations, than with a normal Canvas. It works by
    drawing all the images to a buffer first and than blit that buffer
    all at once.

    Warrning: This class might not work very good in every situation
*/
abstract class DoubleBufferedCanvas
    extends Canvas
{
    /// The offscreen image buffer
    private Image buffered_image;

    public DoubleBufferedCanvas () {
	super ();
	System.out.println ("-----------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	System.exit (1);
    }

    public DoubleBufferedCanvas (int w, int h) {
	super.setSize (w, h);
	buffered_image = new BufferedImage (w, h, BufferedImage.TYPE_INT_ARGB);//createImage(w, h);
    }

    public void setSize (int w, int h)
    {
	//System.out.println ("!!!!!!!!!!!!!!!!!!!!!!!!!!!oeuotaehunoh!");
	super.setSize (w, h);
	//System.out.println ("Buffered Image: " + buffered_image);
    }

    public void update (Graphics canvas)
    {
	paint (canvas);
    }

    public void paint (Graphics canvas)
    {
	if (buffered_image != null)
	    {
		buffered_paint (buffered_image.getGraphics ());
		canvas.drawImage (buffered_image, 0, 0, null);
	    }
    }

    public boolean isDoubleBuffered() {
	return true;
    }

    public abstract void buffered_paint (Graphics canvas);
}

// EOF //
