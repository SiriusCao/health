package com.cao.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.cao.POJO.Member;
import com.cao.dao.MemberDao;
import com.cao.utils.MD5Utils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword()!=null){
            String md5 = MD5Utils.md5(member.getPassword());
            member.setPassword(md5);
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {
        List<Integer> memberCount=new ArrayList<>();
        for (String month : months) {
            month=month+"-31";
            Integer memberCountBeforeDate = memberDao.findMemberCountBeforeDate(month);
            memberCount.add(memberCountBeforeDate);
        }
        return memberCount;
    }
}
