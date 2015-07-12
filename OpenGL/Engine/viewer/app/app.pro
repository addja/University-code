TEMPLATE	= app
LANGUAGE	= C++
TARGET 		= viewer

CONFIG += release
CONFIG += qt warn_on opengl console
CONFIG += build_all
CONFIG += opengl
QT += opengl

CONFIG(debug, debug|release) {
  TARGET = $$join(TARGET,,,d)
  DESTDIR = ../bin
  MOC_DIR = build/debug
  OBJECTS_DIR = build/debug
  RCC_DIR = build/debug
  UI_DIR = build/debug
  unix: LIBS += -Wl,-rpath $$DESTDIR -L$$DESTDIR -lglwidgetd
  win32:  LIBS += ../bin/glwidgetd.lib -L../bin/ -lglwidgetd
} else {
  DESTDIR = ../bin
  MOC_DIR = build/release
  OBJECTS_DIR = build/release
  RCC_DIR = build/release
  UI_DIR = build/release
  unix: LIBS += -Wl,-rpath $$DESTDIR -L$$DESTDIR -lglwidget
  win32:  LIBS += ../bin/pluginglwidget.lib -L../bin/ -lglwidget
}

# GLEW
unix: LIBS += -lGLEW

win32:INCLUDEPATH += C:/lib/glew-1.5.8/include/
win32:LIBS += -LC:/lib/glew-1.5.8/lib
win32:LIBS += -lglew32
win32:LIBS += -lcore

macx:LIBS+=-lGLEW -lcore

#win32:debug {
#  LIBS += ../bin/pluginglwidgetd.lib
#  LIBS += -L../bin/ -lpluginglwidgetd
#} else {
#  LIBS += ../bin/pluginglwidget.lib
#  LIBS += -L../bin/ -lpluginglwidget
#}

#unix:debug {
#   LIBS += -L$$DESTDIR -lpluginglwidgetd
#} else {
#   LIBS += -L$$DESTDIR -lpluginglwidget
#}


INCLUDEPATH += include
INCLUDEPATH += ../glwidget/include
INCLUDEPATH += ../core/include
INCLUDEPATH += ../interfaces

SOURCES	+= *.cpp

#message(Working dir: $$PWD)
#message(Target dir: $$DESTDIR)
