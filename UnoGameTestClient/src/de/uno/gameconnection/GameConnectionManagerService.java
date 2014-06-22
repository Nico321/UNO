package de.uno.gameconnection;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2014-06-20T23:04:47.667+02:00
 * Generated source version: 2.7.7
 * 
 */
@WebServiceClient(name = "GameConnectionManagerService", 
                  wsdlLocation = "http://192.168.2.104:8080/UnoGame/GameConnectionManager?wsdl",
                  targetNamespace = "http://gameconnection.uno.de/") 
public class GameConnectionManagerService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://gameconnection.uno.de/", "GameConnectionManagerService");
    public final static QName GameConnectionManagerPort = new QName("http://gameconnection.uno.de/", "GameConnectionManagerPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.2.104:8080/UnoGame/GameConnectionManager?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(GameConnectionManagerService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.2.104:8080/UnoGame/GameConnectionManager?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public GameConnectionManagerService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public GameConnectionManagerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public GameConnectionManagerService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public GameConnectionManagerService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public GameConnectionManagerService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public GameConnectionManagerService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns GameConnectionManager
     */
    @WebEndpoint(name = "GameConnectionManagerPort")
    public GameConnectionManager getGameConnectionManagerPort() {
        return super.getPort(GameConnectionManagerPort, GameConnectionManager.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns GameConnectionManager
     */
    @WebEndpoint(name = "GameConnectionManagerPort")
    public GameConnectionManager getGameConnectionManagerPort(WebServiceFeature... features) {
        return super.getPort(GameConnectionManagerPort, GameConnectionManager.class, features);
    }

}
