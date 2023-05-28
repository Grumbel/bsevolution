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

import java.net.URL;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.*;

/** The main window of the application */
class MainWindow
    implements KeyListener,
	       //ItemListener,
	       ActionListener,
	       WindowListener
{
    //private Label status;
    private Scrollbar update_time;
    private Scrollbar refresh_rate;
    private WorldDisplay canvas;
    private ValueDisplay value_display;
    private Frame frame;

    public Label update_time_label;
    public Label refresh_rate_label;
    public Label evo_steps;
    public Panel button_panel;

    private Label current_winner;
    private Checkbox stat_graph_checkbox;
    private Button restart_button;
    private Button startstop_button;
    private Button step_button;

    public StatGraphCanvas statgraph;

    public Checkbox get_statgraph_checkbox ()
    {
	return stat_graph_checkbox;
    }
    /*
    public void itemStateChanged(ItemEvent e)
    {
	if (e.getStateChange() == 1)
	    Main.main.stat_graph.setVisible (true);
	else
	    Main.main.stat_graph.setVisible (false);
    }
    */
    public void actionPerformed(ActionEvent e) {
	if (e.getActionCommand ().equals ("Startwerte...")) {
	    //System.out.println ("Optionen");
	    Main.main.stop_world ();
	    Main.main.config_dialog.setVisible (true);
	} else if (e.getActionCommand ().equals ("Schrittweise")) {
	    Main.main.stop_world ();
	    startstop_button.setLabel ("Start");
	    Main.main.do_a_step ();
	    Main.main.update ();
	} else if (e.getActionCommand ().equals ("Start")) {
	    //System.out.println ("Start");
	    //status.setText ("Status: läuft");
	    startstop_button.setLabel ("Stop");
	    Main.main.start_world ();
	} else if (e.getActionCommand ().equals ("Stop")) {
	    //System.out.println ("Stop");
	    startstop_button.setLabel ("Start");
	    //status.setText ("Status: gestoppt");
	    Main.main.stop_world ();
	    Main.main.redisplay ();
	}
    }

    public void setVisible (boolean show)
    {
	//System.out.println ("Gui Pointer: " + frame);
	frame.setVisible (show);
	if (show)
	    canvas.repaint ();
    }

    // Once this is called it will never return
    public MainWindow () {
	frame = new Frame ();
	Panel top_panel = new Panel ();
	Panel left_panel = new Panel ();
	Panel right_panel = new Panel ();
	Panel bottom_panel = new Panel ();

	button_panel = new Panel();

	frame.addWindowListener(this);
	frame.setTitle ("BSEvolution");

	statgraph = new StatGraphCanvas (145, 250);

	right_panel.setLayout(new BorderLayout());
	right_panel.add ("North", button_panel);
	right_panel.add ("South", statgraph);

	button_panel.setLayout(new GridLayout(8, 1));

	frame.setLayout(new BorderLayout ());
	// Gui Elements
	canvas = new WorldDisplay (500, 450);
	frame.add ("West", canvas);

	value_display = new ValueDisplay ();
	frame.add ("South", value_display);
	frame.add ("East", right_panel);

	// Buttons
	//status         = new Label ("Status: gestoppt");
	restart_button = new Button ("Startwerte...");
	startstop_button   = new Button ("Start");
	step_button    = new Button ("Schrittweise");

	restart_button.addActionListener (this);
	startstop_button.addActionListener (this);
	step_button.addActionListener (this);

	button_panel.add (restart_button);
	//button_panel.add (status);
	button_panel.add (startstop_button);
	button_panel.add (step_button);

	update_time = new Scrollbar(Scrollbar.HORIZONTAL, Main.main.get_update_time (), 250, 1, 2250);
	// new TextField (Integer.toString(Main.main.get_update_time ()), 10);
	update_time_label = new Label ("Zeit/Schritt: " + Main.main.get_update_time () + "msec");
	//update_time.addKeyListener (this);
	update_time.addAdjustmentListener (new UpdateTimeListener ());
	button_panel.add (update_time_label);
	button_panel.add (update_time);

	refresh_rate = new Scrollbar(Scrollbar.HORIZONTAL, (Main.main.get_refresh_rate () + 1), 10, 1, 60);
	//= new TextField (Integer.toString(Main.main.get_refresh_rate ()), 10);
	refresh_rate_label = new Label ("Wiederholfrequenz: 1/" + (Main.main.get_refresh_rate () + 1));
	refresh_rate.addAdjustmentListener (new RefreshRateListener ());
	button_panel.add (refresh_rate_label);
	button_panel.add (refresh_rate);

	//stat_graph_checkbox = new Checkbox ("Show Graph", false);
	//stat_graph_checkbox.addItemListener (this);
	//button_panel.add (stat_graph_checkbox);

	button_panel.add (evo_steps = new Label ("Schritte: 0"));
	//button_panel.add (current_winner = new Label (""));

	//update_time = new TextField (Integer.toString(Main.main.get_update_time ()), 10);
	frame.setSize (660, 505);
    }

    public void update_canvas()
    {
	//System.out.println ("updateing canvas");
	canvas.repaint();
    }

    public void update_value_display()
    {
	value_display.update ();
	evo_steps.setText ("Schritte: " + Main.main.get_evo_steps ());
	//button_panel.repaint ();
	//current_winner.setText ("CurrentWinner: " + Main.main.get_world ().get_current_winner ());
    }

    public boolean isDoubleBuffered () {
	System.out.println ("Call double buffer------------------------");
	return true;
    }
    public void keyPressed (KeyEvent e) {
	if (e.getKeyCode () == e.VK_ENTER) {
	    try {
		// grumbel Main.main.set_update_time (Integer.parseInt (update_time.getText ()));
		Main.main.update ();
	    } catch (NumberFormatException a) {
		System.out.println("not a number");
	    }

	    // System.out.println ("Enter pressed");
	}
    }

    public void keyReleased (KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
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
	//System.out.println ("window ... closing");
	System.exit (0);
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
	//	System.out.println ("window ... opened");
    }
}


