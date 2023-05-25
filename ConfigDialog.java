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

class ConfigDialog
    extends Frame
    implements ActionListener,
	       WindowListener
{
    private Label     biego_max_label;
    private TextField biego_max_field;

    private Label     biego_start_width_label;
    private TextField biego_start_width_field;

    private Label     biego_start_height_label;
    private TextField biego_start_height_field;

    private Label     stachelo_max_label;
    private TextField stachelo_max_field;

    private Label     stachelo_start_width_label;
    private TextField stachelo_start_width_field;

    private Label     stachelo_start_height_label;
    private TextField stachelo_start_height_field;

    private Panel biego_panel;
    private Panel stego_panel;

    private Button ok_button;
    private Button cancel_button;

    public ConfigDialog ()
    {
	addWindowListener(this);
	setLayout(new BorderLayout ());
	setSize (200, 290);

	setTitle ("BSEvolution - Startwerte");

	Panel panel = new Panel ();

	add (panel, BorderLayout.NORTH);

	Label biego_label = new Label ("..::Biegosaurus ::..", Label.CENTER);
	Label stachelo_label = new Label ("..:: Stachelophyte ::..", Label.CENTER);

	biego_label.setBackground (new Color (0.7f, 0.7f, 0.7f));
	stachelo_label.setBackground (new Color (0.7f, 0.7f, 0.7f));

	biego_max_field = new TextField ("100");
	biego_max_label = new Label ("Maximal Größe: ");

	biego_start_width_label = new Label ("Breite: ");
	biego_start_width_field = new TextField ("100");

	biego_start_height_label = new Label ("Höhe: ");
	biego_start_height_field = new TextField ("100");

	stachelo_max_field = new TextField ("100");
	stachelo_max_label = new Label ("Maximal Größe: ");

	stachelo_start_width_label = new Label ("Breite: ");
	stachelo_start_width_field = new TextField ("100");

	stachelo_start_height_label = new Label ("Höhe: ");
	stachelo_start_height_field = new TextField ("100");

	GridBagLayout gridbag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();

	ok_button = new Button ("Ok");
	cancel_button = new Button ("Abbrechen");

	panel.setLayout (gridbag);

	c.gridwidth = GridBagConstraints.REMAINDER;
	c.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(biego_label, c);
	panel.add (biego_label);

	c.anchor = GridBagConstraints.WEST;
	c.gridwidth = GridBagConstraints.RELATIVE;
	gridbag.setConstraints(biego_max_label, c);
	panel.add (biego_max_label);

	c.gridwidth = GridBagConstraints.REMAINDER;
	//c.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(biego_max_field, c);
	panel.add (biego_max_field);

	c.gridwidth = GridBagConstraints.RELATIVE;
	//c.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(biego_start_width_label, c);
	panel.add (biego_start_width_label);

	c.gridwidth = GridBagConstraints.REMAINDER;
	//c.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(biego_start_width_field, c);
	panel.add (biego_start_width_field);

	c.gridwidth = GridBagConstraints.RELATIVE;
	//c.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(biego_start_height_label, c);
	panel.add (biego_start_height_label);

	c.gridwidth = GridBagConstraints.REMAINDER;
	//	c.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(biego_start_height_field, c);
	panel.add (biego_start_height_field);


	c.gridwidth = GridBagConstraints.REMAINDER;
	gridbag.setConstraints(stachelo_label, c);
	panel.add (stachelo_label);

	c.gridwidth = GridBagConstraints.RELATIVE;
	//	c.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(stachelo_max_label, c);
	panel.add (stachelo_max_label);

	c.gridwidth = GridBagConstraints.REMAINDER;
	//	c.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(stachelo_max_field, c);
	panel.add (stachelo_max_field);

	c.gridwidth = GridBagConstraints.RELATIVE;
//	c.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(stachelo_start_width_label, c);
	panel.add (stachelo_start_width_label);

	c.gridwidth = GridBagConstraints.REMAINDER;
	//	c.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(stachelo_start_width_field, c);
	panel.add (stachelo_start_width_field);

	c.gridwidth = GridBagConstraints.RELATIVE;
	//	c.fill = GridBagConstraints.NONE;
	gridbag.setConstraints(stachelo_start_height_label, c);
	panel.add (stachelo_start_height_label);

	c.gridwidth = GridBagConstraints.REMAINDER;
	//	c.fill = GridBagConstraints.HORIZONTAL;
	gridbag.setConstraints(stachelo_start_height_field, c);
	panel.add (stachelo_start_height_field);

	Panel button_panel = new Panel ();
	add (button_panel, BorderLayout.SOUTH);
	button_panel.add(ok_button);
	button_panel.add(cancel_button);
	ok_button.addActionListener (this);
	cancel_button.addActionListener (this);
    }

    public void setVisible (boolean visible)
    {
	if (visible)
	    get_config ();
	super.setVisible (visible);
    }

    public void get_config ()
    {
	biego_max_field.setText (Integer.toString (Main.main.get_world ().get_bmax()));
	biego_start_width_field.setText (Integer.toString (Main.main.get_world ().get_biego ().get_width ()));
	biego_start_height_field.setText (Integer.toString (Main.main.get_world ().get_biego ().get_height ()));

	stachelo_max_field.setText (Integer.toString (Main.main.get_world ().get_smax()));
	stachelo_start_width_field.setText (Integer.toString (Main.main.get_world ().get_stachelo ().get_width ()));
	stachelo_start_height_field.setText (Integer.toString (Main.main.get_world ().get_stachelo ().get_height ()));
    }

    public void set_config ()
    {
	try {
	    int biego_start_width = Integer.parseInt (biego_start_width_field.getText ());
	    if (biego_start_width >= 0)
		Main.main.get_world ().get_biego ().set_width (biego_start_width);
	    else
		new MessageBox ("Fehler: Biegosaurier: Breite muss positiv sein");
	} catch (NumberFormatException e) {
	    new MessageBox ("Fehler: Biegosaurier: Breite muss eine Zahl sein");
	}

	try {
	    int biego_start_height = Integer.parseInt (biego_start_height_field.getText ());
	    if (biego_start_height >= 0)
		Main.main.get_world ().get_biego ().set_height (biego_start_height);
	    else
		new MessageBox ("Fehler: Biegosaurier: Höhe muss positiv sein");
	} catch (NumberFormatException e) {
	    new MessageBox ("Fehler: Biegosaurier: Höhe muss eine Zahl sein");
	}

	try {
	    int biego_max = Integer.parseInt (biego_max_field.getText ());
	    if (biego_max >= 0)
		Main.main.get_world ().set_bmax (biego_max);
	    else
		new MessageBox ("Fehler: Biegosaurier: Maximale Größe muss positiv sein");
	} catch (NumberFormatException e) {
	    new MessageBox ("Fehler: Biegosaurier: Maximale Größe muss eine Zahl sein");
	}

	try {
	    int stachelo_start_width = Integer.parseInt (stachelo_start_width_field.getText ());
	    if (stachelo_start_width >= 0)
		Main.main.get_world ().get_stachelo ().set_width (stachelo_start_width);
	    else
		new MessageBox ("Fehler: Stachelophyte: Breite muss positiv sein");
	} catch (NumberFormatException e) {
	    new MessageBox ("Fehler: Stachelophyte: Breite muss eine Zahl sein");
	}

	try {
	    int stachelo_start_height = Integer.parseInt (stachelo_start_height_field.getText ());
	    if (stachelo_start_height >= 0)
		Main.main.get_world ().get_stachelo ().set_height (stachelo_start_height);
	    else
		new MessageBox ("Fehler: Stachelophyte: Höhe muss positiv sein");
	} catch (NumberFormatException e) {
	    new MessageBox ("Fehler: Stachelophyte: Höhe muss eine Zahl sein");
	}

	try {
	    int stachelo_max = Integer.parseInt (stachelo_max_field.getText ());
	    if (stachelo_max >= 0)
		Main.main.get_world ().set_smax(stachelo_max);
	    else
		new MessageBox ("Fehler: Stachelophyte: Maximale Größe muss positiv sein");
	} catch (NumberFormatException e) {
	    new MessageBox ("Fehler: Stachelophyte: Maximale Größe muss eine Zahl sein");
	}

	Main.main.set_evo_steps (0);
    }

    public void actionPerformed(ActionEvent e)
    {
	if (e.getActionCommand ().equals ("Ok"))
	    {
		Main.main.config_dialog.setVisible (false);
		set_config ();
		Main.main.redisplay ();
	    }
	else if (e.getActionCommand ().equals ("Abbrechen"))
	    {
		Main.main.config_dialog.setVisible (false);
	    }
    }

    public void windowActivated(WindowEvent e)
    {
    }

    public void windowClosed(WindowEvent e)
    {
	//System.out.println ("window ... closed");
    }

    public void windowClosing(WindowEvent e)
    {
	setVisible (false);
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
	//System.out.println ("window ... opened");
    }
}

// EOF //
