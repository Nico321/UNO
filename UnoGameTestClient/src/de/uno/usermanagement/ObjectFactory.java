
package de.uno.usermanagement;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the de.uno.usermanagement package. 
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

    private final static QName _RemoveUserFromFriendlistResponse_QNAME = new QName("http://usermanagement.uno.de/", "RemoveUserFromFriendlistResponse");
    private final static QName _ShowWannabeFriendsResponse_QNAME = new QName("http://usermanagement.uno.de/", "ShowWannabeFriendsResponse");
    private final static QName _AddUserResponse_QNAME = new QName("http://usermanagement.uno.de/", "AddUserResponse");
    private final static QName _AddNewWannabeFriend_QNAME = new QName("http://usermanagement.uno.de/", "AddNewWannabeFriend");
    private final static QName _AddUser_QNAME = new QName("http://usermanagement.uno.de/", "AddUser");
    private final static QName _ShowFriendListResponse_QNAME = new QName("http://usermanagement.uno.de/", "ShowFriendListResponse");
    private final static QName _RemoveUserFromFriendlist_QNAME = new QName("http://usermanagement.uno.de/", "RemoveUserFromFriendlist");
    private final static QName _AddNewWannabeFriendResponse_QNAME = new QName("http://usermanagement.uno.de/", "AddNewWannabeFriendResponse");
    private final static QName _Login_QNAME = new QName("http://usermanagement.uno.de/", "Login");
    private final static QName _ShowWannabeFriends_QNAME = new QName("http://usermanagement.uno.de/", "ShowWannabeFriends");
    private final static QName _LoginResponse_QNAME = new QName("http://usermanagement.uno.de/", "LoginResponse");
    private final static QName _AddUserToFriendlistResponse_QNAME = new QName("http://usermanagement.uno.de/", "AddUserToFriendlistResponse");
    private final static QName _AddUserToFriendlist_QNAME = new QName("http://usermanagement.uno.de/", "AddUserToFriendlist");
    private final static QName _ShowFriendList_QNAME = new QName("http://usermanagement.uno.de/", "ShowFriendList");
    private final static QName _FindUserByName_QNAME = new QName("http://usermanagement.uno.de/", "FindUserByName");
    private final static QName _FindUserByNameResponse_QNAME = new QName("http://usermanagement.uno.de/", "FindUserByNameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: de.uno.usermanagement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddNewWannabeFriendResponse }
     * 
     */
    public AddNewWannabeFriendResponse createAddNewWannabeFriendResponse() {
        return new AddNewWannabeFriendResponse();
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link ShowWannabeFriends }
     * 
     */
    public ShowWannabeFriends createShowWannabeFriends() {
        return new ShowWannabeFriends();
    }

    /**
     * Create an instance of {@link AddUser }
     * 
     */
    public AddUser createAddUser() {
        return new AddUser();
    }

    /**
     * Create an instance of {@link ShowFriendListResponse }
     * 
     */
    public ShowFriendListResponse createShowFriendListResponse() {
        return new ShowFriendListResponse();
    }

    /**
     * Create an instance of {@link RemoveUserFromFriendlist }
     * 
     */
    public RemoveUserFromFriendlist createRemoveUserFromFriendlist() {
        return new RemoveUserFromFriendlist();
    }

    /**
     * Create an instance of {@link AddUserResponse }
     * 
     */
    public AddUserResponse createAddUserResponse() {
        return new AddUserResponse();
    }

    /**
     * Create an instance of {@link AddNewWannabeFriend }
     * 
     */
    public AddNewWannabeFriend createAddNewWannabeFriend() {
        return new AddNewWannabeFriend();
    }

    /**
     * Create an instance of {@link RemoveUserFromFriendlistResponse }
     * 
     */
    public RemoveUserFromFriendlistResponse createRemoveUserFromFriendlistResponse() {
        return new RemoveUserFromFriendlistResponse();
    }

    /**
     * Create an instance of {@link ShowWannabeFriendsResponse }
     * 
     */
    public ShowWannabeFriendsResponse createShowWannabeFriendsResponse() {
        return new ShowWannabeFriendsResponse();
    }

    /**
     * Create an instance of {@link FindUserByNameResponse }
     * 
     */
    public FindUserByNameResponse createFindUserByNameResponse() {
        return new FindUserByNameResponse();
    }

    /**
     * Create an instance of {@link FindUserByName }
     * 
     */
    public FindUserByName createFindUserByName() {
        return new FindUserByName();
    }

    /**
     * Create an instance of {@link ShowFriendList }
     * 
     */
    public ShowFriendList createShowFriendList() {
        return new ShowFriendList();
    }

    /**
     * Create an instance of {@link AddUserToFriendlist }
     * 
     */
    public AddUserToFriendlist createAddUserToFriendlist() {
        return new AddUserToFriendlist();
    }

    /**
     * Create an instance of {@link AddUserToFriendlistResponse }
     * 
     */
    public AddUserToFriendlistResponse createAddUserToFriendlistResponse() {
        return new AddUserToFriendlistResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserFromFriendlistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "RemoveUserFromFriendlistResponse")
    public JAXBElement<RemoveUserFromFriendlistResponse> createRemoveUserFromFriendlistResponse(RemoveUserFromFriendlistResponse value) {
        return new JAXBElement<RemoveUserFromFriendlistResponse>(_RemoveUserFromFriendlistResponse_QNAME, RemoveUserFromFriendlistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowWannabeFriendsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "ShowWannabeFriendsResponse")
    public JAXBElement<ShowWannabeFriendsResponse> createShowWannabeFriendsResponse(ShowWannabeFriendsResponse value) {
        return new JAXBElement<ShowWannabeFriendsResponse>(_ShowWannabeFriendsResponse_QNAME, ShowWannabeFriendsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "AddUserResponse")
    public JAXBElement<AddUserResponse> createAddUserResponse(AddUserResponse value) {
        return new JAXBElement<AddUserResponse>(_AddUserResponse_QNAME, AddUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNewWannabeFriend }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "AddNewWannabeFriend")
    public JAXBElement<AddNewWannabeFriend> createAddNewWannabeFriend(AddNewWannabeFriend value) {
        return new JAXBElement<AddNewWannabeFriend>(_AddNewWannabeFriend_QNAME, AddNewWannabeFriend.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "AddUser")
    public JAXBElement<AddUser> createAddUser(AddUser value) {
        return new JAXBElement<AddUser>(_AddUser_QNAME, AddUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowFriendListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "ShowFriendListResponse")
    public JAXBElement<ShowFriendListResponse> createShowFriendListResponse(ShowFriendListResponse value) {
        return new JAXBElement<ShowFriendListResponse>(_ShowFriendListResponse_QNAME, ShowFriendListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveUserFromFriendlist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "RemoveUserFromFriendlist")
    public JAXBElement<RemoveUserFromFriendlist> createRemoveUserFromFriendlist(RemoveUserFromFriendlist value) {
        return new JAXBElement<RemoveUserFromFriendlist>(_RemoveUserFromFriendlist_QNAME, RemoveUserFromFriendlist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNewWannabeFriendResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "AddNewWannabeFriendResponse")
    public JAXBElement<AddNewWannabeFriendResponse> createAddNewWannabeFriendResponse(AddNewWannabeFriendResponse value) {
        return new JAXBElement<AddNewWannabeFriendResponse>(_AddNewWannabeFriendResponse_QNAME, AddNewWannabeFriendResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "Login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowWannabeFriends }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "ShowWannabeFriends")
    public JAXBElement<ShowWannabeFriends> createShowWannabeFriends(ShowWannabeFriends value) {
        return new JAXBElement<ShowWannabeFriends>(_ShowWannabeFriends_QNAME, ShowWannabeFriends.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "LoginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToFriendlistResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "AddUserToFriendlistResponse")
    public JAXBElement<AddUserToFriendlistResponse> createAddUserToFriendlistResponse(AddUserToFriendlistResponse value) {
        return new JAXBElement<AddUserToFriendlistResponse>(_AddUserToFriendlistResponse_QNAME, AddUserToFriendlistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserToFriendlist }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "AddUserToFriendlist")
    public JAXBElement<AddUserToFriendlist> createAddUserToFriendlist(AddUserToFriendlist value) {
        return new JAXBElement<AddUserToFriendlist>(_AddUserToFriendlist_QNAME, AddUserToFriendlist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShowFriendList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "ShowFriendList")
    public JAXBElement<ShowFriendList> createShowFriendList(ShowFriendList value) {
        return new JAXBElement<ShowFriendList>(_ShowFriendList_QNAME, ShowFriendList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "FindUserByName")
    public JAXBElement<FindUserByName> createFindUserByName(FindUserByName value) {
        return new JAXBElement<FindUserByName>(_FindUserByName_QNAME, FindUserByName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindUserByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://usermanagement.uno.de/", name = "FindUserByNameResponse")
    public JAXBElement<FindUserByNameResponse> createFindUserByNameResponse(FindUserByNameResponse value) {
        return new JAXBElement<FindUserByNameResponse>(_FindUserByNameResponse_QNAME, FindUserByNameResponse.class, null, value);
    }

}
