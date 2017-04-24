JC = javac 
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
JVM = java

CLASSES = \
	Database.java \
	Record.java \
	Table.java \
	Upload.java

DATABASE = Database
RECORD = Record
TABLE = Table
UPLOAD = Upload

default: classes

classes: $(CLASSES:.java=.class)

run1: classes
	$(JVM) -ea $(DATABASE)

run2: classes
	$(JVM) -ea $(RECORD)

run3: classes
	$(JVM) -ea $(TABLE)

run4: classes 
	$(JVM) -ea $(UPLOAD)

clean: 
	$(RM) *.class
