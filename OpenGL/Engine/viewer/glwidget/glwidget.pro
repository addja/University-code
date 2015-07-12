TEMPLATE   = lib

TARGET     = glwidget

CONFIG     += designer plugin 
CONFIG     += release
CONFIG     += build_all
CONFIG     *= opengl
QT         *= opengl

INCLUDEPATH += include
INCLUDEPATH += ../core/include
INCLUDEPATH += ../interfaces

DESTDIR = $$(PWD)/../bin
#message("will install in $$DESTDIR")

CONFIG(debug, debug|release) {
  TARGET = $$join(TARGET,,,d)
  MOC_DIR = build/debug
  OBJECTS_DIR = build/debug
  RCC_DIR = build/debug
} else {
  TARGET = $$join(TARGET,,,)
  MOC_DIR = build/release
  OBJECTS_DIR = build/release
  RCC_DIR = build/release
}

unix:target.path = ~/.designer/plugins/designer
win32:target.path = $$[QT_INSTALL_PLUGINS]/designer

# GLEW
macx{
   LIBS +=  -L../bin/ -lGLEW -lcore -install_name $$DESTDIR/libpluginglwidget.dylib
} else {
   unix{
      LIBS += -Wl,--rpath-link=../bin -L../bin -lGLEW -lGLU -lcore -lGL # Cal a linux, però no a Mac...
   }
}

win32{
   INCLUDEPATH += C:/lib/glew-1.5.8/include/
   LIBS += -LC:/lib/glew-1.5.8/lib
   LIBS += -lglew32
   LIBS += -L../bin/ -lcore
}

INSTALLS   += target

DEFINES += PLUGINGLWIDGET_LIBRARY   # see Qt docs, "Creating shared libraries"

HEADERS += include/*.h 
SOURCES	+= src/*.cpp

