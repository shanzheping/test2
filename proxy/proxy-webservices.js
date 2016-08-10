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

var myService = {
  RegSjWebService: {
    RegSjWebServiceSoap: {
      NetTest: function(args) {
        console.log('xml:');
        console.log(args.xml);
        parseString(args.xml, function(err, result) {
          console.log(result.ROOT.FUN_CODE);
        });
        return {
          NetTestResult: resXml('29C7433D6F7C9D6EC8BEF046C6F3B671', 'SQWUfujhjmzuuKE/ZebPtaKNRRn2hCiyNVuw4vpTptmijUUZ9oQosjVbsOL6U6bZJXw65UnnP7sAe9EOtXDqFmTEpLAZOo7QwtIOyZyA8iVZ/o8OSYwPSz9r3vo9hP9Foo1FGfaEKLI1W7Di+lOm2aKNRRn2hCiyNVuw4vpTptmiYHoaJvXEhiM8MHcK9aRZ')
        };
      },
      GetRegInfo: function(args) {
        console.log('xml:');
        console.log(args.xml);
        parseString(args.xml, function(err, result) {
          console.log(result.ROOT.FUN_CODE);
        });
        return {
          GetRegInfoResult: resXml('A234C69D21C6A94CECEFC275CE0F0DB2', 'S+adteu99Z5O04jlOseXl/nf5JLGya0+pljZ8F1U47Rx1EeA25Qf34ngQIwtY4U0OgvfUNGNlhMvUcShdT8UsUEXQo3VsHw/dMMKdoqmebJnWTGam8lbIslhh3z0cQ5jxDTOSugty8ICJLoxAfv0ZgWAeDTum/dCu3shFJNHaL8rRYZcU1LuJ3/kT6YFCyaIcAkLA5NGElqMwDbP2iqZHdeHAJHALJISxTSJPefkAqpOnz/lgrskC3qQ+x4D46PPAuv6Yv94NXAJe0EsXtbBoKo73LAjejqCb3FOUg8smZ9U1InRNwH/9CNXLx4MBv1VuIcSzQIyXShW4DOIITLlFWFESiZBRV4AYjVkj9FT/5UwWK3qvwpe1DhyKYQZjLvu2+odT6IrHJiVk7bSSx05UT4xVEOiKQ0B7PyrFi3koNx5YfKCz9DipMygL06scZsjhszrtd/vd7fVOjEf5iAmVKCnxRaleOXlQyB1WmBpFU9+wAVRim7KmFfHoP/xQE4PCOYRjUe0U5c7Ro6uVQpqMIvV1qT/d+AhdoIile+lix84c6gWfkCf1ybhwj2DM3Wk12d4FofhiM4TFbGDnn/3DDflA/1foZBJVp4a0bwqGagSsp5gEuvNjHFe2yqvYhva/2+IwYGm1BKYmL/0bE0GXuLuokoSptENqvbr+fozIVvGzp5v4JN5uLHnay70O0bsyEHHS1P5xZCT7Yn/WBJHWXYUdKzUN0FsDX/3wy6Z8KQxIQrOGS+cLZvdzPK24eK0AhaKxRBxuw/hKPNcrQnYz8/z2xvkQc0mnXdhGQFjtPt7JtRgth2XcR04KeXhy17i7ToyqF4Msk4Kgldb+OFOyGMEL2CZ83vQWPXY85yeheymz4b6DRSDD2eLS5K6Wu3prmxrHxdZOd4QeCuK/v/1XQhpWBf9KG63i0hj+qi4W8Z5RehN+B2lfES9Bmmwgqn7rp71AYMKiV30RRK6PePP5cTooWNdUuV19vxZiIR4FjsAdTx74PPwbcsL9nbRyxlUpveNtXyeGotiYpE2SLJxxh/fQBiJ1ucp3BVrqwvYHUjCvw8VVuv3Ax0ykXKrg/MxTOSEl3JaOPuFSZrR/kWyBpBZvGaH9Vlz+jr68rlvuen/RflcaX4Cq+R8iDpaSTsk77Oljx+vX55pVVIeTXWDbA==')
        };
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
