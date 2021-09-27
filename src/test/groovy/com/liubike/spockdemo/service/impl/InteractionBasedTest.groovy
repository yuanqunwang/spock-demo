package com.liubike.spockdemo.service.impl

import com.liubike.core.common.enums.ELegalEntity
import com.liubike.core.tools.ThreadContextUtils
import com.liubike.spockdemo.service.Subscriber
import spock.lang.Specification

/**
 * @author yuanqunwang* created on 2021/9/24 11:28
 */
class InteractionBasedTest extends Specification {
    def pub = new PublisherImpl()

    def "spock mocking behavior test"() {
        given:
        def sub1 = Mock(Subscriber)
        def sub2 = Mock(Subscriber)
        pub.subscriberList = [sub1, sub2]

        when:
        pub.send("hello")

        then:
        1 * sub1.receive({it != "hello"})
        1 * sub2.receive("hello")
    }

    def "spock mocking behavior order test"() {
        given:
        def sub = Mock(Subscriber)
        pub.subscriberList = [sub]

        when:
        pub.send("hello")
        pub.send("goodBye")
        pub.send("hello")

        then:
        2 * sub.receive("hello")
        1 * sub.receive("goodBye")
    }

    def "spock mock object's stubbing behavior test"() {
        given:
        def sub = Mock(Subscriber)
        pub.subscriberList = [sub]

        when:
        pub.send("hello")

        then:
        sub.receive("hello")  >> "ok"
    }

    def "spock mock object's stubbing behavior2 test" () {
        given:
        Subscriber sub = Mock {
            receive("hello") >> "ok"
        }
        pub.subscriberList = [sub]

        when:
        pub.send("hello")

        then:
        sub.receive("hello") == "ok"
    }

    def "spock mock object's stubbing behavior3 test" () {
        given:
        Subscriber sub = Mock {
            receive(_) >>> ["ok", "failed"]
        }
        pub.subscriberList = [sub]

        when:
        pub.send("hello")
        pub.send("hello")

        then:
        sub.receive("hello") == "ok"
        sub.receive("hello") == "failed"
    }

    def "GroovyMock mock static method test"() {
        setup:
        GroovySpy(ThreadContextUtils, global:true)
        ThreadContextUtils.getLegalEntity() >> ELegalEntity.XIAOLIU

        expect:
        ThreadContextUtils.getLegalEntity() == ELegalEntity.XIAOLIU_BJ
    }
}
