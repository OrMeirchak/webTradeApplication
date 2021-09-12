package fileLoader;

import exception.ExtensionException;
import exception.NotFullPathException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;


 public class XmlFileLoader<E> {

     private E OBJECT;

    private final static String XML_PACKAGE_NAME = "generated";

    public XmlFileLoader(InputStream inputStream) {
        try {
            OBJECT = deserializeFrom(inputStream);
        }
        catch(JAXBException e){
            e.printStackTrace();
        }
    }

     private E deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jb= JAXBContext.newInstance(XML_PACKAGE_NAME);
        Unmarshaller um=jb.createUnmarshaller();
        return (E) um.unmarshal(in);
    }

     public E getObject() {
         return OBJECT;
     }

 }
