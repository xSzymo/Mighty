package game.mightywarriors.web.rest.mighty.bookmarks.work;

import game.mightywarriors.configuration.system.variables.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.work.WorkerManager;
import game.mightywarriors.web.json.objects.bookmarks.ChampionInformer;
import game.mightywarriors.web.json.objects.bookmarks.WorkInformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkController {
    @Autowired
    private WorkerManager workerManager;

    @PostMapping("secure/work")
    public void fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody WorkInformer work) throws Exception {
        workerManager.setWorkForUser(authorization, work);
    }

    @PostMapping("secure/work/getPayment")
    public void getPayment(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        workerManager.getPayment(authorization);
    }

    @PostMapping("secure/work/cancel")
    public void cancel(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody ChampionInformer work) throws Exception {
        workerManager.cancelWork(authorization, work);
    }
}
