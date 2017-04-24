JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
JVM = java

CLASSES = \
	Oxo.java \
	Display.java \
	Board.java \
	Position.java \
	Symbols.java \
        AI.java \
        Testing.java


MAIN = Oxo

default: classes

classes: $(CLASSES:.java=.class)

run: classes 
	$(JVM) $(MAIN)

clean:
	$(RM) *.class
