
package de.highscore;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.highscore package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddPointsToUserResponse_QNAME = new QName("http://highscore.de/", "addPointsToUserResponse");
    private final static QName _GetHighscore_QNAME = new QName("http://highscore.de/", "getHighscore");
    private final static QName _GetHighscoreResponse_QNAME = new QName("http://highscore.de/", "getHighscoreResponse");
    private final static QName _AddPointsToUser_QNAME = new QName("http://highscore.de/", "addPointsToUser");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.highscore
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddPointsToUserResponse }
     * 
     */
    public AddPointsToUserResponse createAddPointsToUserResponse() {
        return new AddPointsToUserResponse();
    }

    /**
     * Create an instance of {@link GetHighscore }
     * 
     */
    public GetHighscore createGetHighscore() {
        return new GetHighscore();
    }

    /**
     * Create an instance of {@link GetHighscoreResponse }
     * 
     */
    public GetHighscoreResponse createGetHighscoreResponse() {
        return new GetHighscoreResponse();
    }

    /**
     * Create an instance of {@link AddPointsToUser }
     * 
     */
    public AddPointsToUser createAddPointsToUser() {
        return new AddPointsToUser();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPointsToUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://highscore.de/", name = "addPointsToUserResponse")
    public JAXBElement<AddPointsToUserResponse> createAddPointsToUserResponse(AddPointsToUserResponse value) {
        return new JAXBElement<AddPointsToUserResponse>(_AddPointsToUserResponse_QNAME, AddPointsToUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHighscore }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://highscore.de/", name = "getHighscore")
    public JAXBElement<GetHighscore> createGetHighscore(GetHighscore value) {
        return new JAXBElement<GetHighscore>(_GetHighscore_QNAME, GetHighscore.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHighscoreResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://highscore.de/", name = "getHighscoreResponse")
    public JAXBElement<GetHighscoreResponse> createGetHighscoreResponse(GetHighscoreResponse value) {
        return new JAXBElement<GetHighscoreResponse>(_GetHighscoreResponse_QNAME, GetHighscoreResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPointsToUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://highscore.de/", name = "addPointsToUser")
    public JAXBElement<AddPointsToUser> createAddPointsToUser(AddPointsToUser value) {
        return new JAXBElement<AddPointsToUser>(_AddPointsToUser_QNAME, AddPointsToUser.class, null, value);
    }

}
