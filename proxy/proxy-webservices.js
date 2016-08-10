'use strict';

var http = require('http');
var soap = require('soap');
var parseString = require('xml2js').parseString;

console.log('');
console.log('PEP WebServices Proxy Server');
console.log('');

var resXml = function(sign, resEnc) {
  return '<![CDATA[<?xml version="1.0" encoding="UTF-8"?>'
    + '<ROOT>'
    + '<RETURN_CODE><![CDATA[0]]]]>><![CDATA[</RETURN_CODE>'
    + '<RETURN_MSG><![CDATA[交易成功]]]]>><![CDATA[</RETURN_MSG>'
    + '<SIGN_TYPE><![CDATA[MD5]]]]>><![CDATA[</SIGN_TYPE>'
    + '<SIGN><![CDATA[' + sign + ']]]]>><![CDATA[</SIGN>'
    + '<RES_ENCRYPTED><![CDATA[' + resEnc + ']]]]>><![CDATA[</RES_ENCRYPTED>'
    + '</ROOT>]]>';
};

var commonStep = function(args) {
  console.log('xml:');
  console.log(args.xml);
  parseString(args.xml, function(err, result) {
    console.log(result.ROOT.FUN_CODE);
  });
};

var myService = {
  RegSjWebService: {
    RegSjWebServiceSoap: {
      NetTest: function(args) {
        commonStep(args);
        return {
          NetTestResult: resXml('29C7433D6F7C9D6EC8BEF046C6F3B671', 'SQWUfujhjmzuuKE/ZebPtaKNRRn2hCiyNVuw4vpTptmijUUZ9oQosjVbsOL6U6bZJXw65UnnP7sAe9EOtXDqFmTEpLAZOo7QwtIOyZyA8iVZ/o8OSYwPSz9r3vo9hP9Foo1FGfaEKLI1W7Di+lOm2aKNRRn2hCiyNVuw4vpTptmiYHoaJvXEhiM8MHcK9aRZ')
        };
      },
      GetRegInfo: function(args) {
        commonStep(args);
        return {
          GetRegInfoResult: resXml('A234C69D21C6A94CECEFC275CE0F0DB2', 'S+adteu99Z5O04jlOseXl/nf5JLGya0+pljZ8F1U47Rx1EeA25Qf34ngQIwtY4U0OgvfUNGNlhMvUcShdT8UsUEXQo3VsHw/dMMKdoqmebJnWTGam8lbIslhh3z0cQ5jxDTOSugty8ICJLoxAfv0ZgWAeDTum/dCu3shFJNHaL8rRYZcU1LuJ3/kT6YFCyaIcAkLA5NGElqMwDbP2iqZHdeHAJHALJISxTSJPefkAqpOnz/lgrskC3qQ+x4D46PPAuv6Yv94NXAJe0EsXtbBoKo73LAjejqCb3FOUg8smZ9U1InRNwH/9CNXLx4MBv1VuIcSzQIyXShW4DOIITLlFWFESiZBRV4AYjVkj9FT/5UwWK3qvwpe1DhyKYQZjLvu2+odT6IrHJiVk7bSSx05UT4xVEOiKQ0B7PyrFi3koNx5YfKCz9DipMygL06scZsjhszrtd/vd7fVOjEf5iAmVKCnxRaleOXlQyB1WmBpFU9+wAVRim7KmFfHoP/xQE4PCOYRjUe0U5c7Ro6uVQpqMIvV1qT/d+AhdoIile+lix84c6gWfkCf1ybhwj2DM3Wk12d4FofhiM4TFbGDnn/3DDflA/1foZBJVp4a0bwqGagSsp5gEuvNjHFe2yqvYhva/2+IwYGm1BKYmL/0bE0GXuLuokoSptENqvbr+fozIVvGzp5v4JN5uLHnay70O0bsyEHHS1P5xZCT7Yn/WBJHWXYUdKzUN0FsDX/3wy6Z8KQxIQrOGS+cLZvdzPK24eK0AhaKxRBxuw/hKPNcrQnYz8/z2xvkQc0mnXdhGQFjtPt7JtRgth2XcR04KeXhy17i7ToyqF4Msk4Kgldb+OFOyGMEL2CZ83vQWPXY85yeheymz4b6DRSDD2eLS5K6Wu3prmxrHxdZOd4QeCuK/v/1XQhpWBf9KG63i0hj+qi4W8Z5RehN+B2lfES9Bmmwgqn7rp71AYMKiV30RRK6PePP5cTooWNdUuV19vxZiIR4FjsAdTx74PPwbcsL9nbRyxlUpveNtXyeGotiYpE2SLJxxh/fQBiJ1ucp3BVrqwvYHUjCvw8VVuv3Ax0ykXKrg/MxTOSEl3JaOPuFSZrR/kWyBpBZvGaH9Vlz+jr68rlvuen/RflcaX4Cq+R8iDpaSTsk77Oljx+vX55pVVIeTXWDbA==')
        };
      },
      GetHosInfo: function(args) {
        commonStep(args);
        return {
          GetHosInfoResult: resXml('AD714348376B93E74E235211A033D538', 'Cr/91EhfFgoaz6Mh45iq3x21aBvlwhgF8AKSxgmKyJPTgE9cP+8ar3SEZsnl4GL5rUiKel9/d7T+Fh/NB9rjkANQ75TiKosQ2mbm0m5ZaQAsSOZwNaWgHSEtRshVbbSeJp4PyRU91CvG+Tx7A+xtomDaUlM6lmltsPGCtNlFRbQ2eJ8YquXHjAynQHZ4JxyKp1IZp/WLGDE0lxZ3bjHJLZGOhPPk/NIuV64M4x4xJQ1Lte4NR4wVXrilYFEoJrn7EMly495CKchIzvDCqV7tNnOtgGxoNiFgTK8S/hrQ4KvclGihHjF533sBB53rUvuw9e33I2Q0sn7EuBge9yDsuuod+2c4x/MAB7YpWZ87y+YDBWm2ne0MGF1cpgxAL38lRdjyVKbxCcysjH+QEK6db4ifrEuyVhK0Tg8JuDcQF3OSX2WITwEyIA9+ABoOaUT+aNhSG/fTLdOLOcsl0A2n68AenNDYl6QiBxll3AB1/fXxvQ9FdtpD1V/ivRxhl7O9QzRKAzSvUoEOEP68qM8zEcwtu7rdayA/S9wlq+ghk1PxwUvUl3p3l/3Gnxf19FrLKWFpDJV9YfxAMOhUy1LYyI+Jt/PN0RVu6HuVIutMrYLe7xwHwphj169yNl5GpuEa3xNOvKX8ewx5DM8ZTtxJ1+q7ovP5H9gKi6uwzkkYhpw=')
        }
      },
      GetDeptInfo: function(args) {
        commonStep(args);
        return {
          GetDeptInfoResult: resXml('91C34762E580DAB51CB109409F6CE129', '9U5qC4TnA/oF+2+tGlrHYDx6k7alpZa9Eet6QAWpbITnSezzmfSYsWCRpxxOT77S9kiiQ4CD2jc7r6StnRddEj/lXc5fzoyImbuiAGzg8zrJjKwLnJ8IXgdSqdTMinrj+N9RAbTir8uU9Qtl28mNr5p54kJyILtOppYwdzS/d/cqplJKB9tIZrddqQBLog6tn/vKmRGT7HCnCd/cM+VfxcGsVU7RtchTFR6eIokw6cOvrmqd5pMVkn3nQxXPHFrt97i34TeH8ZitGYrt+U29mQ8s+3EBfxHWiuWZoY9XYkIwC8xm+simL5gKa06xvSxT2u66l/gmOQbGUFk4sowzVEcl/b2QQEMCpCjAbZO8IgCY+NQRJajcHEWNg/XoJpEXfZguo36MqzNjLWVEBH4O5Q==')
        }
      },
      OrderReg: function(args) {
        commonStep(args);
        return {
          OrderRegResult: resXml('77B4C7EBC1F1EDFFFD7100C812C2CA79', 'OgjuLBvA0dBE86sMU9oaCPsPph7mFuO/anOHjSWeX6+beQgJ0fzT9DUCigcfTtH8zKFqCPqaaxwc56E8QPpMuwgFP2KBMO/K8r6bMi2v4QvOi9gkN3RPydNKaKR4mDdHrbxIqhCapknyXNoAoqUJvSC+heYGbeWgxPMSJqwocyF1rjGSFnDehMZbpnQxOhqiN0kwL8uTMuCohbi2ZfSqhf1u1Udq+njzth466HuHx/z8dybAL1CXpVhKXXTDRbv24eiH5a5yXcl3S6rxEDd2+cKPvBJchq0jTyAxftqGcChyiH0f1aeVV57giVc63NkQxHf8UhGAWRuQ8AgI9ff5bFg6a67tMCcEiCLbgoRp9UAiZ6rpdxr6Q42QFAD5TeZDJbOJKoHg2A99TLblrdLqGffi6/SPi/eiD1pRn215qAVh72kQUFbOEj1vXlN45iQ0pJnbVktx/wcHznFXTE8sl3R6D+KQNopVuTA7wx0fCaMFTrxTscGLUgeyengRn45LpuSTvGXjiC5VCJFGCHpzyWyAagrAwTa4k7VyYY8XdZ1DugWPCEMWgAyGzNkManuHL6fs/Ezm5l83SewkmcalO9OQKRWQUTg0mACKbSRiIAfpSrhsVvQQkhcUKQEtjOtBjOS3xN2tTSR/sBzroESYuG6feJAZll5pD81CzQxuJCEtZDXtxoEnXozq9G17kvPte1rCtAJTXcp0DaBaef+RVJ+VviVob1EkMtCUhYw5hP8=')
        }
      }
    }
  }
};

var xml = require('fs').readFileSync('proxy/test.wsdl', 'utf8'),
server = http.createServer(function(request,response) {
  response.end("404: Not Found: " + request.url);
});

server.listen(8080);
soap.listen(server, '/wsdl', myService, xml)
.log = function(type, data) {
  console.log('type: ' + type);
  console.log('data: ' + data);
};
