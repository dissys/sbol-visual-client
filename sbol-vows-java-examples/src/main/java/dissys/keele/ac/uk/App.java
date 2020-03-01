package dissys.keele.ac.uk;

import org.apache.jena.rdf.model.*;

public class App 
{
	private static String SBOLVO = "http://sbols.org/visual/v2#";
	private static String RDFS="http://www.w3.org/2000/01/rdf-schema#";
			
    public static void main( String[] args )
    {
        
        System.out.println("The best matching glyphs for the SO:0000031 term:");
        Model g = ModelFactory.createDefaultModel();
        g.read("https://vows.sbolstandard.org/mapping/SO:0000031", "RDF/JSON");
        printGlyphProperties(g);
        
        System.out.println("Glyphs that can be used to represent interactions:");
        g = ModelFactory.createDefaultModel();
        g.read("https://vows.sbolstandard.org/query/SBO:0000231","RDF/JSON");
        printGlyphProperties(g);
        
        System.out.println("Retrieve metadata about the CDSGlyph term:");
        g = ModelFactory.createDefaultModel();
        g.read("https://vows.sbolstandard.org/metadata/CDSGlyph", "RDF/JSON");
        printMetadata(g);
        
    }
    
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
}