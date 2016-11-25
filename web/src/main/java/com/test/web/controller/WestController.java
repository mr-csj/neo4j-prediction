package com.test.web.controller;

import com.test.data.domain.West;
import com.test.data.model.TeamQo;
import com.test.data.repository.WestTeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Set;

@Controller
@RequestMapping("/west")
public class WestController {
    private static Logger logger = LoggerFactory.getLogger(WestController.class);

    @Autowired
    WestTeamRepository westTeamRepository;

    @RequestMapping("/index")
    public String index(ModelMap model) throws Exception{
        return "west/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        West west = westTeamRepository.findOne(id);
        model.addAttribute("team",west);
        return "west/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<West> getList(TeamQo teamQo) {
        try {
            Pageable pageable = new PageRequest(teamQo.getPage(), teamQo.getSize(), null);
            Set<West> wests = westTeamRepository.findWest(teamQo.getName(), teamQo.getPage() * teamQo.getSize(), teamQo.getSize());
            int count = westTeamRepository.findWestCount(teamQo.getName());
            return new PageImpl(new ArrayList(wests), pageable, (long)count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,West west){
        model.addAttribute("team", west);
        return "west/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(West west) throws Exception{
        westTeamRepository.save(west);
        logger.info("新增->ID="+west.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        West west = westTeamRepository.findOne(id);
        model.addAttribute("team",west);
        return "west/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(West west) throws Exception{
        westTeamRepository.save(west);
        logger.info("修改->ID="+west.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        westTeamRepository.delete(id);
        logger.info("删除->ID=" + id);
        return "1";
    }

}
