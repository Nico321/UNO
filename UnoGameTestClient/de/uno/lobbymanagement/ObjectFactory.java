
package de.uno.lobbymanagement;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.uno.lobbymanagement package. 
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

    private final static QName _InitResponse_QNAME = new QName("http://lobbymanagement.uno.de/", "initResponse");
    private final static QName _Init_QNAME = new QName("http://lobbymanagement.uno.de/", "init");
    private final static QName _AddFriendsToGameResponse_QNAME = new QName("http://lobbymanagement.uno.de/", "addFriendsToGameResponse");
    private final static QName _ShowOpenGamesResponse_QNAME = new QName("http://lobbymanagement.uno.de/", "showOpenGamesResponse");
    private final static QName _StartGameResponse_QNAME = new QName("http://lobbymanagement.uno.de/", "startGameResponse");
    private final static QName _AddFriendsToGame_QNAME = new QName("http://lobbymanagement.uno.de/", "addFriendsToGame");
    private final static QName _CreateNewGame_QNAME = new QName("http://lobbymanagement.uno.de/", "createNewGame");
    private final static QName _CreateNewGameResponse_QNAME = new QName("http://lobbymanagement.uno.de/", "createNewGameResponse");
    private final static QName _EnterRandom_QNAME = new QName("http://lobbymanagement.uno.de/", "enterRandom");
    private final static QName _EnterRandomResponse_QNAME = new QName("http://lobbymanagement.uno.de/", "enterRandomResponse");
    private final static QName _ShowOpenGames_QNAME = new QName("http://lobbymanagement.uno.de/", "showOpenGames");
    private final static QName _StartGame_QNAME = new QName("http://lobbymanagement.uno.de/", "startGame");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.uno.lobbymanagement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ShowOpenGames }
     * 
     */
    public ShowOpenGames createShowOpenGames() {
        return new ShowOpenGames();
    }

    /**
     * Create an instance of {@link EnterRandomResponse }
     * 
     */
    public EnterRandomResponse createEnterRandomResponse() {
        return new EnterRandomResponse();
    }

    /**
     * Create an instance of {@link EnterRandom }
     * 
     */
    public EnterRandom createEnterRandom() {
        return new EnterRandom();
    }

    /**
     * Create an instance of {@link CreateNewGameResponse }
     * 
     */
    public CreateNewGameResponse createCreateNewGameResponse() {
        return new CreateNewGameResponse();
    }

    /**
     * Create an instance of {@link CreateNewGame }
     * 
     */
    public CreateNewGame createCreateNewGame() {
        return new CreateNewGame();
    }

    /**
     * Create an instance of {@link AddFriendsToGame }
     * 
     */
    public AddFriendsToGame createAddFriendsToGame() {
        return new AddFriendsToGame();
    }

    /**
     * Create an instance of {@link StartGame }
     * 
     */
    public StartGame createStartGame() {
        return new StartGame();
    }

    /**
     * Create an instance of {@link Init }
     * 
     */
    public Init createInit() {
        return new Init();
    }

    /**
     * Create an instance of {@link StartGameResponse }
     * 
     */
    public StartGameResponse createStartGameResponse() {
        return new StartGameResponse();
    }

    /**
     * Create an instance of {@link ShowOpenGamesResponse }
     * 
     */
    public ShowOpenGamesResponse createShowOpenGamesResponse() {
        return new ShowOpenGamesResponse();
    }

    /**
     * Create an instance of {@link AddFriendsToGameResponse }
     * 
     */
    public AddFriendsToGameResponse createAddFriendsToGameResponse() {
        return new AddFriendsToGameResponse();
    }

    /**
     * Create an instance of {@link InitResponse }
     * 
     */
    public InitResponse createInitResponse() {
        return new InitResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "initResponse")
    public JAXBElement<InitResponse> createInitResponse(InitResponse value) {
        return new JAXBElement<InitResponse>(_InitResponse_QNAME, InitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Init }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "init")
    public JAXBElement<Init> createInit(Init value) {
        return new JAXBElement<Init>(_Init_QNAME, Init.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFriendsToGameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "addFriendsToGameResponse")
    public JAXBElement<AddFriendsToGameResponse> createAddFriendsToGameResponse(AddFriendsToGameResponse value) {
        return new JAXBElement<AddFriendsToGameResponse>(_AddFriendsToGameResponse_QNAME, AddFriendsToGameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowOpenGamesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "showOpenGamesResponse")
    public JAXBElement<ShowOpenGamesResponse> createShowOpenGamesResponse(ShowOpenGamesResponse value) {
        return new JAXBElement<ShowOpenGamesResponse>(_ShowOpenGamesResponse_QNAME, ShowOpenGamesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartGameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "startGameResponse")
    public JAXBElement<StartGameResponse> createStartGameResponse(StartGameResponse value) {
        return new JAXBElement<StartGameResponse>(_StartGameResponse_QNAME, StartGameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFriendsToGame }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "addFriendsToGame")
    public JAXBElement<AddFriendsToGame> createAddFriendsToGame(AddFriendsToGame value) {
        return new JAXBElement<AddFriendsToGame>(_AddFriendsToGame_QNAME, AddFriendsToGame.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateNewGame }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "createNewGame")
    public JAXBElement<CreateNewGame> createCreateNewGame(CreateNewGame value) {
        return new JAXBElement<CreateNewGame>(_CreateNewGame_QNAME, CreateNewGame.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateNewGameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "createNewGameResponse")
    public JAXBElement<CreateNewGameResponse> createCreateNewGameResponse(CreateNewGameResponse value) {
        return new JAXBElement<CreateNewGameResponse>(_CreateNewGameResponse_QNAME, CreateNewGameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnterRandom }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "enterRandom")
    public JAXBElement<EnterRandom> createEnterRandom(EnterRandom value) {
        return new JAXBElement<EnterRandom>(_EnterRandom_QNAME, EnterRandom.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnterRandomResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "enterRandomResponse")
    public JAXBElement<EnterRandomResponse> createEnterRandomResponse(EnterRandomResponse value) {
        return new JAXBElement<EnterRandomResponse>(_EnterRandomResponse_QNAME, EnterRandomResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowOpenGames }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "showOpenGames")
    public JAXBElement<ShowOpenGames> createShowOpenGames(ShowOpenGames value) {
        return new JAXBElement<ShowOpenGames>(_ShowOpenGames_QNAME, ShowOpenGames.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartGame }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://lobbymanagement.uno.de/", name = "startGame")
    public JAXBElement<StartGame> createStartGame(StartGame value) {
        return new JAXBElement<StartGame>(_StartGame_QNAME, StartGame.class, null, value);
    }

}
