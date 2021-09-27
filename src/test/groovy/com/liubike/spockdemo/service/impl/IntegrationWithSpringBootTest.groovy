package com.liubike.spockdemo.service.impl

import com.liubike.customer.core.common.dto.config.ChargeDto
import com.liubike.spockdemo.service.ChargeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.See
import spock.lang.Specification


/**
 * @author yuanqunwang* created on 2021/9/24 11:40
 */
@SpringBootTest
class IntegrationWithSpringBootTest extends Specification {
    @Autowired
    private ChargeService chargeService

    @See("https://www.baeldung.com/spring-spock-testing")
    def "Name"() {
        given:
        def chargeDto = new ChargeDto()

        when:
        def money = chargeService.getMoney(chargeDto, 2200)

        then:
        println money
    }
}