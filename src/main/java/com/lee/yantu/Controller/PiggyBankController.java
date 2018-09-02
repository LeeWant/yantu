package com.lee.yantu.Controller;

import com.lee.yantu.Entity.PiggyBank;
import com.lee.yantu.VO.Result;
import com.lee.yantu.service.PiggyBankService;
import com.lee.yantu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PiggyBankController {
    @Autowired
    PiggyBankService piggyBankService;

    /**
     * 添加一个存钱罐
     * @param piggyBank
     * @param bindingResult
     * @return
     */
    @PostMapping("/piggyBank")
    public Result add(@Valid PiggyBank piggyBank, BindingResult bindingResult){
        return ResultUtil.success(piggyBankService.add(piggyBank,bindingResult));
    }

    /**
     * 删除一个存钱罐
     * @param bankId
     * @return
     */
    @DeleteMapping("/piggyBank/{bankId}")
    public Result delete(@PathVariable("bankId") Integer bankId){
        piggyBankService.delete(bankId);
        return ResultUtil.success();
    }
    /**
     * 获取存钱罐通过userId
     * @param userId
     * @return
     */
    @GetMapping("/piggyBanks/{userId}")
    public Result findByUserId(@PathVariable("userId") Integer userId){
        List<PiggyBank> banks = piggyBankService.findByUserId(userId);
        return ResultUtil.success(banks);
    }

    /**
     * 获取一个存钱罐
     * @param bankId
     * @return
     */
    @GetMapping("/piggyBank/{bankId}")
    public Result find(@PathVariable("bankId") Integer bankId){
        return ResultUtil.success(piggyBankService.findOne(bankId));
    }

    @PostMapping("/piggyBank/add")
    public Result addMoney(@RequestParam("money") Integer money,
                           @RequestParam("bankId") Integer bankId){
        return ResultUtil.success(piggyBankService.raiseMoney(bankId,money));
    }

    @PostMapping("/piggyBank/sub")
    public Result subMoney(@RequestParam("money") Integer money,
                           @RequestParam("bankId") Integer bankId){
        return ResultUtil.success(piggyBankService.reduceMoney(bankId,money));
    }


    @GetMapping("/piggyBank/getDetails/{bankId}/{page}")
    public Result getDetails(@PathVariable("bankId")Integer bankId,
                             @PathVariable("page")Integer page){
       return ResultUtil.success(piggyBankService.findDetails(bankId,page));
    }

}
