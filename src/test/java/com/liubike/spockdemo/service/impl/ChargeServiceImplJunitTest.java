package com.liubike.spockdemo.service.impl;

import com.google.common.collect.ImmutableMap;
import com.liubike.customer.core.common.dto.config.ChargeDto;
import com.liubike.spockdemo.service.ChargeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map;

public class ChargeServiceImplJunitTest {
    private ChargeDto chargeDto;

    private ChargeService chargeService;

    @Before
    public void before() {
        chargeDto = new ChargeDto();
        Map<Integer, BigDecimal> gradBasicTime = ImmutableMap.of(25, BigDecimal.ONE, 50, BigDecimal.ONE, 5, BigDecimal.ONE);
        chargeDto.setGradBasicMoney(gradBasicTime);
        chargeService = new ChargeServiceImpl();
    }

    ///////////////////////////////gradBasic////////////////////////////
    @Test
    public void whenGradBasicTimeBelowFreeTimeTest() {
        long ridingSeconds = chargeDto.getFreeTime() - 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(BigDecimal.ZERO));

    }
    @Test
    public void whenRidingMin_0_5_Test() {
        long ridingSeconds = chargeDto.getFreeTime() + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(BigDecimal.ONE));
    }


    @Test
    public void whenRidingMin5Test() {
        long ridingSeconds = 5 * 60 + chargeDto.getExceedTime();
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(BigDecimal.ONE));
    }

    @Test
    public void whenRidingMin5_25Test() {
        long ridingSeconds = 5 * 60 + chargeDto.getExceedTime() + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("2")));
    }

    @Test
    public void whenRidingMin5_25UpperTest() {
        long ridingSeconds = 25 * 60 + chargeDto.getExceedTime();
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("2")));
    }


    @Test
    public void whenRidingMin25_50LowerTest() {
        long ridingSeconds = 25 * 60 + chargeDto.getExceedTime() + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("3")));
    }

    @Test
    public void whenRidingMin25_50UpperTest() {
        long ridingSeconds = 50 * 60 + chargeDto.getExceedTime();
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("3")));
    }

    @Test
    public void whenExceed50MinTest() {
        long ridingSeconds = 50 * 60 + chargeDto.getExceedTime() + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("4")));
    }

    @Test
    public void whenExceed50MinUpperTest() {
        long ridingSeconds = 50 * 60 + chargeDto.getExceedTime() + chargeDto.getAddTime() * 60 * 4 + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("8")));
    }

    ////////////////////////////////////basic///////////////////

    @Test
    public void whenBelowFreeTime() {
        chargeDto.setGradBasicMoney(null);
        long ridingSeconds = chargeDto.getFreeTime() - 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(BigDecimal.ZERO));

    }

    @Test
    public void whenBasicTest() {
        chargeDto.setGradBasicMoney(null);
        long ridingSeconds = chargeDto.getFreeTime() + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("1")));
    }

    @Test
    public void whenBasic1_5Test() {
        chargeDto.setGradBasicMoney(null);
        long ridingSeconds = chargeDto.getBasicTime() * 60 + chargeDto.getExceedTime();
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("1")));
    }

    @Test
    public void whenExceedBasicTimeTest() {
        chargeDto.setGradBasicMoney(null);
        long ridingSeconds = chargeDto.getBasicTime() * 60 + chargeDto.getExceedTime() + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("2")));
    }

    @Test
    public void whenExceedBasicTimeAndAddTimeTest() {
        chargeDto.setGradBasicMoney(null);
        long ridingSeconds = chargeDto.getBasicTime() * 60 + chargeDto.getAddTime() * 60 * 4 + chargeDto.getExceedTime();
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("5")));
    }

    @Test
    public void whenExceedBasicTimeAndAddTimePlusOneTest() {
        chargeDto.setGradBasicMoney(null);
        long ridingSeconds = chargeDto.getBasicTime() * 60 + chargeDto.getAddTime() * 60 * 4 + chargeDto.getExceedTime() + 1;
        BigDecimal money = chargeService.getMoney(chargeDto, ridingSeconds);
        Assert.assertEquals(0, money.compareTo(new BigDecimal("6")));
    }
}
