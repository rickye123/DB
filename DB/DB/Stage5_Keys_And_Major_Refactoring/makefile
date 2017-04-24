JC = javac 
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java
JVM = java

CLASSES = \
	Database.java \
	Record.java \
	Table.java \
	Upload.java \
	Print.java \
	Key.java

DATABASE = Database
RECORD = Record
TABLE = Table
UPLOAD = Upload
KEY = Key
PRINT = Print

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

run5: classes 
	$(JVM) -ea $(KEY)

run6: classes
	$(JVM) -ea $(PRINT)

clean: 
	$(RM) *.class
