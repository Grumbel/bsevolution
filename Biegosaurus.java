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

/** An animal which wants to eat the stachelophyte, but it can only do
    that if his neg is longer than the stachelophytes spikes */
class Biegosaurus 
    extends Creature
{
    Biegosaurus (int width, int height) {
	super(width, height);
    }

    /** Create a new biegosaurus with randomly mutated height and
        width. */
    Creature breed ()
    {
	Biegosaurus biego = new Biegosaurus (width, height);

	if (Math.random () < 0.5) 
	    biego.width += 1;
	else 
	    biego.width -= 1;

	if (Math.random () < 0.5)
	    biego.height += 1;
	else 
	    biego.height -= 1;

	if (biego.width < 0)
	    biego.width = 0;

	if (biego.height < 0)
	    biego.height = 0;

	return biego;
    }

    // Paint the biego onto a Graphics contex as a line thing
    public void paint (Graphics canvas) {
	canvas.drawLine(290, 290 - height, 290 - width, 290 - height);
	canvas.drawLine(290, 290, 290, 290 - height);
    }

    public String toString ()
    {
	return "[Biegosaurier: width = " + width + " height = " + height + "]";
    }
}

// EOF //
