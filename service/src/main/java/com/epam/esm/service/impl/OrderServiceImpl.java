package com.epam.esm.service.impl;

import com.epam.esm.error.exception.GiftCertificateNotFoundException;
import com.epam.esm.error.exception.OrderNotFoundException;
import com.epam.esm.error.exception.UserNotFoundException;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final GiftCertificateDao giftCertificateDao;
    private final UserDao userDao;
    private final MessageSource messageSource;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, GiftCertificateDao giftCertificateDao, UserDao userDao, MessageSource messageSource) {
        this.orderDao = orderDao;
        this.giftCertificateDao = giftCertificateDao;
        this.userDao = userDao;
        this.messageSource = messageSource;
    }

    @Override
    public Order findById(long id) {
        return orderDao.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public List<Order> findAll(int amount, int page) {
        checkPagination(amount, page);
        return orderDao.findAll(amount, page - 1);
    }

    @Override
    @Transactional
    public Order create(Order order) {
        Order newOrder = new Order();
        long userId = order.getUser().getId();
        User user = userDao.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        newOrder.setUser(user);
        long certificateId = order.getCertificate().getId();
        GiftCertificate giftCertificate = giftCertificateDao.findById(certificateId)
                .orElseThrow(() -> new GiftCertificateNotFoundException(certificateId));
        newOrder.setCertificate(giftCertificate);
        newOrder.setPrice(giftCertificate.getPrice());
        return orderDao.create(newOrder);
    }

    @Override
    public List<Order> findUserOrders(long id, int amount, int page) {
        checkPagination(amount, page);
        return orderDao.findUserOrders(id, amount, page - 1);
    }
}
