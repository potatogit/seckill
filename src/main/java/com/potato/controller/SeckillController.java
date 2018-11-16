package com.potato.controller;

import com.potato.Exception.RepeatSeckillException;
import com.potato.Exception.SeckillCloseException;
import com.potato.dto.Exposer;
import com.potato.dto.SeckillExecution;
import com.potato.dto.SeckillResult;
import com.potato.entity.Seckill;
import com.potato.service.SeckillService;
import enums.SeckillStatEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/seckill")
public class SeckillController {

	@Autowired
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("list", seckillList);
		// list.jsp + model = ModelAndView
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId,  Model model) {
		if(seckillId == null) {
			return "redirect: /seckill/list";
		}
		Seckill seckill = seckillService.getSeckillById(seckillId);
		if(Objects.isNull(seckill)) {
			return "forward:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.GET,
	produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public SeckillResult<Exposer> expose(@PathVariable("seckillId") Long seckillId) {
		SeckillResult<Exposer> result;
		try {
			Exposer exposer = seckillService.exposeSeckillUrl(seckillId);
			result = new SeckillResult<>(true, exposer);
		} catch (Exception e) {
			result = new SeckillResult<>(false, e.getMessage());
			log.error("expose error", e);
		}
		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST,
	produces="application/json;charset=UTF-8")
	@ResponseBody
	public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
	                                               @PathVariable("md5") String md5,
	                                               @CookieValue("userPhone") Long userPhone) {
		SeckillResult<SeckillExecution> result;
		SeckillExecution seckillExecution;
		if(userPhone == null) {
			result = new SeckillResult<>(false, "user is not registered");
			return result;
		}
		try {
			seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
			result =  new SeckillResult<>(true, seckillExecution);
		} catch (RepeatSeckillException e1) {
			seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			result = new SeckillResult<>(true, seckillExecution);
		} catch (SeckillCloseException e2) {
			seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END);
			result = new SeckillResult<>(false, seckillExecution);
		} catch (Exception e) {
			log.error("execute error", e);
			seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			result = new SeckillResult<>(false, seckillExecution);
		}
		return result;
	}

	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time() {
		return new SeckillResult<>(true, System.currentTimeMillis());
	}

}
