package com.proper.enterprise.isj.webservices.model

import com.proper.enterprise.isj.webservices.model.enmus.IDCardType
import com.proper.enterprise.isj.webservices.model.enmus.Sex
import spock.lang.Specification

class ReqOrderSpec extends Specification {

    def "Convert RegOrder to map"() {
        def order = new RegOrder()
        order.setSex(Sex.MALE)
        order.setIdcardType(IDCardType.DRIVE)
        order.setAddress('NYC')

        expect:
        println order.toParams()
    }

}
