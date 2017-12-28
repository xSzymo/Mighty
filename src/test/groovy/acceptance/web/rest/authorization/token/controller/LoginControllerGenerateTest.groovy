package acceptance.web.rest.authorization.token.controller

import acceptance.Requester
import groovy.json.JsonSlurper
import spock.lang.Specification

class LoginControllerGenerateTest extends Specification {
    private String url = "http://localhost:8080"
    private String tokenURL = url + "/token"
    private String currentUserURL = url + "/secure/getCurrentUser"

    private String correctJSONLogin = '{ "login": "admin0", "password": "admin"}'
    private String wrongJSONLogin = '{ "login": "wrong", "password": "wrong"}'
    private Requester requester = new Requester()
    private String GET = "GET"

    void "can login on acc and can get user's data with correct token"() {
        given: "get token after send login request"
            def json = requester.request(tokenURL, correctJSONLogin)
            String myToken = json.getInputStream().getText()

        when: "check possibility get user's account data"
            def token = requester.request(currentUserURL, new JsonSlurper().parseText(myToken).token, GET)

        then: "check everything went right"
            myToken != null
            token.getResponseCode() == 200
            token.getInputStream().getText() != null
    }


    void "double request to token controller will create different tokens and only latest will be correct"() {
        given: "get 2 times token after send login request"
            def json = requester.request(tokenURL, correctJSONLogin)
            String myToken = json.getInputStream().getText()
            def json1 = requester.request(tokenURL, correctJSONLogin)
            String myToken1 = json1.getInputStream().getText()

        when: "check possibility get user's account data with tokens"
            def token = requester.request(currentUserURL, new JsonSlurper().parseText(myToken).token, GET)
            def token1 = requester.request(currentUserURL, new JsonSlurper().parseText(myToken1).token, GET)

        then: "check the first token was unable to connect with security site and second one was correct"
            token1 != null
            myToken != myToken1
            token.getResponseCode() == 500
            token1.getResponseCode() == 200
    }

    void "can login but can't get user's data with wrong token"() {
        given: "get token after send login request"
            def json = requester.request(tokenURL, correctJSONLogin)
            String myToken = json.getInputStream().getText()

        when: "check possibility get user's account data with wrong token"
            def token = requester.request(currentUserURL, new JsonSlurper().parseText(myToken).token + "X", GET)

        then: "check end point returned status 500 - wrong jwt token"
            myToken != null
            token.getResponseCode() == 500
    }

    void "can't login on account with wrong login/password"() {
        when: "user log in with wrong login/password"
            def json = requester.request(tokenURL, wrongJSONLogin)

        then: "check end point returned status 500 - wrong login/password"
            json != null
            json.getResponseCode() == 500
    }
}
