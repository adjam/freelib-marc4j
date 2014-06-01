/**
 * Copyright (C) 2002 Bas Peters
 *
 * This file is part of MARC4J
 *
 * MARC4J is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * MARC4J is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with MARC4J; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package info.freelibrary.marc.converter.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.marc4j.MarcException;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * <p>
 * <code>ReverseCodeTableHash</code> defines a data structure to facilitate
 * UnicodeToAnsel character conversion.
 * </p>
 * 
 * @author Corey Keith
 * @see DefaultHandler
 */
public class ReverseCodeTableHash extends ReverseCodeTable {

    protected static Hashtable<Character, Hashtable<Integer, char[]>> charsets;

    protected static Vector<Character> combining;

    /**
     * Returns <code>true</code> if the supplied {@link Character} is a
     * combining character; else, <code>false</code>.
     * 
     * @param c
     * @return
     */
    public boolean isCombining(Character c) {
        return combining.contains(c);
    }

    /**
     * Gets the character table for the supplied {@link Character}.
     * 
     * @param c
     * @return The character table for the supplied {@link Character}
     */
    public Hashtable<Integer, char[]> getCharTable(Character c) {
        return charsets.get(c);
    }

    /**
     * Creates a reverse codetable hash from the supplied {@link InputStream}.
     * 
     * @param byteStream
     */
    public ReverseCodeTableHash(InputStream byteStream) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            SAXParser saxParser = factory.newSAXParser();
            XMLReader rdr = saxParser.getXMLReader();

            InputSource src = new InputSource(byteStream);

            ReverseCodeTableHandler saxUms = new ReverseCodeTableHandler();

            rdr.setContentHandler(saxUms);
            rdr.parse(src);

            charsets = saxUms.getCharSets();
            combining = saxUms.getCombiningChars();

        } catch (Exception e) {
            throw new MarcException(e.getMessage(), e);
        }

    }

    /**
     * Creates a reverse codetable hash from the supplied file name.
     * 
     * @param filename
     */
    public ReverseCodeTableHash(String filename) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            SAXParser saxParser = factory.newSAXParser();
            XMLReader rdr = saxParser.getXMLReader();

            File file = new File(filename);
            InputSource src = new InputSource(new FileInputStream(file));

            ReverseCodeTableHandler saxUms = new ReverseCodeTableHandler();

            rdr.setContentHandler(saxUms);
            rdr.parse(src);

            charsets = saxUms.getCharSets();
            combining = saxUms.getCombiningChars();

        } catch (Exception e) {
            throw new MarcException(e.getMessage(), e);
        }
    }

    /**
     * Creates a reverse codetable hash from the supplied {@link URI}.
     * 
     * @param uri
     */
    public ReverseCodeTableHash(URI uri) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(false);
            SAXParser saxParser = factory.newSAXParser();
            XMLReader rdr = saxParser.getXMLReader();

            InputSource src = new InputSource(uri.toURL().openStream());

            ReverseCodeTableHandler saxUms = new ReverseCodeTableHandler();

            rdr.setContentHandler(saxUms);
            rdr.parse(src);

            charsets = saxUms.getCharSets();
            combining = saxUms.getCombiningChars();

        } catch (Exception e) {
            throw new MarcException(e.getMessage(), e);
        }
    }

}
