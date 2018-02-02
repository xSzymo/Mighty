package acceptance.web.rest.bookmarks.tavern

import acceptance.Requester
import groovy.json.JsonSlurper

class ArenaControllerTest extends GroovyTestCase {

    private String baseURL = "http://localhost:8080"
    private String tokenURL = baseURL + "/token"
    private String checkChampionURL = baseURL + "/secure/check/champion"
    private String checkRankings = baseURL + "/rankings/"
    private String fightURL = baseURL + "/secure/arena/fight"
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
    private String opponentName
    private int ONE_SECOND = 1000

    void testFight() {
        loginJSON = '{ "login": "user3", "password": "user"}'
        opponentName = "user1"
        run(3)
    }

    void run(int howManyChampions) {
        def currentUser = 'log in and set champion & mission id'(loginJSON)
        'set up json to send champions request'(howManyChampions)

        long opponentRanking = parser(getRanking(opponentName)).ranking

        def checkLeftTimeResponseFirst = checkChampion(myJson)

        if (parser(checkLeftTimeResponseFirst).leftTime > 0)
            sleep(parser(checkLeftTimeResponseFirst).leftTime * ONE_SECOND + ONE_SECOND)

        String fightJSON = fight(myJson).getInputStream().getText()

        assertEquals(opponentRanking, parser(getRanking(parser(loginJSON).login)).ranking)
        assertEquals(2, parser('log in and set champion & mission id'(loginJSON)).arenaPoints)
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
            myJson = '{ "championId":[' + championId + '], "opponentName": ' + "\"" + opponentName + "\"" + '}'
        else if (howManyChamps == 2)
            myJson = '{ "championId":[' + championId + ', ' + championId1 + '], "opponentName": ' + "\"" + opponentName + "\"" + '}'
        else if (howManyChamps == 3)
            myJson = '{ "championId":[' + championId + ', ' + championId1 + ', ' + championId2 + '], "opponentName": ' + "\"" + opponentName + "\"" + '}'
        else
            throw new Exception("choose number of champion between 1-3")
    }


    private def parser(String text) {
        return new JsonSlurper().parseText(text)
    }

    private void setUpChampionAndMission(def currentUser) {
        setUpChampionId(currentUser)
    }

    private void setUpChampionId(String text) {
        championId = parser(text).champions.id[0]
        championId1 = parser(text).champions.id[1]
        championId2 = parser(text).champions.id[2]
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

    private getRanking(String name) {
        return requester.request(checkRankings + name, parser(myToken).token, GET).getInputStream().getText()
    }

    private def fight(String myJson) {
        return requester.request(fightURL, parser(myToken).token, GET, myJson)
    }
}
