package com.lendico.plangenerator.controller;

import com.lendico.plangenerator.domainobject.Repayment;
import com.lendico.plangenerator.domainobject.RepaymentPlan;
import com.lendico.plangenerator.service.repayment.RepaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * A web controller for generating repayment plans.
 *
 * @author anil
 */
@Controller
@RequestMapping("/generate-plan")
public class RepaymentPlanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepaymentPlanController.class);
    @Autowired
    private RepaymentService service;

    @PostMapping
    public @ResponseBody
    List<Repayment> generatePlan(
            @RequestBody RepaymentPlanRequest request) {
        LOGGER.info("POST /generate-plan");

        /* Obtain the request parameters. */
        BigDecimal loanAmount = request.getLoanAmount();
        Double nominalRate = request.getNominalRate();
        Integer duration = request.getDuration();

        Date startDate = request.getStartDate();
        Instant instant = startDate.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localStartDate = LocalDateTime.ofInstant(instant, zone);

        /* Delegates the execution to the service. */
        RepaymentPlan plan = service.generatePlan(loanAmount, nominalRate,
                duration, localStartDate);
        List<Repayment> repayments = plan.getRepayments();

        return repayments;
    }
}