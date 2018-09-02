package com.lee.yantu.service.Impl;

import com.lee.yantu.Entity.UserDefinedTag;
import com.lee.yantu.repository.UserDefinedTagRepository;
import com.lee.yantu.service.UserDefinedTagService;
import com.lee.yantu.enums.SystemEnum;
import com.lee.yantu.enums.UserEnum;
import com.lee.yantu.exception.ResultException;
import com.lee.yantu.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDefinedTagImpl implements UserDefinedTagService {
    @Autowired
    private UserDefinedTagRepository udtRepository;

    @Override
    public UserDefinedTag findOne(Integer tagId) {
        return udtRepository.findOne(tagId);
    }

    @Override
    public UserDefinedTag save(UserDefinedTag tag) {
        return udtRepository.save(tag);
    }

    @Override
    public List<UserDefinedTag> findByUserId(Integer userId) {
        return udtRepository.findByUserId(userId);
    }

    /**
     * 添加用户自定义标签(字符串为""则删除该用户定义的所有标签,为null则不运行)
     * @param tagInfo
     */
    @Override
    @Transactional
    public void addUserDefinedTag(Integer userId,String tagInfo) {
        if (null != tagInfo && !"".equals(tagInfo)) {
            //切割字符串以";"为标志
            String[] tags = tagInfo.split(";");
            List<UserDefinedTag> tagList = new ArrayList<>();
            //每个用户只能自己定义五个标签
            if (tags.length < 6) {
                //检查标签是否符合标准
                for (String tag : tags) {
                    if (CheckUtil.checkTagInfo(tag)) {
                        UserDefinedTag udTag = new UserDefinedTag();
                        udTag.setTagInfo(tag);
                        udTag.setUserId(userId);
                        tagList.add(udTag);
                    } else throw new ResultException(SystemEnum.TAG_FORMAT_ERR);
                }
                //添加之前删除用户原来定义的所有标签
                udtRepository.deleteAllByUserId(userId);
                //批量添加
                udtRepository.save(tagList);
            } else throw new ResultException(UserEnum.TAG_LIMIT);
        } else if("".equals(tagInfo)){
            //字符串为空,则删除用户定义的所有标签
            udtRepository.deleteAllByUserId(userId);
        }
    }


}
