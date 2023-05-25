objects = Biegosaurus.java       Main.java		  World.java \
ConfigDialog.java  DoubleBufferedCanvas.java  Stachelophyte.java \
Creature.java	   MainWindow.java		      StatGraph.java MessageBox.java

distname = "bsevolution-`date +\"%m%d%Y\"`"

all: $(objects)
	javac $(objects)

dist:
	mkdir -p $(distname)
	mkdir -p $(distname)/images/
	mkdir -p $(distname)/doc/
	cp bsevolution Makefile INSTALL COPYING README BUGS AUTHORS $(distname)
	cp doc/bsevolution.tex doc/bsevolution_gui.ps $(distname)/doc/
	cp *.java $(distname)/
	cp images/*.gif $(distname)/images/
	tar czf $(distname).tar.gz $(distname)
	rm -rf $(distname)

clean:
	rm *.class

# EOF #
