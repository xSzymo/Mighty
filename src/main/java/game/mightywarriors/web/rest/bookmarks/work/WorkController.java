package game.mightywarriors.web.rest.bookmarks.work;

import game.mightywarriors.configuration.system.SystemVariablesManager;
import game.mightywarriors.services.bookmarks.work.WorkerManager;
import game.mightywarriors.web.json.objects.bookmarks.tavern.MissionFightInformer;
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
    public void fight(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer work) throws Exception {
        workerManager.setWorkForUser(authorization, work);
    }

    @PostMapping("secure/work/getPayment")
    public void getPayment(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization) throws Exception {
        workerManager.getPayment(authorization);
    }

    @PostMapping("secure/work/cancel")
    public void cancel(@RequestHeader(value = SystemVariablesManager.NAME_OF_JWT_HEADER_TOKEN) String authorization, @RequestBody MissionFightInformer work) throws Exception {
        workerManager.cancelWork(authorization, work);
    }
}
