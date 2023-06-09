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


/** The world of one biegosaurus and one stachelophyte. The creatures
    breed () here and it will be selected the creature which is the
    most successfull in an evolution loop. */
class World
{
    /** The maximal width + height of the stachelophyte, before the
	punish value takes action */
    public int smax = 100;

    /** The maximal width + height of the biegosaurus, before the
	punish value takes action */
    public int bmax = 100;

    public Biegosaurus biego;
    public Stachelophyte stachelo;

    public boolean stachelo_eaten;

    /** Create a new world with one biegosaurus and one stachelophyte */
    public World ()
    {
	biego    = new Biegosaurus (50, 50);
	stachelo = new Stachelophyte (50, 50);
    }

    public Biegosaurus get_biego () {
	return biego;
    }

    public Stachelophyte get_stachelo () {
	return stachelo;
    }

    public boolean get_stachelo_eaten () {
	return stachelo_eaten;
    }

    /// Process one step of the evolution process
    public void update ()
    {
	Biegosaurus biego2 = (Biegosaurus)biego.breed ();
	Biegosaurus biego3 = (Biegosaurus)biego.breed ();

	Stachelophyte stachelo2 = (Stachelophyte)stachelo.breed ();
	Stachelophyte stachelo3 = (Stachelophyte)stachelo.breed ();

	// We select the best biegosaurus and the best stachelophyte
	biego = select (biego, biego2, biego3);
	stachelo = select (stachelo, stachelo2, stachelo3);

	// We set the winner of this round
	if  (biego.get_width () > stachelo.get_width ()
	     && biego.get_height () >= stachelo.get_height ())
	    stachelo_eaten = true;
	else
	    stachelo_eaten = false;
    }

    /** Select the best out of three biegosaurus */
    private Biegosaurus select (Biegosaurus biego1,
				 Biegosaurus biego2,
				 Biegosaurus biego3)
    {
	Biegosaurus best_biego;

	if (biego_abstand (stachelo, biego1) < biego_abstand (stachelo, biego2))
	    best_biego = biego1;
	else
	    best_biego = biego2;

	if (biego_abstand (stachelo, biego3) < biego_abstand (stachelo, best_biego))
	    best_biego = biego3;

	return best_biego;
    }

    /** Select the best out of three stachelophytes */
    private Stachelophyte select (Stachelophyte stachelo1,
				  Stachelophyte stachelo2,
				  Stachelophyte stachelo3)
    {
	Stachelophyte best_stachelo;

	if (stachelo_abstand (stachelo1, biego) > stachelo_abstand (stachelo2, biego))
	    best_stachelo = stachelo1;
	else
	    best_stachelo = stachelo2;

	if (stachelo_abstand (stachelo3, biego) > stachelo_abstand (best_stachelo, biego))
	    best_stachelo = stachelo3;

	return best_stachelo;
    }

    /** Calculate the distance between a stachelo and a biego. The
        distance is calculated by width and height and a punish value
        if the size goes over the max values */
    private int biego_abstand (Stachelophyte stachelo, Biegosaurus biego)
    {
	int abstand;
	int strafe = 0;

	if ((biego.get_width () + biego.get_height ()) > bmax
	    || biego.get_width () <= 0
	    || biego.get_height () <= 0)
	    {
		strafe = 10000;
	    }

	if (biego.get_width () <  stachelo.get_width ()) {
	    abstand =
		(stachelo.get_width () - biego.get_width ()) * (stachelo.get_width () - biego.get_width ())
		+ (stachelo.get_height () - biego.get_height ()) * (stachelo.get_height () - biego.get_height ())
		+ strafe;
	} else {
	    abstand = (stachelo.get_height () - biego.get_height ())
		*  (stachelo.get_height () - biego.get_height ()) + strafe;
	}

	return abstand;
    }

    /** Calculate the distance between a stachelo and a biego. The
        distance is calculated by width and height and a punish value
        if the size goes over the max values */
    private int stachelo_abstand (Stachelophyte stachelo, Biegosaurus biego)
    {
	int abstand;
	int strafe = 0;

	if ((stachelo.get_width () + stachelo.get_height ()) > smax
	    || stachelo.get_width () <= 0
	    || stachelo.get_height () <= 0)
	    {
		strafe = -10000;
	    }

	if (biego.get_width () <  stachelo.get_width ()) {
	    abstand =
		(stachelo.get_width () - biego.get_width ()) * (stachelo.get_width () - biego.get_width ())
		+ (stachelo.get_height () - biego.get_height ()) * (stachelo.get_height () - biego.get_height ())
		+ strafe;
	} else {
	    abstand = (stachelo.get_height () - biego.get_height ())
		*  (stachelo.get_height () - biego.get_height ()) + strafe;
	}

	return abstand;
    }

    /** Paint the biego and the stachelo to a graphics
        context. Obsolete, since it uses only two lines to represent
        each creature */
    public void paint (Graphics canvas) {
	biego.paint (canvas);
	stachelo.paint (canvas);
    }

    /** Print the values of the biego and the stachelo, for debugging
        only */
    public void print () {
	System.out.println("--------------------------");
	System.out.println(biego);
	System.out.println(stachelo);
	System.out.println("--------------------------");
    }

    public String toString () {
	return biego.toString () + "\n" + stachelo.toString ();
    }

    public int get_smax() {
	return smax;
    }

    public int get_bmax() {
	return bmax;
    }

    public void set_smax(int i) {
	smax = i;
    }

    public void set_bmax(int i) {
	bmax = i;
    }
}

// EOF //