/** This canvas displays the data of the world class in a nice way */
class WorldDisplay
    extends DoubleBufferedCanvas
    implements ImageObserver
{
    // Biego elements
    Image hals_v;
    Image hals_h;
    Image kopf;
    Image kopfeat;
    Image winkel;
    Image biego;
    Image schwanz;
    Image schwanzende;
    Image biego_shadow;

    // Stachelo elements
    Image green;
    Image greeneat;
    Image baum;
    Image stacheln;
    Image stachelo_shadow;

    Image background;

  public Image createImage(String name)
  {
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    URL url = getClass().getResource("/data/" + name);
    return toolkit.createImage(url);
  }

    public WorldDisplay (int w, int h)
    {
	super (w, h);
	hals_v = createImage("images/hals_v.gif");
	hals_h = createImage("images/hals_h.gif");
	kopf = createImage("images/kopf.gif");
	kopfeat = createImage("images/kopfeat.gif");
	baum = createImage("images/baum.gif");
	green = createImage("images/green.gif");
	greeneat = createImage("images/greeneat.gif");
	stacheln = createImage("images/stacheln.gif");
	stachelo_shadow = createImage("images/stacheloshadow.gif");
	winkel = createImage("images/winkel.gif");
	biego = createImage("images/biego.gif");
	schwanz = createImage("images/schwanz.gif");
	schwanzende = createImage("images/schwanzende.gif");
	biego = createImage("images/biego.gif");
	biego_shadow = createImage("images/biegoshadow.gif");
	background = createImage("images/background.gif");

	this.prepareImage(hals_v, this);
	this.prepareImage(hals_h, this);
	this.prepareImage(kopf, this);
	this.prepareImage(kopfeat, this);
	this.prepareImage(baum, this);
	this.prepareImage(green, this);
	this.prepareImage(greeneat, this);
	this.prepareImage(stacheln, this);
	this.prepareImage(stachelo_shadow, this);
	this.prepareImage(winkel, this);
	this.prepareImage(biego, this);
	this.prepareImage(schwanz, this);
	this.prepareImage(schwanzende, this);
	this.prepareImage(biego, this);
	this.prepareImage(biego_shadow, this);
	this.prepareImage(background, this);
    }

    /** Paint the world */
    public void buffered_paint (Graphics canvas)
    {
	canvas.drawImage (background, 0,0, this);

	// We calculate of much space we need between the two
	// creatures to look good
	int distance = Math.max(Main.main.get_world ().get_stachelo ().get_width (),
				Main.main.get_world ().get_biego ().get_width ());

	paint_stachelo (canvas, 170 - distance/2, 425,
			Main.main.get_world ().get_stachelo ().get_width (),
			Main.main.get_world ().get_stachelo ().get_height ());

	paint_biego (canvas, 305 + distance/2, 430 - 162,
		     Main.main.get_world ().get_biego ().get_width (),
		     Main.main.get_world ().get_biego ().get_height ());
    }

    /** Paint the stachelo image to the given Graphics object */
    public void paint_stachelo (Graphics canvas,
				int x_pos, int y_pos,
				int width, int height)
    {
	//width = (int) (Math.random () *  200) + 40;
	//height = (int) (Math.random () * 200) + 50;

	// We need to change the height a bit so that it fits onto the
	// screen
	height += 192;
	width  += 90;

	canvas.drawImage (stachelo_shadow, x_pos - 125, y_pos - 20, this);

	int num_tiles = (int)((height / 42.0) + 1.0);
	float tile_height = (float) height / num_tiles + 1;

	for (int i = 1; i <= num_tiles; i++)
	    canvas.drawImage (baum, x_pos,y_pos - (int) (i * tile_height),
			      28, (int) tile_height + 1,
			      this);

	if (Main.main.get_world ().get_stachelo_eaten ())
	    canvas.drawImage (greeneat, x_pos - 32, y_pos - 30 - height, this);
	else
	    canvas.drawImage (green, x_pos - 32, y_pos - 30 - height, this);

	// 108 90
	canvas.drawImage (stacheln, x_pos - width + 20, y_pos - 95 + 31,
			  width * 2, 70, //101,
			  this);
    }

    /** Paint the biego image to the given Graphics object */
    public void paint_biego (Graphics canvas,
			     int x_pos, int y_pos,
			     int width, int height)
    {
	// We add a bit to the real width/height to avoid ugly tiles
	width  += 20;
	height += 20;

	canvas.drawImage (biego_shadow, x_pos - 100, y_pos + 115, this);

	canvas.drawImage (biego, x_pos - 38, y_pos, this);

	int num_tiles = (int)((width / 50.0) + 1.0);
	float tile_width = (float) width / num_tiles + 1;
	for (int i = 0; i < num_tiles ; i++)
	    canvas.drawImage (schwanz,
			      x_pos + 126 + (int) (i*tile_width),
			      y_pos + 137,
			      (int) tile_width + 1, 22,
			      this);

	canvas.drawImage (schwanzende, x_pos + 126 + width, y_pos + 137, this);

	num_tiles = (int)((height / 37.0) + 1.0);
	if (num_tiles == 0) {
	    //System.out.println ("--- numtiles: height= " + height);
	    System.exit (0);
	}
	float tile_height = (float) height / num_tiles + 1;

	for (int i = 1; i <= num_tiles ; i++)
	    canvas.drawImage (hals_v,
			      x_pos,
			      y_pos - (int)(tile_height * i),
			      28, (int) tile_height + 1,
			      this);

	canvas.drawImage (winkel, x_pos - 4, y_pos - height - 34, this);

	num_tiles = (int)((width / 37.0) + 1.0);

	if (num_tiles == 0) {
	    System.out.println ("--- numtiles: width = " + width);
	    System.exit (0);
	}

	tile_width = (float)width / num_tiles + 1;

	for (int i = 1; i <= num_tiles ; i++)
	    canvas.drawImage (hals_h,
			      x_pos - (int)(i * tile_width),
			      y_pos - height - 34,
			      (int) tile_width + 1, 28,
			      this);

	if (Main.main.get_world ().get_stachelo_eaten ())
	    canvas.drawImage (kopfeat, x_pos - width - 62,
			      y_pos - height - 36, this);
	else
	    canvas.drawImage (kopf, x_pos - width - 62,
			      y_pos - height - 36, this);
    }

    // Don't know exactly was this thing does, but Java requires it
    public boolean imageUpdate(Image img,
			       int infoflags,
			       int x,
			       int y,
			       int width,
			       int height)
    {
	/*
	  // When the image is loaded completly, we do a redraw
	if (infoflags == ImageObserver.ALLBITS && img == background)
	    {
		this.repaint ();
		return false;
	    }
	*/
	return true;
    }
}

