JC = javac 
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
JVM = java

CLASSES = \
	Database.java \
	Record.java

DATABASE = Database
RECORD = Record

default: classes

classes: $(CLASSES:.java=.class)

run1: classes
	$(JVM) -ea $(DATABASE)

run2: classes
	$(JVM) -ea $(RECORD)

clean: 
	$(RM) *.class
