package com.liubike.spockdemo.service.impl

import com.liubike.customer.core.common.dto.config.ChargeDto
import spock.lang.Specification


/**
 * @author yuanqunwang* created on 2021/9/24 11:39
 */
class ExceptionTest extends Specification {
    def chargeService = new ChargeServiceImpl()

    def "exception test demo" () {
        when:
        chargeService.getMoney(chargeDto, ridingSecond)
        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == message

        where:
        chargeDto       | ridingSecond || message
        null            | 0            || "chargeDto"
        new ChargeDto() | null         || "ridingSecond"

    }
}