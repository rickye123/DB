JC = javac 
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
JVM = java

CLASSES = \
	Database.java \
	Record.java \
	Table.java 

DATABASE = Database
RECORD = Record
TABLE = Table

default: classes

classes: $(CLASSES:.java=.class)

run1: classes
	$(JVM) -ea $(DATABASE)

run2: classes
	$(JVM) -ea $(RECORD)

run3: classes
	$(JVM) -ea $(TABLE)

clean: 
	$(RM) *.class
