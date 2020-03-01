'''
Created on 1 Mar 2020

@author: gokselmisirli
'''
import rdflib
from rdflib import Graph, plugin, URIRef,Namespace
from rdflib.namespace import RDF, RDFS

from rdflib.parser import Parser

#plugin.register("rdf-json", Parser, "rdflib_rdfjson.rdfjson_parser", "RdfJsonParser")
SBOLVO = Namespace("http://sbols.org/visual/v2#")

def printGlyphProperties(g):
    for glyph in g.subjects(predicate=SBOLVO.defaultGlyph):
        print ("Glyph: " + str(glyph))
        print ("Recommended: " + g.value(glyph,SBOLVO.recommended))
        print ("File: " + g.value(glyph,SBOLVO.defaultGlyph))
        print ("Directory: " + g.value(glyph,SBOLVO.glyphDirectory))  
        print ("----------")  
        
def printMetadata(g):
  for glyph in g.subjects(predicate=SBOLVO.defaultGlyph):
        print ("Glyph: " + str(glyph))
        print ("Name: " + str(g.value(glyph,RDFS.label)))
        print ("Description: " + str(g.value(glyph,RDFS.comment)))
        print ("Recommended: " + g.value(glyph,SBOLVO.recommended))
        print ("File: " + g.value(glyph,SBOLVO.defaultGlyph))
        print ("Directory: " + g.value(glyph,SBOLVO.glyphDirectory))  
        print ("Notes: " + str(g.value(glyph,SBOLVO.notes)))
        print ("Example: " + str(g.value(glyph,SBOLVO.prototypicalExample)))
        print ("----------")  

        
g = rdflib.Graph()

print("The best matching glyphs for the SO:0000031 term:")
g.parse("https://vows.sbolstandard.org/mapping/SO:0000031",format="rdf-json")
printGlyphProperties(g)

print("Glyphs that can be used to represent interactions:")
g.parse("https://vows.sbolstandard.org/query/SBO:0000231",format="rdf-json")
printGlyphProperties(g)

print("Retrieve metadata about the CDSGlyph term:")
g.parse("https://vows.sbolstandard.org/metadata/CDSGlyph",format="rdf-json")
printMetadata(g)  
