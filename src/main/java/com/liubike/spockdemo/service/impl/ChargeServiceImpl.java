package com.liubike.spockdemo.service.impl;

import com.liubike.customer.core.common.dto.config.ChargeDto;
import com.liubike.spockdemo.service.ChargeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * @author yuanqunwang
 * created on 2021/9/23 13:50
 */
@Slf4j
@Service
public class ChargeServiceImpl implements ChargeService {

    @Override
    public BigDecimal getMoney(ChargeDto chargeDto, Long ridingSecond) {
        if (chargeDto == null) {
            throw new IllegalArgumentException("chargeDto");
        }
        if (ridingSecond == null || ridingSecond < 0) {
            throw new IllegalArgumentException("ridingSecond");
        }
        BigDecimal money = BigDecimal.ZERO;
        Integer ridingMin = null;

        try {
            chargeDto = Optional.ofNullable(chargeDto).orElse(new ChargeDto());

            // 用户骑行分钟
            ridingMin = new Double(Math.ceil((ridingSecond - chargeDto.getExceedTime()) / 60d)).intValue();

            // 小于1分支按1分钟算
            ridingMin = ridingMin < 1 ? 1 : ridingMin;

            if (ridingSecond > chargeDto.getFreeTime()) {
                if (chargeDto.getIsTime() == 1) {
                    BigDecimal basicMoney = BigDecimal.ZERO;
                    int addTime;
                    if (MapUtils.isNotEmpty(chargeDto.getGradBasicMoney())) {
                        int nextLower;
                        Map.Entry<Integer, BigDecimal> entry;
                        Iterator<Map.Entry<Integer, BigDecimal>> iterator =
                            new TreeMap<>(chargeDto.getGradBasicMoney()).entrySet().iterator();
                        do {
                            entry = iterator.next();
                            nextLower = entry.getKey();
                            basicMoney = basicMoney.add(entry.getValue());
                        } while (iterator.hasNext() && ridingMin > nextLower);

                        // addTime可能小于等于0
                        addTime = ridingMin - nextLower;
                    } else {
                        basicMoney = chargeDto.getBasicMoney();

                        // addTime可能小于等于0
                        addTime = ridingMin - chargeDto.getBasicTime();
                    }

                    // addMoney有可能小于等于0
                    BigDecimal addMoney = BigDecimal.valueOf(Math.ceil(addTime / chargeDto.getAddTime().doubleValue()))
                        .multiply(chargeDto.getAddMoney());
                    money = basicMoney.add(addMoney.compareTo(BigDecimal.ZERO) > 0 ? addMoney : BigDecimal.ZERO);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        log.info("getMoney ridingSecond[{}],ridingMin[{}],money[{}],[{}]", ridingSecond, ridingMin, money, chargeDto);
        return money;
    }
}
