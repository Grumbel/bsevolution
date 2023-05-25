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

/** This class represents an animal or plant in the World. An creature
    contains only width and height and some members to duplicate
    itself. */
abstract class Creature
{
    // The width of the creature
    protected int width;

    // The height of the creature
    protected int height;

    // Construct an new creature with the given width and height
    public Creature (int width, int height) {
	this.width = width;
	this.height  = height;
    }
    
    public int get_width() {
	return width;
    }

    public int get_height() {
	return height;
    }

    public void set_width (int i) {
	if (i >= 0)
	    width = i;
    }

    public void set_height (int i) {
	if (i >= 0)
	    height = i;
    }

    // Create a new creature of the same kind, but with randomly
    // mutated genes
    abstract Creature breed ();

    // Paint the creature to Graphics contex
    abstract void  paint (Graphics canvas);
}

// EOF //
