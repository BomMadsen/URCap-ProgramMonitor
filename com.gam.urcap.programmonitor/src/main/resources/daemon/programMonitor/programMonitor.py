#!/usr/bin/env python
# -*- coding: utf-8 -*-

from SimpleXMLRPCServer import SimpleXMLRPCServer

min_reset = 10000.0
max_reset = -10000.0
gotAnyData = False

xmax = max_reset
ymax = max_reset
zmax = max_reset
xmin = min_reset
ymin = min_reset
zmin = min_reset
speedmax = 0

def resetResults():
    print "Resetting values"
    global gotAnyData
    global xmax
    global xmin
    global ymax
    global ymin
    global zmax
    global zmin
    global speedmax
    xmax = max_reset
    ymax = max_reset
    zmax = max_reset
    xmin = min_reset
    ymin = min_reset
    zmin = min_reset
    speedmax = 0
    gotAnyData = False

def getResult(type):
    value = None
    if(type=="xmax"):
        value = xmax
    elif(type=="xmin"):
        value = xmin
    elif(type=="ymax"):
        value = ymax
    elif(type=="ymin"):
        value = ymin
    elif(type=="zmax"):
        value = zmax
    elif(type=="zmin"):
        value = zmin
    elif(type=="speedmax"):
        value = speedmax
    print "Returning ", type, " as ", value
    return value

def setResult(type, value):
    print "Setting ", type, " to ", value
    global gotAnyData
    gotAnyData = True
    if(type=="xmax"):
        global xmax
        xmax = value
    elif(type=="xmin"):
        global xmin 
        xmin = value
    elif(type=="ymax"):
        global ymax
        ymax = value
    elif(type=="ymin"):
        global ymin
        ymin = value
    elif(type=="zmax"):
        global zmax
        zmax = value
    elif(type=="zmin"):
        global zmin
        zmin = value
    elif(type=="speedmax"):
        global speedmax
        speedmax = value

def hasMonitored():
    return gotAnyData

resetResults()

print "Opening XML-RPC Server"
server = SimpleXMLRPCServer(("", 23444), allow_none=True)
server.register_function(resetResults, "resetResults")
server.register_function(getResult, "getResult")
server.register_function(setResult, "setResult")
server.register_function(hasMonitored, "hasMonitored")
server.serve_forever() 