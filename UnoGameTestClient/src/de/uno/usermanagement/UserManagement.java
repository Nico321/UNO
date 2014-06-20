package de.uno.usermanagement;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2014-06-02T02:29:49.117+02:00
 * Generated source version: 2.7.7
 * 
 */
@WebService(targetNamespace = "http://usermanagement.uno.de/", name = "UserManagement")
@XmlSeeAlso({ObjectFactory.class})
public interface UserManagement {

    @RequestWrapper(localName = "AddUserToFriendlist", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.AddUserToFriendlist")
    @WebMethod(operationName = "AddUserToFriendlist")
    @ResponseWrapper(localName = "AddUserToFriendlistResponse", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.AddUserToFriendlistResponse")
    public void addUserToFriendlist(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "FindUserByName", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.FindUserByName")
    @WebMethod(operationName = "FindUserByName")
    @ResponseWrapper(localName = "FindUserByNameResponse", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.FindUserByNameResponse")
    public java.lang.String findUserByName(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0
    );

    @RequestWrapper(localName = "RemoveUserFromFriendlist", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.RemoveUserFromFriendlist")
    @WebMethod(operationName = "RemoveUserFromFriendlist")
    @ResponseWrapper(localName = "RemoveUserFromFriendlistResponse", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.RemoveUserFromFriendlistResponse")
    public void removeUserFromFriendlist(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "AddUser", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.AddUser")
    @WebMethod(operationName = "AddUser")
    @ResponseWrapper(localName = "AddUserResponse", targetNamespace = "http://usermanagement.uno.de/", className = "de.uno.usermanagement.AddUserResponse")
    public java.lang.String addUser(
        @WebParam(name = "arg0", targetNamespace = "")
        java.lang.String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        java.lang.String arg1
    );
}
