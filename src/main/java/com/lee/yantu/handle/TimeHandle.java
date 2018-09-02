package com.lee.yantu.handle;


import com.lee.yantu.Entity.PiggyBank;
import com.lee.yantu.Entity.Yoosure;
import com.lee.yantu.repository.YoosureRepository;
import com.lee.yantu.service.PiggyBankService;
import com.lee.yantu.service.UserService;
import com.lee.yantu.service.YoosureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeHandle {
    @Autowired
    private YoosureService yoosureService;

    @Autowired
    private PiggyBankService piggyBankService;

    /**
     * 每天0点检查yoosure并关闭过期的yoosure
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkYoosure() {
        //查找所有能够加入的游学
        List<Yoosure> yoosures = yoosureService.findAllNotFinish();
        for (Yoosure yoosure : yoosures) {
            //如果出发时间小于系统时间则关闭游学
            if (yoosure.getGoDate().getTime() <= System.currentTimeMillis()) {
                System.out.println("################# close #####################");
                yoosure.setIsJoin(0);
                yoosureService.save(yoosure);
            }
            if (yoosure.getGoDate().getTime() + 24 * 60 * 60 * 1000 <= System.currentTimeMillis()) {
                System.out.println("################# finish #####################");
                yoosureService.finish(yoosure);
            }
        }
    }

    /**
     * 每天0点关闭过期的存钱罐
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkBank(){
        List<PiggyBank> piggyBanks = piggyBankService.findNotFinish();
        for(PiggyBank piggyBank : piggyBanks){
            //如果小于当前时间则关闭存钱罐
            if (piggyBank.getTargetDate().getTime() <= System.currentTimeMillis()){
                piggyBank.setIsSave(0);
                piggyBankService.save(piggyBank);
            }
        }
    }
}
