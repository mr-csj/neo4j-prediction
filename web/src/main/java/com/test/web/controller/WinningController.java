package com.test.web.controller;

import com.test.data.domain.Playoff;
import com.test.data.domain.Team;
import com.test.data.model.TeamQo;
import com.test.data.repository.PlayoffRepository;
import com.test.data.repository.RatioRepository;
import com.test.data.repository.WinningRepository;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/winning")
public class WinningController {
    private static Logger logger = LoggerFactory.getLogger(WinningController.class);

    @Autowired
    private RatioRepository ratioRepository;
    @Autowired
    private PlayoffRepository playoffRepository;
    @Autowired
    private WinningRepository winningRepository;

    @RequestMapping("/index")
    public String index(ModelMap model) throws Exception{
        Iterable<Team> teams = ratioRepository.findAll();
        model.addAttribute("teams",teams);
        return "winning/index";
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Page<Map<String,Object>> getList(TeamQo teamQo) {
        try {
            Pageable pageable = new PageRequest(teamQo.getPage(), teamQo.getSize(), null);
            Set<Map<String,Object>> maps = ratioRepository.findHistoryByTeamName(teamQo.getName(), teamQo.getPage() * teamQo.getSize(), teamQo.getSize());
            int count = ratioRepository.findHistoryByTeamNameCount(teamQo.getName());
            return new PageImpl(new ArrayList(maps), pageable, (long)count);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/new")
    public String create(ModelMap model){
        Iterable<Team> teams = ratioRepository.findAll();
        Iterable<Playoff> playoffs = playoffRepository.findPlayoff();
        model.addAttribute("teams", teams);
        model.addAttribute("playoffs", playoffs);
        return "winning/new";
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(HttpServletRequest request) throws Exception{
        String t1 = request.getParameter("t1");
        String t2 = request.getParameter("t2");
        String win = request.getParameter("win");
        String loss = request.getParameter("loss");
        String pid = request.getParameter("pid");

        Playoff playoff = playoffRepository.findOne(new Long(pid));

        Team team1 = ratioRepository.findOne(new Long(t1));
        team1.win(new Integer(win), playoff);
        ratioRepository.save(team1);

        Team team2 =  ratioRepository.findOne(new Long(t2));
        team2.win(new Integer(loss), playoff);
        ratioRepository.save(team2);

        logger.info("新增->ID="+playoff.getId());
        return "1";
    }

    @RequestMapping(value="/delete/{wid}/{lid}",method = RequestMethod.GET)
    @ResponseBody
    public String delete(@PathVariable Long wid, @PathVariable Long lid) throws Exception{
        if(wid != null) {
            winningRepository.delete(wid);
            logger.info("删除->WID=" + wid);
        }
        if(lid != null){
            winningRepository.delete(lid);
            logger.info("删除->LID=" + lid);
        }
        return "1";
    }

}
