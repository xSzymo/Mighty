package acceptance.web.rest.bookmarks.tavern

import acceptance.Requester
import groovy.json.JsonSlurper
import org.junit.Test


class WorkControllerTest extends GroovyTestCase {

    private String baseURL = "http://localhost:8080"
    private String tokenURL = baseURL + "/token"
    private String checkChampionURL = baseURL + "/secure/check/champion"
    private String getPayment = baseURL + "/secure/work/getPayment"
    private String cancelWork = baseURL + "/secure/work/cancel"
    private String workURL = baseURL + "/secure/work"
    private String currentUserURL = baseURL + "/secure/getCurrentUser"
    private String loginJSON

    private Requester requester = new Requester()
    private String GET = "GET"
    private String POST = "POST"

    private long championId
    private long championId1
    private long championId2
    private String myToken
    private String myJson

    @Test
    void testCancel() {
        loginJSON = '{ "login": "admin0", "password": "admin"}'
        runCancel(1, 3)
    }


    void runCancel(int howManyChampions, int hours) {
        def currentUser = 'log in and set champion & mission id'(loginJSON)
        'set up json to send champions request'(howManyChampions, hours)

        cancelWork(myJson)

        currentUser = 'log in and set champion & mission id'(loginJSON)
        assertEquals([null, null, null], parser(currentUser).champions.blockUntill)
    }

    void run(int howManyChampions, int hours) {
        def currentUser = 'log in and set champion & mission id'(loginJSON)
        'set up json to send champions request'(howManyChampions, hours)
        long gold = parser(currentUser).gold

        work(myJson).getInputStream().getText()

//        getPayment()
//
//        currentUser = 'log in and set champion & mission id'(loginJSON)
//        long gold2 = parser(currentUser).gold
//
//        def checkLeftTimeResponseFirst = checkChampion(myJson)
//        if (parser(checkLeftTimeResponseFirst).leftTime > 0)
//            sleep(parser(checkLeftTimeResponseFirst).leftTime * ONE_SECOND + ONE_SECOND)
//
//        getPayment()
//
//        currentUser = 'log in and set champion & mission id'(loginJSON)
//        long gold1 = parser(currentUser).gold
//
//        assertEquals(gold, gold2)
//        assertEquals(gold + (1 * howManyChampions * 10 * hours), gold1)
    }


    private def 'log in and set champion & mission id'(String loginJSON) {
        this.loginJSON = loginJSON
        myToken = getAuthenticatedToken()
        def currentUser = getCurrentUser()
        setUpChampionAndMission(currentUser)
        return currentUser
    }

    private 'set up json to send champions request'(int howManyChamps, int hours) {
        if (howManyChamps == 1)
            myJson = '{ "championId":[' + championId + '], "hours": ' + hours + '}'
        else if (howManyChamps == 2)
            myJson = '{ "championId":[' + championId2 + ', ' + championId1 + '], "hours": ' + hours + '}'
        else
            throw new Exception("choose number of champion between 1-2")
    }

    private 'set up json to send champions request'(long howManyChamps) {
        myJson = '{ "hours": ' + howManyChamps + '}'
    }

    private void setUpChampionAndMission(def currentUser) {
        setUpChampionId(currentUser)
    }

    private void setUpChampionId(String text) {
        championId = parser(text).champions.id[0]
        championId1 = parser(text).champions.id[1]
        championId2 = parser(text).champions.id[2]
    }

    private def getArrayOfChampionsId(def sendChampionResponse) {
        long[] myArray = new long[parser(sendChampionResponse).champions.size]

        for (int i = 0; i < myArray.size(); i++)
            myArray[i] = parser(sendChampionResponse).champions[i].id

        return myArray
    }

    private def parser(String text) {
        return new JsonSlurper().parseText(text)
    }

    private String getAuthenticatedToken() {
        return requester.request(tokenURL, loginJSON).getInputStream().getText()
    }

    private checkChampion(String myJson) {
        return requester.request(checkChampionURL, parser(myToken).token, POST, myJson).getInputStream().getText()
    }

    private getCurrentUser() {
        return requester.request(currentUserURL, parser(myToken).token, GET).getInputStream().getText()
    }

    private def work(String myJson) {
        return requester.request(workURL, parser(myToken).token, POST, myJson)
    }

    private def cancelWork(String myJson) {
        return requester.request(cancelWork, parser(myToken).token, POST, myJson)
    }

    private def getPayment() {
        return requester.request(getPayment, parser(myToken).token, POST)
    }
}
