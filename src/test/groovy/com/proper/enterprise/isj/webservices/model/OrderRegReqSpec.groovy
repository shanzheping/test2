package com.proper.enterprise.isj.webservices.model

import com.proper.enterprise.isj.webservices.model.enmus.IDCardType
import com.proper.enterprise.isj.webservices.model.enmus.Sex
import com.proper.enterprise.isj.webservices.model.req.OrderRegReq
import spock.lang.Specification

class OrderRegReqSpec extends Specification {

    def "Convert OrderRegReq to map"() {
        def req = new OrderRegReq()
        req.setSex(Sex.MALE)
        req.setIdcardType(IDCardType.DRIVE)
        req.setAddress('NYC')
        def map = req.toParams()

        expect:
        assert map.get('SEX') == Sex.MALE.getCode().toString()
        assert map.get('IDCARD_TYPE') == IDCardType.DRIVE.getCode().toString()
        assert map.get('ADDRESS') == 'NYC'
    }

}
