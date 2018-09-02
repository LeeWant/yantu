package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.BankDetails;
import com.lee.yantu.Entity.PiggyBank;
import com.lee.yantu.Entity.User;
import com.lee.yantu.dto.SortDto;
import com.lee.yantu.enums.BankEnum;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.enums.YoosureEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.repository.BankDetailsRepository;
import com.lee.yantu.repository.PiggyBankRepository;
import com.lee.yantu.repository.UserRepository;
import com.lee.yantu.service.PiggyBankService;
import com.lee.yantu.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PiggyBankServiceImpl implements PiggyBankService {

    @Autowired
    PiggyBankRepository piggyBankRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankDetailsRepository bankDetailsRepository;

    @Override
    public PiggyBank save(PiggyBank piggyBank) {
        return piggyBankRepository.save(piggyBank);
    }

    @Override
    public List<PiggyBank> findNotFinish() {
        List<PiggyBank> piggyBanks = piggyBankRepository.findByIsSaveAndIsDelete(1,0);
        return piggyBanks;
    }

    @Override
    public List<PiggyBank> findByUserId(Integer userId) {
        List<PiggyBank> banks = piggyBankRepository.findByUserIdAndIsSaveNotIn(userId, 0);
        return banks;
    }

    @Override
    public Page findDetails(Integer bankId, Integer page) {
        return bankDetailsRepository.findByBankId(PageUtil.basicPage(page,15,new SortDto("opDate")),bankId);
    }

    @Override
    public PiggyBank findOne(Integer bankId) {
        return piggyBankRepository.findOne(bankId);
    }

    /**
     * 添加一个PiggyBank
     *
     * @param bank
     * @param bindingResult
     * @return
     */
    @Override
    public PiggyBank add(PiggyBank bank, BindingResult bindingResult) {
        User user = userRepository.findOne(bank.getUserId());
        //检查用户是否存在
        if (null == user || user.getIsDelete() == 1) {
            throw new ResultException(UserEnum.USER_NOT_FOUND);
        }
        //检查是否大于5个存钱罐
        List<PiggyBank> piggyBanks = piggyBankRepository.findByUserIdAndIsSave(bank.getUserId(), 1);
        if (piggyBanks.size() >= 5) {
            throw new ResultException(BankEnum.NO_MORE_BANK);
        }
        //检查格式是否有问题
        if (bindingResult.hasErrors()) {
            throw new ResultException(YoosureEnum.PUBLISH_ERR, bindingResult.getFieldError().getDefaultMessage());
        }
        bank.setCreDate(new Date());
        return piggyBankRepository.save(bank);
    }

    /**
     * 删除存钱罐
     *
     * @param bankId
     */
    @Override
    public void delete(Integer bankId) {
        PiggyBank piggyBank = piggyBankRepository.findOne(bankId);
        if (null != piggyBank) {
            piggyBank.setIsDelete(1);
            piggyBank.setIsSave(0);
        }
        piggyBankRepository.save(piggyBank);
    }

    /**
     * 存钱
     *
     * @return
     */
    @Override
    @Transactional
    public PiggyBank raiseMoney(Integer bankId, Integer raiseMoney) {
        PiggyBank piggyBank = piggyBankRepository.findOne(bankId);
        BigDecimal money = new BigDecimal(raiseMoney);
        //判断存钱罐是否存在
        if (null == piggyBank) {
            throw new ResultException(BankEnum.NOT_FIND);
            //判断是否被删除
        } else if (piggyBank.getIsDelete() == 1) {
            throw new ResultException(BankEnum.NOT_FIND);
        }
        //判断存钱罐是否可存（isSave为1）
        if (piggyBank.getIsSave() == 0 || piggyBank.getIsSave() == 2) {
            throw new ResultException(BankEnum.CAN_NOT_SAVE);
        }
        //判断存钱金额是否大于0小于100,000
        if (raiseMoney <= 0 && raiseMoney >= 100000) {
            throw new ResultException(BankEnum.NEED_RIGHT_NUM);
        }
        //如果 当前金额+存款金额 >= 目标金额，将isSave变为2
        BigDecimal newMoney = piggyBank.getMoney().add(money);
        if (newMoney.compareTo(piggyBank.getTargetMoney()) >= 0) {
            piggyBank.setIsSave(2);
        }
        //piggyBank更新
        piggyBank.setMoney(newMoney);
        //bankDetails入库
        BankDetails bankDetails = new BankDetails();
        bankDetails.setMoney(money);
        bankDetails.setBankId(piggyBank.getBankId());
        bankDetails.setBalance(newMoney);
        bankDetailsRepository.save(bankDetails);
        return piggyBankRepository.save(piggyBank);
    }

    /**
     * 取钱
     *
     * @param bankId
     * @param reduceMoney
     * @return
     */
    @Override
    @Transactional
    public PiggyBank reduceMoney(Integer bankId, Integer reduceMoney) {
        PiggyBank piggyBank = piggyBankRepository.findOne(bankId);
        BigDecimal money = new BigDecimal(reduceMoney);
        //判断存钱罐是否存在
        if (null == piggyBank) {
            throw new ResultException(BankEnum.NOT_FIND);
            //判断是否被删除
        } else if (piggyBank.getIsDelete() == 1) {
            throw new ResultException(BankEnum.NOT_FIND);
        }
        //判断存钱罐是否可取（isSave为1）
        if (piggyBank.getIsSave() == 0 || piggyBank.getIsSave() == 2) {
            throw new ResultException(BankEnum.CAN_NOT_SAVE);
        }
        //取出金额不能小于已存金额
        if (piggyBank.getMoney().compareTo(money) < 0) {
            throw new ResultException(BankEnum.CAN_NOT_REDUCE);
        }
        //减少已存金额
        BigDecimal newMoney = piggyBank.getMoney().subtract(money);
        piggyBank.setMoney(newMoney);
        //bankDetails入库
        BankDetails bankDetails = new BankDetails();
        bankDetails.setMoney(new BigDecimal(-reduceMoney));
        bankDetails.setBankId(piggyBank.getBankId());
        bankDetails.setBalance(newMoney);
        bankDetailsRepository.save(bankDetails);
        return piggyBankRepository.save(piggyBank);
    }
}
