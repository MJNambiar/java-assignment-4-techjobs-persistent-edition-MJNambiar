package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;
    //may not need this

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("jobs", jobRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute("employers", employerRepository.findAll());
        model.addAttribute("skills", skillRepository.findAll());
        model.addAttribute(new Job());
        return "add";
    }

    @PostMapping("add")
        public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                Errors errors, Model model, @RequestParam int employerId, @RequestParam(required = false) List<Integer> skills) {

        if (errors.hasErrors() || skills == null) {
            model.addAttribute("title", "Add Job");
            model.addAttribute("employers", employerRepository.findAll());
            model.addAttribute("skills", skillRepository.findAll());
            return "add";

            } else {
            List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
                model.addAttribute("skills", skillObjs);
                newJob.setSkills(skillObjs);

            Optional optEmployer = employerRepository.findById(employerId);
                if (optEmployer.isPresent()) {
                    Employer employer = (Employer) optEmployer.get();
                    model.addAttribute("employers", employer);
                    newJob.setEmployer(employer);
                }
                jobRepository.save(newJob);
                model.addAttribute("job", newJob);

                return "redirect:";
            }
        }


    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional optJob = jobRepository.findById(jobId);


        if (optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            return "view";
        } else {
            return "redirect:../";
        }

    }

}
