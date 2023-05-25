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

/** The stachelophyte is a plant, which has long spikes to protect
    itself from the biegosaurus. */
public class Stachelophyte
    extends Creature
{
    /// Construct a stachelophyte with the given width and heigth
    Stachelophyte (int width, int height)
    {
	super(width, height);
    }

    public Creature breed ()
    {
	Stachelophyte stachelo = new Stachelophyte (width, height);

	if (Math.random () < 0.5)
	    stachelo.width += 1;
	else
	    stachelo.width -= 1;

	if (Math.random () < 0.5)
	    stachelo.height += 1;
	else
	    stachelo.height -= 1;

	if (stachelo.width < 0)
	    stachelo.width = 0;

	if (stachelo.height < 0)
	    stachelo.height = 0;

	return stachelo;
    }

    public void paint (Graphics canvas) {
	canvas.drawLine(10, 290, 10, 290 - height);
	canvas.drawLine(10, 290, 10 + width, 290);
    }

    public String toString () {
	return "Stachelophyte: width = " + width + " height = " + height;
    }
}

// EOF //
