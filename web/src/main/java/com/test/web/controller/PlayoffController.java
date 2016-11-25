package com.test.web.controller;

import com.test.data.domain.Playoff;
import com.test.data.model.PlayoffQo;
import com.test.data.repository.PlayoffRepository;
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
@RequestMapping("/playoff")
public class PlayoffController {
    private static Logger logger = LoggerFactory.getLogger(PlayoffController.class);

    @Autowired
    private PlayoffRepository playoffRepository;

    @RequestMapping("/index")
    public String index(ModelMap model) throws Exception{
        return "playoff/index";
    }

    @RequestMapping(value="/{id}")
    public String show(ModelMap model,@PathVariable Long id) {
        Playoff playoff = playoffRepository.findOne(id);
        model.addAttribute("playoff",playoff);
        return "playoff/show";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Playoff> getList(PlayoffQo playoffQo) {
        try {
            Pageable pageable = new PageRequest(playoffQo.getPage(), playoffQo.getSize(), null);
            Set<Playoff> playoffs = playoffRepository.findPlayoff(playoffQo.getYear(), playoffQo.getPage() * playoffQo.getSize(), playoffQo.getSize());
            int count = playoffRepository.findPlayoffCount(playoffQo.getYear());
            return new PageImpl(new ArrayList(playoffs), pageable, (long)count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model,Playoff playoff){
        model.addAttribute("playoff", playoff);
        return "playoff/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(Playoff playoff) throws Exception{
        playoffRepository.save(playoff);
        logger.info("新增->ID="+playoff.getId());
        return "1";
    }

    @RequestMapping(value="/edit/{id}")
    public String update(ModelMap model,@PathVariable Long id){
        Playoff playoff = playoffRepository.findOne(id);
        model.addAttribute("playoff",playoff);
        return "playoff/edit";
    }

    @RequestMapping(method = RequestMethod.POST, value="/update")
    @ResponseBody
    public String update(Playoff playoff) throws Exception{
        playoffRepository.save(playoff);
        logger.info("修改->ID="+playoff.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long id) throws Exception{
        playoffRepository.delete(id);
        logger.info("删除->ID=" + id);
        return "1";
    }

}
