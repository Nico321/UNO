package de.highscore;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2014-06-23T16:21:05.035+02:00
 * Generated source version: 2.7.7
 * 
 */
@WebService(targetNamespace = "http://highscore.de/", name = "HighScore")
@XmlSeeAlso({ObjectFactory.class})
public interface HighScore {

    @RequestWrapper(localName = "addPointsToUser", targetNamespace = "http://highscore.de/", className = "de.highscore.AddPointsToUser")
    @WebMethod
    @ResponseWrapper(localName = "addPointsToUserResponse", targetNamespace = "http://highscore.de/", className = "de.highscore.AddPointsToUserResponse")
    public void addPointsToUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.Integer arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "getHighscore", targetNamespace = "http://highscore.de/", className = "de.highscore.GetHighscore")
    @WebMethod
    @ResponseWrapper(localName = "getHighscoreResponse", targetNamespace = "http://highscore.de/", className = "de.highscore.GetHighscoreResponse")
    public java.lang.String getHighscore();
}
