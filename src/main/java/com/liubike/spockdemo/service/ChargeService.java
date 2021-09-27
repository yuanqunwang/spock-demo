package com.liubike.spockdemo.service;

import com.liubike.customer.core.common.dto.config.ChargeDto;

import java.math.BigDecimal;

/**
 * @author yuanqunwang
 * created on 2021/9/23 13:50
 */
public interface ChargeService {

    BigDecimal getMoney(ChargeDto chargeDto, Long ridingSecond);

}
