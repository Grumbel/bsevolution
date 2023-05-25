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

/** This class creates all the other classes and manages the main
    loop. */
class Main
    extends Thread
{
    private World world;

    // Gui Elements
    public MainWindow main_window;
    //public StatGraph stat_graph;
    public ConfigDialog config_dialog;

    private boolean running;
    private int update_time;
    private int refresh_rate;
    private int evo_steps;
    private int refresh_counter = 0;
    private boolean do_step = false;
    public static Main main;

    /** Init all elements of the class. We need a seperate init()
        member, since the this. reference isn't ready in the
        constructor. */
    public void init ()
    {
	update_time = 250;
	refresh_rate = 4;

	// Construct all parts of the user interface
	world = new World ();
	main_window = new MainWindow ();
	//stat_graph = new StatGraph ();
	config_dialog = new ConfigDialog ();

	main_window.setVisible (true);

	// Start the thread
	this.start ();
    }

    /** We create only the main thread here, all the real work is done
        then thread */
    public static void main (String args[])
    {
	main = new Main ();
	main.init ();
    }

    // The main loop
    public void run ()
    {
	running = false;
	evo_steps = 0;
	long sleep_time = 0;
	long start_time = 0;

	// Main loop
	while (true)
	    {
		if ((running && sleep_time == 0)
		    || do_step)
		    {
			evo_steps++;
			world.update ();

			refresh_counter++;

			main_window.update_value_display();
			main_window.statgraph.update ();

			// We update the GUI only every refresh_rate step
			if (refresh_counter >= refresh_rate
			    || do_step)
			    {
				do_step = false;
				refresh_counter = 0;
				main_window.update_canvas ();
			    }
		    }

		if (!running)
		    {
			try {
			    // Busy waiting, should use
			    // wait()/notify() instead
			    sleep (50);
			}
			catch (InterruptedException e)
			    {
			    }
		    }
		else
		    {
			/* sleep () until the time to the next evo_step has
			   passed, if the sleep() is interrupted we calculate
			   how long we have sleeped and cache that value for
			   the next sleep call. */
			try
			    {
				start_time = System.currentTimeMillis();

				if (update_time - sleep_time > 0)
				    sleep (update_time - sleep_time);

				sleep_time = 0;
			    }
			catch (InterruptedException e)
			    {
				sleep_time = System.currentTimeMillis() - start_time + sleep_time;
			    }
		    }
	    }
    }

    public int get_refresh_rate () {
	return refresh_rate;
    }

    public void set_refresh_rate (int rrate) {
	refresh_rate = rrate;
    }

    public int get_update_time () {
	return update_time;
    }

    public void set_update_time (int u_time) {
	update_time = u_time;
    }

    public World get_world () {
	return world;
    }

    public int get_evo_steps () {
	return evo_steps;
    }

    public void set_evo_steps (int e) {
	evo_steps = e;
    }

    public void start_world () {
	running = true;
    }

    // Stop the main loop and wait for something happen
    public void stop_world ()
    {
	update ();
	running = false;
    }

    // Refresh the display, call this when you have changed values
    public void redisplay ()
    {
	main_window.update_value_display();
	main_window.statgraph.update ();
	main_window.update_canvas ();
	refresh_counter = 0;
    }

    /** Interrupt the sleep so that the main loop gets the new
        update_time and refresh_rate, without the need to wait for the
        next loop cycle. Call this member once you have changed
        data. */
    public void update ()
    {
	main_window.refresh_rate_label.setText ("Wiederholfrequenz: 1/" + (Main.main.get_refresh_rate () + 1));
	main_window.update_time_label.setText ("Zeit/Schritt: " + Main.main.get_update_time () + "msec");
	main_window.evo_steps.setText ("Schritte: " + Main.main.get_evo_steps ());
	main_window.button_panel.repaint ();

	try {
	    this.interrupt ();
	}
	catch (IllegalMonitorStateException me) {
	    System.out.println (" -------- IllegalMonitorStateException caugt ---------");
	}
    }

    public void do_a_step () {
	do_step = true;
    }

    public MainWindow get_main_window () {
	return main_window;
    }
}

// EOF //
