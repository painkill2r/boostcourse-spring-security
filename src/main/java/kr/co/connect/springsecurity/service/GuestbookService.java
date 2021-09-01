package kr.co.connect.springsecurity.service;

import kr.co.connect.springsecurity.dto.Guestbook;

import java.util.List;

public interface GuestbookService {

    static final Integer LIMIT = 5;

    int getCount();

    List<Guestbook> getGuestbooks(Integer start);

    Guestbook addGuestbook(Guestbook guestbook, String ip);

    int deleteGuestbook(Long id, String ip);
}
