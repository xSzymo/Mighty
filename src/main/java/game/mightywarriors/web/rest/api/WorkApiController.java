package game.mightywarriors.web.rest.api;


import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.data.services.WorkService;
import game.mightywarriors.data.tables.User;
import game.mightywarriors.data.tables.Work;
import game.mightywarriors.services.security.UsersRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class WorkApiController {
    @Autowired
    private WorkService service;
    @Autowired
    private UsersRetriever retriever;

    @GetMapping("secure/works")
    public Set<Work> getMissionFights(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        User user = retriever.retrieveUser(authorization);
        return service.find(user.getLogin());
    }
}
