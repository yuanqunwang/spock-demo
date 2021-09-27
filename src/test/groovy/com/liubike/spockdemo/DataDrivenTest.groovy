package com.liubike.spockdemo


import spock.lang.Specification
/**
 * @author yuanqunwang* created on 2021/9/23 13:28
 */
class DataDrivenTest extends Specification {
    def "max number of two"() {
        expect:
        Math.max(1, 2) == 2
        Math.max(2, 4) == 4
        Math.max(3, 4) == 4
    }

    def "max number of two with data table" () {
        when:
        def c = Math.max(a, b)

        then:
        c == max

        where:
        a | b | max
        1 | 2 | 2
        2 | 4 | 4
        3 | 4 | 4
    }


    def "max number of #a and #b is #max" () {
        when:
        def c = Math.max(a, b)
        then:
        c == max

        where:
        a | b | max
        1 | 2 | 2
        2 | 4 | 4
        3 | 4 | 4
    }

    def "where block with one column" () {
        expect:
        println a

        where:
        a  |  _
        1  |  _
    }

    def "where block using data pipes" (){
        expect:
        println a

        where:
        a << [1, 2, 3, 4]
    }

    def "max number of two using data pipes" (){
        when:
        def c = Math.max(a, b)

        then:
        c == max

        where:
        a << [1, 2, 3]
        b << [2, 1, 2]
        max << [2, 2, 3]
    }


}