/** A panel at the bottom of the main window, which displays the
    current height and width of the creatures */
class ValueDisplay
    extends Panel
{
    Label biego;
    Label stachelo;
    Label height;
    Label width;
    Label biego_height;
    Label biego_width;
    Label biego_max;
    Label stachelo_height;
    Label stachelo_width;
    Label stachelo_max;
    Label max;
    Label label;

    ValueDisplay ()
    {
	label = new Label ("Maße\\Art",  Label.CENTER);
	biego    = new Label ("Biego",  Label.CENTER);
	stachelo = new Label ("Stachelo",  Label.CENTER);
	height   = new Label ("Höhe",  Label.CENTER);
	width    = new Label ("Breite",  Label.CENTER);
	//distance = = new Label ("Abstand",  Label.CENTER);
	max = new Label ("Max. Größe",  Label.CENTER);

	biego_width  = new Label (Integer.toString (Main.main.get_world().get_biego ().get_width()),  Label.CENTER);
	biego_height = new Label (Integer.toString (Main.main.get_world().get_biego ().get_height()),  Label.CENTER);
	biego_max = new Label (Integer.toString (Main.main.get_world().get_bmax()),  Label.CENTER);
	//	biego_distance = = new Label ("Breite",  Label.CENTER);

	stachelo_width  = new Label (Integer.toString (Main.main.get_world().get_stachelo ().get_width()),  Label.CENTER);
	stachelo_height = new Label (Integer.toString (Main.main.get_world().get_stachelo ().get_height()),  Label.CENTER);
	stachelo_max = new Label (Integer.toString (Main.main.get_world().get_smax ()),  Label.CENTER);

	label.setBackground (new Color (0.5f, 0.5f, 0.5f));
	biego.setBackground (new Color (0.6f, 0.6f, 0.6f));
	stachelo.setBackground (new Color (0.6f, 0.6f, 0.6f));
	width.setBackground (new Color (0.6f, 0.6f, 0.6f));
	height.setBackground (new Color (0.6f, 0.6f, 0.6f));
	max.setBackground (new Color (0.6f, 0.6f, 0.6f));

	biego_width.setBackground (new Color (0.5f, 0.5f, 1.0f));
	biego_height.setBackground (new Color (1.0f, 0.5f, 0.5f));
	stachelo_width.setBackground (new Color (1.0f, 1.0f, 1.0f));
	stachelo_height.setBackground (new Color (0.0f, 1.0f, 0.0f));

	//stachelo_distance = = new Label ("Breite",  Label.CENTER);

	setLayout (new GridLayout (3, 4));

	add (label);
	add (width);
	add (height);
	add (max);

	add (biego);
	add (biego_width);
	add (biego_height);
	add (biego_max);

	add (stachelo);
	add (stachelo_width);
	add (stachelo_height);
	add (stachelo_max);
    }

    /// Updating the bottom panel with the new width and height values
    void update ()
    {
	biego_width.setText (Integer.toString (Main.main.get_world ().get_biego ().get_width ()));
	biego_height.setText (Integer.toString (Main.main.get_world ().get_biego ().get_height ()));
	biego_max.setText (Integer.toString (Main.main.get_world().get_bmax()));

	stachelo_width.setText (Integer.toString (Main.main.get_world ().get_stachelo ().get_width ()));
	stachelo_height.setText (Integer.toString (Main.main.get_world ().get_stachelo ().get_height ()));
	stachelo_max.setText (Integer.toString (Main.main.get_world().get_smax ()));
    }
}

class RefreshRateListener
    implements AdjustmentListener
{
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
	Main.main.set_refresh_rate (e.getValue () - 1);
	Main.main.update ();
	//System.out.println ("-- Refresh Adjust changed  --" + e.getValue ());
    }
}

class UpdateTimeListener
    implements AdjustmentListener
{
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
	Main.main.set_update_time (e.getValue ());
	Main.main.update ();
	//System.out.println ("-- Update Adjust changed  --" + (e.getValue () + 1));
    }
}

// EOF //
