TEMPLATE    = lib
TARGET      = core
DEFINES     += CORE_LIBRARY  # See Qt Ref doc, "Creating Shared Libraries"

CONFIG      += release plugin
CONFIG      += build_all
CONFIG      *= opengl
QT          += opengl

INCLUDEPATH += include

DESTDIR     = $$(PWD)/../bin

CONFIG(debug, debug|release) {
    TARGET  = $$join(TARGET,,,d)
    MOC_DIR = build/debug
    OBJECTS_DIR = build/debug
    RCC_DIR = build/debug
} else {
    TARGET = $$join(TARGET,,,)
    MOC_DIR = build/release
    OBJECTS_DIR = build/release
    RCC_DIR = build/release
}

# GLEW
unix:LIBS += -lGLEW -lGLU
win32:INCLUDEPATH += C:/lib/glew-1.5.8/include/
win32:LIBS += -LC:/lib/glew-1.5.8/lib
win32:LIBS += -lglew32
macx:LIBS += -lGLEW -install_name $$DESTDIR/libcore.dylib

HEADERS	+= include/*.h
SOURCES	+= src/*.cpp
