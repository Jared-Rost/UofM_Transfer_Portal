# Variables
JAR_PATH=course_transfer_java/Application/lib/sqlite-jdbc-3.47.1.0.jar
JAVA_CP=.:$(JAR_PATH)
JAVA_FILES=$(shell find . -name "*.java")
CLASS_FILES=$(shell find . -name "*.class")

# Default target
all: compile run

# Compile target
compile:
	javac -cp $(JAVA_CP) $(JAVA_FILES)

# Run target
run:
	java -cp $(JAVA_CP) course_transfer_java/Application/Main

# Clean target
clean:
	find . -name "*.class" -type f -delete

# Phony targets
.PHONY: all compile run clean