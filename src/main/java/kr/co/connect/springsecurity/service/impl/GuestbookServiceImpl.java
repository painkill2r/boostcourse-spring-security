package kr.co.connect.springsecurity.service.impl;

import kr.co.connect.springsecurity.dao.GuestbookDao;
import kr.co.connect.springsecurity.dao.LogDao;
import kr.co.connect.springsecurity.dto.Guestbook;
import kr.co.connect.springsecurity.dto.Log;
import kr.co.connect.springsecurity.service.GuestbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GuestbookServiceImpl implements GuestbookService {

    @Autowired
    private GuestbookDao guestbookDao;

    @Autowired
    private LogDao logDao;

    @Override
    public int getCount() {
        return guestbookDao.selectCount();
    }

    @Override
    @Transactional //읽기만 하는 경우 readOnly=true 형태로 커넥션을 사용하도록 트랜잭션 설정(=> 트랙잭션 적용안함)
    public List<Guestbook> getGuestbooks(Integer start) {
        return guestbookDao.selectAll(start, GuestbookService.LIMIT);
    }

    @Override
    @Transactional(readOnly = false) //트랙잭션을 적용하기 위해 readOnly=false 설정
    public Guestbook addGuestbook(Guestbook guestbook, String ip) {
        guestbook.setRegdate(new Date());

        Long id = guestbookDao.insert(guestbook);

        guestbook.setId(id);

//        if (true)
//            throw new RuntimeException("트랜잭션 확인을 위한 강제 예외 발생...");

        Log log = new Log();
        log.setIp(ip);
        log.setMethod("INSERT");
        log.setRegdate(new Date());

        logDao.insert(log);

        return guestbook;
    }

    @Override
    @Transactional(readOnly = false) //트랙잭션을 적용하기 위해 readOnly=false 설정
    public int deleteGuestbook(Long id, String ip) {
        int deleteCount = guestbookDao.deleteById(id);

        Log log = new Log();
        log.setIp(ip);
        log.setMethod("DELEET");
        log.setRegdate(new Date());

        logDao.insert(log);

        return deleteCount;
    }
}
