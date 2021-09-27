package com.liubike.spockdemo.service.impl;

import com.liubike.spockdemo.service.Publisher;
import com.liubike.spockdemo.service.Subscriber;

import java.util.List;

/**
 * @author yuanqunwang
 * created on 2021/9/24 11:17
 */
public class PublisherImpl implements Publisher {
    List<Subscriber> subscriberList;

    @Override
    public void send(String msg) {
        for(Subscriber subscriber : subscriberList) {
            subscriber.receive(msg);
            System.out.println(msg + " received");
        }
    }
}
