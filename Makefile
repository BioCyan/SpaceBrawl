JAVAFX=/usr/share/openjfx/lib
MODULES=javafx.graphics,javafx.fxml,javafx.controls
JAVA_FLAGS=--module-path $(JAVAFX) --add-modules $(MODULES) -cp bin
JAVAC_FLAGS=$(JAVA_FLAGS) -d bin -sourcepath src

run: build
	java $(JAVA_FLAGS) application.Main

build:
	javac $(JAVAC_FLAGS) src/application/Main.java src/application/controller/*.java
	cp -u resources/* bin
	[ -d bin/application/view ] || mkdir bin/application/view
	cp -u src/application/view/* bin/application/view/

clean:
	rm -r bin
