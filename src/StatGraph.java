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
import java.awt.event.*;
import java.util.Vector;

/** Display a simple graph which shows the width and height progress
    of the creatures */
class StatGraph
    extends Frame
    implements WindowListener
{
    int max_size = 500;
    StatGraphCanvas canvas;

    StatGraph ()
    {
	addWindowListener (this);
	setTitle ("BSEvolution - Höhe/Breite Graph");
	canvas = new StatGraphCanvas (max_size, 100);
	setSize (max_size, 100);
	add (canvas);
	//setVisible (true);
    }

    void update ()
    {
	canvas.update ();
    }

    public void windowActivated(WindowEvent e)
    {
    }

    public void windowClosed(WindowEvent e)
    {
    }

    public  void windowClosing(WindowEvent e)
    {
	System.out.println ("Window close");
	this.setVisible (false);
	Main.main.get_main_window ().get_statgraph_checkbox ().setState (false);
    }

    public void windowDeactivated(WindowEvent e)
    {
    }

    public void windowDeiconified(WindowEvent e)
    {
    }

    public void windowIconified(WindowEvent e)
    {
    }

    public void windowOpened(WindowEvent e)
    {
    }
}

/** This is the canvas on which the progress graph is drawn */
class StatGraphCanvas
    extends DoubleBufferedCanvas
{
    Vector biego_widths;
    Vector biego_heights;
    Vector stachelo_widths;
    Vector stachelo_heights;
    int steps;
    int max_size;

    int repaint_steps;
    int refresh_rate;

    int width;
    int height;

    public StatGraphCanvas (int w, int h)
    {
	super (w, h);
	width = w;
	height = h;
	setSize (w, h);
	biego_widths     = new Vector ();
	biego_heights    = new Vector ();
	stachelo_widths  = new Vector ();
	stachelo_heights = new Vector ();
	steps = 0;
	max_size = w;
    }

    public void draw_vector (Graphics canvas, int scale, Vector vector, Color color)
    {
	if (vector.size() < 2)
	    return;

	if (color != null)
	    canvas.setColor (color);

	for (int i=1; i < vector.size (); i++)
	    canvas.drawLine (i - 1,
			     ((Integer)vector.elementAt (i-1)).intValue () * height / scale,
			     i,
			     ((Integer)vector.elementAt (i)).intValue () * height / scale);
    }

    public void buffered_paint (Graphics canvas)
    {
	canvas.setColor (new Color (0.0f, 0.0f, 0.0f));
	canvas.fillRect (0, 0, width, height);

	draw_vector (canvas, Main.main.get_world ().get_bmax (), biego_widths, new Color (0.5f, 0.5f, 1.0f));
	draw_vector (canvas, Main.main.get_world ().get_bmax (), biego_heights, new Color (1.0f, 0.5f, 0.5f));
	draw_vector (canvas, Main.main.get_world ().get_smax (), stachelo_widths, new Color (1.0f, 1.0f, 1.0f));
	draw_vector (canvas, Main.main.get_world ().get_smax (), stachelo_heights, new Color (0.0f, 1.0f, 0.0f));
    }

    public void update ()
    {
	// when the graph window is full, we reset it and start from
	// the beginning
	if (steps++ >= max_size)
	    {
		biego_widths.removeAllElements();
		biego_heights.removeAllElements();
		stachelo_widths.removeAllElements();
		stachelo_heights.removeAllElements();
		steps = 0;
	    }

	biego_widths.add (Main.main.get_world ().get_biego ().get_width ());
	biego_heights.add (Main.main.get_world ().get_biego ().get_height ());
	stachelo_widths.add (Main.main.get_world ().get_stachelo ().get_width ());
	stachelo_heights.add (Main.main.get_world ().get_stachelo ().get_height ());

	if (repaint_steps++ >= refresh_rate)
	    {
		repaint_steps = 0;
		repaint ();
	    }
    }
}

// EOF //
