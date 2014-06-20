package de.uno.lobbymanagement;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2014-06-02T02:30:01.481+02:00
 * Generated source version: 2.7.7
 * 
 */
@WebServiceClient(name = "LobbyService", 
                  wsdlLocation = "http://localhost:8080/Management/Lobby?wsdl",
                  targetNamespace = "http://lobbymanagement.uno.de/") 
public class LobbyService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://lobbymanagement.uno.de/", "LobbyService");
    public final static QName LobbyPort = new QName("http://lobbymanagement.uno.de/", "LobbyPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/Management/Lobby?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(LobbyService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8080/Management/Lobby?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public LobbyService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public LobbyService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public LobbyService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public LobbyService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public LobbyService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public LobbyService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns Lobby
     */
    @WebEndpoint(name = "LobbyPort")
    public Lobby getLobbyPort() {
        return super.getPort(LobbyPort, Lobby.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Lobby
     */
    @WebEndpoint(name = "LobbyPort")
    public Lobby getLobbyPort(WebServiceFeature... features) {
        return super.getPort(LobbyPort, Lobby.class, features);
    }

}
