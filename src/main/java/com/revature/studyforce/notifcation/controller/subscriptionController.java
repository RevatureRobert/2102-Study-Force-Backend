package com.revature.studyforce.notifcation.controller;

import com.revature.studyforce.notifcation.model.StackTraceSubscription;
import com.revature.studyforce.notifcation.service.StackTraceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class subscriptionController {

    private final StackTraceService stackTraceService;

    public subscriptionController(StackTraceService stackTraceService) {
        this.stackTraceService = stackTraceService;
    }

    @GetMapping(value = "/subscription")
    public @ResponseBody
    List<StackTraceSubscription> findSubs() {
        return this.stackTraceService.findSubs();
    }

    /**
     * Finds all the subscription tied to a user
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/user/subscription")
    public @ResponseBody
    List<StackTraceSubscription> findUsers(@RequestParam int id) {
        return this.stackTraceService.findByUser(id);
    }

    /**
     * find all the users tied to a stack Trace
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/subscription/users")
    public @ResponseBody
    List<StackTraceSubscription> findSubs(@RequestParam int id) {
        return this.stackTraceService.findByStackTrace(id);
    }

    /**
     * Add a new subscription
     *
     * @param stackTraceSubscription
     * @return
     */
    @PostMapping(value = "/subscription")
    public @ResponseBody
    StackTraceSubscription addSub(@RequestBody StackTraceSubscription stackTraceSubscription) {
        return this.stackTraceService.add(stackTraceSubscription);
    }

    /**
     * Removes a single subscription
     *
     * @param stackTraceSubscription
     * @return
     */
    @DeleteMapping(value = "/subscription")
    public @ResponseBody
    int remove(@RequestBody StackTraceSubscription stackTraceSubscription) {

        this.stackTraceService.remove(stackTraceSubscription);
        return 1;
    }

    /**
     * Removes all the subscriptions tied to a certain stack trace
     *
     * @param stackId
     * @return
     */
    @DeleteMapping(value = "/subscription/bye")
    public @ResponseBody
    int removeBySub(@RequestParam int stackId) {
        this.stackTraceService.removeAllFromStackTrace(stackId);
        return 1;
    }


}
