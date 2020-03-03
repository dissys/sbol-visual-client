# Programmatic access to the SBOL Visual Ontology web service (SBOL-VOWS)
This repository provides examples to use the SBOL Visual Ontology web service (SBOL-VOWS) remotely. Examples are provided in both Python and Java programming languages.

## Accessing the ```glyph``` interface
SBOL-VOWS acts an image service using the `glyph` interface. Images are directly resolved via HTTP. Hence, there is no special API required when accessing the glyphs via the ```query``` interface. E.g. https://vows.sbolstandard.org/glyph/AptamerGlyph resolves the glyph URL http://synbiodex.github.io/SBOL-visual/Glyphs/aptamer/aptamer-specification.png.

## Accessing information about glyphs
SBOL-VOWS can be used to search for suitable glyphs and to return metadadata about these glyphs. Textual information about glyphs are returned as RDF graphs. Hence, any RDF library can be used to process this information. Please note that SBOL VOWS uses RDF/JSON as the default RDF format. Hence, both RDF and JSON libraries can be used to access information.

### Prerequisites for the Python examples
The Python examples provided below depend on RDFLib, a widely used RDF library. To try out the examples, please first install RDFLib.
```
python3 install rdflib 
```

Then install the ```rdfjson``` module for RDFlib.
```
git clone https://github.com/RDFLib/rdflib-rdfjson.git
cd rdflib-rdfjson
python3 setup.py install
```

### ```Mapping``` examples
The following examples show how to retrive information about the recommended glyph when the query term used is ```SO:0000031``` (the Aptamer term from the Sequence Ontology).

**Python**:
```python
SBOLVO = Namespace("http://sbols.org/visual/v2#")

def printGlyphProperties(g):
    for glyph in g.subjects(predicate=SBOLVO.defaultGlyph):
        print ("Glyph: " + str(glyph))
        print ("Recommended: " + g.value(glyph,SBOLVO.recommended))
        print ("File: " + g.value(glyph,SBOLVO.defaultGlyph))
        print ("Directory: " + g.value(glyph,SBOLVO.glyphDirectory))  
        print ("----------")  
               
g = rdflib.Graph()
g.parse("https://vows.sbolstandard.org/mapping/SO:0000031",format="rdf-json")
printGlyphProperties(g)
```
**Java:**
```Java
Model g = ModelFactory.createDefaultModel();
g.read("https://vows.sbolstandard.org/mapping/SO:0000031", "RDF/JSON");
printGlyphProperties(g);

private static String getValue(Model g, Resource res, String prop){
    String value=null;
    Property p=g.createProperty(prop);
    Statement stmt=res.getProperty(p);
    if (stmt!=null){
    	value=stmt.getObject().asLiteral().getValue().toString();
    }
    return value;
}
    
private static void printGlyphProperties(Model g){
    ResIterator it=g.listSubjects();
	while (it.hasNext()){
		Resource glyph=it.next();
		System.out.println("Glyph: " + glyph.getURI());
		System.out.println("Recommended: " + getValue(g, glyph, SBOLVO + "recommended"));
		System.out.println("File: " + getValue(g,glyph,SBOLVO + "defaultGlyph"));
		System.out.println("Directory: " + getValue(g, glyph,SBOLVO + "glyphDirectory"));
		System.out.println("----------"); 
	}
}
```

### ```Query``` examples
The following examples show how to retrieve information about glyphs that can be used to represent interactions.

**Python**:
```python
g.parse("https://vows.sbolstandard.org/query/SBO:0000231",format="rdf-json")
printGlyphProperties(g)
```

**Java**:
```Java
g = ModelFactory.createDefaultModel();
g.read("https://vows.sbolstandard.org/query/SBO:0000231","RDF/JSON");
printGlyphProperties(g);
```        

### ```Metadata``` examples
The following examples show how to retrieve metadata about the CDSGlyph term.

**Python**:
```python
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

g.parse("https://vows.sbolstandard.org/metadata/CDSGlyph",format="rdf-json")
printMetadata(g)  
```

**Java**:
```Java
g = ModelFactory.createDefaultModel();
g.read("https://vows.sbolstandard.org/metadata/CDSGlyph", "RDF/JSON");
printMetadata(g);

private static void printMetadata(Model g){
    ResIterator it=g.listSubjects();
    while (it.hasNext()){
    	Resource glyph=it.next();
    	System.out.println("Glyph: " + glyph.getURI());
    	System.out.println ("Name: " + getValue(g, glyph, RDFS + "label"));
    	System.out.println ("Description: " + getValue(g, glyph, RDFS + "comment"));
    	System.out.println("Recommended: " + getValue(g, glyph, SBOLVO + "recommended"));
    	System.out.println("File: " + getValue(g,glyph,SBOLVO + "defaultGlyph"));
    	System.out.println("Directory: " + getValue(g, glyph,SBOLVO + "glyphDirectory"));
    	System.out.println("Notes: " + getValue(g, glyph,SBOLVO + "notes"));
    	System.out.println("Example: " + getValue(g, glyph,SBOLVO + "prototypicalExample"));    
    	System.out.println("----------"); 
    }
}
``` 



