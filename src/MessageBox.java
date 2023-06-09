//     BSEvolution - A simple toy to simulate the evolution of a
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

/** A simple message box, which can be used for error messages and
    warrnings */
class MessageBox
    extends Frame
    implements ActionListener
{
    Label error_msg;
    Button ok_button;

    MessageBox (String msg)
    {
	setLayout (new BorderLayout ());
	setTitle ("BSEvolution - Fehler");
	error_msg = new Label (msg, Label.CENTER);
	ok_button = new Button ("Ok");
	ok_button.addActionListener (this);
	add ("Center", error_msg);
	add ("South", ok_button);
	setSize (300, 100);
	setVisible (true);
    }

    public void actionPerformed(ActionEvent e)
    {
	if (e.getActionCommand ().equals ("Ok"))
	    {
		this.dispose ();
	    }
    }
}
