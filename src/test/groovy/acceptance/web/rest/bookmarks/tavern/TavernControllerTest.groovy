package acceptance.web.rest.bookmarks.tavern

import acceptance.Requester
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

import static junit.framework.Assert.assertEquals
import static org.junit.Assert.assertNotEquals

class TavernControllerTest extends GroovyTestCase {

    private String baseURL = "http://localhost:8080"
    private String tokenURL = baseURL + "/token"
    private String sendURL = baseURL + "/secure/tavern/send"
    private String checkChampionURL = baseURL + "/secure/check/champion"
    private String getMissionFightsURL = baseURL + "/secure/getMissionFights"
    private String fightURL = baseURL + "/secure/tavern/fight"
    private String currentUserURL = baseURL + "/secure/getCurrentUser"
    private String loginJSON

    private Requester requester = new Requester()
    private String GET = "GET"
    private String POST = "POST"

    private long championId
    private long championId1
    private long championId2
    private long missionId
    private String myToken
    private String myJson
    private int ONE_SECOND = 1000

    void testFight() {
        loginJSON = '{ "login": "admin0", "password": "admin"}'
        run(1)

        loginJSON = '{ "login": "admin0", "password": "admin"}'
        run(3)

        loginJSON = '{ "login": "admin2", "password": "admin"}'
        run(2)
    }

    void run(int howManyChampions) {
        def currentUser = 'log in and set champion & mission id'(loginJSON)
        'set up json to send champions request'(howManyChampions)

        def sendChampionResponse = send(myJson)
        def usersMissionFight = getMissionFights()
        def checkLeftTimeResponseFirst = checkChampion(myJson)

        if(parser(checkLeftTimeResponseFirst).leftTime > 0)
        sleep(parser(checkLeftTimeResponseFirst).leftTime * ONE_SECOND + ONE_SECOND)

        def checkLeftTimeResponseSecond = checkChampion(myJson)

        myJson = 'set up json to fight requesst'(sendChampionResponse)
        String fightJSON = fight(myJson).getInputStream().getText()

        assertTrue(parser(checkLeftTimeResponseSecond).leftTime <= 0)
        assertEquals(parser(usersMissionFight).champion[0].size(), howManyChampions)
        assertEquals(parser(usersMissionFight).id[0], parser(sendChampionResponse).id)
        for (def x : parser(sendChampionResponse).champion)
            assertTrue(x.id in parser(usersMissionFight).champion[0].id)

        assertEquals(parser(usersMissionFight).mission[0].id, parser(sendChampionResponse).mission.id)
        assertEquals(parser(fightJSON).winner.id, parser(currentUser).id)
        assertNotEquals(parser(currentUser).gold, parser('log in and set champion & mission id'(loginJSON)).gold)
    }

    private def 'log in and set champion & mission id'(String loginJSON) {
        this.loginJSON = loginJSON
        myToken = getAuthenticatedToken()
        def currentUser = getCurrentUser()
        setUpChampionAndMission(currentUser)
        return currentUser
    }

    private 'set up json to send champions request'(long howManyChamps) {
        if (howManyChamps == 1)
            myJson = '{ "championId":[' + championId + '], "missionId": ' + missionId + '}'
        else if (howManyChamps == 2)
            myJson = '{ "championId":[' + championId + ', ' + championId1 + '], "missionId": ' + missionId + '}'
        else if (howManyChamps == 3)
            myJson = '{ "championId":[' + championId + ', ' + championId1 + ', ' + championId2 + '], "missionId": ' + missionId + '}'
        else
            throw new Exception("choose number of champion between 1-3")
    }

    private def 'set up json to fight requesst'(def sendChampionResponse) {
        def json = new JsonBuilder()

        json {
            id parser(sendChampionResponse).id
            missionId parser(sendChampionResponse).mission.id
            championId getArrayOfChampionsId(sendChampionResponse)
        }

        return json
    }

    private def getArrayOfChampionsId(def sendChampionResponse) {
        long[] myArray = new long[parser(sendChampionResponse).champion.size]

        for (int i = 0; i < myArray.size(); i++)
            myArray[i] = parser(sendChampionResponse).champion[i].id

        return myArray
    }

    private def parser(String text) {
        return new JsonSlurper().parseText(text)
    }

    private void setUpChampionAndMission(def currentUser) {
        setUpChampionId(currentUser)
        setUpMissionId(currentUser)
    }

    private void setUpChampionId(String text) {
        championId = parser(text).champions.id[0]
        championId1 = parser(text).champions.id[1]
        championId2 = parser(text).champions.id[2]
    }

    private void setUpMissionId(String text) {
        missionId = parser(text).missions.id[0]
    }

    private String getAuthenticatedToken() {
        return requester.request(tokenURL, loginJSON).getInputStream().getText()
    }

    private String getMissionFights() {
        return requester.request(getMissionFightsURL, parser(myToken).token, GET).getInputStream().getText()
    }

    private checkChampion(String myJson) {
        return requester.request(checkChampionURL, parser(myToken).token, GET, myJson).getInputStream().getText()
    }

    private getCurrentUser() {
        return requester.request(currentUserURL, parser(myToken).token, GET).getInputStream().getText()
    }

    private def send(String myJson) {
        return requester.request(sendURL, parser(myToken).token, POST, myJson).getInputStream().getText()
    }

    private def fight(String myJson) {
        return requester.request(fightURL, parser(myToken).token, GET, myJson)
    }
}
