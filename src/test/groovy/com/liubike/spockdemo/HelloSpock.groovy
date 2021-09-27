package com.liubike.spockdemo

import spock.lang.Specification


/**
 * @author yuanqunwang* created on 2021/9/23 13:15
 */
class HelloSpock extends Specification {

    //feature
    def helloSpock() {
        expect:
        1 == 1
    }

    //feature
    def "hello spock with string desc" () {
        expect:
        1 ==1
    }

    //feature
    def "spock feature block labels" () {
        given: "def two vars"
        def a = 1
        def b = 2

        when: "find the max of two"
        def c = Math.max(a, b)

        then: "verify result"
        c == 2
    }

    //fixture
    void setupSpec() {
        println "setupSpec runs once"
    }

    //fixture
    void setup() {
        println "setup runs every test"
    }

    //fixture
    void cleanup() {
        println "cleanup runs every test"
    }

    //fixture
    void cleanupSpec() {
        println "cleanupSpec runs once"
    }
}