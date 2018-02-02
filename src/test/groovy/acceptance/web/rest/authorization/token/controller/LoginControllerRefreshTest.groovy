package acceptance.web.rest.authorization.token.controller

import acceptance.Requester
import groovy.json.JsonSlurper
import spock.lang.Ignore
import spock.lang.Specification

class LoginControllerRefreshTest extends Specification {
    private String url = "http://localhost:8080"
    private String tokenURL = url + "/token"
    private String refreshTokenURL = url + "/secure/refresh"
    private String currentUserURL = url + "/secure/getCurrentUser"

    private String correctJSONLogin = '{ "login": "admin0", "password": "admin"}'
    private String GET = "GET"
    private String POST = "POST"

    private Requester requester = new Requester()

    @Ignore
    void "refresh existing token"() {
        given: "get token after send login request"
        def json = requester.request(tokenURL, correctJSONLogin)
        String myToken = json.getInputStream().getText()

        when: "refresh token with generated token, send request with non updated tokens"
        def token1 = requester.request(currentUserURL, new JsonSlurper().parseText(myToken).token, GET)
        def token = requester.request(refreshTokenURL, new JsonSlurper().parseText(myToken).token, POST)
        String myToken1 = token.getInputStream().getText()

        def token2 = requester.request(currentUserURL, new JsonSlurper().parseText(myToken).token, GET)
        def token3 = requester.request(currentUserURL, new JsonSlurper().parseText(myToken1).token, GET)

        then: "check token and (refreshed) token1 were able to connect with user acc"
        myToken != null
        token.getResponseCode() == 200
        token1.getResponseCode() == 200
        token2.getResponseCode() == 500
        token3.getResponseCode() == 200
        myToken1 != myToken
    }

    @Ignore
    void "check refresh token end point which should return every request another token"() {
        given: "log in"
        def json = requester.request(tokenURL, correctJSONLogin)
        String myToken = json.getInputStream().getText()

        when: "refresh token 2 times"
        def token = requester.request(refreshTokenURL, new JsonSlurper().parseText(myToken).token, POST)
        String myToken1 = token.getInputStream().getText()
        def token1 = requester.request(refreshTokenURL, new JsonSlurper().parseText(myToken1).token, POST)
        def token2 = requester.request(refreshTokenURL, new JsonSlurper().parseText(myToken1).token, POST)

        then: "returned tokens were not same & only latest is able to get user's data"
        token.getResponseCode() == 200
        token1.getResponseCode() == 200
        token2.getResponseCode() == 500
        token1 != null
        myToken1 != token1.getInputStream().getText() && myToken != myToken1
    }

    void "check refresh token end point with wrong token"() {
        given: "log in"
        def json = requester.request(tokenURL, correctJSONLogin)
        String myToken = json.getInputStream().getText()

        when: "refresh wrong token"
        def token = requester.request(refreshTokenURL, new JsonSlurper().parseText((myToken)).token + "X", POST)

        then: "user could not refresh wrong token"
        token.getResponseCode() == 500
    }
}
