package com.liubike.spockdemo.service.impl

import com.liubike.customer.core.common.dto.config.ChargeDto
import spock.lang.Shared
import spock.lang.Specification

/**
 * @author yuanqunwang* created on 2021/9/23 13:56
 */
class ChargeServiceImplSpockTest extends Specification {

    @Shared
    def gradMoney = [5: 1.0, 25: 1.0, 50: 1.0] as Map<Integer, BigDecimal>
    @Shared
    def chargeDto = new ChargeDto(gradBasicMoney: gradMoney)

    def "gradBasicMoney chargeService.getMoney(chargeDto, #ridingSecond) = #money" () {
        given:
        def chargeService = new ChargeServiceImpl()


        expect:
        chargeService.getMoney(chargeDto as ChargeDto, ridingSecond as Long) == money

        where:
        ridingSecond                                                    || money
        chargeDto.freeTime - 1                                          || 0
        chargeDto.freeTime                                              || 0
        chargeDto.freeTime + 1                                          || 1

        5 * 60 + chargeDto.exceedTime - 1                               || 1
        5 * 60 + chargeDto.exceedTime                                   || 1
        5 * 60 + chargeDto.exceedTime + 1                               || 2

        25 * 60 + chargeDto.exceedTime - 1                              || 2
        25 * 60 + chargeDto.exceedTime                                  || 2
        25 * 60 + chargeDto.exceedTime + 1                              || 3

        50 * 60 + chargeDto.exceedTime - 1                              || 3
        50 * 60 + chargeDto.exceedTime                                  || 3
        50 * 60 + chargeDto.exceedTime + 1                              || 4

        50 * 60 + chargeDto.addTime * 4 * 60 + chargeDto.exceedTime - 1 || 7
        50 * 60 + chargeDto.addTime * 4 * 60 + chargeDto.exceedTime     || 7
        50 * 60 + chargeDto.addTime * 4 * 60 + chargeDto.exceedTime + 1 || 8
    }

    def "legacy basic money chargeService.getMoney(chargeDto, #ridingSecond) = money"() {
        given:
        def chargeService = new ChargeServiceImpl()

        and: "设置个人gradBasicMoney为null, 使用legacy basic money"
        chargeDto.setGradBasicMoney(null)

        expect:
        chargeService.getMoney(chargeDto, ridingSecond) == money

        where:
        ridingSecond                                                                     || money
        chargeDto.freeTime - 1                                                           || 0
        chargeDto.freeTime                                                               || 0
        chargeDto.freeTime + 1                                                           || 1

        chargeDto.basicTime * 60 + chargeDto.exceedTime - 1                              || 1
        chargeDto.basicTime * 60 + chargeDto.exceedTime                                  || 1
        chargeDto.basicTime * 60 + chargeDto.exceedTime + 1                              || 2

        chargeDto.basicTime * 60 + 4 * chargeDto.addTime * 60 + chargeDto.exceedTime - 1 || chargeDto.basicMoney + 4 * chargeDto.addMoney
        chargeDto.basicTime * 60 + 4 * chargeDto.addTime * 60 + chargeDto.exceedTime     || chargeDto.basicMoney + 4 * chargeDto.addMoney
        chargeDto.basicTime * 60 + 4 * chargeDto.addTime * 60 + chargeDto.exceedTime + 1 || chargeDto.basicMoney + 5 * chargeDto.addMoney
    }

}
